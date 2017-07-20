package com.zhilu.device.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	 * @param tblIotDeviceEntity
	 * @return
	 */
	public static Map<Integer, String> paramJudge(TblIotDevice tblIotDeviceEntity) {
		String userid = tblIotDeviceEntity.getUserid();
		String name = tblIotDeviceEntity.getName();
		String product = tblIotDeviceEntity.getProduct();
		int protocol = tblIotDeviceEntity.getProtocol();
		String devices = tblIotDeviceEntity.getId();
		Map<Integer, String> map = new HashMap<Integer, String>();
		int code = 3002;
		String error;
		// List<String> list = new ArrayList<String>();
		// list.add("error");

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

	// 从用户请求中解析出一组设备ID,
	public static String[] getDevids(String requestBody) {
		// 转化为json对象
		com.alibaba.fastjson.JSONObject paramsJson = JSON.parseObject(requestBody);
		String idsStr = paramsJson.get("devices").toString();
		// 去首尾空格
		idsStr = idsStr.trim();
		// 去除两头中括号
		String newIdsStr = idsStr.substring(1, idsStr.length() - 1);
		// 分割成数组
		String[] idsArray = newIdsStr.split(",");
		return idsArray;
	}

	public static boolean checkToken(String token) {
		RestTemplate restTemplate = new RestTemplate();
		String url = TOKEN_URL + "/check_token?token=" + token;
		Object result = restTemplate.getForObject(url, String.class);

		com.alibaba.fastjson.JSONObject jobj = JSON.parseObject((String) result);
		String code = jobj.get("code").toString();

		boolean flag = false;
		if (code == "0") {
			flag = true;
		}

		return flag;
	}

}
