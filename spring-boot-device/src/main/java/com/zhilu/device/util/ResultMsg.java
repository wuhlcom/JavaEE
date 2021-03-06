package com.zhilu.device.util;

public class ResultMsg {	

		private int errcode;
		private String errmsg;
		private Object devices;
		
		public ResultMsg(int ErrCode, Object Devices)
		{
			this.errcode = ErrCode;				
			this.devices = Devices;
		}
		
		public ResultMsg(int ErrCode, String ErrMsg, Object devices)
		{
			this.errcode = ErrCode;			
			this.errmsg = ErrMsg;
			this.devices = devices;
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
		public String getErrmsg() {
			return errmsg;
		}
		public void setErrmsg(String errmsg) {
			this.errmsg = errmsg;
		}
}
