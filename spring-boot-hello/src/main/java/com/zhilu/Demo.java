package com.zhilu;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Demo {
	private int id;
	private String name;
	private Date createTime;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
