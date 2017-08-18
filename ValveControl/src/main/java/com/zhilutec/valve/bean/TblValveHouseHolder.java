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
@Table(name = "zl_householder")
public class TblValveHouseHolder {
	@Id
	private int id = 0;
	private String house_code;				// 住户编号
	private int comm_type;					// 通信类型 1-有线 0-lora
	private String dtu_code;                 // dtu编号
	private String comm_address;             // 通信地址,
	private int period_setting;              // 上报周期',
	private int collect_time;				 // 采集时间',
	private int comm_status;                 // 通信状态 0:正常 1:异常',
	private int valve_state;				 //	阀门状态 0:开 1:关 其他:异常',  
	private int open_time;				     // 分摊周期开阀时间',  
	private double sharing_heat;             // 分摊周期热量',  
	private double set_temper;  			 // 设定温度，瞬时值',  
	private double average_temper;			 // 平均温度，瞬时值',
	private double accumulated_heat;		 //  本供热季累计使用热量',  
	private double supply_temp;				//  进水温度，瞬时值',  
	private double return_temp;              // 回水温度，瞬时值',  
	private double open_ratio;               // 开启率',
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHouse_code() {
		return house_code;
	}
	public void setHouse_code(String house_code) {
		this.house_code = house_code;
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
	public int getComm_status() {
		return comm_status;
	}
	public void setComm_status(int comm_status) {
		this.comm_status = comm_status;
	}
	public int getValve_state() {
		return valve_state;
	}
	public void setValve_state(int valve_state) {
		this.valve_state = valve_state;
	}
	public int getOpen_time() {
		return open_time;
	}
	public void setOpen_time(int open_time) {
		this.open_time = open_time;
	}
	public double getSharing_heat() {
		return sharing_heat;
	}
	public void setSharing_heat(double sharing_heat) {
		this.sharing_heat = sharing_heat;
	}
	public double getSet_temper() {
		return set_temper;
	}
	public void setSet_temper(double set_temper) {
		this.set_temper = set_temper;
	}
	public double getAverage_temper() {
		return average_temper;
	}
	public void setAverage_temper(double average_temper) {
		this.average_temper = average_temper;
	}
	public double getAccumulated_heat() {
		return accumulated_heat;
	}
	public void setAccumulated_heat(double accumulated_heat) {
		this.accumulated_heat = accumulated_heat;
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
	public double getOpen_ratio() {
		return open_ratio;
	}
	public void setOpen_ratio(double open_ratio) {
		this.open_ratio = open_ratio;
	}
	
}
