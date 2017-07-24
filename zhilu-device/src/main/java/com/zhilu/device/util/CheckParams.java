/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月21日 上午11:51:13 * 
*/
package com.zhilu.device.util;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Matchers.booleanThat;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.zhilu.device.bean.TblIotDevice;
import com.zhilu.device.bean.TblIotDeviceBasic;
import com.zhilu.device.bean.TblIotDeviceDyn;
import com.zhilu.device.bean.TblIotProductInfo;
import com.zhilu.device.bean.TblIotUser;
import com.zhilu.device.service.TbIotProductSrv;
import com.zhilu.device.service.TblIotDevBasicSrv;
import com.zhilu.device.service.TblIotDevDynSrv;
import com.zhilu.device.service.TblIotDevSrv;
import com.zhilu.device.service.TblIotUsrSrv;

@Component
public class CheckParams {

	final static int[] PROTOCOL = { 0, 1, 2, 3 };
	final static int[] TYPE = { 0, 1, 2, 3, 4, 5 };
	final static String TOKEN_URL = "http://119.29.68.198:9080/index.php/Users";
	final static String USER_ID = "userid";
	final static String NAME = "name";
	final static String PRODUCT = "product";
	final static String PROTOCOL_STR = "protocol";
	final static int ID_LENGTH = 16;

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
	public static Result checkAdd(String requestBody) {
		// 获取请求参数转为JsonObject
		com.alibaba.fastjson.JSONObject paramsJson = JSON.parseObject(requestBody);
		Result resultMsg = null;
		// 通过json解析参数
		String userid = paramsJson.get(USER_ID).toString();

		// 用户id为空不添加
		if (isUidNull(userid) == true) {
			resultMsg = new ResultErr(ResultStatusCode.UID_EMP.getCode(), ResultStatusCode.UID_EMP.getErrmsg());
			return resultMsg;
		}
		// 输入不合法不添加
		if (isUid(userid) == false) {
			resultMsg = new ResultErr(ResultStatusCode.UID_ERR.getCode(), ResultStatusCode.UID_ERR.getErrmsg());
			return resultMsg;
		}

		// 用户ID不存在不添加
		if (isUidAdd(userid) == false) {
			resultMsg = new ResultErr(ResultStatusCode.UID_NOT_EXISTED.getCode(),
					ResultStatusCode.UID_NOT_EXISTED.getErrmsg());
			return resultMsg;
		}
		// 未检测合法性
		String name = paramsJson.get(NAME).toString();

		String productId = paramsJson.get(PRODUCT).toString();
		if (isProductIdNull(productId) == true) {
			resultMsg = new ResultErr(ResultStatusCode.PROID_EMP.getCode(), ResultStatusCode.PROID_EMP.getErrmsg());
			return resultMsg;
		}

		if (isProductIdErr(productId) == true) {
			resultMsg = new ResultErr(ResultStatusCode.PROID_FORMAT_ERR.getCode(),
					ResultStatusCode.PROID_FORMAT_ERR.getErrmsg());
			return resultMsg;
		}

		// id不存在不添加
		if (isProductIdAdd(productId) == false) {
			resultMsg = new ResultErr(ResultStatusCode.PROID_NOT_EXISTED.getCode(),
					ResultStatusCode.PROID_NOT_EXISTED.getErrmsg());
			return resultMsg;
		}

		// 协议不正确不添加
		int protocol = Integer.parseInt(paramsJson.get(PROTOCOL_STR).toString());
		if (isProtocol(protocol) == false) {
			resultMsg = new ResultErr(ResultStatusCode.PROTOCOL_ERR.getCode(),
					ResultStatusCode.PROTOCOL_ERR.getErrmsg());
		}

		// 得到Id数组
		String[] macsArray = PubMethod.getDevids(requestBody);
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
		return resultMsg;
	}

