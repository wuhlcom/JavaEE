/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月18日 下午6:20:47 * 
*/ 
package com.prison.service;

import com.alibaba.fastjson.JSONObject;
import com.prison.db.entity.ResourceEntity;

public interface RoleResourceService {
  public int add(JSONObject jsonObj);

ResourceEntity query(JSONObject jsonObj);
}
