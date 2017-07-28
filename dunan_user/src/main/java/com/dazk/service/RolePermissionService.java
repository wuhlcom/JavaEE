/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月27日 上午9:32:23 * 
*/ 
package com.dazk.service;

import java.util.List;
import com.alibaba.fastjson.JSONObject;
import com.dazk.db.model.RolePermission;

public interface RolePermissionService {
	public List<RolePermission> queryRolePermission(JSONObject obj);
	public int queryRolePermiCount(JSONObject obj) ;
	public int updateRolePermi(JSONObject obj);
	public int addRolePermi(JSONObject obj);
	public int delRolePermi(JSONObject obj);
}
