package com.zhilu.device.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zhilu.device.bean.primary.TblIotDeviceBasic;

public interface TblIotDevBasicRepo
		extends JpaRepository<TblIotDeviceBasic, String>, JpaSpecificationExecutor<TblIotDeviceBasic> {
	void deleteTblIotDeviceBasicByDeviceid(String id);

	TblIotDeviceBasic findTblIotDeviceBasicByDeviceid(String id);

	TblIotDeviceBasic getTblIotDeviceBasicByDeviceid(String id);
}
