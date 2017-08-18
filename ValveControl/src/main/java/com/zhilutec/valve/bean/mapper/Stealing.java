/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月18日 下午5:17:23 * 
*/ 
package com.zhilutec.valve.bean.mapper;


public class Stealing {
	private Long collect_time; // 住户编号
	private String comm_address; // 通信地址,
	private Integer valve_state; // 阀门状态 0:开 1:关 其他:异常',
	private Double supply_temp; // 进水温度，瞬时值',
	private Double return_temp; // 回水温度，瞬时值',

	
	/**
	 * 
	 */
	public Stealing() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param house_code
	 * @param comm_type
	 * @param dtu_code
	 * @param comm_address
	 * @param collect_time
	 * @param valve_state
	 * @param supply_temp
	 * @param return_temp
	 */
	public Stealing(String house_code, Integer comm_type, String dtu_code, String comm_address, Long collect_time,
			Integer valve_state, Double supply_temp, Double return_temp) {
		super();
		this.collect_time = collect_time;
		this.comm_address = comm_address;
	
		this.valve_state = valve_state;
		this.supply_temp = supply_temp;
		this.return_temp = return_temp;
	}

	public Long getCollect_time() {
		return collect_time;
	}

	public void setCollect_time(Long collect_time) {
		this.collect_time = collect_time;
	}

	public String getComm_address() {
		return comm_address;
	}

	public void setComm_address(String comm_address) {
		this.comm_address = comm_address;
	}

	public Integer getValve_state() {
		return valve_state;
	}

	public void setValve_state(Integer valve_state) {
		this.valve_state = valve_state;
	}

	public Double getSupply_temp() {
		return supply_temp;
	}

	public void setSupply_temp(Double supply_temp) {
		this.supply_temp = supply_temp;
	}

	public Double getReturn_temp() {
		return return_temp;
	}

	public void setReturn_temp(Double return_temp) {
		this.return_temp = return_temp;
	}		
	
	
}
