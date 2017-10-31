package com.zhilutec.valve.bean.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "zl_dtu")
public class DTU {
	@Id
	String comm_address;                 // dtu编号
	Integer collect_time;                // 采集时间。上一次心跳时间
	
	public String getComm_address() {
		return comm_address;
	}

	public void setComm_address(String comm_address) {
		this.comm_address = comm_address;
	}

	public Integer getCollect_time() {
		return collect_time;
	}

	public void setCollect_time(Integer collect_time) {
		this.collect_time = collect_time;
	}
}
