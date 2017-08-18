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

// 热表当前数据表
@Entity()
@Table(name = "zl_building_calorimeter")
public class TblBuildingCalorimeter {
	@Id
	private int id;								// 自增ID',
	private String code;						// 编号',
	private int comm_type;					// 通信类型 1-有线 0-lora',
	private String dtu_code;					// dtu编号',  
	private String comm_address;				// 通信地址',
	private int period_setting;				// 上报周期',
	private int collect_time;				// 采集时间',
	private double supply_temp;					// 进水温度，瞬时值',  
	private double return_temp;					// 回水温度，瞬时值',   
	private double temper_diff;					// 温差',
	private double use_flow;					// 瞬时流量，瞬时值',  
	private double total_flow;					// 累计流量',   
	private double use_heat;					// 瞬时热量，瞬时值',
	private double period_heat;					// 分摊周期使用热量',  
	private double collect_heat;				// 供热季使用热量',   
	private double value;						// 热表读数',    
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
	public double getSupply_temp() {
		return supply_temp;
	}
	public void setSupply_temp(double supply_temp) {
		this.supply_temp = supply_temp;
	}
	public double getReturn_temp() {
		return return_temp;
	}
	public void setReturn_temp(double return_temp) {
		this.return_temp = return_temp;
	}
	public double getTemper_diff() {
		return temper_diff;
	}
	public void setTemper_diff(double temper_diff) {
		this.temper_diff = temper_diff;
	}
	public double getUse_flow() {
		return use_flow;
	}
	public void setUse_flow(double use_flow) {
		this.use_flow = use_flow;
	}
	public double getTotal_flow() {
		return total_flow;
	}
	public void setTotal_flow(double total_flow) {
		this.total_flow = total_flow;
	}
	public double getUse_heat() {
		return use_heat;
	}
	public void setUse_heat(double use_heat) {
		this.use_heat = use_heat;
	}
	public double getPeriod_heat() {
		return period_heat;
	}
	public void setPeriod_heat(double period_heat) {
		this.period_heat = period_heat;
	}
	public double getCollect_heat() {
		return collect_heat;
	}
	public void setCollect_heat(double collect_heat) {
		this.collect_heat = collect_heat;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}

}
