package com.zhilu.demo.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author Administrator
 *1. 使用@Entity进行实体类的持久化操作
 *
 *2. 如何指定主键 以及主键的生成策略
 */
@Entity
public class Cat {
	/*
	 * 
	 */
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String catName;
	
	private int catAge;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public int getCatAge() {
		return catAge;
	}
	public void setCatAge(int catAge) {
		this.catAge = catAge;
	}
	
	
}
