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
	public void deleteById(String id) {
		tblIotDevBasicRepo.deleteTblIotDeviceBasicByDeviceid(id);
	}
	
	public List<TblIotDeviceBasic> findById(String id) {
		List<TblIotDeviceBasic> rs = tblIotDevBasicRepo.findTblIotDeviceBasicByDeviceid(id);
		return rs;
	}

}
