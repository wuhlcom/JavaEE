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
public class TblHouseHolder {
	@Id
	private Long id;
	private Integer comm_type; // 通信类型 1-有线 0-lora
	@Column(name = "dtu_code")
	private String dtu_code; // dtu编号
	@Column(name = "comm_address")
	private String comm_address; // 通信地址,
	private Long period_setting = -1L; // 上报周期',
	private Long collect_time; // 采集时间',
	private Integer comm_status; // 通信状态 0:正常 1:异常',
	private Integer valve_state; // 阀门状态 0:开 1:关 其他:异常',
	private Long open_time = 0L; // 分摊周期开阀时间',
	private Double sharing_heat = 0.0; // 分摊周期热量',
	private Double set_temper = 0.0; // 设定温度，瞬时值',
	private Double average_temper = 0.0; // 平均温度，瞬时值',
	private Double accumulated_heat = 0.0; // 本供热季累计使用热量',
	private Double supply_temp = 0.0; // 进水温度，瞬时值',
	private Double return_temp = 0.0; // 回水温度，瞬时值',
	private Double open_ratio = 0.0; // 开启率',
	private Integer ifpush;
	@Column(name = "heating_season_begin")
	private String heating_season_begin;
	@Column(name = "heating_season_end")
	private String heating_season_end;

	
	/**
	 * 
	 */
	public TblHouseHolder() {
		super();
		// TODO Auto-generated constructor stub
	}	

	public TblHouseHolder(boolean flag) {
		super();
		this.id = null;
		this.comm_type = null;
		this.dtu_code = null;
		this.comm_address = null;
		this.period_setting = null;
		this.collect_time = null;
		this.comm_status = null;
		this.valve_state = null;
		this.open_time = null;
		this.sharing_heat = null;
		this.set_temper = null;
		this.average_temper = null;
		this.accumulated_heat = null;
		this.supply_temp = null;
		this.return_temp = null;
		this.open_ratio = null;
		this.ifpush = null;
		this.heating_season_begin = null;
		this.heating_season_begin = null;
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

	public Integer getIfpush() {
		return ifpush;
	}

	public void setIfpush(Integer ifpush) {
		this.ifpush = ifpush;
	}

	public String getHeating_season_begin() {
		return heating_season_begin;
	}

	public void setHeating_season_begin(String heating_season_begin) {
		this.heating_season_begin = heating_season_begin;
	}

	public String getHeating_season_end() {
		return heating_season_end;
	}

	public void setHeating_season_end(String heating_season_end) {
		this.heating_season_end = heating_season_end;
	}

}
