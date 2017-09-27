/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月27日 上午9:32:23 * 
*/ 
package com.dazk.service;

import java.util.List;
import com.alibaba.fastjson.JSONObject;
import com.dazk.db.model.Role;


public interface RoleService {
	public int addRole(JSONObject obj);

	public int delRole(JSONObject obj);

	public int updateRole(JSONObject obj);
	
	public int queryRoleCount(JSONObject obj);

	public List<Role> queryRole(JSONObject obj);

	Role queryRoleOne(JSONObject obj);

	int addSuperRole(JSONObject obj);


}
