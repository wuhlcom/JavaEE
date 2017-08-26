/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月31日 下午5:26:33 * 
*/
package com.dazk.validator;

import com.alibaba.fastjson.JSONObject;
import com.dazk.common.util.ParamValidator;
import com.dazk.common.util.RegexUtil;
import com.mysql.jdbc.Field;

public class UserValidator {
	public static boolean userVal(JSONObject json) {
		String parent_user = json.getString("parent_user");
		System.out.println("parent_user：" + parent_user);
		// if (!isUserId(user_id,FieldLimit.USER_ID_MIN,FieldLimit.USER_ID_MAX))
		// {
		// return false;
		// }
		String name = json.getString("name");
		System.out.println("name：" + name);
		if (RegexUtil.isNull(name)) {
			return false;
		}

		if (!isUserName(name))
			return false;

		String nickname = json.getString("nickname");
		System.out.println("nickname：" + nickname);

		if (!isUserName(nickname))
			return false;

		String username = json.getString("username");
		System.out.println("username：" + username);
		if (RegexUtil.isNull(username)) {
			return false;
		}

		if (!isLogin_name(username))
			return false;

		String password = json.getString("password");
		System.out.println("password：" + password);
		if (RegexUtil.isNull(password)) {
			return false;
		}

		if (RegexUtil.isNotNull(nickname)
				&& !ParamValidator.isStrLength(password, FieldLimit.USER_PW_MIN, FieldLimit.USER_PW_MAX)) {
			return false;
		}

		String sex = json.getString("sex");
		System.out.println("sex：" + sex);
		if (RegexUtil.isNull(sex)) {
			return false;
		}

		if (RegexUtil.isNotNull(sex) && !sex.equals("0") && sex.equals("1"))
			return false;

		String email = json.getString("email");
		System.out.println("email：" + email);
		if (RegexUtil.isNull(email)) {
			return false;
		}

		if (RegexUtil.isNotNull(email) && !RegexUtil.isEmail(email))
			return false;

		String telephone = json.getString("telephone");
		System.out.println("telephone：" + telephone);
		if (RegexUtil.isNotNull(telephone) && !RegexUtil.isTel(telephone))
			return false;

		String company = json.getString("company");
		System.out.println("company：" + company);
		if (RegexUtil.isNotNull(company) && !isCompany(company))
			return false;

		String address = json.getString("address");
		System.out.println("address：" + address);
		if (RegexUtil.isNotNull(address) && !isAddress(address))
			return false;

		String position = json.getString("position");
		System.out.println("position：" + position);
		if (RegexUtil.isNotNull(position) && !isPosition(position))
			return false;

		String idcard = json.getString("idcard");
		System.out.println("idcard：" + idcard);
		if (RegexUtil.isNotNull(idcard) && !RegexUtil.isIdCardNo(idcard))
			return false;

		String lv = json.getString("lv");
		System.out.println("lv：" + lv);
		if (RegexUtil.isNotNull(lv) && !isLv(lv))
			return false;

		String role_id = json.getString("role_id");
		if (RegexUtil.isNull(role_id)) {
			return false;
		}

		System.out.println("role_id：" + role_id);
		if (!RolePermiValidator.isRoleId(role_id, FieldLimit.ROLE_ID_MIN, FieldLimit.ROLE_ID_MAX)) {
			return false;
		}

		String remark = json.getString("remark");

		System.out.println("验证通过");
		return true;
	}

	public static boolean queryUserVal(JSONObject json) {
		String type = json.getString("type");	
		System.out.println("type：" + type);
		if (RegexUtil.isNull(type))
			return false;		
		
		if (RegexUtil.isNotNull(type) && !type.equals("0") && !type.equals("1")) {
			return false;
		}

		// 按角色名查询
		if (type.equals("1")) {
			String search = json.getString("search");
			System.out.println("roleName：" + search);
			if (!isLogin_name(search))
				return false;
		}

		String parent_user = json.getString("parent_user");
		System.out.println("parent_user：" + parent_user);
		if (RegexUtil.isNull(parent_user)) {
			return false;
		}

		String page = json.getString("page");
		String listRows = json.getString("listRows");
		if (!PubParamValidator.pageVal(page, listRows))
			return false;

		System.out.println("验证通过");
		return true;
	}

