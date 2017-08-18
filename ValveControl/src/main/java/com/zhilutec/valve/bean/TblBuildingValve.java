package com.zhilutec.valve.bean;


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
	private int id;								// 自增ID',
	private String code;						// 编号',
	private int comm_type;						// 通信类型 1-有线 0-lora',
	private String dtu_code;					// dtu编号',  
	private String comm_address;				// 通信地址',
	private int period_setting;					// 上报周期',
	private int collect_time;					// 采集时间',
	private int openning;						// 开度',
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getComm_address() {
		return comm_address;
	}
	public void setComm_address(String comm_address) {
		this.comm_address = comm_address;
	}
	public int getPeriod_setting() {
		return period_setting;
	}
	public void setPeriod_setting(int period_setting) {
		this.period_setting = period_setting;
	}
	public int getCollect_time() {
		return collect_time;
	}
	public void setCollect_time(int collect_time) {
		this.collect_time = collect_time;
	}
	public int getOpenning() {
		return openning;
	}
	public void setOpenning(int openning) {
		this.openning = openning;
	}
}
