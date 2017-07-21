/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月21日 上午11:51:13 * 
*/
package com.zhilu.device.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.zhilu.device.bean.TblIotDevice;
import com.zhilu.device.bean.TblIotProductInfo;
import com.zhilu.device.bean.TblIotUser;
import com.zhilu.device.service.TbIotProductSrv;
import com.zhilu.device.service.TblIotUsrSrv;

@Component
public class CheckParams {

	final static int[] PROTOCOL = { 0, 1, 2, 3 };
	final static String TOKEN_URL = "http://119.29.68.198:9080/index.php/Users";
	final static String USER_ID = "userid";
	final static String NAME = "name";
	final static String PRODUCT = "product";
	final static String PROTOCOL_STR = "protocol";

	private static TblIotUsrSrv tblIotUsrSrv;
	private static TbIotProductSrv tblIotProSrv;

	public CheckParams() {
	}

	// 无法直接给静态方法private static TblIotUsrSrv tblIotUsrSrv;
	// 将Autowire添加到构造方法配合Compnent达到实现注解静态方法的目的
	//但只能给一个构造函数加Autowired
	@Autowired
	public CheckParams(TblIotUsrSrv tblIotUsrSrv) {
		CheckParams.tblIotUsrSrv = tblIotUsrSrv;
	}

	//无法使用
	// @Autowired
	// public CheckParams(TbIotProductSrv tblIotProSrv) {
	// this.tblIotProSrv = tblIotProSrv;
	// }
	//
	
	 @Autowired
	    private TbIotProductSrv tblIotProSrv2;
	    
	    @PostConstruct
	    public void beforeInit() {
	    	tblIotProSrv = tblIotProSrv2;
	    }
	    


	/**
	 * 判断添加设备参数是否正确
	 * 
	 * @param paramsJson
	 * @return
	 */
	public static Result checkAdd(String requestBody) {
		// 获取请求参数转为JsonObject
		com.alibaba.fastjson.JSONObject paramsJson = JSON.parseObject(requestBody);

		// 通过json解析参数
		String userid = paramsJson.get(USER_ID).toString();
		// 未检测合法性
		String name = paramsJson.get(NAME).toString();
		String product = paramsJson.get(PRODUCT).toString();
		int protocol = Integer.parseInt(paramsJson.get(PROTOCOL_STR).toString());

		// 得到Id数组
		String[] macsArray = PubMethod.getDevids(requestBody);

		Result check = null;
		// 返回值为null表示输入有效,
		// 不为null,return返回值
		check = checkUserid(userid);
		if (check != null) {
			return check;
		}

		check = checkIds(macsArray);
		if (check != null) {
			return check;
		}

		check = checkProId(product);
		if (check != null) {
			return check;
		}

		check = checkProtocol(protocol);
		if (check != null) {
			return check;
		}
		return check;
	}

	/**
	 * @Title: checkIds @Description: TODO 只检测了设备ID或mac或IEMI是否为空 @param @param
	 *         macsArray @return void @throws
	 */
	private static Result checkIds(String[] macsArray) {
		Result resultMsg = null;
		if ((macsArray == null) || (macsArray.length == 0)) {
			resultMsg = new ResultDevAdd(ResultStatusCode.DEVID_EMP.getCode(), ResultStatusCode.DEVID_EMP.getErrmsg());
		}
		return resultMsg;
	}

	/**
	 * 判断协议是否确
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
	 * @Title: checkProtocol @Description: TODO @param @param protocol @return
	 *         Result @throws
	 */
	private static Result checkProtocol(int protocol) {
		Result resultMsg = null;
		boolean b = isProtocol(protocol);
		if (!b) {
			resultMsg = new ResultDevAdd(ResultStatusCode.UID_EMP.getCode(), ResultStatusCode.PROTOCOL_ERR.getErrmsg());
		}
		return resultMsg;
	}

	/**
	 * @Title: checkProId @Description: TODO @param @param product @return
	 *         void @throws
	 */
	private static Result checkProId(String productId) {
		Result resultMsg = null;
		if (productId == null || productId.length() <= 0) {
			resultMsg = new ResultDevAdd(ResultStatusCode.PROID_EMP.getCode(), ResultStatusCode.PROID_EMP.getErrmsg());
		} else {
			if ((productId.length() != 16)) {
				resultMsg = new ResultDevAdd(ResultStatusCode.PROID_FORMAT_ERR.getCode(),
						ResultStatusCode.PROID_FORMAT_ERR.getErrmsg());
			}
		}

		// 如果返回为空或null表示找不到产品,表明产品Id错误
		if (resultMsg == null) {
			List<TblIotProductInfo> products = tblIotProSrv.findProductById(productId);
			if ((products == null) || products.isEmpty()) {
				resultMsg = new ResultDevAdd(ResultStatusCode.PROID_NOT_EXISTED.getCode(),
						ResultStatusCode.PROID_NOT_EXISTED.getErrmsg());
			}
		}
		return resultMsg;

	}

	/**
	 * @Title: checkUserid
	 * @Description: TODO
	 * @param userid
	 */
	private static Result checkUserid(String userid) {
		Result resultMsg = null;
		if (userid == null || userid.length() <= 0) {
			resultMsg = new ResultDevAdd(ResultStatusCode.UID_EMP.getCode(), ResultStatusCode.UID_EMP.getErrmsg());
		} else {
			List<TblIotUser> users = tblIotUsrSrv.findUserByUserid(userid);
			// 如果返回为空或null表示找不到用户,则返回错误
			if ((users == null) || users.isEmpty()) {
				resultMsg = new ResultDevAdd(ResultStatusCode.UID_NOT_EXISTED.getCode(),
						ResultStatusCode.UID_NOT_EXISTED.getErrmsg());
			}
		}
		return resultMsg;
	}

	/**
	 * @param tblIotDevice
	 * @return 返回设备Id 从TblIotDevice对象中获取设备Id
	 */
	public static ArrayList<String> getDevId(TblIotDevice tblIotDevice) {
		String devId = tblIotDevice.getId();
		ArrayList<String> listDevIds = new ArrayList<>();
		listDevIds.add(devId);
		return listDevIds;
	}

	public static ArrayList<String> getDevId(List<TblIotDevice> devs) {
		ArrayList<String> listDevIds = new ArrayList<>();
		java.util.Iterator<TblIotDevice> it = devs.iterator();
		while (it.hasNext()) {
			TblIotDevice dev = it.next();
			String devId = dev.getId();
			listDevIds.add(devId);
		}
		return listDevIds;
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
	public static Boolean checkToken(String token) {
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
