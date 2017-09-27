package com.zhilutec.valve.util.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.zhilutec.valve.service.HouseHolderDataService;

public class StealingValidator {

	public final static Logger logger = LoggerFactory.getLogger(HouseHolderDataService.class);

	public static boolean queryStealValidator(JSONObject object) {	
		System.out.println("参数检查开始");
		String start_time = object.getString("start_time");
		Double start = object.getDouble("start_time");
		System.out.println("start_time:" + start_time);
		if (RegexUtil.isNull(start_time)) {
			return false;
		}

		if (RegexUtil.isNotNull(start_time) && (!RegexUtil.isDigits(start_time) || start_time.length() != 10)) {
			return false;
		}

		String end_time = object.getString("end_time");
		Double end = object.getDouble("end_time");
		System.out.println("end_time:" + end_time);
		if (RegexUtil.isNull(end_time)) {
			return false;
		}

		if (RegexUtil.isNotNull(end_time) && (!RegexUtil.isDigits(end_time) || end_time.length() != 10)) {
			return false;
		}
		
		if (RegexUtil.isNotNull(start_time)&&RegexUtil.isNotNull(end_time) && (start> end)) {
			return false;
		}		
		
		String wit_min = object.getString("wit_min");
		Double witMin = object.getDouble("wit_min");
		System.out.println("wit_min:" + wit_min);		

		if (RegexUtil.isNotNull(wit_min) && !RegexUtil.isDouble(wit_min)) {
			return false;
		}
		
		String wit_max = object.getString("wit_max");
		Double witMax = object.getDouble("wit_max");
		System.out.println("wit_max:" + wit_max);	

		if (RegexUtil.isNotNull(wit_max) && !RegexUtil.isDouble(wit_max)) {
			return false;
		}
		
		if (RegexUtil.isNotNull(wit_max) && RegexUtil.isNotNull(wit_min) && (witMin> witMax)) {
			return false;
		}
		
		String condition1 = object.getString("condition1");		
		System.out.println("condition1:" + condition1);
		if (RegexUtil.isNotNull(condition1) && !RegexUtil.isCondition(condition1.toString())) {
			return false;
		}

		String wot_min = object.getString("wot_min");
		Double wotMin = object.getDouble("wot_min");
		System.out.println("wot_min:" + wot_min);	

		if (RegexUtil.isNotNull(wot_min) && !RegexUtil.isDouble(wot_min)) {
			return false;
		}
		
		String wot_max = object.getString("wot_max");
		Double wotMax = object.getDouble("wot_max");
		System.out.println("wot_max:" + wot_max);
		if (RegexUtil.isNotNull(wot_max) && !RegexUtil.isDouble(wot_max)) {
			return false;
		}
		
		if (RegexUtil.isNotNull(wot_max) && RegexUtil.isNotNull(wot_min) && (wotMin> wotMax)) {
			return false;
		}
		
		String condition2 = object.getString("condition2");
		System.out.println("condition2:" + condition2);	
		if (RegexUtil.isNotNull(condition2) && !RegexUtil.isCondition(condition2)) {
			return false;
		}
		
		String temdif = object.getString("temdif");
		System.out.println("temdif:" + temdif);	
		if (RegexUtil.isNotNull(temdif) && !RegexUtil.isDouble(temdif)) {
			return false;
		}

		return true;

	}

}
