/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月27日 上午11:17:24 * 
*/
package com.dazk.db.dao;

import java.util.List;

import com.dazk.common.util.UserUtilMapper;
import com.dazk.db.model.User;
import com.dazk.db.param.UserParam;

public interface UserMapper extends UserUtilMapper<User> {
	List<User> queryUser(UserParam obj);

	int queryUserCount(UserParam obj);
}
