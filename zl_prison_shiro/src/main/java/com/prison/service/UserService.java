/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月18日 下午6:20:47 * 
*/ 
package com.prison.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.prison.db.entity.ResourceEntity;
import com.prison.db.entity.UserEntity;

public interface UserService {
  public int add(JSONObject jsonObj);

UserEntity query(JSONObject jsonObj);

UserEntity queryByAccount(String account);

List<String> queryAllPerms(Long userId);

}
