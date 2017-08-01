/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月31日 下午5:26:33 * 
*/
package com.dazk.validator;

import com.alibaba.fastjson.JSONObject;
import com.dazk.common.util.ParamValidator;
import com.dazk.common.util.RegexUtil;

public class UserValidator {
	public static boolean userVal(JSONObject json) {
		String login_name = json.getString("login_name");
		System.out.println("login_name：" + login_name);
		if (login_name == null) {
			return false;
		}
	
		if (!isLogin_name(login_name))
			return false;

		String password = json.getString("password");
		System.out.println("password：" + password);
		if (password == null) {
			return false;
		}

		if (password != null) {
			if (!ParamValidator.isStrLength(password, FieldLimit.USER_PW_MIN, FieldLimit.USER_PW_MAX))
				return false;
		}

		Integer sex = json.getInteger("sex");
		System.out.println("sex：" + sex);
		if (sex == null) {
			return false;
		}
	
		if (sex != null && sex != 0 && sex != 1)
			return false;	

		String name = json.getString("name");
		System.out.println("name：" + name);
		if (name == null) {
			return false;
		}
		
		if (name != null && !isUserName(name))
			return false;

		String email = json.getString("email");
		System.out.println("email：" + email);
		if (email != null && !RegexUtil.isEmail(email))
			return false;

		String telephone = json.getString("telephone");
		System.out.println("telephone：" + telephone);
		if (telephone != null && !RegexUtil.isTel(telephone))
			return false;

		String company = json.getString("company");
		System.out.println("company：" + company);
		if (company != null && !isCompany(company))
			return false;

		String address = json.getString("address");
		System.out.println("address：" + address);
		if (address != null && !isAddress(address))
			return false;

		String position = json.getString("position");
		System.out.println("position：" + position);
		if (position != null && !isPosition(position))
			return false;

		String idcard = json.getString("idcard");
		System.out.println("idcard：" + idcard);
		if (idcard != null && !RegexUtil.isIdCardNo(idcard))
			return false;

		String lv = json.getString("lv");
		System.out.println("lv：" + lv);
		if (position != null && !isLv(lv))
			return false;

		String remark = json.getString("remark");

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

		if (!isLogin_name(login_name))
			return false;

		System.out.println("id：" + id);
		if (!isUserId(id, FieldLimit.USER_ID_MIN, FieldLimit.USER_ID_MAX)) {
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
	public static boolean isLogin_name(String login_name) {

		if (login_name != null) {
			if (!ParamValidator.isStrLength(login_name, FieldLimit.LOGIN_NAME_MIN, FieldLimit.LOGIN_NAME_MAX)) {
				return false;
			}

			if (!RegexUtil.isLoginName(login_name)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 用户名是否合法
	 */
	public static boolean isUserName(String name) {
		if (RegexUtil.isNull(name)
				|| !ParamValidator.isStrLength(name, FieldLimit.USER_NAME_MIN, FieldLimit.USER_NAME_MAX)) {
			return false;
		}
		if (!RegexUtil.stringCheck(name)) {
			return false;
		}

		return true;
	}

	/**
	 * 公司名是否合法
	 */
	public static boolean isCompany(String company) {
		if (RegexUtil.isNull(company)
				|| !ParamValidator.isStrLength(company, FieldLimit.COMPANY_MIN, FieldLimit.COMPANY_MAX)) {
			return false;
		}
		if (!RegexUtil.stringCheck(company)) {
			return false;
		}

		return true;
	}

	/**
	 * 地址是否合法
	 */
	public static boolean isAddress(String address) {
		if (RegexUtil.isNull(address)
				|| !ParamValidator.isStrLength(address, FieldLimit.ADDRESS_MIN, FieldLimit.ADDRESS_MAX)) {
			return false;
		}
		if (!RegexUtil.stringCheck(address)) {
			return false;
		}

		return true;
	}

	/**
	 * 职务是否合法
	 */
	public static boolean isPosition(String position) {
		if (RegexUtil.isNull(position)
				|| !ParamValidator.isStrLength(position, FieldLimit.POSITION_MIN, FieldLimit.POSITION_MAX)) {
			return false;
		}
		if (!RegexUtil.stringCheck(position)) {
			return false;
		}

		return true;
	}

	/**
	 * 级别是否合法
	 */
	public static boolean isLv(String lv) {
		if (RegexUtil.isNull(lv) && !RegexUtil.isDigits(lv))
			return false;
		return true;
	}

	/**
	 * 描述是否合法
	 */
	public static boolean isRemark(String remark) {
		if (RegexUtil.isNull(remark)
				|| !ParamValidator.isStrLength(remark, FieldLimit.REMARK_MIN, FieldLimit.REMARK_MAX)) {
			return false;
		}
		if (!RegexUtil.stringCheck(remark)) {
			return false;
		}

		return true;
	}

	/**
	 * user_id这里只约束code长度和限制为数字
	 */
	public static boolean isUserId(String code, int min, int max) {
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
		if (!ParamValidator.isStrLength(loginName, FieldLimit.LOGIN_NAME_MIN, FieldLimit.LOGIN_NAME_MAX))
			return false;
		return RegexUtil.isLoginName(loginName);
	}
}
