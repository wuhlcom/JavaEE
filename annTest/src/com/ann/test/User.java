package com.ann.test;

public class User {
	/*
	 * 忽略警告
	 */
	@SuppressWarnings("deprecation")
	public String sing(){
		Person p =new Child();
		String s =p.sing();
		return s;
		
	}
}
