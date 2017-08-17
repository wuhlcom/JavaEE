package com.dazk.common.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;



public abstract class ExcelReader extends DefaultHandler {
	
	/** 共享字符串表 */
	private SharedStringsTable sst;

	/** 上一次的内容 */
	private String lastContents;

	/** 字符串标识 */
	private boolean nextIsString;

	/** 工作表索引 */
	private int sheetIndex = -1;

	/** 行集合 */
	private List<String> rowlist = new ArrayList<String>();

	/** 当前行 */
	private int curRow = 0;

	/** 当前列 */
	private int curCol = 0;

	/** T元素标识 */
	private boolean isTElement;

	/** 异常信息，如果为空则表示没有异常 */
	private String exceptionMessage;

	/** 单元格数据类型，默认为字符串类型 */
	private CellDataType nextDataType = CellDataType.SSTINDEX;

	/** 封装第一行的标题信息，防止空单元格 */
	private List<String> title = new ArrayList<String>();

	/** 单元格 */
	private StylesTable stylesTable;

	/** 记录当前检索到的单元格信息 */
	private String curTitle;

	private final DataFormatter formatter = new DataFormatter();

	private short formatIndex;

	private String formatString;

	private Map<String, Object> data;

	/**
	 * 处理每一行数据，可以根据业务需要封装成业务实体<rowList与title一一对应，否则请检查<BR>
	 * 增加data参数，表明从EXCEL外部引入的数据。
	 */
	public abstract void optRow(int sheetIndex, int curRow, List<String> rowList, List<String> title, Map<String, Object> data)throws Exception;

	/**
	 * 处理完EXCEL数据后的钩子。
	 */
	public void callback() {

	};

