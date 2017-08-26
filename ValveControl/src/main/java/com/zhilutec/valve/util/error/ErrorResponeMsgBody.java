package com.zhilutec.valve.util.error;
import com.zhilutec.valve.util.error.ErrorCode;

public class ErrorResponeMsgBody {
	private int errcode;
	private String errdesc;
	
	public ErrorResponeMsgBody() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ErrorResponeMsgBody(ErrorCode errcode) {
		this.errcode = errcode.getCode();
		this.errdesc = errcode.getErrdesc();
	}
	

	public ErrorResponeMsgBody(int errCode, String errdesc) {
		this.errcode = errCode;
		this.errdesc = errdesc;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errdesc;
	}

	public void setErrmsg(String errmsg) {
		this.errdesc = errmsg;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ErrorResponeMsgBody [errcode=" + errcode + ", errdesc=" + errdesc + "]";
	}
	
	
}
