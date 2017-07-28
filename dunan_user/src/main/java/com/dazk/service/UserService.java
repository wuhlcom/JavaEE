/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月27日 上午9:52:09 * 
*/ 
package com.dazk.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.dazk.db.model.User;

public interface UserService {
	public int addUser(JSONObject obj);

	public int delUser(JSONObject obj);

	public List<User> queryUser(JSONObject obj);

	public int updateUser(JSONObject obj);
	
	public int queryUserCount(JSONObject obj);

	Object queryUserByRole(JSONObject obj);
}
