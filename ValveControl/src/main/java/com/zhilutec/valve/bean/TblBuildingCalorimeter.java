package com.zhilutec.valve.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 热表当前数据表
@Entity()
@Table(name = "zl_building_calorimeter")
public class TblBuildingCalorimeter {
	@Id
	private Long id;						    // 自增ID',
	private Integer comm_type;					// 通信类型 1-有线 0-lora',
	private String dtu_code;					// dtu编号',
	@Column(name="comm_address")
	private String comm_address;				// 通信地址',
	private Long period_setting;				// 上报周期',
	private Long collect_time;				// 采集时间',
//	comm_status` tinyint(4) NOT NULL,
	private Integer comm_status;
	private Double supply_temp;					// 进水温度，瞬时值',  
	private Double return_temp;					// 回水温度，瞬时值',   
	private Double temper_diff;					// 温差',
	private Double use_flow;					// 瞬时流量，瞬时值',  
	private Double total_flow;					// 累计流量',   
	private Double use_heat;					// 瞬时热量，瞬时值',
	private Double period_heat;					// 分摊周期使用热量',  
	private Double collect_heat;				// 供热季使用热量',   
	private Double value;						// 热表读数',   
//	ifpush` tinyint(4) DEFAULT NULL,
	private Integer ifpush;
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
	public Long getPeriod_setting() {
		return period_setting;
	}
	public void setPeriod_setting(Long period_setting) {
		this.period_setting = period_setting;
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
	public Double getTemper_diff() {
		return temper_diff;
	}
	public void setTemper_diff(Double temper_diff) {
		this.temper_diff = temper_diff;
	}
	public Double getUse_flow() {
		return use_flow;
	}
	public void setUse_flow(Double use_flow) {
		this.use_flow = use_flow;
	}
	public Double getTotal_flow() {
		return total_flow;
	}
	public void setTotal_flow(Double total_flow) {
		this.total_flow = total_flow;
	}
	public Double getUse_heat() {
		return use_heat;
	}
	public void setUse_heat(Double use_heat) {
		this.use_heat = use_heat;
	}
	public Double getPeriod_heat() {
		return period_heat;
	}
	public void setPeriod_heat(Double period_heat) {
		this.period_heat = period_heat;
	}
	public Double getCollect_heat() {
		return collect_heat;
	}
	public void setCollect_heat(Double collect_heat) {
		this.collect_heat = collect_heat;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Integer getIfpush() {
		return ifpush;
	}
	public void setIfpush(Integer ifpush) {
		this.ifpush = ifpush;
	}
	
}
