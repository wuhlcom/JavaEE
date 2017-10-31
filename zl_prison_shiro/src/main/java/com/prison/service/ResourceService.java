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

public interface ResourceService {
  public int add(JSONObject jsonObj);

Set<String> getUserPermissions(Long userId);

List<ResourceEntity> queryList(Map<String, Object> map);
}
