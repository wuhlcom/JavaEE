package com.ann.test;

@Description("class ann")
public class Child implements Person {

	@Override
	@Description("method ann")
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int age() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String sing() {
		// TODO Auto-generated method stub
		return "sing";
	}
}
