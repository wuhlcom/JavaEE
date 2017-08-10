/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月21日 上午11:51:13 * 
*/
package com.zhilu.device.util.validator;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhilu.device.bean.primary.TblIotDevice;
import com.zhilu.device.bean.primary.TblIotDeviceBasic;
import com.zhilu.device.bean.primary.TblIotDeviceDyn;
import com.zhilu.device.bean.primary.TblIotProductInfo;
import com.zhilu.device.bean.primary.TblIotUser;
import com.zhilu.device.service.primary.TbIotProductSrv;
import com.zhilu.device.service.primary.TblIotDevBasicSrv;
import com.zhilu.device.service.primary.TblIotDevDynSrv;
import com.zhilu.device.service.primary.TblIotDevSrv;
import com.zhilu.device.service.primary.TblIotUsrSrv;
import com.zhilu.device.util.PubMethod;
import com.zhilu.device.util.errorcode.Result;
import com.zhilu.device.util.errorcode.ResultErr;
import com.zhilu.device.util.errorcode.ResultStatusCode;

@Component
public class CheckParams {

	final static int[] DEV_PROTOCOL = { 0, 1, 2, 3, 4 };
	final static int[] QUERY_TYPE = { 0, 1, 2, 3, 4, 5 };
	final static String TOKEN_URL = "http://119.29.68.198:9080/index.php/Users";
	final static String USER_ID = "userid";
	final static String NAME = "name";
	final static String TYPE = "type";
	final static String PAGE = "page";
	final static String LIST_ROWS = "listRows";
	final static String DEV_ID = "devid";
	final static String SEARCH = "search";
	final static String PRODUCT = "productid";
	final static String PROTOCOL_STR = "protocol";

	private static TblIotUsrSrv tblIotUsrSrv;
	private static TbIotProductSrv tblIotProSrv;
	private static TblIotDevSrv tblIotDevSrv;
	private static TblIotDevBasicSrv tblIotDevBasicSrv;
	private static TblIotDevDynSrv tblIotDevDynSrv;

	public CheckParams() {
	}

	// 无法直接给静态方法private static TblIotUsrSrv tblIotUsrSrv;
	// 将Autowire添加到构造方法配合Compnent达到实现注解静态方法的目的
	// 但只能给一个构造函数加Autowired
	@Autowired
	public CheckParams(TblIotUsrSrv tblIotUsrSrv) {
		CheckParams.tblIotUsrSrv = tblIotUsrSrv;
	}

	// 无法使用
	// @Autowired
	// public CheckParams(TbIotProductSrv tblIotProSrv) {
	// this.tblIotProSrv = tblIotProSrv;
	// }
	//

	// @autowired+postConsturc结合实现多个srv注解
	@Autowired
	private TbIotProductSrv tblIotProSrv2;
	@Autowired
	private TblIotDevSrv tblIotDevSrv2;
	@Autowired
	private TblIotDevBasicSrv tblIotDevBasicSrv2;
	@Autowired
	private TblIotDevDynSrv tblIotDevDynSrv2;

	@PostConstruct
	public void beforeInit() {
		tblIotProSrv = tblIotProSrv2;
		tblIotDevSrv = tblIotDevSrv2;
		tblIotDevBasicSrv = tblIotDevBasicSrv2;
		tblIotDevDynSrv = tblIotDevDynSrv2;
	}

