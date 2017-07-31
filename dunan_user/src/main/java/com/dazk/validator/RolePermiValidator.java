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
		String role_code = json.getString("role_code");
		System.out.println("role_code：" + role_code);
		if (role_code == null) {
			return false;
		}
		
		if (!RoleValidator.isRoleCode(role_code, FieldLimit.ROLE_CODE_MIN, FieldLimit.ROLE_CODE_MAX)) {
			return false;
		}

		String reso_code = json.getString("reso_code");
		System.out.println("reso_code：" + reso_code);
		if (reso_code == null) {
			return false;
		}
		
		if (!MenuValidator.isMenuCode(reso_code, FieldLimit.MENU_CODE_MIN, FieldLimit.MENU_CODE_MAX)) {
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
	
	
	public static boolean rolePermiQueryVal(JSONObject json) {
		String role_code = json.getString("role_code");
		System.out.println("role_code：" + role_code);
		if (role_code == null) {
			return false;
		}
		
		if (!RoleValidator.isRoleCode(role_code, FieldLimit.ROLE_CODE_MIN, FieldLimit.ROLE_CODE_MAX)) {
			return false;
		}

		String reso_code = json.getString("reso_code");
		System.out.println("reso_code：" + reso_code);
		if (reso_code != null&&!MenuValidator.isMenuCode(reso_code, FieldLimit.MENU_CODE_MIN, FieldLimit.MENU_CODE_MAX)) {
			return false;
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
}
