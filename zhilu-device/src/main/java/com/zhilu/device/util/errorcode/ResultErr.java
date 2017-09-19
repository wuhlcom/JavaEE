/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月20日 上午11:20:06 * 
*/
package com.zhilu.device.util.errorcode;

public class ResultErr implements Result {
	private int errcode;
	private String err_msg;
	/**
	 * 
	 */
	public ResultErr() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param errcode
	 * @param err_msg
	 */
	public ResultErr(int errcode, String err_msg) {
		super();
		this.errcode = errcode;
		this.err_msg = err_msg;
	}
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResultErr [errcode=" + errcode + ", err_msg=" + err_msg + "]";
	}

	
}