	/**
	 * 判断添加设备参数是否正确以及设备是否已经添加
	 * 
	 * @param paramsJson
	 * @return
	 */
	public static Result checkAdd(JSONObject paramsJson) {	
		
		Result resultMsg = null;
		Integer type = paramsJson.getInteger("type");
		System.out.println("type:"+type);
		
		//添加的类型设备类型是否正确
		if(!FieldLimit.containCkList(FieldLimit.DEV_TYPE,type)){
			return new ResultErr(ResultStatusCode.DEVTYPE_ERR.getCode(), ResultStatusCode.DEVTYPE_ERR.getErrmsg());
		}
		
		// 通过json解析参数
		String userid = paramsJson.getString(USER_ID);

		// 用户id为空不添加
		if (isUidNull(userid) == true) {
			return new ResultErr(ResultStatusCode.UID_EMP.getCode(), ResultStatusCode.UID_EMP.getErrmsg());
		}
		// 输入不合法不添加
		if (isUid(userid) == false) {
			return new ResultErr(ResultStatusCode.UID_ERR.getCode(), ResultStatusCode.UID_ERR.getErrmsg());
		}

		// // 用户ID不存在不添加
		// if (isUidAdd(userid) == false) {
		// resultMsg = new ResultErr(ResultStatusCode.UID_NOT_EXISTED.getCode(),
		// ResultStatusCode.UID_NOT_EXISTED.getErrmsg());
		// return resultMsg;
		// }

		// 设备名称
		String name = paramsJson.getString(NAME);
		System.out.println("name:"+name);
		
		if (RegexUtil.isNull(name)) {
			return new ResultErr(ResultStatusCode.DEVNAME_ERR.getCode(), ResultStatusCode.DEVNAME_ERR.getErrmsg());
		}

		String productId = paramsJson.getString(PRODUCT);
		System.out.println("productId:"+productId);
		
		if (isProductIdNull(productId) == true) {
			resultMsg = new ResultErr(ResultStatusCode.PROID_EMP.getCode(), ResultStatusCode.PROID_EMP.getErrmsg());
			return resultMsg;
		}

		if (isProductIdErr(productId) == true) {
			resultMsg = new ResultErr(ResultStatusCode.PROID_FORMAT_ERR.getCode(),
					ResultStatusCode.PROID_FORMAT_ERR.getErrmsg());
			return resultMsg;
		}

		// // 产品id不存在不添加
		// if (isProductIdAdd(productId) == false) {
		// resultMsg = new
		// ResultErr(ResultStatusCode.PROID_NOT_EXISTED.getCode(),
		// ResultStatusCode.PROID_NOT_EXISTED.getErrmsg());
		// return resultMsg;
		// }

		// 协议不正确不添加
		Integer protocol = paramsJson.getInteger(PROTOCOL_STR);
		System.out.println("protocol:"+protocol);
		if (isProtocol(protocol) == false) {
			resultMsg = new ResultErr(ResultStatusCode.PROTOCOL_ERR.getCode(),
					ResultStatusCode.PROTOCOL_ERR.getErrmsg());
		}

		// 得到Id数组
		String[] macsArray = PubMethod.getDevids(paramsJson);
		if (isIds(macsArray) == false) {
			resultMsg = new ResultErr(ResultStatusCode.DEVID_EMP.getCode(), ResultStatusCode.DEVID_EMP.getErrmsg());
		}

		for (String mac : macsArray) {
			if (isIdNull(mac) == true) {
				resultMsg = new ResultErr(ResultStatusCode.DEVID_EMP.getCode(), ResultStatusCode.DEVID_EMP.getErrmsg());
				return resultMsg;
			}
			if (isIdAdd(mac) == true) {
				resultMsg = new ResultErr(ResultStatusCode.DEVID_EXISTED.getCode(),
						ResultStatusCode.DEVID_EXISTED.getErrmsg());
			}
		}
		System.out.println("参数校验PASS");
		return resultMsg;
	}

	// id可能是mac imei id,检查输入是否合法
	public static Result checkDelete(JSONObject paramsJson) {
		Result resultMsg = null;
		String userid = paramsJson.get(USER_ID).toString();
		String id = paramsJson.get(DEV_ID).toString();
		// 用户ID输入不合法
		if (isUid(userid) == false) {
			resultMsg = new ResultErr(ResultStatusCode.UID_EMP.getCode(), ResultStatusCode.UID_EMP.getErrmsg());
			return resultMsg;
		}

		// 用户ID不存在
		// if (isUidAdd(userid) == false) {
		// resultMsg = new ResultErr(ResultStatusCode.UID_NOT_EXISTED.getCode(),
		// ResultStatusCode.UID_NOT_EXISTED.getErrmsg());
		// return resultMsg;
		// }

		// 设备ID是否为空
		if (isIdNull(id) == true) {
			resultMsg = new ResultErr(ResultStatusCode.DEVID_EMP.getCode(), ResultStatusCode.DEVID_EMP.getErrmsg());
			return resultMsg;
		}

		// 设备ID不存在不需要删除
		if (isIdAdd(id) == false) {
			resultMsg = new ResultErr(ResultStatusCode.DEVID_NOT_EXISTED.getCode(),
					ResultStatusCode.DEVID_NOT_EXISTED.getErrmsg());
			return resultMsg;
		}
		return resultMsg;
	}

