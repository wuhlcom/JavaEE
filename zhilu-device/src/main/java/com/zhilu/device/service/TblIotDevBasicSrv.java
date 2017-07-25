/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月21日 上午8:57:56 * 
*/

package com.zhilu.device.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.zhilu.device.bean.TblIotDeviceBasic;
import com.zhilu.device.repository.TblIotDevBasicRepo;
@Service
public class TblIotDevBasicSrv {
	
	@Autowired
	private TblIotDevBasicRepo tblIotDevBasicRepo;

	@Transactional
	@Modifying
	public void saveDevicesBasic(List<TblIotDeviceBasic> devices) {
		tblIotDevBasicRepo.save(devices);
	}	
	
	@Transactional
	public void saveDevicesBasic(TblIotDeviceBasic device) {
		tblIotDevBasicRepo.save(device);
	}	


	@Transactional
	public void deleteById(String id) {
		tblIotDevBasicRepo.deleteTblIotDeviceBasicByDeviceid(id);
	}
	
	public TblIotDeviceBasic findById(String id) {
		TblIotDeviceBasic rs = tblIotDevBasicRepo.findTblIotDeviceBasicByDeviceid(id);
		return rs;
	}

	public TblIotDeviceBasic getById(String id) {
		TblIotDeviceBasic rs = tblIotDevBasicRepo.getTblIotDeviceBasicByDeviceid(id);
		return rs;
	}
}
