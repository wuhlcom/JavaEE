package com.dazk.validator;

import com.alibaba.fastjson.JSONObject;

import com.dazk.common.util.ParamValidator;
import com.dazk.common.util.RegexUtil;

public class JsonParamValidator {

	// Menu restriction
	public static final int MENU_NAME_MAX = 16;
	public static final int URI_LEN_MIN = 2;
	public static final int URI_LEN_MAX = 45;
	public static final int MENU_CODE_MIN = 1;
	public static final int MENU_CODE_MAX = 20;

	public static final int ROLE_REMARKS_MAX = 45;
	public static final int ROLE_ID_MAX = 11;
	public static final int ROLE_CODE_MIN = 1;
	public static final int ROLE_CODE_MAX = 20;

	public static final int LOGIN_NAME_MIN = 8;
	public static final int LOGIN_NAME_MAX = 32;

	public static final int USER_ID_MIN = 1;
	public static final int USER_ID_MAX = 11;

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
		if (!isCode(code, MENU_CODE_MIN, MENU_CODE_MAX)) {
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

			if (!isCode(search, MENU_CODE_MIN, MENU_CODE_MAX)) {
				return false;
			}
		}

		String page = json.getString("page");
		System.out.println("page：" + page);
		if (page != null && !RegexUtil.isDigits(page)) {
			return false;
		}

		if (Integer.parseInt(page.toString()) == 0) {
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
	 * 角色参数校验 @Title: RoleVal @Description: TODO @param @param
	 * json @param @return @return boolean @throws
	 */
	public static boolean roleVal(JSONObject json) {
		String name = json.getString("name");
		System.out.println("name：" + name);
		if (!isMenuName(name)) {
			return false;
		}

		String code = json.getString("code");
		System.out.println("code：" + code);
		if (!isCode(code, MENU_CODE_MIN, MENU_CODE_MAX)) {
			return false;
		}

		String remark = json.getString("remark");
		System.out.println("remark：" + remark);
		if (RegexUtil.isNotNull(remark) && !ParamValidator.isStrLength(remark, 0, ROLE_REMARKS_MAX)) {
			return false;
		}

		System.out.println("验证通过");
		return true;
	}

	public static boolean roleQueryVal(JSONObject json) {
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
			if (!isMenuName(search)) {
				return false;
			}
		}

		String page = json.getString("page");
		System.out.println("page：" + page);
		if (page != null && !RegexUtil.isDigits(page)) {
			return false;
		}

		if (Integer.parseInt(page.toString()) == 0) {
			return false;
		}
		// listRows可以为空
		String listRows = json.getString("listRows");
		if (listRows != null && !RegexUtil.isDigits(listRows)) {
			return false;
		}

		System.out.println("验证通过");
		return true;
	}

	public static boolean rolePermiVal(JSONObject json) {
		String role_id = json.getString("role_id");
		System.out.println("role_id：" + role_id);
		if (!isCode(role_id, 1, ROLE_ID_MAX)) {
			return false;
		}
		System.out.println("验证通过");
		return true;
	}