	public static Result checkQuery(JSONObject paramsJson) {
		Result resultMsg = null;
		String userid = paramsJson.get(USER_ID).toString();
		int type = Integer.parseInt(paramsJson.get(TYPE).toString());
		String search = paramsJson.get(SEARCH).toString();
		Long page = Long.parseLong(paramsJson.get(PAGE).toString());
		Long rows = Long.parseLong(paramsJson.get(LIST_ROWS).toString());
		// 用户ID输入不合法
		if (isUid(userid) == false) {
			resultMsg = new ResultErr(ResultStatusCode.UID_EMP.getCode(), ResultStatusCode.UID_EMP.getErrmsg());
			return resultMsg;
		}

		// // 用户ID不存在
		// if (isUidAdd(userid) == false) {
		// resultMsg = new ResultErr(ResultStatusCode.UID_NOT_EXISTED.getCode(),
		// ResultStatusCode.UID_NOT_EXISTED.getErrmsg());
		// return resultMsg;
		// }

		// type类型限制
		if (isQueryType(type) == false) {
			resultMsg = new ResultErr(ResultStatusCode.TYPE_ERR.getCode(), ResultStatusCode.TYPE_ERR.getErrmsg());
			return resultMsg;
		}

		if (isIntLong(page) == false) {
			resultMsg = new ResultErr(ResultStatusCode.PAGE_ERR.getCode(), ResultStatusCode.PAGE_ERR.getErrmsg());
			return resultMsg;
		}

		if (isIntLong(rows) == false) {
			resultMsg = new ResultErr(ResultStatusCode.ROWS_ERR.getCode(), ResultStatusCode.ROWS_ERR.getErrmsg());

		}

		// search内容检查待补充
		if (type == 0) {
			// 所有

		} else if (type == 1) {
			// name

		} else if (type == 2) {
			// id
			if (isIdNull(search) == false) {
				resultMsg = new ResultErr(ResultStatusCode.DEVID_EMP.getCode(), ResultStatusCode.DEVID_EMP.getErrmsg());
				return resultMsg;
			}
		} else if (type == 3) {
			// product

		} else if (type == 4) {
			// groupid

		} else if (type == 5) {
			// mac

		} else {
		}
		return resultMsg;
	}

	public static Result checkOnlineStatus(JSONObject paramsJson) {
		Result resultMsg = null;
		String devmac = paramsJson.get("device_id").toString();
		Long time = Long.parseLong(paramsJson.get("time").toString());
		Integer onlineStatus = Integer.parseInt(paramsJson.get("onlineStatus").toString());

		if (isIdNull(devmac) == true) {
			resultMsg = new ResultErr(ResultStatusCode.DEVID_EMP.getCode(), ResultStatusCode.DEVID_EMP.getErrmsg());
			return resultMsg;
		}

		if (isIdAdd(devmac) == false) {
			resultMsg = new ResultErr(ResultStatusCode.DEVID_NOT_EXISTED.getCode(),
					ResultStatusCode.DEVID_NOT_EXISTED.getErrmsg());
			return resultMsg;
		}
		return resultMsg;
	}

	public static boolean isNull(Object obj) {
		if (obj != null) {
			return false;
		}
		return true;
	}

	public static boolean isStrNull(String obj) {
		boolean rs = false;
		if (obj == null || obj.length() <= 0) {
			rs = true;
		}
		return rs;
	}

	// 删除设备后查询是否删除
	public static Result checkDelResult(String id) {
		Result rs = null;
		TblIotDevice rs1 = tblIotDevSrv.findById(id);
		TblIotDeviceBasic rs2 = tblIotDevBasicSrv.findById(id);
		TblIotDeviceDyn rs3 = tblIotDevDynSrv.findById(id);
		boolean flag1 = (rs1 == null);
		boolean flag2 = (rs2 == null);
		boolean flag3 = (rs3 == null);
		if (!(flag1 && flag2 && flag3)) {
			rs = new ResultErr(ResultStatusCode.DEVID_EXISTED.getCode(), ResultStatusCode.DEVID_EXISTED.getErrmsg());
		}
		return rs;
	}

	public static boolean isIds(String[] ids) {
		boolean rs = true;
		if ((ids == null) || (ids.length == 0)) {
			rs = false;
		}
		return rs;
	}

