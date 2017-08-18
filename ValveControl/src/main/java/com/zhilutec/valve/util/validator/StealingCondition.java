/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月18日 上午9:24:52 * 
*/
package com.zhilutec.valve.util.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhilutec.valve.service.HouseHolderService;
import com.zhilutec.valve.util.error.ErrorCode;
import com.zhilutec.valve.util.error.GlobalErrorException;

public class StealingCondition {
	private Long start_time; // 起始时间
	private Long end_time; // 终止时间
	private Double wit_min;
	private Double wit_max;
	private String condition1 = "and";
	private Double wot_min;
	private Double wot_max;
	private String condition2 = "and";
	private Double temdif;

	public final static Logger logger = LoggerFactory.getLogger(HouseHolderService.class);
	
	public StealingCondition(){}
	
	
	public StealingCondition(String requestBody) {
		super();
		JSONObject object = JSON.parseObject(requestBody);
		this.start_time = object.getLong("start_time");
		this.end_time = object.getLong("end_time");
		this.wit_min = object.getDouble("wit_min");
		this.wit_max = object.getDouble("wit_max");
		this.condition1 = object.getString("condition1");
		this.wot_min = object.getDouble("wot_min");
		this.wot_max = object.getDouble("wot_max");
		this.condition2 = object.getString("condition2");
		this.temdif = object.getDouble("temdif");;
	}
	
	public StealingCondition(JSONObject object) {
		super();
		this.start_time = object.getLong("start_time");
		this.end_time = object.getLong("end_time");
		this.wit_min = object.getDouble("wit_min");
		this.wit_max = object.getDouble("wit_max");
		this.condition1 = object.getString("condition1");
		this.wot_min = object.getDouble("wot_min");
		this.wot_max = object.getDouble("wot_max");
		this.condition2 = object.getString("condition2");
		this.temdif = object.getDouble("temdif");;
	}

	/**
	 * @param start_time
	 * @param end_time
	 * @param wit_min
	 * @param wit_max
	 * @param condition1
	 * @param wot_min
	 * @param wot_max
	 * @param condition2
	 * @param temdif
	 */
	public StealingCondition(Long start_time, Long end_time, Double wit_min, Double wit_max, String condition1,
			Double wot_min, Double wot_max, String condition2, Double temdif) {
		super();
		this.start_time = start_time;
		this.end_time = end_time;
		this.wit_min = wit_min;
		this.wit_max = wit_max;
		this.condition1 = condition1;
		this.wot_min = wot_min;
		this.wot_max = wot_max;
		this.condition2 = condition2;
		this.temdif = temdif;
	}



	public ErrorCode getCondition(JSONObject object) {
		try {
			if (!StealingValidator.queryStealValidator(object)) {
				return ErrorCode.PARAM_ERROR;
			}

		} catch (Exception e) {
			logger.error("failed to parse query condition", e);
			return ErrorCode.JSON_FORMAT_ERROR;
		}

		return ErrorCode.OK;
	}

	public ErrorCode getCondition(String requestBody) throws GlobalErrorException {
		JSONObject object = JSON.parseObject(requestBody);
		try {
			if (!StealingValidator.queryStealValidator(object)) {
				return ErrorCode.PARAM_ERROR;
			}

		} catch (Exception e) {
			logger.error("failed to parse query condition", e);
			return ErrorCode.JSON_FORMAT_ERROR;
		}

		return ErrorCode.OK;
	}

	public Long getStart_time() {
		return start_time;
	}

	public void setStart_time(Long start_time) {
		this.start_time = start_time;
	}

	public Long getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Long end_time) {
		this.end_time = end_time;
	}

	public Double getWit_min() {
		return wit_min;
	}

	public void setWit_min(Double wit_min) {
		this.wit_min = wit_min;
	}

	public Double getWit_max() {
		return wit_max;
	}

	public void setWit_max(Double wit_max) {
		this.wit_max = wit_max;
	}

	public String getCondition1() {
		return condition1;
	}

	public void setCondition1(String condition1) {
		this.condition1 = condition1;
	}

	public Double getWot_min() {
		return wot_min;
	}

	public void setWot_min(Double wot_min) {
		this.wot_min = wot_min;
	}

	public Double getWot_max() {
		return wot_max;
	}

	public void setWot_max(Double wot_max) {
		this.wot_max = wot_max;
	}

	public String getCondition2() {
		return condition2;
	}

	public void setCondition2(String condition2) {
		this.condition2 = condition2;
	}

	public Double getTemdif() {
		return temdif;
	}

	public void setTemdif(Double temdif) {
		this.temdif = temdif;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StealingCondition [start_time=" + start_time + ", end_time=" + end_time + ", wit_min=" + wit_min
				+ ", wit_max=" + wit_max + ", condition1=" + condition1 + ", wot_min=" + wot_min + ", wot_max="
				+ wot_max + ", condition2=" + condition2 + ", temdif=" + temdif + "]";
	}

	

}
