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

		String code_value = json.getString("code_value");
		System.out.println("code_value：" + code_value);
		if (code_value == null) {
			return false;
		}

		if (!ParamValidator.isCode(code_value, FieldLimit.DATA_CODE_MIN, FieldLimit.DATA_CODE_MIN)) {
			return false;
		}

		Integer data_type = json.getInteger("data_type");
		System.out.println("data_type：" + data_type);
		if (data_type == null) {
			return false;
		}

		Integer data_typeInt = json.getInteger("data_type");
		if (RegexUtil.isNotNull(data_type) && data_typeInt != 0 && data_typeInt != 1) {
			return false;
		}

		Integer code_type = json.getInteger("code_type");
		System.out.println("code_type：" + code_type);
		if (code_type == null) {
			return false;
		}

		if (!FieldLimit.containCkList(FieldLimit.CODE_TYPE_ARR, code_type)) {
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

		String code_value = json.getString("code_value");
		System.out.println("code_value：" + code_value);		
		if (RegexUtil.isNotNull(code_value) && !ParamValidator.isCode(code_value, FieldLimit.DATA_CODE_MIN, FieldLimit.DATA_CODE_MIN)) {
			return false;
		}

		Integer data_type = json.getInteger("data_type");
		System.out.println("data_type：" + data_type);	

		Integer data_typeInt = json.getInteger("data_type");
		if (RegexUtil.isNotNull(data_type) && data_typeInt != 0 && data_typeInt != 1) {
			return false;
		}

		Integer code_type = json.getInteger("code_type");
		System.out.println("code_type：" + code_type);		
		if (RegexUtil.isNotNull(code_type)&&!FieldLimit.containCkList(FieldLimit.CODE_TYPE_ARR, code_type)) {
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