	// id mac imei为null返回true
	public static boolean isIdNull(String id) {
		boolean rs = false;
		// if ((id == null) || (id.length() != 16)) {
		if ((id == null) || id.length() <= 0) {
			rs = true;
		}
		return rs;
	}

	// id mac imei已添加返回true
	public static boolean isIdAdd(String id) {
		boolean rs = true;
		TblIotDevice devs = tblIotDevSrv.getDevByMac(id);// byMac
		if (devs == null) {
			rs = false;
		}

		return rs;
	}

	/**
	 * 判断协议是否确,正确返回true
	 */
	public static boolean isProtocol(int protocol) {
		boolean isExist = false;
		for (int i = 0; i < DEV_PROTOCOL.length; i++) {
			if (DEV_PROTOCOL[i] == protocol) {
				isExist = true;
			}
		}
		return isExist;
	}

	/**
	 * 判断查询类型是否正确,正确返回true
	 */
	public static boolean isQueryType(int type) {
		boolean isExist = false;
		for (int i = 0; i < QUERY_TYPE.length; i++) {
			if (QUERY_TYPE[i] == type) {
				isExist = true;
			}
		}
		return isExist;
	}

	/**
	 * 判断页码类型是否正确，正确返回true
	 */
	public static boolean isIntLong(Object... args) {
		boolean rs = false;
		if (args[0] instanceof Integer || args[0] instanceof Long)
			rs = true;
		return rs;
	}

	// 为pid为Null返回true
	public static boolean isProductIdNull(String productId) {
		if (productId == null || productId.length() <= 0) {
			return true;
		}
		return false;
	}

	// pid是错误的返回true
	public static boolean isProductIdErr(String productId) {
		if ((productId.length() > FieldLimit.PRO_ID_MAX) || productId.length() < FieldLimit.PRO_ID_MIN) {
			return true;
		}
		return false;
	}

	// pid已经添加返回true
	public static boolean isProductIdAdd(String productId) {
		List<TblIotProductInfo> products = tblIotProSrv.findProductById(productId);
		if ((products == null) || products.isEmpty()) {
			return false;
		}
		return true;
	}

	// 判断Uid输入是否合法,合法返回true
	public static boolean isUidNull(String userid) {
		boolean rs = false;
		if (userid.length() <= 0 || (userid == null)) {
			rs = true;
		}
		return rs;
	}

	// 判断Uid输入是否合法,合法返回true
	public static boolean isUid(String userid) {
		if (userid.length() > 64) {
			return false;
		}
		return true;
	}

	// 判断是否已经添加UID,存在返回true
	private static boolean isUidAdd(String userid) {
		List<TblIotUser> users = tblIotUsrSrv.findUserByUserid(userid);
		if (users == null || users.isEmpty()) {
			return false;
		}
		return true;
	}

	// 从用户请求中解析出一组设备ID或mac或其它唯一标识,
	public static String[] getDevids(String requestBody) {
		// 转化为json对象
		JSONObject paramsJson = JSON.parseObject(requestBody);
		String idsStr = paramsJson.get("devices").toString();
		// 去首尾空格
		idsStr = idsStr.trim();
		String newIdsStr = idsStr;
		// 去除两头中括号
		if ((idsStr.charAt(0) == '[') && (idsStr.charAt(idsStr.length() - 1) == ']')) {
			newIdsStr = idsStr.substring(1, idsStr.length() - 1);
		} else if (idsStr.charAt(0) == '[') {
			newIdsStr = idsStr.substring(1);
		} else if (idsStr.charAt(idsStr.length() - 1) == ']') {
			newIdsStr = idsStr.substring(0, idsStr.length() - 1);
		} else {
		}
		// 分割成数组
		String[] idsArray = newIdsStr.split(",");
		return idsArray;
	}

	// 转发token到token验证服务器
	public static Boolean isToken(String token) {	
		// lora服务器使用固定token
		String fixToken = "zhilutyui150219547342f1b20258f8374835226fe";
		if (token == fixToken) {
			return true;
		}

		if ((token == "") || (token.length() <= 0)) {
			return null;
		}

		String url = TOKEN_URL + "/check_token?token=" + token;
		RestTemplate restTemplate = new RestTemplate();

		String result = restTemplate.getForObject(url, String.class);
		JSONObject jsonObj = JSON.parseObject(result);
		System.out.println(jsonObj);
		Integer code = jsonObj.getInteger("code");
		if (code == 0) {
			return true;
		}
		return false;
	}

}
