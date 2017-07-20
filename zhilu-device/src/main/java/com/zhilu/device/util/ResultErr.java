/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月20日 上午11:20:06 * 
*/
package com.zhilu.device.util;

public class ResultErr implements Result {
	private int code;
	private String err_msg;

	/**
	 * @param code
	 * @param err_msg
	 */
	public ResultErr(int code, String err_msg) {
		super();
		this.code = code;
		this.err_msg = err_msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

}
