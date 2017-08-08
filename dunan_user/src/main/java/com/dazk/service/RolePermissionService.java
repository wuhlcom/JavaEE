/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月27日 上午9:32:23 * 
*/ 
package com.dazk.service;

import java.util.List;
import com.alibaba.fastjson.JSONObject;
import com.dazk.db.model.Role;
import com.dazk.db.model.RolePermission;

public interface RolePermissionService {

	public int addRolePermi(JSONObject obj);
	public int delRolePermi(JSONObject obj);
	public int updateRolePermi(JSONObject obj);	
	List<RolePermission> queryRolePermi(JSONObject obj);
	public int queryRolePermiCount(JSONObject obj) ;
	List<RolePermission> queryRoleMenu(JSONObject obj);
	int addRoleMenu(JSONObject obj);
	int updateRoleMenu(JSONObject obj);
	int delRoleMenu(JSONObject obj);
	boolean menuAuth(String url, Long roleId);
}
