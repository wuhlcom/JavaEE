/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月21日 上午9:00:21 * 
*/ 
package com.zhilu.device.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhilu.device.bean.TblIotDeviceDyn;
import com.zhilu.device.repository.TblIotDevDynRepo;

public class TblIotDevDynSrv {
	
	@Autowired
    private TblIotDevDynRepo tblIotDevDynRepo;
	
	@Transactional
	public void saveDevicesDyn(List<TblIotDeviceDyn> devices) {
		 tblIotDevDynRepo.save(devices);
	}
}
