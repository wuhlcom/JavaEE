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
import com.zhilutec.valve.bean.models.TblLoraGlobalSetting;
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

		Long period_setting = json.getLong("period_setting");
		Long timing_operate = json.getLong("timing_operate");
		Double temp_upper_limit = json.getDouble("temp_upper_limit");
		Double temp_lower_limit = json.getDouble("temp_lower_limit");
		Double temp_setting = json.getDouble("temp_setting");
		Double anti_freezing_temp = json.getDouble("anti_freezing_temp");
		
		TblLoraGlobalSetting lg = loraGlobalSettingRepo.findOne(1);
		if (lg == null) {
			lg = new TblLoraGlobalSetting();
		}
		if (period_setting != null) {
			lg.setPeriod_setting(period_setting);
		}
		if (timing_operate != null) {
			lg.setTiming_operate(timing_operate);
		}
		if (temp_upper_limit != null) {
			lg.setTemp_upper_limit(temp_upper_limit);
		}
		if (temp_lower_limit != null) {
			lg.setTemp_lower_limit(temp_lower_limit);
		}
		if (temp_setting != null) {
			lg.setTemp_setting(temp_setting);
		}
		if (anti_freezing_temp != null) {
			lg.setAnti_freezing_temp(anti_freezing_temp);
		}
		//
		// lg = JSON.parseObject(params, TblLoraGlobal.class);
		
		TblLoraGlobalSetting rs = loraGlobalSettingRepo.save(lg);
		if (rs == null) {
			return new ErrorResponeMsgBody(ErrorCode.INSERT_ERR);
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
