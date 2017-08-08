package com.zhilu.device.util.errorcode;

public enum ResultStatusCode {
	
	OK(0, "OK"),	
	UNKNOWN_ERR(80001, "未知错误"),
	SYSTEM_ERR(80002, "未知错误"), //whl add
	
	TOKEN_EMP(60003, "AccessToken为空"),
    TOKEN_ERR(60004, "无效的AccessToken或过期"),
    TOKEN_EXP(60005, "无效的AccessToken或过期"),
    
    UID_EMP(23001, "用户ID不能为空"),
    UID_NOT_EXISTED(23002, "用户ID不存在"),
    UID_ERR(23003, "用户ID格式错误"), //whl add
    
    PROID_EMP(70002, "产品ID为空"),
    PROID_FORMAT_ERR(70003, "产品ID格式错误"),
    PROID_NOT_EXISTED(70004, "产品ID不存在"),//whl add
	PROTOCOL_ERR(70005,"协议类型错误"), //whl add
    
	DEVID_EMP(40002, "设备ID为空"),
	DEVID_NOT_EXISTED(40003, "设备不存在"),
	DEVID_EXISTED(40004, "设备存在"),//whl add
	DEVNAME_ERR(40005, "设备名称错误"),//whl add
	
	ONLINE_STATUS_ERR(60001, "查谒不到符合状态的设备"),//whl add
	ONLINE_TIME_ERR(60002, "查询不到对应时间的设备"),//whl add
	
	TYPE_ERR(50001, "查询类型错误"),//whl add
	PAGE_ERR(50002, "查询页码错误"),//whl add
	ROWS_ERR(50003, "显示内容数量错误"),//whl add
	
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