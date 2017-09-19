package com.zhilu.device.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class PubMethod {
	// 从用户请求中解析出一组设备ID或mac或其它唯一标识,
	public static List<String> getDevids(JSONObject paramsJson) {
		JSONArray deveuis = paramsJson.getJSONArray("devices");
		List<String> euiList = new ArrayList<>();
		// 判断设备是否添加
		for (Object deveui : deveuis) {
			String eui = deveui.toString();
			euiList.add(eui);
		}
		return euiList;
	}

	// 从用户请求中解析出一组设备ID或mac或其它唯一标识,
	public static String[] getDevidsOld(JSONObject paramsJson) {

		String[] idsArray = { "" };
		// 转化为json对象
		String idsStr = paramsJson.getString("devices");
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
		System.out.println("getDevids------2--------");
		System.out.println(newIdsStr);
		// 分割成数组
		if (newIdsStr.indexOf(",") > 0) {
			idsArray = newIdsStr.split(",");
		} else {
			idsArray[0] = newIdsStr;
		}
		;
		return idsArray;
	}

	// String转化为Timestamp:
	public static Timestamp str2timestamp() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		Timestamp ts = Timestamp.valueOf(time);
		return ts;
	}

	/*
	 * 将时间转换为时间戳
	 */
	public static String date2Stamp(String s) throws ParseException {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = simpleDateFormat.parse(s);
		long ts = date.getTime();
		res = String.valueOf(ts);
		return res;
	}

	/*
	 * 将时间戳转换为时间
	 */
	public static String stamp2Date(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	// 生成id
	public static String generateDevId(int strLen) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		java.util.Random random = new java.util.Random();
		StringBuffer sb = new StringBuffer();
		String prefix = "whl";
		sb.append(prefix);
		for (int i = 0; i < strLen - prefix.length(); i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString().toLowerCase();
	}

	// 生成uuid
	public static String generateUuid(int strLen) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMOPQRST";
		java.util.Random random = new java.util.Random();
		StringBuffer sb = new StringBuffer();
		String prefix = "whl";
		sb.append(prefix);
		for (int i = 0; i < strLen - prefix.length(); i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	// 去除首尾"
	public static String removeQuto(String str) {
		String newStr = str;
		if ((str.charAt(0) == '\"') && (str.charAt(str.length() - 1) == '\"')) {
			newStr = str.substring(1, str.length() - 1);
		} else if (str.charAt(0) == '\"') {
			newStr = str.substring(1);
		} else if (str.charAt(str.length() - 1) == '\"') {
			newStr = str.substring(0, str.length() - 1);
		} else {
		}
		return newStr;
	}

}
