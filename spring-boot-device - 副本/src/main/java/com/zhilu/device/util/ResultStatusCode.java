package com.zhilu.device.util;

public enum ResultStatusCode {
	OK(0, "OK"),
	SYSTEM_ERR(30001, "System error");
	
	private int errcode;
	private String errmsg;
	
	private ResultStatusCode(int Errode, String ErrMsg)
	{
		this.errcode = Errode;
		this.errmsg = ErrMsg;
	}
	
	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
}