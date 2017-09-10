/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月31日 下午4:54:30 * 
*/
package com.dazk.validator;

import com.alibaba.fastjson.JSONObject;
import com.dazk.common.errcode.ResultErr;
import com.dazk.common.errcode.ResultStatusCode;
import com.dazk.common.util.ParamValidator;
import com.dazk.common.util.RegexUtil;

public class RolePermiValidator {
	/**
	 * 角色参数校验 @Title: RoleVal @Description: TODO @param @param
	 * json @param @return @return boolean @throws
	 */
	public static boolean rolePermiVal(JSONObject json) {
		String role_id = json.getString("role_id");
		System.out.println("role_id：" + role_id);
		if (RegexUtil.isNull(role_id)) {
			return false;
		}
		if (!isRoleId(role_id, FieldLimit.ROLE_ID_MIN, FieldLimit.ROLE_ID_MAX)) {
			return false;
		}

		String reso_id = json.getString("reso_id");
		System.out.println("reso_id：" + reso_id);
		if (RegexUtil.isNull(reso_id)) {
			return false;
		}

		if (!isMenuId(reso_id, FieldLimit.MENU_ID_MIN, FieldLimit.MENU_ID_MAX)) {
			return false;
		}

		String disused = json.getString("disused");
		System.out.println("disused：" + disused);
		if (RegexUtil.isNotNull(disused) && !disused.equals("0") && !disused.equals("1")) {
			return false;
		}

		System.out.println("验证通过");
		return true;
	}

	public static ResultErr roleMenuVal(JSONObject json) {
		String name = json.getString("name");
		System.out.println("name：" + name);
		if (RegexUtil.isNull(name) || !RoleValidator.isRoleName(name)) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "角色名错误");
		}

		String remark = json.getString("remark");
		System.out.println("remark：" + remark);
		if (RegexUtil.isNotNull(remark) && !ParamValidator.isStrLength(remark, 0, FieldLimit.ROLE_REMARKS_MAX)) {
			return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "备注信息错误");
		}

		String menus = json.getString("menus");
		System.out.println("menus：" + menus);
		// if (RegexUtil.isNull(menus))
		// return false;

		System.out.println("验证通过");
		return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
	}

	public static boolean roleMenuUpdateVal(JSONObject json) {
		String role_id = json.getString("role_id");
		System.out.println("role_id：" + role_id);
		if (RegexUtil.isNull(role_id)) {
			return false;
		}

		if (!isRoleId(role_id, FieldLimit.ROLE_ID_MIN, FieldLimit.ROLE_ID_MAX)) {
			return false;
		}

		String name = json.getString("name");
		System.out.println("name：" + name);
		if (!RoleValidator.isRoleName(name)) {
			return false;
		}

		String remark = json.getString("remark");
		System.out.println("remark：" + remark);
		if (RegexUtil.isNotNull(remark) && !ParamValidator.isStrLength(remark, 0, FieldLimit.ROLE_REMARKS_MAX)) {
			return false;
		}

		// 可以为空或null
		String menus = json.getString("menus");
		System.out.println("menus：" + menus);

		System.out.println("验证通过");
		return true;
	}

	public static boolean roleMenuDelVal(JSONObject json) {
		String role_id = json.getString("role_id");
		System.out.println("role_id：" + role_id);
		if (RegexUtil.isNull(role_id)) {
			return false;
		}

		if (!isRoleId(role_id, FieldLimit.ROLE_ID_MIN, FieldLimit.ROLE_ID_MAX)) {
			return false;
		}

		String menus = json.getString("menus");
		System.out.println("menus：" + menus);
		if (RegexUtil.isNull(menus)) {
			return false;
		}

		boolean isMatched = menus.matches("^\\d+");
		System.out.println(isMatched);
		if (!isMatched) {
			return false;
		}

		System.out.println("验证通过");
		return true;
	}

	public static boolean rolePermiQueryVal(JSONObject json) {
		String role_id = json.getString("role_id");
		System.out.println("role_id：" + role_id);
		if (RegexUtil.isNull(role_id)) {
			return false;
		}

		if (!isRoleId(role_id, FieldLimit.ROLE_ID_MIN, FieldLimit.ROLE_ID_MAX)) {
			return false;
		}

		String reso_id = json.getString("reso_id");
		System.out.println("reso_id：" + reso_id);
		if (RegexUtil.isNotNull(reso_id) && !isMenuId(reso_id, FieldLimit.MENU_ID_MIN, FieldLimit.MENU_ID_MAX)) {
			return false;
		}

		String disused = json.getString("disused");
		System.out.println("disused：" + disused);
		if (RegexUtil.isNotNull(disused) && !disused.equals("0") && !disused.equals("1")) {
			return false;
		}

		String page = json.getString("page");
		String listRows = json.getString("listRows");
		if (!PubParamValidator.pageVal(page, listRows))
			return false;

		System.out.println("验证通过");
		return true;
	}

	/**
	 * 角色Id
	 */
	public static boolean isRoleId(String id, int min, int max) {
		if (RegexUtil.isNotNull(id) && (!ParamValidator.isStrLength(id, min, max) || !RegexUtil.isDigits(id))) {
			return false;
		}
		return true;
	}

	/**
	 * 角色Id
	 */
	public static boolean isMenuId(String id, int min, int max) {
		if (RegexUtil.isNotNull(id) && (!ParamValidator.isStrLength(id, min, max) || !RegexUtil.isDigits(id))) {
			return false;
		}
		return true;
	}
}