	/**
	 * 遍历工作簿中所有的电子表格
	 * @throws Exception 
	 */
	public void process(String filename, Map<String, Object> data) throws Exception {
		this.data = Collections.unmodifiableMap(data);// 设置data数据
		OPCPackage pkg = null;
		try {
			pkg = OPCPackage.open(filename);
			reader(pkg);
		} catch (Exception e) {
//			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (pkg != null) {
					pkg.close();
				}
			} catch (IOException e) {
//				e.printStackTrace();
				throw e;
			} finally {
				pkg = null;
			}
		}
	}

	public void process(InputStream in, Map<String, Object> data) throws Exception {
		this.data = Collections.unmodifiableMap(data);// 设置data数据
		OPCPackage pkg = null;
		try {
			pkg = OPCPackage.open(in);
			reader(pkg);
		} catch (Exception e) {
//			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (pkg != null) {
					pkg.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
//				e.printStackTrace();
				throw e;
			} finally {
				pkg = null;
				in = null;
			}
		}
	}

	public void process(MultipartFile file, Map<String, Object> data) throws Exception {
		this.data = Collections.unmodifiableMap(data);// 设置data数据
		OPCPackage pkg = null;
		InputStream in = null;
		try {
			in = file.getInputStream();
			pkg = OPCPackage.open(in);
			reader(pkg);
		} catch (Exception e) {
//			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (pkg != null) {
					pkg.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
//				e.printStackTrace();
				throw e;
			} finally {
				pkg = null;
				in = null;
			}
		}
	}

	private void reader(OPCPackage pkg) throws Exception {
		XSSFReader xssfReader = null;
		SharedStringsTable sst = null;
		XMLReader parser = null;
		Iterator<InputStream> sheets = null;
		try {
			xssfReader = new XSSFReader(pkg);
			stylesTable = xssfReader.getStylesTable();
			sst = xssfReader.getSharedStringsTable();
			parser = this.fetchSheetParser(sst);
			sheets = xssfReader.getSheetsData();
			while (sheets.hasNext()) {
				curRow = 0;
				sheetIndex++;
				InputStream sheet = sheets.next();
				try {
					InputSource sheetSource = new InputSource(sheet);
					parser.parse(sheetSource);
				} catch (Exception e) {
//					e.printStackTrace();
					throw e;
				} finally {
					if (sheet != null) {
						sheet.close();
					}
					sheet = null;
				}
			}
		} catch (IOException e) {
//			e.printStackTrace();
			throw e;
		} catch (OpenXML4JException e) {
//			e.printStackTrace();
			throw e;
		} catch (SAXException e) {
//			e.printStackTrace();
			throw e;
		} finally {
			// 终结操作
			callback();
		}
	}

	/** 处理数据信息，进行解析 */
	public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader();
		this.sst = sst;
		parser.setContentHandler(this);
		return parser;
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		// c => 单元格 cv的交替出现。。。
		if ("c".equals(name)) {
			// 设定单元格类型
			this.setNextDataType(attributes);
		}

		// 当元素为t时
		if ("t".equals(name)) {
			isTElement = true;
		} else {
			isTElement = false;
		}

		// 封装title（属性日不为空并且是以字母开头的（大写字母，暂时不必须）。）
		String type = attributes.getValue("r");
		if (StringUtils.isNotEmpty(type) && hasLetter(type)) {
			curTitle = type;
		}

		// 置空
		lastContents = "";
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		// 根据SST的索引值的到单元格的真正要存储的字符串；这时characters()方法可能会被调用多次
		if (nextIsString) {
			int idx = Integer.parseInt(lastContents);
			lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
		}
		// t元素也包含字符串
		if (isTElement) {
			// 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
			String value = lastContents.trim();
			rowlist.add(curCol, value);
			curCol++;
			isTElement = false;
			// 处理title信息
			title.add(curTitle);
		} else if ("v".equals(name)) {
			// v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
			String value = this.getDataValue(lastContents.trim(), "");
			rowlist.add(curCol, value);
			curCol++;
			// 处理title信息
			title.add(curTitle);
		} else if ("row".equals(name)) {
			// 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
			try {
				// 一行信息结束时进行业务逻辑处理（重点关注对象）
				optRow(sheetIndex, curRow, rowlist, title, data);
				// 重置当前行标题信息
				title = new ArrayList<String>();
			} catch (Exception e) {
				e.printStackTrace();
				// 检查插入数据的时候打开！！！
				// System.exit(0);
			}
			rowlist.clear();
			curRow++;
			curCol = 0;
		}
	}

	/**
	 * 将为空的单元内容读取出来（上面方法无法取到空单元格，所以人工处理一下）
	 * @param rowList
	 * @param title
	 */
	public List<String[]> changeList(List<String> rowList, List<String> title) {
		List<String[]> result = new ArrayList<String[]>();
		for (int i = 0; i < rowList.size(); i++) {
			result.add(new String[] { title.get(i), rowList.get(i) });
		}
		return result;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// 得到单元格内容的值
		lastContents += new String(ch, start, length);
	}

	/**
	 * @return the exceptionMessage
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	/**
	 * 单元格中的数据可能的数据类型
	 */
	enum CellDataType {
		BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER, DATE, NULL
	}

	/**
	 * 对解析出来的数据进行类型处理
	 */
	public String getDataValue(String value, String thisStr) {
		switch (nextDataType) {
		// 这几个的顺序不能随便交换，交换了很可能会导致数据错误
		case BOOL:
			char first = value.charAt(0);
			thisStr = first == '0' ? "FALSE" : "TRUE";
			break;
		case ERROR:
			thisStr = "\"ERROR:" + value.toString() + '"';
			break;
		case FORMULA:
			thisStr = '"' + value.toString() + '"';
			break;
		case INLINESTR:
			XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
			thisStr = rtsi.toString();
			rtsi = null;
			break;
		case SSTINDEX:
			String sstIndex = value.toString();
			try {
				int idx = Integer.parseInt(sstIndex);
				XSSFRichTextString rtss = new XSSFRichTextString(sst.getEntryAt(idx));
				thisStr = rtss.toString();
				rtss = null;
			} catch (NumberFormatException ex) {
				thisStr = value.toString();
			}
			break;
		case NUMBER:
			if (formatString != null) {
				thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString).trim();
			} else {
				thisStr = value;
			}
			thisStr = thisStr.replace("_", "").trim();
			break;
		case DATE:
			thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString);
			// 对日期字符串作特殊处理
			thisStr = thisStr.replace(" ", "T");
			break;
		default:
			thisStr = " ";
			break;
		}
		return thisStr;
	}

	/**
	 * 处理数据类型
	 */
	public void setNextDataType(Attributes attributes) {
		nextDataType = CellDataType.NUMBER;
		formatIndex = -1;
		formatString = null;
		String cellType = attributes.getValue("t");
		String cellStyleStr = attributes.getValue("s");
		if ("b".equals(cellType)) {
			nextDataType = CellDataType.BOOL;
		} else if ("e".equals(cellType)) {
			nextDataType = CellDataType.ERROR;
		} else if ("inlineStr".equals(cellType)) {
			nextDataType = CellDataType.INLINESTR;
		} else if ("s".equals(cellType)) {
			nextDataType = CellDataType.SSTINDEX;
		} else if ("str".equals(cellType)) {
			nextDataType = CellDataType.FORMULA;
		} else if (StringUtils.isNotEmpty(cellStyleStr)) {
			int styleIndex = Integer.parseInt(cellStyleStr);
			XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
			formatIndex = style.getDataFormat();
			formatString = style.getDataFormatString();
			//自定义格式yyyy年m月d日 formatIndex=31,formatString=reserved-0x1f,value=reserved-42215x1f
			//自定义格式yyyy/m/d   formatIndex=14,formatString=m/d/yy,value=6/30/16
			//日期格式yyyy年m月d日 formatIndex =177,formatString= [$-F800]dddd\,\ mmmm\ dd\,\ yyyy,value=星期四, 六月 30, 2016
			if(formatIndex == 31 && "reserved-0x1f".equals(formatString)){
				formatString = "m/d/yy";
			}
			if (formatString == null) {
				nextDataType = CellDataType.NULL;
				formatString = BuiltinFormats.getBuiltinFormat(formatIndex);
			}
		}
	}

	// 每一组1000记录集，一次插入数据库。
	protected static final int GROUP_SIZE = 1000;

	/** 提供一个抽象方法，将结果集进行分组。 **/
	public List<List<String[]>> group(List<String[]> data) {
		List<List<String[]>> result = new ArrayList<List<String[]>>();
		int record = 0;
		List<String[]> temp = new ArrayList<String[]>();
		for (int i = 0; i < data.size(); i++) {
			if (i % GROUP_SIZE < GROUP_SIZE) {
				temp.add(data.get(i));
				record++;
			}
			if (record % GROUP_SIZE == 0) {
				result.add(temp);
				temp = new ArrayList<String[]>();
				record = 0;
			}
		}
		if (record != 0) {
			result.add(temp);
		}
		return result;
	}

	// 判断是否包含字母。。。true：存在字母
	protected boolean hasLetter(String string) {
		return StringUtils.isNotBlank(filterAlphabet(string));
	}

	// 把字符串中的数字过滤掉
	protected String filterAlphabet(String string) {
		return string.replaceAll("[0-9]", "");
	}

	/** 反射处理对应关系 
	 * @throws Exception */
	protected <T> void generation(T t, String name, String value){
		Class<?> clazz = t.getClass();
		Method[] methods = clazz.getMethods();
		Method method = null;
		String paramName = null;
		for (int i = 0; i < methods.length; i++) {
			method = methods[i];
			if (StringUtils.isNotEmpty(name) && StringUtils.equals("set".concat(StringUtils.capitalize(name)), method.getName())) {
				try {
					Class<?>[] params = method.getParameterTypes();
					// set只存在一个参数的
					for (Class<?> cl : params) {
						paramName = cl.getName();
					}
					System.out.println("paramName="+paramName );
					// 处理参数调用处理
					if (StringUtils.equals("java.lang.String", paramName)) {
						method.invoke(t, value);
					} else if (StringUtils.equals("java.lang.Long", paramName)) {
						method.invoke(t, Long.valueOf(value));
					}else if (StringUtils.equals("java.lang.Double", paramName)) {
						method.invoke(t, Double.valueOf(value));
					} else if (StringUtils.equals("java.lang.Integer", paramName)) {
						method.invoke(t, Integer.valueOf(value));
					}
					break;
				} catch (IllegalArgumentException e) {
					
				} catch (IllegalAccessException e) {
					
				} catch (InvocationTargetException e) {
					

				}
			}
		}
	}

	/** 反射处理对应关系日期大于2037-12-31 
	 * @throws Exception */
	protected <T> void generationDate(T t, String name, String value) throws Exception {
		Class<?> clazz = t.getClass();
		Method[] methods = clazz.getMethods();
		Method method = null;
		String paramName = null;
		for (int i = 0; i < methods.length; i++) {
			method = methods[i];
			if (StringUtils.isNotEmpty(name) && StringUtils.equals("set".concat(StringUtils.capitalize(name)), method.getName())) {
				try {
					Class<?>[] params = method.getParameterTypes();
					// set只存在一个参数的
					for (Class<?> cl : params) {
						paramName = cl.getName();
					}
					// 处理参数调用处理
					if (StringUtils.equals("java.lang.String", paramName)) {
						method.invoke(t, value);
					} else if (StringUtils.equals("java.lang.Long", paramName)) {
						method.invoke(t, Long.valueOf(value));
					}else if (StringUtils.equals("java.lang.Double", paramName)) {
						method.invoke(t, Double.valueOf(value));
					} else if (StringUtils.equals("java.lang.Integer", paramName)) {
						method.invoke(t, Integer.valueOf(value));
					}
					break;
				} catch (IllegalArgumentException e) {
					throw e;
				} catch (IllegalAccessException e) {
					throw e;
				} catch (InvocationTargetException e) {
					throw e;
				} finally {

				}
			}
		}
	}
	
}