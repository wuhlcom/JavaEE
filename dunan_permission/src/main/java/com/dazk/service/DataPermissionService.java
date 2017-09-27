/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月28日 下午3:00:31 * 
*/ 
package com.dazk.service;

import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.dazk.db.model.DataPermission;

public interface DataPermissionService {
	public int addDataPermi(JSONObject json);

	public int delDataPermi(JSONObject json);
	public int updateDataPermi(JSONObject json);	

	public Set<String> queryDataPermi(JSONObject json);	

	int addDataPermiBatch(JSONObject obj);

	int queryDataCount(JSONObject obj);

	List<DataPermission> queryData(JSONObject obj);
}
