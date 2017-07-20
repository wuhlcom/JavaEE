package com.zhilu.device.util;

public enum ResultStatusCode {
	
	OK(0, "OK"),	
	SYSTEM_ERR(30001, "System error"),
    TOKEN_ERR(60004, "无效的Token或过期");
	
	private int code;
	private String errmsg;
	
	private ResultStatusCode(int Errode, String ErrMsg)
	{
		this.code = Errode;
		this.errmsg = ErrMsg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	
	
}