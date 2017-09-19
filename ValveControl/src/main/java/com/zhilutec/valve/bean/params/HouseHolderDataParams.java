/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年9月18日 上午9:11:14 * 
*/
package com.zhilutec.valve.bean.params;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页查询参数
 * 
 * @author huangzhenwei
 * @since 2014-11-21
 * 
 */
public class HouseHolderDataParams {
	// start_time Long 否 开始时间 unix时间戳（秒）
	// end_time Long 否 开始时间 unix时间戳（秒）
	// wit_min double 是 进水温度下限
	// wit_max double 是 进水温度上限
	// condition1 string 否 and/or 默认为and
	// wot_min double 是 回水温度下限
	// wot_max double 是 回水温度上限
	// condition2 string 否 and/or 默认为and
	// temdif double 是 温差范围
	private Long start_time;
	private Long end_time;
	
	private Double wit_min;
	private Double wit_max;
	
	private String condition1;
	
	private Double wot_min;
	private Double wot_max;
	
	private String condition2;
	
	private Double temdif;
	//o开1关
	private Integer valve_state=0;	
	
	
	/**
	 * 
	 */
	public HouseHolderDataParams() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @param valve_state
	 */
	public HouseHolderDataParams(Long start_time, Long end_time, Double wit_min, Double wit_max, String condition1,
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
		this.valve_state=0;
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
	public Integer getValve_state() {
		return valve_state;
	}
	public void setValve_state(Integer valve_state) {
		this.valve_state = valve_state;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HouseHolderDataParams [start_time=" + start_time + ", end_time=" + end_time + ", wit_min=" + wit_min
				+ ", wit_max=" + wit_max + ", condition1=" + condition1 + ", wot_min=" + wot_min + ", wot_max="
				+ wot_max + ", condition2=" + condition2 + ", temdif=" + temdif + ", valve_state=" + valve_state + "]";
	}	
	
	

}
