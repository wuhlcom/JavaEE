/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月24日 下午6:48:29 * 
*/ 
package com.prison.service;

import com.alibaba.fastjson.JSONObject;
import com.prison.common.result.Result;
import com.prison.db.entity.PrisonerEntity;
public interface PrisonerService {

	Result add(JSONObject jsonObj);

	Result update(JSONObject jsonObj);

	Result delete(JSONObject jsonObj);

	PrisonerEntity query(JSONObject jsonObj);

	PrisonerEntity query(String number);

}
