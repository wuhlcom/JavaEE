/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月24日 下午6:48:29 * 
*/ 
package com.prison.service;

import com.alibaba.fastjson.JSONObject;
import com.prison.common.result.Result;
import com.prison.db.entity.UserEntity;

public interface UserService {

	Result add(JSONObject jsonObj);

	UserEntity query(JSONObject jsonObj);

	UserEntity query(String username);

	Result update(JSONObject jsonObj);

	Result delete(JSONObject jsonObj);

}
