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
		if (RegexUtil.isNull(user_id)) {
			return false;
		}

		if (RegexUtil.isNotNull(user_id) && !RegexUtil.isDigits(user_id)) {
			return false;
		}

		String code_value = json.getString("code_value");
		System.out.println("code_value：" + code_value);
		if (RegexUtil.isNull(code_value)) {
			return false;
		}

		if (RegexUtil.isNotNull(code_value)
				&& !ParamValidator.isCode(code_value, FieldLimit.DATA_CODE_MIN, FieldLimit.DATA_CODE_MAX)) {
			return false;
		}

		Integer data_type = json.getInteger("data_type");
		System.out.println("data_type：" + data_type);

		if (RegexUtil.isNotNull(data_type) && data_type != 0 && data_type != 1) {
			return false;
		}

		String code_typeStr = json.getString("code_type");
		System.out.println("code_type：" + code_typeStr);
		if (RegexUtil.isNull(code_typeStr)) {
			return false;
		}
		Integer code_type = json.getInteger("code_type");
		if (RegexUtil.isNotNull(code_typeStr) && !FieldLimit.containCkList(FieldLimit.CODE_TYPE_ARR, code_type)) {
			return false;
		}

		System.out.println("验证通过");
		return true;
	}

	public static boolean dataPermiBatchVal(JSONObject json) {
		String user_id = json.getString("userid");
		System.out.println("userid：" + user_id);
		if (RegexUtil.isNull(user_id)) {
			return false;
		}

		if (RegexUtil.isNotNull(user_id) && !RegexUtil.isDigits(user_id)) {
			return false;
		}

		String data = json.getString("data");
		System.out.println("data：" + data);
		if (RegexUtil.isNull(data)) {
			return false;
		}

		System.out.println("验证通过");
		return true;
	}

	public static boolean dataPermiUpdateVal(JSONObject json) {
		String id = json.getString("id");
		System.out.println("id：" + id);
		if (RegexUtil.isNull(id)) {
			return false;
		}

		if (RegexUtil.isNotNull(id) && !RegexUtil.isDigits(id)) {
			return false;
		}

		String user_id = json.getString("user_id");
		System.out.println("user_id：" + user_id);
		if (RegexUtil.isNull(user_id)) {
			return false;
		}

		if (RegexUtil.isNotNull(user_id) && !RegexUtil.isDigits(user_id)) {
			return false;
		}

		String code_value = json.getString("code_value");
		System.out.println("code_value：" + code_value);
		if (RegexUtil.isNotNull(code_value)
				&& !ParamValidator.isCode(code_value, FieldLimit.DATA_CODE_MIN, FieldLimit.DATA_CODE_MAX)) {
			return false;
		}

		String data_type = json.getString("data_type");
		System.out.println("data_type：" + data_type);

		if (RegexUtil.isNotNull(data_type) && !data_type.equals("0") && !data_type.equals("1")) {
			return false;
		}

		Integer code_type = json.getInteger("code_type");
		System.out.println("code_type：" + code_type);
		if (RegexUtil.isNotNull(code_type) && !FieldLimit.containCkList(FieldLimit.CODE_TYPE_ARR, code_type)) {
			return false;
		}

		System.out.println("验证通过");
		return true;
	}

	public static boolean dataPermiDelVal(JSONObject json) {
		String typeStr = json.getString("type");
		System.out.println("type：" + typeStr);
		if (RegexUtil.isNull(typeStr)) {
			return false;
		}

		Integer type = json.getInteger("type");
		if (RegexUtil.isNotNull(typeStr) && !FieldLimit.containCkList(FieldLimit.SEARCH_TYPE_ARR, type)) {
			return false;
		}

		String user_id = json.getString("user_id");
		System.out.println("user_id：" + user_id);
		if (RegexUtil.isNotNull(user_id) && !RegexUtil.isDigits(user_id)) {
			return false;
		}

		String id = json.getString("id");
		System.out.println("id：" + id);
		if (RegexUtil.isNotNull(id) && !RegexUtil.isDigits(id)) {
			return false;
		}

		if (RegexUtil.isNotNull(id)
				&& !ParamValidator.isStrLength(id, FieldLimit.DATA_ID_MIN, FieldLimit.DATA_ID_MAX)) {
			return false;
		}

		System.out.println("验证通过");
		return true;
	}

	public static boolean dataQueryVal(JSONObject json) {
		String type = json.getString("type");
		System.out.println("type：" + type);
		if (RegexUtil.isNull(type))
			return false;

		if (RegexUtil.isNotNull(type) && !type.equals("0") && !type.equals("1") && !type.equals("2")) {
			return false;
		}    
		
		String search = json.getString("search");
		System.out.println("search：" + search);		
		if (RegexUtil.isNotNull(search) && (type.equals("1")||type.equals("2"))&& !RegexUtil.isDigits(search)) {
			return false;
		}

		String page = json.getString("page");
		String listRows = json.getString("listRows");
		if (!PubParamValidator.pageVal(page, listRows))
			return false;

		System.out.println("验证通过");
		return true;
	}
	
	public static boolean dataPermiQueryVal(JSONObject json) {
		String user_id = json.getString("user_id");
		System.out.println("user_id：" + user_id);
		if (RegexUtil.isNull(user_id))
			return false;
		
		if (RegexUtil.isNotNull(user_id) && !RegexUtil.isDigits(user_id)) {
			return false;
		}

		System.out.println("验证通过");
		return true;
	}
	
}
