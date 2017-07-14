package com.ann.test;

public interface Person {
	public String name();
	public int age();
	
	/*
	 * 	表示方法过时
	 */
	@Deprecated
	public String sing();
}
