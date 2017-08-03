/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月31日 下午4:54:30 * 
*/
package com.dazk.validator;

import com.alibaba.fastjson.JSONObject;
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
		if (!isRoleId(role_id, FieldLimit.ROLE_ID_MIN, FieldLimit.ROLE_ID_MAX)) {
			return false;
		}

		String reso_id = json.getString("reso_id");
		System.out.println("reso_id：" + reso_id);
		if (!isMenuId(reso_id, FieldLimit.MENU_ID_MIN, FieldLimit.MENU_ID_MAX)) {
			return false;
		}

		Integer disused = json.getInteger("disused");
		System.out.println("disused：" + disused);
		if (disused != null && disused != 0 && disused != 1) {
			return false;
		}

		System.out.println("验证通过");
		return true;
	}

	public static boolean roleMenuVal(JSONObject json) {
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

		String menus = json.getString("menus");
		System.out.println("menus：" + menus);
		if (RegexUtil.isNull(menus))
			return false;

		System.out.println("验证通过");
		return true;
	}

	public static boolean rolePermiQueryVal(JSONObject json) {
		String role_id = json.getString("role_id");
		System.out.println("role_id：" + role_id);
		if (role_id == null) {
			return false;
		}

		if (!isRoleId(role_id, FieldLimit.ROLE_ID_MIN, FieldLimit.ROLE_ID_MAX)) {
			return false;
		}

		String reso_id = json.getString("reso_id");
		System.out.println("reso_id：" + reso_id);
		if (reso_id != null) {
			if (!isMenuId(reso_id, FieldLimit.MENU_ID_MIN, FieldLimit.MENU_ID_MAX)) {
				return false;
			}
		}

		Integer disused = json.getInteger("disused");
		System.out.println("disused：" + disused);
		if (disused != null && disused != 0 && disused != 1) {
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

	/**
	 * 角色Id
	 */
	public static boolean isRoleId(String id, int min, int max) {
		if (RegexUtil.isNull(id) || !ParamValidator.isStrLength(id, min, max)) {
			return false;
		}
		if (!RegexUtil.isDigits(id)) {
			return false;
		}
		return true;
	}

	/**
	 * 角色Id
	 */
	public static boolean isMenuId(String id, int min, int max) {
		if (RegexUtil.isNull(id) || !ParamValidator.isStrLength(id, min, max)) {
			return false;
		}
		if (!RegexUtil.isDigits(id)) {
			return false;
		}
		return true;
	}
}
