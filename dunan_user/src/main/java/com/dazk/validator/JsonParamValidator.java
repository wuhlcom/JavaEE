package com.dazk.validator;

import com.alibaba.fastjson.JSONObject;

import com.dazk.common.util.ParamValidator;
import com.dazk.common.util.RegexUtil;

public class JsonParamValidator {
	public static final int house_code_len = 16;

	public static final int MENU_NAME_MAX = 16;
	public static final int URI_LEN_MIN = 2;
	public static final int URI_LEN_MAX = 45;
	public static final int CODE_LEN_MIN = 2;
	public static final int CODE_LEN_MAX = 20;

	public static final int building_unique_code_len = 10;

	public static final int concentrator_code_len = 7;

	public static boolean menuVal(JSONObject json) {
		String name = json.getString("name");
		System.out.println("name：" + name);
		if (!isMenuName(name)) {
			return false;
		}

		String type = json.getString("type");
		System.out.println("type：" + type);
		if (type != null && !RegexUtil.isDigits(type)) {
			return false;
		}
		String parent = json.getString("parent");
		System.out.println("parent：" + parent);
		if (parent != null && !RegexUtil.isDigits(parent)) {
			return false;
		}

		String uri = json.getString("uri");
		System.out.println("uri：" + uri);
		isUriParam(uri);

		String code = json.getString("code");
		System.out.println("code：" + code);
		if(!isCode(code)){
			return false;
		}

		System.out.println("验证通过");
		return true;
	}

	// type
	// search
	// page
	// listRows
	public static boolean menuQueryVal(JSONObject json) {	
		String type = json.getString("type");
		System.out.println("type：" + type);
		if (type != null && !RegexUtil.isDigits(type)) {
			return false;
		}

		if (type.matches("1")) {
			String search = json.getString("search");
			System.out.println("search：" + search);
			if (type != null && !RegexUtil.isDigits(search)) {
				return false;
			}
			
			if(!isCode(search)){
				return false;
			}
		}

		String page = json.getString("page");
		System.out.println("period：" + page);
		if (page != null && !RegexUtil.isDigits(page)) {		
			return false;
		}
		
		String listRows = json.getString("listRows");
		if (listRows != null && !RegexUtil.isDigits(listRows)) {		
			return false;
		}

		System.out.println("验证通过");
		return true;
	}

	// 菜单是否合法
	public static boolean isMenuName(String str) {
		if (RegexUtil.isNotNull(str)) {
			if (!ParamValidator.isStrLength(str, 1, MENU_NAME_MAX) | RegexUtil.isContainsSpecialChar(str)) {
				return false;
			}
			if (RegexUtil.isChinese(str)) {
				return true;
			}
		}
		return true;
	}

	/**
	 * Uri是否合法
	 */
	private static boolean isUriParam(String uri) {
		if (RegexUtil.isNotNull(uri) && (!RegexUtil.isUri(uri))) {
			return false;
		}
		if (!ParamValidator.isStrLength(uri, URI_LEN_MIN, URI_LEN_MAX))
			return false;
		return true;
	}

	/**
	 * @Title: isCode @Description: TODO @param @param code @return
	 *         boolean @throws
	 */
	public static boolean isCode(String code) {
		if (RegexUtil.isNotNull(code) && ParamValidator.isStrLength(code, CODE_LEN_MIN, CODE_LEN_MAX)) {
			return true;
		}
		return false;
	}

	// 是否为楼栋唯一编号
	public static boolean isBuildingUniqueCode(String str) {
		if (str != null) {
			return ParamValidator.isDecNum(str, building_unique_code_len, building_unique_code_len);
		}
		return false;
	}

	// 是否为采集器编号
	public static boolean isConcentratorCode(String str) {
		if (str != null) {
			return ParamValidator.isDecNum(str, concentrator_code_len, concentrator_code_len);
		}
		return false;
	}

	// 是否为通信地址
	public static boolean isCommAddress(String str) {
		if (str != null) {
			return ParamValidator.isHexNum(str, 0, 32);
		}
		return false;
	}

}
