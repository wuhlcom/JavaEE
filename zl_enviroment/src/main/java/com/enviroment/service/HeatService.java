/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月20日 下午2:04:48 * 
*/ 
package com.enviroment.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.enviroment.db.entity.HeatEntity;
import com.enviroment.db.entity.PowerEntity;

public interface HeatService {

	List<HeatEntity> query(JSONObject jsonObj);

	int add(JSONObject jsonObj);

	String result(JSONObject jsonObj);

}
