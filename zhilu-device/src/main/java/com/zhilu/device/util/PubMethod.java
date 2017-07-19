package com.zhilu.device.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.zhilu.device.bean.TblIotDevice;

public class PubMethod {
	final static int[] PROTOCOL = { 0, 1, 2, 3 };

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
	 * @param tblIotDeviceEntity
	 * @return   返回设备Id
	 */
	public static ArrayList<String> getDevId(TblIotDevice tblIotDeviceEntity) {
		String devId = tblIotDeviceEntity.getId();
		ArrayList<String> listDevIds = new ArrayList<>();
		listDevIds.add(devId);
		return listDevIds;
	}

	

}