	// id可能是mac imei id,检查输入是否合法
	public static Result checkDelete(String userid, String id) {
		Result resultMsg = null;
		// 用户ID输入不合法
		if (isUid(userid) == false) {
			resultMsg = new ResultErr(ResultStatusCode.UID_EMP.getCode(), ResultStatusCode.UID_EMP.getErrmsg());
			return resultMsg;
		}

		// 用户ID不存在
		if (isUidAdd(userid) == false) {
			resultMsg = new ResultErr(ResultStatusCode.UID_NOT_EXISTED.getCode(),
					ResultStatusCode.UID_NOT_EXISTED.getErrmsg());
			return resultMsg;
		}

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
	
	public static boolean isNull(Object obj){
		boolean rs= false;
		if(obj==null){
			rs =true;
		}
		return rs;
	}
	
	public static boolean isStrNull(String obj){	
		boolean rs= false;
		if(obj==null||obj.length()<=0){
			rs =true;
		}
		return rs;
	}

	// 删除设备后查询是否删除
	public static Result checkDelResult(String id) {
		Result rs = null;
		List<TblIotDevice> rs1 = tblIotDevSrv.findById(id);
		List<TblIotDeviceBasic> rs2 = tblIotDevBasicSrv.findById(id);
		List<TblIotDeviceDyn> rs3 = tblIotDevDynSrv.findById(id);
		boolean flag1 = ((rs1 == null) || rs1.isEmpty());
		boolean flag2 = ((rs1 == null) || rs1.isEmpty());
		boolean flag3 = ((rs1 == null) || rs1.isEmpty());
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

		List<TblIotDevice> devs = tblIotDevSrv.getDevByMac(id);// byMac
		System.out.println("((((((((((((((((((((isIdAdd))))))))))))))))))))");
		System.out.println(devs);

		if (devs == null || devs.isEmpty()) {
			rs = false;
		}
		System.out.println(rs);
		System.out.println("((((((((((((((((((((isIdAdd))))))))))))))))))))");
		return rs;
	}

	/**
	 * 判断协议是否确,正确返回true
	 */
	public static boolean isProtocol(int protocol) {
		boolean isExist = false;
		for (int i = 0; i < PROTOCOL.length; i++) {
			if (PROTOCOL[i] == protocol) {
				isExist = true;
			}
		}
		return isExist;
	}

	/**
	 * 判断查询类型是否正确,正确返回true
	 */
	public static boolean isType(int type) {
		boolean isExist = false;
		for (int i = 0; i < TYPE.length; i++) {
			if (TYPE[i] == type) {
				isExist = true;
			}
		}
		return isExist;
	}

	/**
	 * 判断页码类型是否正确，正确返回true
	 */
	public static boolean isInt(Object... args) {
		boolean rs = false;
		if (args[0] instanceof Integer || args[0] instanceof Long)
			rs = true;
		return rs;
	}

	// 为pid为Null返回true
	public static boolean isProductIdNull(String productId) {
		boolean rs = false;
		if (productId == null || productId.length() <= 0) {
			rs = true;
		}
		return rs;
	}

	// pid是错误的返回true
	public static boolean isProductIdErr(String productId) {
		boolean rs = false;
		if ((productId.length() != ID_LENGTH)) {
			rs = true;
		}
		return rs;
	}

	// pid已经添加返回true
	public static boolean isProductIdAdd(String productId) {
		boolean rs = true;
		List<TblIotProductInfo> products = tblIotProSrv.findProductById(productId);
		if ((products == null) || products.isEmpty()) {
			rs = false;
		}
		return rs;
	}

	// 判断Uid输入是否合法,合法返回true
	public static boolean isUidNull(String userid) {
		boolean rs = false;
		if (userid.length() <= 0 && userid == null) {
			rs = true;
		}
		return rs;
	}

	// 判断Uid输入是否合法,合法返回true
	public static boolean isUid(String userid) {
		boolean rs = false;
		if (userid.length() == 64) {
			rs = true;
		}
		return rs;
	}

	// 判断是否已经添加UID,存在返回true
	private static boolean isUidAdd(String userid) {
		boolean rs = false;
		List<TblIotUser> users = tblIotUsrSrv.findUserByUserid(userid);
		if (!(users == null) && !users.isEmpty()) {
			rs = true;
		}
		return rs;
	}

	// 从用户请求中解析出一组设备ID或mac或其它唯一标识,
	public static String[] getDevids(String requestBody) {
		// 转化为json对象
		com.alibaba.fastjson.JSONObject paramsJson = JSON.parseObject(requestBody);
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
		Boolean flag = false;
		if ((token == "") || (token.length() <= 0)) {
			flag = null;
		}
		String url = TOKEN_URL + "/check_token?token=" + token;
		RestTemplate restTemplate = new RestTemplate();

		Object result = restTemplate.getForObject(url, String.class);
		com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject((String) result);
		String code = jsonObj.get("code").toString();

		if (code == "0") {
			flag = true;
		}
		return flag;
	}

}
