/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月31日 下午5:26:33 * 
*/
package com.dazk.validator;

import com.alibaba.fastjson.JSONObject;
import com.dazk.common.errcode.ResultErr;
import com.dazk.common.errcode.ResultStatusCode;
import com.dazk.common.util.ParamValidator;
import com.dazk.common.util.RegexUtil;
import com.mysql.jdbc.Field;

public class UserValidator {
	public static ResultErr userVal(JSONObject json) {
		String parent_user = json.getString("parent_user");
		System.out.println("parent_user：" + parent_user);

		String name = json.getString("name");
		System.out.println("name：" + name);
		if (RegexUtil.isNull(name) || !isUserName(name)) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "用户名格式错误");
		}

		String nickname = json.getString("nickname");
		System.out.println("nickname：" + nickname);

		if (!isUserName(nickname))
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "昵称格式错误");

		String username = json.getString("username");
		System.out.println("username：" + username);
		if (RegexUtil.isNull(username) || !isLogin_name(username)) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "登录账户格式错误");
		}

		String password = json.getString("password");
		System.out.println("password：" + password);
		// 修改密码不判断密码是否为空
		Boolean check = json.getBoolean("checkPass");
		if (check != null && check == false) {
			if ((RegexUtil.isNotNull(nickname)
					&& !ParamValidator.isStrLength(password, FieldLimit.USER_PW_MIN, FieldLimit.USER_PW_MAX))) {
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "密码格式错误");
			}
		} else {
			if (RegexUtil.isNull(password) || (RegexUtil.isNotNull(nickname)
					&& !ParamValidator.isStrLength(password, FieldLimit.USER_PW_MIN, FieldLimit.USER_PW_MAX))) {
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "密码格式错误");
			}
		}

		String sex = json.getString("sex");
		System.out.println("sex：" + sex);
		if (RegexUtil.isNull(sex) || (RegexUtil.isNotNull(sex) && !sex.equals("0") && !sex.equals("1"))) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "性别格式错误");
		}

		String email = json.getString("email");
		System.out.println("email：" + email);
		if (RegexUtil.isNotNull(email) && !RegexUtil.isEmail(email)) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "邮箱格式错误");
		}

		String telephone = json.getString("telephone");
		System.out.println("telephone：" + telephone);
		if (RegexUtil.isNotNull(telephone) && !RegexUtil.isTel(telephone))
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "电话号码格式错误");

		String company = json.getString("company");
		System.out.println("company：" + company);
		if (RegexUtil.isNotNull(company) && !isCompany(company))
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "公司名格式错误");

		String address = json.getString("address");
		System.out.println("address：" + address);
		if (RegexUtil.isNotNull(address) && !isAddress(address))
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "联系地址格式错误");

		String position = json.getString("position");
		System.out.println("position：" + position);
		if (RegexUtil.isNotNull(position) && !isPosition(position))
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "职务格式错误");

		String idcard = json.getString("idcard");
		System.out.println("idcard：" + idcard);
		if (RegexUtil.isNotNull(idcard) && !RegexUtil.isIdCardNo(idcard))
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "身份证号码格式错误");

		String lv = json.getString("lv");
		System.out.println("lv：" + lv);
		if (RegexUtil.isNotNull(lv) && !isLv(lv))
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "用户级别格式错误");

		String role_id = json.getString("role_id");
		System.out.println("role_id：" + role_id);
		if (RegexUtil.isNull(role_id)
				|| !RolePermiValidator.isRoleId(role_id, FieldLimit.ROLE_ID_MIN, FieldLimit.ROLE_ID_MAX)) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "用户角色格式错误");
		}

		String remark = json.getString("remark");
		System.out.println("remark：" + remark);

		System.out.println("验证通过");
		return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
	}

	public static ResultErr updateVal(JSONObject json) {
		String parent_user = json.getString("parent_user");
		System.out.println("parent_user：" + parent_user);

		String id = json.getString("id");
		System.out.println("id：" + id);
		if (RegexUtil.isNull(id) || RegexUtil.isNotNull(id)
				&& (!ParamValidator.isStrLength(id, FieldLimit.USER_ID_MIN, FieldLimit.USER_ID_MAX))) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "用户ID格式错误");
		}

		String name = json.getString("name");
		System.out.println("name：" + name);
		if (!isUserName(name)) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "用户名格式错误");
		}

		String nickname = json.getString("nickname");
		System.out.println("nickname：" + nickname);

		if (!isUserName(nickname))
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "昵称格式错误");

		String username = json.getString("username");
		System.out.println("username：" + username);
		if (!isLogin_name(username)) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "登录账户格式错误");
		}

		String password = json.getString("password");
		System.out.println("password：" + password);
		// 修改密码不判断密码是否为空
		if ((RegexUtil.isNotNull(nickname)
				&& !ParamValidator.isStrLength(password, FieldLimit.USER_PW_MIN, FieldLimit.USER_PW_MAX))) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "密码格式错误");
		}

		String sex = json.getString("sex");
		System.out.println("sex：" + sex);
		if ((RegexUtil.isNotNull(sex) && !sex.equals("0") && !sex.equals("1"))) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "性别格式错误");
		}

		String email = json.getString("email");
		System.out.println("email：" + email);
		if (RegexUtil.isNotNull(email) && !RegexUtil.isEmail(email)) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "邮箱格式错误");
		}

		String telephone = json.getString("telephone");
		System.out.println("telephone：" + telephone);
		if (RegexUtil.isNotNull(telephone) && !RegexUtil.isTel(telephone))
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "电话号码格式错误");

		String company = json.getString("company");
		System.out.println("company：" + company);
		if (RegexUtil.isNotNull(company) && !isCompany(company))
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "公司名格式错误");

		String address = json.getString("address");
		System.out.println("address：" + address);
		if (RegexUtil.isNotNull(address) && !isAddress(address))
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "联系地址格式错误");

		String position = json.getString("position");
		System.out.println("position：" + position);
		if (RegexUtil.isNotNull(position) && !isPosition(position))
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "职务格式错误");

		String idcard = json.getString("idcard");
		System.out.println("idcard：" + idcard);
		if (RegexUtil.isNotNull(idcard) && !RegexUtil.isIdCardNo(idcard))
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "身份证号码格式错误");

		String lv = json.getString("lv");
		System.out.println("lv：" + lv);
		if (RegexUtil.isNotNull(lv) && !isLv(lv))
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "用户级别格式错误");

		String role_id = json.getString("role_id");
		System.out.println("role_id：" + role_id);
		if (!RolePermiValidator.isRoleId(role_id, FieldLimit.ROLE_ID_MIN, FieldLimit.ROLE_ID_MAX)) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "用户角色格式错误");
		}

		String remark = json.getString("remark");
		System.out.println("remark：" + remark);

		System.out.println("验证通过");
		return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
	}

	public static boolean queryUserVal(JSONObject json) {
		String type = json.getString("type");
		System.out.println("type：" + type);
		if (RegexUtil.isNull(type))
			return false;

		if (RegexUtil.isNotNull(type) && !type.equals("0") && !type.equals("1") && !type.equals("2")
				&& !type.equals("3")) {
			return false;
		}

		// 按角色名查询
		if (type.equals("1")) {
			String roleName = json.getString("search");
			System.out.println("roleName：" + roleName);
			if (RegexUtil.isNotNull(roleName) && (!ParamValidator.isStrLength(roleName, 1, FieldLimit.ROLE_NAME_MAX)
					|| !RegexUtil.stringCheck(roleName))) {
				return false;
			}
		}

		// 按用户名查询，用户名为空返回所有
		if (type.equals("2")) {
			String userName = json.getString("search");
			System.out.println("userName：" + userName);
			System.out.println(userName.length() < 1);
			if (RegexUtil.isNotNull(userName) && (!ParamValidator.isStrLength(userName, 1, FieldLimit.LOGIN_NAME_MAX))) {
				return false;
			}
		}

		// 按角色id查询,角色id为空查询所有
		if (type.equals("3")) {
			String roleId = json.getString("search");
			System.out.println("roleId：" + roleId);
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

	public static ResultErr resetVal(JSONObject json) {
		String user_id = json.getString("id");
		System.out.println("id：" + user_id);
		if (RegexUtil.isNull(user_id) || RegexUtil.isNotNull(user_id)
				&& (!ParamValidator.isStrLength(user_id, FieldLimit.LOGIN_NAME_MIN, FieldLimit.LOGIN_NAME_MAX))) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "用户ID错误");
		}

		System.out.println("验证通过");
		return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
	}

	/**
	 * 用户账号是否合法
	 */
	public static boolean isLogin_name(String userName) {
		if (RegexUtil.isNotNull(userName)
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
