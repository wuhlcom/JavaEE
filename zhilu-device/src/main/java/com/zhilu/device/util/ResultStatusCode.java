package com.zhilu.device.util;

public enum ResultStatusCode {
	
	OK(0, "OK"),	
	SYSTEM_ERR(10001, "System Error"),
	
	TOKEN_EMP(60003, "AccessToken为空"),
    TOKEN_ERR(60004, "无效的AccessToken或过期"),
    TOKEN_EXP(60005, "无效的AccessToken或过期"),
    
    UID_EMP(23001, "用户ID不能为空"),
    UID_NOT_EXISTED(23002, "用户ID不存在"),
    
    PROID_EMP(70002, "产品ID为空"),
    PROID_FORMAT_ERR(70003, "产品ID格式错误"),
    PROID_NOT_EXISTED(70004, "产品ID不存在"),//whl add
	PROTOCOL_ERR(70005,"协议类型错误"), //whl add
    
	DEVID_EMP(40002, "设备ID为空"),
	DEVID_NOT_EXISTED(40003, "设备不存在"),
	DEVID_EXISTED(40004, "设备已存在"),//whl add
	
	USER_NOT_EXITED(1001, "用户不存在");
	

	
	
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