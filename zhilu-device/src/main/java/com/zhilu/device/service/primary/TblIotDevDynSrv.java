/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月21日 上午9:00:21 * 
*/ 
package com.zhilu.device.service.primary;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.zhilu.device.bean.primary.TblIotDeviceDyn;
import com.zhilu.device.repository.primary.TblIotDevDynRepo;
@Service
public class TblIotDevDynSrv {
	
	@Autowired
    private TblIotDevDynRepo tblIotDevDynRepo;
	
	@Transactional
	@Modifying
	public void saveDevicesDyn(List<TblIotDeviceDyn> devices) {
		 tblIotDevDynRepo.save(devices);
	}
	
	@Transactional
	@Modifying
	public void deleteById(String id) {
		 tblIotDevDynRepo.deleteTblIotDeviceDynByDeviceid(id);
	}
	
	public TblIotDeviceDyn findById(String id) {
		TblIotDeviceDyn rs = tblIotDevDynRepo.findTblIotDeviceDynByDeviceid(id);
		return rs;
	}
}
