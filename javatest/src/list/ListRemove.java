/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年9月13日 下午9:21:28 * 
*/
package list;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListRemove {

	public static List<String> geList() {
		List<String> dataLs = new ArrayList<>();
		// dataLs.add("000");
		dataLs.add("001");
		dataLs.add("001002");
		dataLs.add("00100202");
		dataLs.add("002");
		dataLs.add("002003");
		dataLs.add("003");
		dataLs.add("004006");
		dataLs.add("00400650");
		System.out.println(dataLs);
		return dataLs;
	}

	public static List<String> typeList() {
		List<String> dataLs = geList();
		List<String> ls01 = new ArrayList<>();
		List<String> ls02 = new ArrayList<>();
		List<String> ls03 = new ArrayList<>();
		for (String string : dataLs) {
			if (string.length() == 3) {
				ls01.add(string);
			} else if (string.length() == 6) {
				ls02.add(string);
			} else if (string.length() == 8) {
				ls03.add(string);
			}
		}

		for (String ls1 : ls01) {
			for (String ls2 : ls02) {			
				boolean rs = strStartWith(ls2, ls1);			
				if (rs) {
					dataLs.remove(ls1);
				}

			}
		}

		for (String ls2 : ls02) {
			for (String ls3 : ls03) {	
				boolean rs = strStartWith(ls3, ls2);
				if (rs) {
					dataLs.remove(ls2);
				}
			}
		}
		System.out.println(dataLs);
		return dataLs;
	}

	public static boolean matches(String content, String reg) {
		String pattern = "^" + reg;
		System.out.println(pattern);
		boolean isMatch = Pattern.matches(pattern, content);
		return isMatch;
	}

	public static boolean stringContians(String a, String b) {
		if (a.indexOf(b) != -1) {		
			return true;
		} else {
			
			return false;
		}
	}
	
	public static boolean strStartWith(String content,String reg){
		boolean rs =content.startsWith(reg);
		return rs;
	}

	public static void main(String[] args) {
		typeList();
				
	}
}
