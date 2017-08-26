/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月20日 下午6:31:51 * 
*/
package com.zhilutec.valve.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhilutec.valve.bean.TblLoraGlobalSetting;
import com.zhilutec.valve.bean.TblHouseHolder;
import com.zhilutec.valve.repository.LoraGlobalSettingRepo;
import com.zhilutec.valve.util.error.ErrorCode;
import com.zhilutec.valve.util.error.ErrorResponeMsgBody;

@Service
public class LoraGlobalSettingService {

	@Autowired
	private LoraGlobalSettingRepo loraGlobalSettingRepo;

	// 3.2.3 Lora设备全局配置
	@Transactional
	@Modifying
	public ErrorResponeMsgBody save(String params) {
		JSONObject json = JSON.parseObject(params);

		Long periodSet = json.getLong("period_setting");
		Long timeOperate = json.getLong("timing_operate");
		Double tempUpper = json.getDouble("temp_upper_limit");
		Double tempLower = json.getDouble("temp_lower_limit");

		TblLoraGlobalSetting lg = new TblLoraGlobalSetting();
		lg.setId(1);
		lg.setPeriod_setting(periodSet);
		lg.setTiming_operate(timeOperate);
		lg.setTemp_upper_limit(tempUpper);
		lg.setTemp_lower_limit(tempLower);
		//
		// lg = JSON.parseObject(params, TblLoraGlobal.class);

		TblLoraGlobalSetting rs = loraGlobalSettingRepo.save(lg);
		if (rs == null) {
			return new ErrorResponeMsgBody(ErrorCode.DB_ERROR);
		}
		return new ErrorResponeMsgBody(ErrorCode.OK);
	}

	// 3.2.2 Lora设备全局配置查询
	public List<TblLoraGlobalSetting> findAll() {
		List<TblLoraGlobalSetting> globalSetting = loraGlobalSettingRepo.findAll();
		for (int i = 0; i < globalSetting.size(); i++) {
			TblLoraGlobalSetting dev = globalSetting.get(i);
			dev.setId(null);
		}
		return globalSetting;
	}

}
