package com.zhilutec.valve.bean.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.annotation.JSONField;

@Entity()
@Table(name = "zl_building_valve_data")
public class TblBuildingValveData {
	@Id
	@JSONField(serialize = false)
	private Long id;
	private String comm_address;				// 通信地址',
	private int    comm_type;					// 通信类型 1-有线 0-lora',
	private String dtu_code;					// dtu编号',  
	private int    collect_time;				// 采集时间',
	private int    opening;					// 开度',
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getComm_address() {
		return comm_address;
	}
	public void setComm_address(String comm_address) {
		this.comm_address = comm_address;
	}
	public int getComm_type() {
		return comm_type;
	}
	public void setComm_type(int comm_type) {
		this.comm_type = comm_type;
	}
	public String getDtu_code() {
		return dtu_code;
	}
	public void setDtu_code(String dtu_code) {
		this.dtu_code = dtu_code;
	}
	public int getCollect_time() {
		return collect_time;
	}
	public void setCollect_time(int collect_time) {
		this.collect_time = collect_time;
	}
	public int getOpening() {
		return opening;
	}
	public void setOpening(int opening) {
		this.opening = opening;
	}
}
