/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月20日 下午2:04:48 * 
*/ 
package com.enviroment.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.enviroment.db.entity.PowerEntity;
import com.enviroment.db.entity.WaterEntity;

public interface WaterService {

	List<WaterEntity> query(JSONObject jsonObj);

	int add(JSONObject jsonObj);

	int count(JSONObject jsonObj);

	String result(JSONObject jsonObj);

}
