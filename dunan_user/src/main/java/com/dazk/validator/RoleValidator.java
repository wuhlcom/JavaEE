/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月31日 下午4:53:42 * 
*/ 
package com.dazk.validator;

import com.alibaba.fastjson.JSONObject;
import com.dazk.common.util.ParamValidator;
import com.dazk.common.util.RegexUtil;

public class RoleValidator {
	/**
	 * 角色参数校验 @Title: RoleVal @Description: TODO @param @param
	 * json @param @return @return boolean @throws
	 */
	public static boolean roleVal(JSONObject json) {
		String name = json.getString("name");
		System.out.println("name：" + name);
		if (RegexUtil.isNull(name)) {
			return false;
		}
		
		if (!isRoleName(name)) {
			return false;
		}
		
		String user_id = json.getString("user_id");
		System.out.println("user_id：" + user_id);		
		// if (user_id == null) {
		// return false;
		// }
		//
		// if (RegexUtil.isNotNull(user_id) && !RegexUtil.isDigits(user_id)) {
		// return false;
		// }

		String remark = json.getString("remark");
		System.out.println("remark：" + remark);
		if (RegexUtil.isNotNull(remark) && !ParamValidator.isStrLength(remark, 0, FieldLimit.ROLE_REMARKS_MAX)) {
			return false;
		}

		System.out.println("验证通过");
		return true;
	}

	public static boolean roleQueryVal(JSONObject json) {
		String type = json.getString("type");
		System.out.println("type：" + type);
		if (RegexUtil.isNull(type))
			return false;

		if (RegexUtil.isNotNull(type)&& !type.equals("0") && !type.equals("1")&& !type.equals("2")) {
			return false;
		}

		if (type.equals("1")){
			String search = json.getString("search");
			System.out.println("search：" + search);
			if (RegexUtil.isNull(search))
				return false;
			
			if (!isRoleName(search)) {
				return false;
			}
		}

		String page = json.getString("page");
		String listRows = json.getString("listRows");
		if (!PubParamValidator.pageVal(page, listRows))
			return false;

		System.out.println("验证通过");
		return true;
	}

	public static boolean rolePermiVal(JSONObject json) {
		String role_id = json.getString("role_id");
		System.out.println("role_id：" + role_id);
		if (!isRoleCode(role_id, FieldLimit.ROLE_ID_MIN,FieldLimit.ROLE_ID_MAX)) {
			return false;
		}
		System.out.println("验证通过");
		return true;
	}
	
	/**
	 * 角色名是否合法
	 */
	public static boolean isRoleName(String name) {
		if (RegexUtil.isNotNull(name) && (!ParamValidator.isStrLength(name, FieldLimit.ROLE_NAME_MIN, FieldLimit.ROLE_NAME_MAX)||!RegexUtil.stringCheck(name))) {
			return false;
		}

		return true;
	}
	
	/**
	 * 角色Code
	 */
	public static boolean isRoleCode(String code, int min, int max) {
		if (RegexUtil.isNotNull(code)&&(!ParamValidator.isStrLength(code, min, max)||!RegexUtil.isDigits(code))) {
			return false;
		}

		return true;
	}
}
