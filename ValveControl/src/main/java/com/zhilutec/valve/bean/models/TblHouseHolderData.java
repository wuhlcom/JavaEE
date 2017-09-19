/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月17日 下午3:00:31 * 
*/
package com.zhilutec.valve.bean.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

@Entity()
@Table(name = "zl_householder_data")
public class TblHouseHolderData {
	
	@Id
	@JSONField(serialize = false)
	private Long id;
	private Integer comm_type; // 通信类型 1-有线 0-lora
	private String dtu_code; // dtu编号
	private String comm_address; // 通信地址,
	private Long collect_time; // 采集时间',
	private Integer comm_status; // 通信状态 0:正常 1:异常',
	private Integer valve_state; // 阀门状态 0:开 1:关 其他:异常',
	private Long open_time; // 分摊周期开阀时间',
	private Double sharing_heat; // 分摊周期热量',
	private Double set_temper; // 设定温度，瞬时值',
	private Double average_temper; // 平均温度，瞬时值',
	private Double accumulated_heat; // 本供热季累计使用热量',
	private Double supply_temp; // 进水温度，瞬时值',
	private Double return_temp; // 回水温度，瞬时值',
	private Double open_ratio; // 开启率',
	private Short opening;
	
	public TblHouseHolderData() {
		super();
	}
		

	/**
	 * @param id
	 * @param comm_type
	 * @param dtu_code
	 * @param comm_address
	 * @param collect_time
	 * @param comm_status
	 * @param valve_state
	 * @param open_time
	 * @param sharing_heat
	 * @param set_temper
	 * @param average_temper
	 * @param accumulated_heat
	 * @param supply_temp
	 * @param return_temp
	 * @param open_ratio
	 */
	public TblHouseHolderData(Long id, Integer comm_type, String dtu_code, String comm_address, Long collect_time,
			Integer comm_status, Integer valve_state, Long open_time, Double sharing_heat, Double set_temper,
			Double average_temper, Double accumulated_heat, Double supply_temp, Double return_temp, Double open_ratio) {
		super();
		this.id = id;
		this.comm_type = comm_type;
		this.dtu_code = dtu_code;
		this.comm_address = comm_address;
		this.collect_time = collect_time;
		this.comm_status = comm_status;
		this.valve_state = valve_state;
		this.open_time = open_time;
		this.sharing_heat = sharing_heat;
		this.set_temper = set_temper;
		this.average_temper = average_temper;
		this.accumulated_heat = accumulated_heat;
		this.supply_temp = supply_temp;
		this.return_temp = return_temp;
		this.open_ratio = open_ratio;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getComm_type() {
		return comm_type;
	}

	public void setComm_type(Integer comm_type) {
		this.comm_type = comm_type;
	}

	public String getDtu_code() {
		return dtu_code;
	}

	public void setDtu_code(String dtu_code) {
		this.dtu_code = dtu_code;
	}

	public String getComm_address() {
		return comm_address;
	}

	public void setComm_address(String comm_address) {
		this.comm_address = comm_address;
	}

	public Long getCollect_time() {
		return collect_time;
	}

	public void setCollect_time(Long collect_time) {
		this.collect_time = collect_time;
	}

	public Integer getComm_status() {
		return comm_status;
	}

	public void setComm_status(Integer comm_status) {
		this.comm_status = comm_status;
	}

	public Integer getValve_state() {
		return valve_state;
	}

	public void setValve_state(Integer valve_state) {
		this.valve_state = valve_state;
	}

	public Long getOpen_time() {
		return open_time;
	}

	public void setOpen_time(Long open_time) {
		this.open_time = open_time;
	}

	public Double getSharing_heat() {
		return sharing_heat;
	}

	public void setSharing_heat(Double sharing_heat) {
		this.sharing_heat = sharing_heat;
	}

	public Double getSet_temper() {
		return set_temper;
	}

	public void setSet_temper(Double set_temper) {
		this.set_temper = set_temper;
	}

	public Double getAverage_temper() {
		return average_temper;
	}

	public void setAverage_temper(Double average_temper) {
		this.average_temper = average_temper;
	}

	public Double getAccumulated_heat() {
		return accumulated_heat;
	}

	public void setAccumulated_heat(Double accumulated_heat) {
		this.accumulated_heat = accumulated_heat;
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

	public Double getOpen_ratio() {
		return open_ratio;
	}

	public void setOpen_ratio(Double open_ratio) {
		this.open_ratio = open_ratio;
	}

	public Short getOpening() {
		return opening;
	}

	public void setOpening(Short opening) {
		this.opening = opening;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TblHouseHolderData [id=" + id + ", comm_type=" + comm_type + ", dtu_code=" + dtu_code
				+ ", comm_address=" + comm_address + ", collect_time=" + collect_time + ", comm_status=" + comm_status
				+ ", valve_state=" + valve_state + ", open_time=" + open_time + ", sharing_heat=" + sharing_heat
				+ ", set_temper=" + set_temper + ", average_temper=" + average_temper + ", accumulated_heat="
				+ accumulated_heat + ", supply_temp=" + supply_temp + ", return_temp=" + return_temp + ", open_ratio="
				+ open_ratio + ", opening=" + opening + "]";
	}
	
	
}
