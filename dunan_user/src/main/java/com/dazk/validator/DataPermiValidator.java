/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月31日 下午4:12:40 * 
*/
package com.dazk.validator;

import com.alibaba.fastjson.JSONObject;
import com.dazk.common.util.ParamValidator;
import com.dazk.common.util.RegexUtil;

public class DataPermiValidator {

	public static boolean dataPermiVal(JSONObject json) {
		String user_id = json.getString("user_id");
		System.out.println("user_id：" + user_id);
		if (user_id == null) {
			return false;
		}

		if (RegexUtil.isNotNull(user_id) && !RegexUtil.isDigits(user_id)) {
			return false;
		}

		String codeValue = json.getString("codeValue");
		System.out.println("codeValue：" + codeValue);
		if (codeValue == null) {
			return false;
		}

		if (!ParamValidator.isCode(codeValue, FieldLimit.DATA_CODE_MIN, FieldLimit.DATA_CODE_MIN)) {
			return false;
		}

		Integer dataType = json.getInteger("dataType");
		System.out.println("dataType：" + dataType);
		if (dataType == null) {
			return false;
		}

		Integer dataTypeInt = json.getInteger("dataType");
		if (RegexUtil.isNotNull(dataType) && dataTypeInt != 0 && dataTypeInt != 1) {
			return false;
		}

		Integer codeType = json.getInteger("codeType");
		System.out.println("codeType：" + codeType);
		if (codeType == null) {
			return false;
		}

		if (!FieldLimit.containCkList(FieldLimit.CODE_TYPE_ARR, codeType)) {
			return false;
		}

		System.out.println("验证通过");
		return true;
	}
	
	
	public static boolean dataPermiUpdateVal(JSONObject json) {
		String id = json.getString("id");
		System.out.println("id：" + id);
		if (id == null) {
			return false;
		}
		
		if (RegexUtil.isNotNull(id) && !RegexUtil.isDigits(id)) {
			return false;
		}
		
		String user_id = json.getString("user_id");
		System.out.println("user_id：" + user_id);
		if (user_id == null) {
			return false;
		}

		if (RegexUtil.isNotNull(user_id) && !RegexUtil.isDigits(user_id)) {
			return false;
		}

		String codeValue = json.getString("codeValue");
		System.out.println("codeValue：" + codeValue);		
		if (RegexUtil.isNotNull(codeValue) && !ParamValidator.isCode(codeValue, FieldLimit.DATA_CODE_MIN, FieldLimit.DATA_CODE_MIN)) {
			return false;
		}

		Integer dataType = json.getInteger("dataType");
		System.out.println("dataType：" + dataType);	

		Integer dataTypeInt = json.getInteger("dataType");
		if (RegexUtil.isNotNull(dataType) && dataTypeInt != 0 && dataTypeInt != 1) {
			return false;
		}

		Integer codeType = json.getInteger("codeType");
		System.out.println("codeType：" + codeType);		
		if (RegexUtil.isNotNull(codeType)&&!FieldLimit.containCkList(FieldLimit.CODE_TYPE_ARR, codeType)) {
			return false;
		}

		System.out.println("验证通过");
		return true;
	}

	public static boolean dataPermiDelVal(JSONObject json) {
		Integer type = json.getInteger("type");
		System.out.println("type：" + type);
		if (type == null) {
			return false;
		}

		if (!FieldLimit.containCkList(FieldLimit.SEARCH_TYPE_ARR, type)) {
			return false;
		}

		String user_id = json.getString("user_id");
		System.out.println("user_id：" + user_id);
		if (RegexUtil.isNotNull(user_id) && RegexUtil.isDigits(user_id)) {
			return false;
		}

		String id = json.getString("id");
		System.out.println("id：" + id);
		if (RegexUtil.isNotNull(id) && RegexUtil.isDigits(id)) {
			return false;
		}

		if (!ParamValidator.isStrLength(id, FieldLimit.DATA_ID_MIN, FieldLimit.DATA_ID_MAX)) {
			return false;
		}		

		System.out.println("验证通过");
		return true;
	}

	public static boolean dataPermiQueryVal(JSONObject json) {
		Integer type = json.getInteger("type");
		System.out.println("type：" + type);
		if (type == null) {
			return false;
		}

		if (!FieldLimit.containCkList(FieldLimit.DATA_SEARCH_TYPE_ARR, type)) {
			return false;
		}
		
		String user_id = json.getString("user_id");
		System.out.println("user_id：" + user_id);
		if (RegexUtil.isNotNull(user_id) && RegexUtil.isDigits(user_id)) {
			return false;
		}

		String id = json.getString("id");
		System.out.println("id：" + id);
		if (RegexUtil.isNotNull(id) && RegexUtil.isDigits(id)) {
			return false;
		}		

		if (!ParamValidator.isStrLength(id, FieldLimit.DATA_ID_MIN, FieldLimit.DATA_ID_MAX)) {
			return false;
		}
		

		String page = json.getString("page");
		System.out.println("page：" + page);
		if (page != null && !RegexUtil.isDigits(page)) {
			return false;
		}

		if (page != null && json.getInteger("page") == 0) {
			return false;
		}

		String listRows = json.getString("listRows");
		if (listRows != null && !RegexUtil.isDigits(listRows)) {
			return false;
		}

		
		System.out.println("验证通过");
		return true;
	}
}
