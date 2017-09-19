package com.zhilu.device.util.errorcode;

import java.util.List;

public class ResultDevAdd implements Result {
	
	private int errcode;
	private Object devices;
	
	
	/**
	 * 
	 */
	public ResultDevAdd() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param errcode
	 * @param devices
	 */
	public ResultDevAdd(int errcode, Object devices) {
		super();
		this.errcode = errcode;
		this.devices = devices;
	}


	public int getErrcode() {
		return errcode;
	}


	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}


	public Object getDevices() {
		return devices;
	}


	public void setDevices(Object devices) {
		this.devices = devices;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResultDevAdd [errcode=" + errcode + ", devices=" + devices + "]";
	}
	
		

	

}
