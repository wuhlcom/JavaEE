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
		if (RegexUtil.isNull(name))
			return false;

		if (!isMenuName(name)) {
			return false;
		}

		String is_menu = json.getString("is_menu");
		System.out.println("is_menu：" + is_menu);
		if (RegexUtil.isNull(is_menu))
			return false;

		if (RegexUtil.isNotNull(is_menu) && !is_menu.equals("0") && !is_menu.equals("1") && !is_menu.equals("2")) {
			return false;
		}

		String parent_id = json.getString("parent_id");
		System.out.println("parent_id：" + parent_id);
		if (RegexUtil.isNotNull(parent_id) && !RegexUtil.isDigits(parent_id)) {
			return false;
		}

		String uri = json.getString("uri");
		System.out.println("uri：" + uri);
		if (!isUriParam(uri))
			return false;

		String front_router = json.getString("front_router");
		System.out.println("front_router：" + front_router);
		if (!isUriParam(front_router))
			return false;

		System.out.println("验证通过");
		return true;
	}

	public static boolean menuUpdateVal(JSONObject json) {
		String id = json.getString("id");
		System.out.println("id：" + id);
		if (RegexUtil.isNull(id))
			return false;

		if (!isMenuId(id)) {
			return false;
		}

		String name = json.getString("name");
		System.out.println("name：" + name);
		if (!isMenuName(name)) {
			return false;
		}

		String is_menu = json.getString("is_menu");
		System.out.println("is_menu：" + is_menu);
		if (RegexUtil.isNotNull(is_menu) && !is_menu.equals("0") && !is_menu.equals("1") && !is_menu.equals("2")) {
			return false;
		}

		String parent_id = json.getString("parent_id");
		System.out.println("parent_id：" + parent_id);
		if (RegexUtil.isNotNull(parent_id) && !RegexUtil.isDigits(parent_id)) {
			return false;
		}

		String uri = json.getString("uri");
		System.out.println("uri：" + uri);
		if (!isUriParam(uri))
			return false;

		String front_router = json.getString("front_router");
		System.out.println("front_router：" + front_router);
		if (!isUriParam(front_router))
			return false;

		System.out.println("验证通过");
		return true;
	}

	/**
	 * 查询菜单时校验参数
	 * 
	 */
	public static boolean menuQueryVal(JSONObject json) {
		String type = json.getString("type");
		System.out.println("type：" + type);
		if (RegexUtil.isNull(type))
			return false;

		if (RegexUtil.isNotNull(type) && !type.equals("0") && !type.equals("1") && !type.equals("2")
				&& !type.equals("3")) {
			return false;
		}

		String search = json.getString("search");
		System.out.println("search：" + search);
		if (type.equals("2")) {
			if (!isMenuName(search)) {
				return false;
			}
		}

		if (type.equals("3")) {
			if (RegexUtil.isNotNull(search) && !RegexUtil.isDigits(search)) {
				return false;
			}
		}

		String menuType = json.getString("menuType");
		if (RegexUtil.isNotNull(menuType) && !menuType.equals("0") && !menuType.equals("1")) {
			return false;
		}

		String page = json.getString("page");
		System.out.println("page：" + page);
		if (RegexUtil.isNotNull(page) && !RegexUtil.isDigits(page)) {
			return false;
		}

		if (RegexUtil.isNotNull(page) && json.getInteger("page") == 0) {
			return false;
		}

		String listRows = json.getString("listRows");
		if (RegexUtil.isNotNull(listRows) && !RegexUtil.isDigits(listRows)) {
			return false;
		}

		System.out.println("验证通过");
		return true;
	}

	/**
	 * 菜单名是否合法
	 */
	public static boolean isMenuName(String name) {
		if (RegexUtil.isNotNull(name)
				&& (!ParamValidator.isStrLength(name, FieldLimit.MENU_NAME_MIN, FieldLimit.MENU_NAME_MAX)
						|| !RegexUtil.stringCheck(name))) {
			return false;
		}
		return true;
	}

	/**
	 * Uri是否合法
	 */
	private static boolean isUriParam(String uri) {
		if (RegexUtil.isNotNull(uri)
				&& (!ParamValidator.isStrLength(uri, FieldLimit.URI_LEN_MIN, FieldLimit.URI_LEN_MAX)
						|| !RegexUtil.isUri(uri))) {
			return false;
		}
		return true;
	}

	/**
	 * 菜单code,目前不清楚code格式，这里只约束code长度和限制为数字
	 */
	public static boolean isMenuCode(String code, int min, int max) {
		if (RegexUtil.isNotNull(code) && !ParamValidator.isStrLength(code, min, max)) {
			return false;
		}
		return true;
	}

	public static boolean isMenuId(String id) {
		if (RegexUtil.isNotNull(id) && (!ParamValidator.isStrLength(id, FieldLimit.MENU_ID_MIN,FieldLimit.MENU_ID_MAX) || !RegexUtil.isDigits(id))) {
			return false;
		}
		return true;
	}

}
