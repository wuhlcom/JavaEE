/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月24日 下午6:48:29 * 
*/ 
package com.login.service;

import com.alibaba.fastjson.JSONObject;
import com.login.db.entity.UserEntity;

public interface UserService {

	int add(JSONObject jsonObj);

	UserEntity query(JSONObject jsonObj);

	UserEntity query(String username);

}
