/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月21日 上午8:57:56 * 
*/
package com.zhilu.device.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhilu.device.bean.TblIotDeviceBasic;
import com.zhilu.device.repository.TblIotDevBasicRepo;

public class TblIotDevBasicSrv {
	
	@Autowired
	private TblIotDevBasicRepo tblIotDevBasicRepo;

	@Transactional
	public void saveDevicesBasic(List<TblIotDeviceBasic> devices) {
		tblIotDevBasicRepo.save(devices);
	}

}
