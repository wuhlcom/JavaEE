package com.zhilutec.valve.util.error;

public enum ErrorCode {
	
	OK(0, "OK"),	
	SYSTEM_ERR(-1, "系统错误"),
	JSON_FORMAT_ERROR(1001, "JSON消息格式错误"),
	PARAM_ERROR(1002, "参数错误"),
	PARAM_INCOMPLETE(1003, "参数不完整"),
	DEV_NOTFOUND(1004, "找不到该设备"),
	UNKNOW_ERR(1005, "未知错误"),
    
	DB_ERROR(2001, "数据库错误");
	
	
	private int code;
	private String errdesc;
	
	private ErrorCode(int Errode, String ErrDesc)
	{
		this.code = Errode;
		this.errdesc = ErrDesc;
	}
	
	private ErrorCode(ErrorCode errCode) {
		this.code = errCode.getCode();
		this.errdesc = errCode.getErrdesc();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getErrdesc() {
		return errdesc;
	}

	public void setErrdesc(String errdesc) {
		this.errdesc = errdesc;
	}
}
