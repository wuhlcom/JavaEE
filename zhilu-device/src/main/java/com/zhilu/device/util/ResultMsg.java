package com.zhilu.device.util;

public class ResultMsg implements Result {

	private int errcode;
	private String errmsg;
	private Object info;

	public ResultMsg(int errCode, Object info) {
		this.errcode = errCode;
		this.info = info;
	}

	public ResultMsg(int errCode, String errMsg, Object info) {
		this.errcode = errCode;
		this.errmsg = errMsg;
		this.info = info;
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

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResultMsg [errcode=" + errcode + ", errmsg=" + errmsg + ", info=" + info + "]";
	}

}
