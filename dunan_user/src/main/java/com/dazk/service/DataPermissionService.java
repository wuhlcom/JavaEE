/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月28日 下午3:00:31 * 
*/ 
package com.dazk.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.dazk.db.model.DataPermission;

public interface DataPermissionService {
	public int addDataPermi(JSONObject json);

	public int delDataPermi(JSONObject json);

	public List<DataPermission> queryDataPermi(JSONObject json);

	public int updateDataPermi(JSONObject json);
	
	public int queryDataPermiCount(JSONObject obj);
}
