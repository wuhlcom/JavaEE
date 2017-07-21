package com.zhilu.device.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.zhilu.device.bean.TblIotDevice;

public class PubMethod {
	final static int[] PROTOCOL = { 0, 1, 2, 3 };
	final static String TOKEN_URL = "http://119.29.68.198:9080/index.php/Users";

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
	 * 判断添加设备参数是否正确
	 * 
	 * @param tblIotDev
	 * @return
	 */
	public static Map<Integer, String> paramJudge(TblIotDevice tblIotDev) {
		String userid = tblIotDev.getUserid();
		String name = tblIotDev.getName();
		String product = tblIotDev.getProduct();
		int protocol = tblIotDev.getProtocol();
		String devices = tblIotDev.getId();
		Map<Integer, String> map = new HashMap<Integer, String>();
		int code = 30002;
		String error;	

		if (userid == null || userid.length() <= 0) {
			error = "userid error, userid:" + userid;
			map.put(code, error);
		} else if (name == null || name.length() <= 0) {
			error = "name error, name:" + name;
			map.put(code, error);
		} else if (product == null || product.length() <= 0) {
			error = "product error,product:" + product;
			map.put(code, error);
		} else {
			boolean b = PubMethod.isProtocol(protocol);
			if (!b) {
				error = "protocol error,protocol:" + protocol;
				map.put(code, error);
			} else if (devices == null || devices.length() <= 0) {
				error = "id error,id:" + devices;
				map.put(code, error);
			}
		}
		;
		return map;
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
		while(it.hasNext()) {
			TblIotDevice dev = it.next();
			String devId = dev.getId();					
			listDevIds.add(devId);
		}		
		return listDevIds;
	}

	// 从用户请求中解析出一组设备ID,
	public static String[] getDevids(String requestBody) {
		// 转化为json对象
		com.alibaba.fastjson.JSONObject paramsJson = JSON.parseObject(requestBody);
		String idsStr = paramsJson.get("devices").toString();
		// 去首尾空格
		idsStr = idsStr.trim();
		String newIdsStr =idsStr;
		// 去除两头中括号
		if ((idsStr.charAt(0) == '[') && (idsStr.charAt(idsStr.length() - 1) == ']')) {
			newIdsStr = idsStr.substring(1, idsStr.length() - 1);
		} else if (idsStr.charAt(0) == '[') {
			newIdsStr = idsStr.substring(1);
		} else if (idsStr.charAt(idsStr.length() - 1) == ']') {
			newIdsStr = idsStr.substring(0,idsStr.length()-1);
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

	// String转化为Timestamp:
	public static Timestamp str2timestamp() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		Timestamp ts = Timestamp.valueOf(time);
		return ts;
	}

	//生成id
	public static String generateDevId(int strLen) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		java.util.Random random = new java.util.Random();
		StringBuffer sb = new StringBuffer();
		sb.append("whl");
		for (int i = 0; i < strLen; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString().toLowerCase();
	}

	//去除首尾"
	public static String removeQuto(String str) {
		String newStr = str;
		if ((str.charAt(0) == '\"') && (str.charAt(str.length() - 1) == '\"')) {
			newStr = str.substring(1, str.length() - 1);
		} else if (str.charAt(0) == '\"') {
			newStr = str.substring(1);
		} else if (str.charAt(str.length() - 1) == '\"') {
			newStr = str.substring(0,str.length()-1);
		} else {
		}
		return newStr;
	}

}
