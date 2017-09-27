/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月31日 下午4:53:55 * 
*/
package com.dazk.validator;

import com.alibaba.fastjson.JSONObject;
import com.dazk.common.errcode.Result;
import com.dazk.common.errcode.ResultErr;
import com.dazk.common.errcode.ResultStatusCode;
import com.dazk.common.util.ParamValidator;
import com.dazk.common.util.RegexUtil;

public class MenuValidator {
	/**
	 * 添加菜单时校验参数 1 菜单 0按钮
	 */
	public static ResultErr menuVal(JSONObject json) {
		String name = json.getString("name");
		System.out.println("name：" + name);
		if (RegexUtil.isNull(name) || !isMenuName(name))
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "菜单名格式输入错误");

		String is_menu = json.getString("is_menu");
		System.out.println("is_menu：" + is_menu);
		if (RegexUtil.isNull(is_menu) || (RegexUtil.isNotNull(is_menu) && !is_menu.equals("0") && !is_menu.equals("1")
				&& !is_menu.equals("2")))
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "菜单类型错误");

		String parent_id = json.getString("parent_id");
		System.out.println("parent_id：" + parent_id);
		if (RegexUtil.isNotNull(parent_id) && !RegexUtil.isDigits(parent_id)) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "父菜单ID格式错误");
		}

		String uri = json.getString("uri");
		System.out.println("uri：" + uri);
		if (is_menu.equals("1") || is_menu.equals("0")) {
			if (RegexUtil.isNull(uri) || (RegexUtil.isNotNull(uri) && (uri.trim().equals("/") || !isUriParam(uri))))
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "URI格式错误");
		}

		String front_router = json.getString("front_router");
		System.out.println("front_router：" + front_router);
		if (is_menu.equals("1")) {
			if (RegexUtil.isNull(front_router) || (RegexUtil.isNotNull(front_router)
					&& (front_router.trim().equals("/") || !isUriParam(front_router))))
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "前端路由URI格式错误");
		}

		System.out.println("验证通过");
		return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
	}

	public static ResultErr menuUpdateVal(JSONObject json) {
		String id = json.getString("id");
		System.out.println("id：" + id);
		if (RegexUtil.isNull(id) || !isMenuId(id))
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "菜单ID格式错误");

		String name = json.getString("name");
		System.out.println("name：" + name);
		if (!isMenuName(name)) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "菜单名格式错误");
		}

		String is_menu = json.getString("is_menu");
		System.out.println("is_menu：" + is_menu);
		if (RegexUtil.isNotNull(is_menu) && !is_menu.equals("0") && !is_menu.equals("1") && !is_menu.equals("2")) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "菜单类型格式错误");
		}

		String parent_id = json.getString("parent_id");
		System.out.println("parent_id：" + parent_id);
		if (RegexUtil.isNotNull(parent_id) && !RegexUtil.isDigits(parent_id)) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "父菜单ID格式错误");
		}

		String uri = json.getString("uri");
		System.out.println("uri：" + uri);
		if (is_menu.equals("1") || is_menu.equals("0")) {
			if (RegexUtil.isNull(uri) || (RegexUtil.isNotNull(uri) && (uri.trim().equals("/") || !isUriParam(uri))))
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "URI格式错误");
		}

		String front_router = json.getString("front_router");
		System.out.println("front_router：" + front_router);
		if (is_menu.equals("1")) {
			if (RegexUtil.isNull(front_router) || (RegexUtil.isNotNull(front_router)
					&& (front_router.trim().equals("/") || !isUriParam(front_router))))
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "前端路由URI格式错误");
		}

		System.out.println("验证通过");
		return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
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
		if (RegexUtil.isNotNull(id) && (!ParamValidator.isStrLength(id, FieldLimit.MENU_ID_MIN, FieldLimit.MENU_ID_MAX)
				|| !RegexUtil.isDigits(id))) {
			return false;
		}
		return true;
	}

}
