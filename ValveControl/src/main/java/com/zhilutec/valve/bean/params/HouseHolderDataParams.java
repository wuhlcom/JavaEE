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

	private Long start_time; //开始时间 unix时间戳（秒）
	private Long end_time;  //结束时间 unix时间戳（秒）
	
	private Double wit_min; //进水温度下限
	private Double wit_max; //进水温度上限
	
	private String condition1; //否 and/or 默认为and 与wot_win wot_max构成一组参数
	
	private Double wot_min; //回水温度下限
	private Double wot_max; //回水温度上限
	
	private String condition2; //and/or 默认为and 与temdif构成一组条件	
	private Double temdif; //temdif
	//o开1关
	private Integer valve_state=0;	
	
	//开度 阀门开关状态
	private Short opening=0;
	
	//通信地址
	private List<String> comm_addresses;
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
	 * @param opening
	 * @param comm_addresses
	 */
	public HouseHolderDataParams(Long start_time, Long end_time, Double wit_min, Double wit_max, String condition1,
			Double wot_min, Double wot_max, String condition2, Double temdif, Integer valve_state, Short opening,
			List<String> comm_addresses) {
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
		this.valve_state = valve_state;
		this.opening = opening;
		this.comm_addresses = comm_addresses;
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


	public Short getOpening() {
		return opening;
	}


	public void setOpening(Short opening) {
		this.opening = opening;
	}

	

	public List<String> getComm_addresses() {
		return comm_addresses;
	}


	public void setComm_addresses(List<String> comm_addresses) {
		this.comm_addresses = comm_addresses;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HouseHolderDataParams [start_time=" + start_time + ", end_time=" + end_time + ", wit_min=" + wit_min
				+ ", wit_max=" + wit_max + ", condition1=" + condition1 + ", wot_min=" + wot_min + ", wot_max="
				+ wot_max + ", condition2=" + condition2 + ", temdif=" + temdif + ", valve_state=" + valve_state
				+ ", opening=" + opening + ", comm_addresses=" + comm_addresses + "]";
	}	
	
	

}