	public static boolean userVal(JSONObject json) {
		String login_name = json.getString("login_name");
		System.out.println("login_name：" + login_name);
		if (!isLoginName(login_name)) {
			return false;
		}

		String password = json.getString("password");
		System.out.println("password：" + password);
		if (!RegexUtil.isPwd(password))
			return false;

		String name = json.getString("name");
		System.out.println("name：" + name);
		if (!RegexUtil.isChinese(name) && !RegexUtil.isEnglish(name))
			return false;

		String email = json.getString("email");
		System.out.println("email：" + email);
		if (RegexUtil.isNotNull(email) && !RegexUtil.isEmail(email))
			return false;

		String telephone = json.getString("telephone");
		System.out.println("email：" + telephone);
		if (RegexUtil.isNotNull(telephone) && !RegexUtil.isTel(telephone))
			return false;

		Integer sex = json.getInteger("sex");
		System.out.println("sex：" + sex);
		if (sex == null)
			return false;

		if (sex != null && sex != 0 && sex != 1) {
			return false;
		}

		String company = json.getString("company");
		System.out.println("company：" + company);
		if (RegexUtil.isNotNull(company) && !RegexUtil.stringCheck(company)) {
			return false;
		}

		String address = json.getString("address");
		System.out.println("address：" + address);
		if (RegexUtil.isNotNull(address) && !RegexUtil.stringCheck(address)) {
			return false;
		}

		String lv = json.getString("lv");
		System.out.println("lv：" + lv);
		if (RegexUtil.isNotNull(lv) && !RegexUtil.isDigits(lv)) {
			return false;
		}

		String role_code = json.getString("role_code");
		System.out.println("role_code：" + role_code);
		if (RegexUtil.isNotNull(role_code) && !isCode(role_code, MENU_CODE_MIN, MENU_CODE_MAX)) {
			return false;
		}

		System.out.println("验证通过");
		return true;
	}

	public static boolean queryUserVal(JSONObject json) {
		String login_name = json.getString("login_name");
		String id = json.getString("id");
		System.out.println("login_name：" + login_name);
		if (login_name == null && id == null) {
			return false;
		}

		if (login_name != null) {
			if (!ParamValidator.isStrLength(login_name, LOGIN_NAME_MIN, LOGIN_NAME_MAX))
				return false;
			if (RegexUtil.isLoginName(login_name))
				return false;
		}

		System.out.println("id：" + id);
		if (!isCode(id, USER_ID_MIN, USER_ID_MAX)) {
			return false;
		}

		System.out.println("验证通过");
		return true;
	}

	public static boolean queryUserByRoleVal(JSONObject json) {
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
			if (!isMenuName(search)) {
				return false;
			}
		}

		System.out.println("验证通过");
		return true;
	}

	
	public static boolean dataPermiVal(JSONObject json) {
//		String name = json.getString("name");
//		System.out.println("name：" + name);
//		if (!isMenuName(name)) {
//			return false;
//		}
//
//		String code = json.getString("code");
//		System.out.println("code：" + code);
//		if (!isCode(code, MENU_CODE_MIN, MENU_CODE_MAX)) {
//			return false;
//		}
//
//		String remark = json.getString("remark");
//		System.out.println("remark：" + remark);
//		if (RegexUtil.isNotNull(remark) && !ParamValidator.isStrLength(remark, 0, ROLE_REMARKS_MAX)) {
//			return false;
//		}

		System.out.println("验证通过");
		return true;
	}
	/**
	 * 菜单名是否合法
	 */
	public static boolean isMenuName(String name) {
		if (RegexUtil.isNull(name) || !ParamValidator.isStrLength(name, 1, MENU_NAME_MAX)) {
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
		if (RegexUtil.isNull(uri) || !ParamValidator.isStrLength(uri, URI_LEN_MIN, URI_LEN_MAX)) {
			return false;
		}
		if (!RegexUtil.isUri(uri))
			return false;
		return true;
	}

	/**
	 * 菜单code,目前不清楚code格式，这里只约束code长度和限制为数字
	 */
	public static boolean isCode(String code, int min, int max) {
		if (RegexUtil.isNull(code) || !ParamValidator.isStrLength(code, min, max)) {
			return false;
		}
		if (!RegexUtil.isDigits(code)) {
			return false;
		}
		return true;
	}

	/**
	 * 账户合法改校验,只能是数字,字母下划线,必须以字母开头
	 */
	public static boolean isLoginName(String loginName) {
		if (RegexUtil.isNull(loginName)) {
			return false;
		}
		if (!ParamValidator.isStrLength(loginName, LOGIN_NAME_MIN, LOGIN_NAME_MAX))
			return false;
		return RegexUtil.isLoginName(loginName);
	}

}