	public static boolean queryUserByRoleVal(JSONObject json) {

		String typeStr = json.getString("type");
		System.out.println("type：" + typeStr);
		if (RegexUtil.isNull(typeStr))
			return false;

		Integer type = json.getInteger("type");
		if (RegexUtil.isNotNull(typeStr) && type != 0 && type != 1) {
			return false;
		}

		if (type == 1) {
			String search = json.getString("search");
			System.out.println("search：" + search);
			if (!isUserName(search)) {
				return false;
			}
		}

		System.out.println("验证通过");
		return true;
	}

	/**
	 * 用户账号是否合法
	 */
	public static boolean isLogin_name(String userName) {
		if (!RegexUtil.isNotNull(userName)
				&& (!ParamValidator.isStrLength(userName, FieldLimit.LOGIN_NAME_MIN, FieldLimit.LOGIN_NAME_MAX)
						|| !RegexUtil.isLoginName(userName))) {
			return false;
		}

		return true;
	}

	/**
	 * 用户名是否合法
	 */
	public static boolean isUserName(String name) {
		if (RegexUtil.isNotNull(name)
				&& (!ParamValidator.isStrLength(name, FieldLimit.USER_NAME_MIN, FieldLimit.USER_NAME_MAX)
						|| !RegexUtil.stringCheck(name))) {
			return false;
		}

		return true;
	}

	/**
	 * 公司名是否合法
	 */
	public static boolean isCompany(String company) {
		if (RegexUtil.isNotNull(company)
				&& (!ParamValidator.isStrLength(company, FieldLimit.COMPANY_MIN, FieldLimit.COMPANY_MAX)
						|| !RegexUtil.stringCheck(company))) {
			return false;
		}

		return true;
	}

	/**
	 * 地址是否合法
	 */
	public static boolean isAddress(String address) {
		if (RegexUtil.isNotNull(address)
				&& (!ParamValidator.isStrLength(address, FieldLimit.ADDRESS_MIN, FieldLimit.ADDRESS_MAX)
						|| !RegexUtil.stringCheck(address))) {
			return false;
		}

		return true;
	}

	/**
	 * 职务是否合法
	 */
	public static boolean isPosition(String position) {
		if (RegexUtil.isNotNull(position)
				&& (!ParamValidator.isStrLength(position, FieldLimit.POSITION_MIN, FieldLimit.POSITION_MAX)
						|| !RegexUtil.stringCheck(position))) {
			return false;
		}
		return true;
	}

	/**
	 * 级别是否合法
	 */
	public static boolean isLv(String lv) {
		if (RegexUtil.isNotNull(lv) && !RegexUtil.isDigits(lv))
			return false;
		return true;
	}

	/**
	 * 描述是否合法
	 */
	public static boolean isRemark(String remark) {
		if (RegexUtil.isNotNull(remark)
				&& (!ParamValidator.isStrLength(remark, FieldLimit.REMARK_MIN, FieldLimit.REMARK_MAX)
						|| !RegexUtil.stringCheck(remark))) {
			return false;
		}
		return true;
	}

	/**
	 * user_id这里只约束id长度和限制为数字
	 */
	public static boolean isUserId(String id, int min, int max) {
		if (RegexUtil.isNotNull(id) && (!ParamValidator.isStrLength(id, min, max) || !RegexUtil.isDigits(id))) {
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
		if (RegexUtil.isNotNull(loginName)
				&& !ParamValidator.isStrLength(loginName, FieldLimit.LOGIN_NAME_MIN, FieldLimit.LOGIN_NAME_MAX))
			return false;
		return RegexUtil.isLoginName(loginName);
	}
}
