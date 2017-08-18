package com.zhilutec.valve.util.error;

import com.zhilutec.valve.util.error.ErrorCode;

public class GlobalErrorException extends Exception {
	
	private ErrorCode errCode;
	
	public GlobalErrorException(ErrorCode errcode) {
		this.errCode = errcode;
	}
	
	public ErrorCode getErrorCode() {
		return errCode;
	}
	
	public void setErrorCode(ErrorCode errcode) {
		this.errCode = errcode;
	}
}
