/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月31日 下午4:53:55 * 
*/ 
package com.dazk.validator;

import com.alibaba.fastjson.JSONObject;
import com.dazk.common.util.ParamValidator;
import com.dazk.common.util.RegexUtil;

public class MenuValidator {
	/**
	 * 添加菜单时校验参数
	 * 
	 * @Title: menuVal @Description: TODO @param @param
	 *         json @param @return @return boolean @throws
	 */
	public static boolean menuVal(JSONObject json) {
		String name = json.getString("name");
		System.out.println("name：" + name);
		if (!isMenuName(name)) {
			return false;
		}

		Integer type = json.getInteger("type");
		System.out.println("type：" + type);

		if (type == null)
			return false;

		if (type != null && type != 0 && type != 1) {
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
		if (!isMenuCode(code, FieldLimit.MENU_CODE_MIN, FieldLimit.MENU_CODE_MAX)) {
			return false;
		}

		System.out.println("验证通过");
		return true;
	}

	/**
	 * 查询菜单时校验参数
	 * 
	 * @Title: menuQueryVal @Description: TODO @param @param
	 *         json @param @return @return boolean @throws
	 */
	public static boolean menuQueryVal(JSONObject json) {
		Integer type = json.getInteger("type");
		System.out.println("type：" + type);
		if (type == null)
			return false;

		if (type != null && type != 0 && type != 1) {
			return false;
		}

		if (type == 1) {
			String search = json.getString("search");
			System.out.println("search：" + search);
			if (type != null && !RegexUtil.isDigits(search)) {
				return false;
			}

			if (!isMenuCode(search, FieldLimit.MENU_CODE_MIN, FieldLimit.MENU_CODE_MAX)) {
				return false;
			}
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
	
	/**
	 * 菜单名是否合法
	 */
	public static boolean isMenuName(String name) {
		if (RegexUtil.isNull(name) || !ParamValidator.isStrLength(name, FieldLimit.MENU_NAME_MIN, FieldLimit.MENU_NAME_MAX)) {
			return false;
		}
		if (!RegexUtil.stringCheck(name)) {
			return false;
		}

		return true;
	}

	/**
	 * Uri是否合法
	 */
	private static boolean isUriParam(String uri) {
		if (RegexUtil.isNull(uri) || !ParamValidator.isStrLength(uri, FieldLimit.URI_LEN_MIN, FieldLimit.URI_LEN_MAX)) {
			return false;
		}
		if (!RegexUtil.isUri(uri))
			return false;
		return true;
	}
	

	/**
	 * 菜单code,目前不清楚code格式，这里只约束code长度和限制为数字
	 */
	public static boolean isMenuCode(String code, int min, int max) {
		if (RegexUtil.isNull(code) && !ParamValidator.isStrLength(code, min, max)) {
			return false;
		}
		if (!RegexUtil.isDigits(code)) {
			return false;
		}
		return true;
	}


}
