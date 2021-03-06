package com.zhilu.demo.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class Cat {
	//指定主键为id
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String catName;
	private int catAge;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the catName
	 */
	public String getCatName() {
		return catName;
	}

	/**
	 * @param catName the catName to set
	 */
	public void setCatName(String catName) {
		this.catName = catName;
	}

	/**
	 * @return the catAge
	 */
	public int getCatAge() {
		return catAge;
	}

	/**
	 * @param i the catAge to set
	 */
	public void setCatAge(int catAge) {
		this.catAge = catAge;
	}
	
}
