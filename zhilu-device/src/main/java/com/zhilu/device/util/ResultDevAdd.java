package com.zhilu.device.util;

import java.util.List;

public class ResultDevAdd implements Result {
	
	private int code;
	private Object devices;
	

	public ResultDevAdd(int code, Object devices) {
		this.code = code;
		this.devices = devices;
	}
	
	public ResultDevAdd(int code, List<?> devices) {
		this.code = code;
		this.devices = devices;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
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
		return "ResultDevAdd [code=" + code + ", devices=" + devices + "]";
	}

}
