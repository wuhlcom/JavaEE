package com.zhilu.device.util;

public class ResultCode {	

		private int errcode;
		private Object devices;
		
		public ResultCode(int ErrCode, Object Devices)
		{
			this.errcode = ErrCode;				
			this.devices = Devices;
		}		
		
		/**
		 * @return the devices
		 */
		public Object getDevices() {
			return devices;
		}

		/**
		 * @param devices the devices to set
		 */
		public void setDevices(Object devices) {
			this.devices = devices;
		}		
		
		public int getErrcode() {
			return errcode;
		}
		public void setErrcode(int errcode) {
			this.errcode = errcode;
		}
	
}
