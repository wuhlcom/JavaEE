package com.zhilutec.valve.util;

import com.zhilutec.valve.util.error.ErrorCode;

public class ResponeMsgBody {
	private int errcode;
	private Object data;
	
	public ResponeMsgBody(ErrorCode errcode) {
		this.errcode = errcode.getCode();
	}
	
	public ResponeMsgBody(Object data) {
		this.errcode = ErrorCode.OK.getCode();
		this.data = data;
	}

	public ResponeMsgBody(int errCode, String errdesc) {
		this.errcode = errCode;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
