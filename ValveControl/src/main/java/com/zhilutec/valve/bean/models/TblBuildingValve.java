package com.zhilutec.valve.bean.models;

import java.sql.Timestamp;
import java.util.Date;

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

import org.springframework.beans.factory.annotation.Autowired;

@Entity()
@Table(name = "zl_building_valve")
public class TblBuildingValve {
	
	@Id
	private String comm_address; // 通信地址',
	private Integer comm_type; // 通信类型 1-有线 0-lora',
	private String dtu_code; // dtu编号',
	private Integer period_setting; // 上报周期',
	private Long collect_time; // 采集时间',
	private Long opening; // 开度',
	
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
	public Integer getPeriod_setting() {
		return period_setting;
	}
	public void setPeriod_setting(Integer period_setting) {
		this.period_setting = period_setting;
	}
	public Long getCollect_time() {
		return collect_time;
	}
	public void setCollect_time(Long collect_time) {
		this.collect_time = collect_time;
	}
	public Long getOpening() {
		return opening;
	}
	public void setOpening(Long opening) {
		this.opening = opening;
	}
	
}
