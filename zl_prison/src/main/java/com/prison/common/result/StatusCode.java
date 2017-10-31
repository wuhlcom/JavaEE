package com.prison.common.result;

public enum StatusCode {

	OK(0, "OK"),
	// 成功
	// public static final String success = "10001";
	SUCCESS(10001, "成功"),
	Failed(10002, "失败"),
	// 无对应数据
	// public static final String noData = "10002";
	NODATA_ERR(10003, "无对应数据"),
	// 数据重复插入
	// public static final String repetition = "10003";
	REPETITION_ERR(10004, "数据重复插入"),
	// 未知错误
	// public static final String unknowErr = "10004";
	UNKNOW_ERR(10005, "未知错误"),
	// 程序错误
	// public static final String routineErr = "10005";
	ROUTINE_ERR(10006, "程序错误"),
	// 无权限
	// public static final String noPermission = "10006";
	PERMISSION_ERR(10007, "无权限"),
	// 参数错误
	// public static final String parameErr = "10007";
	PARAME_ERR(10008, "参数错误"),
	TOKEN_ERR(10009,"Token错误"),
	ROLE_NOT_EXIST(10010,"角色不存在");

	private int code;
	private String errmsg;

	private StatusCode(int errode, String errMsg) {
		this.code = errode;
		this.errmsg = errMsg;
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