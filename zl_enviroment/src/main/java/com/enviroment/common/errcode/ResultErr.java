/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月26日 下午4:22:56 * 
*/ 
package com.enviroment.common.errcode;

public class ResultErr implements Result{
	private int errcode;
	private String msg;

	public ResultErr() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param errcode
	 * @param msg
	 */
	public ResultErr(int errcode, String msg) {
		super();
		this.errcode = errcode;
		this.msg = msg;
	}
	

	public int getErrcode() {
		return errcode;
	}

	
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
