package com.dazk.db.model;

import java.util.Date;

import javax.persistence.Table;

@Table(name="import_exception_detail")
public class ImportException extends BaseEntity {
	
	private String batchid;
	private String user_id;
	private String import_type;
	private String line_tag;
	private String error_msg;
	private String remark;
	private Date creat_time;
	
	
	public String getBatchid() {
		return batchid;
	}
	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getImport_type() {
		return import_type;
	}
	public void setImport_type(String import_type) {
		this.import_type = import_type;
	}
	public String getLine_tag() {
		return line_tag;
	}
	public void setLine_tag(String line_tag) {
		this.line_tag = line_tag;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreat_time() {
		return creat_time;
	}
	public void setCreat_time(Date creat_time) {
		this.creat_time = creat_time;
	}
	
}
