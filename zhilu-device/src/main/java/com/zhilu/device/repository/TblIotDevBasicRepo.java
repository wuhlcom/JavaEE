package com.zhilu.device.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zhilu.device.bean.TblIotDeviceBasic;

public interface TblIotDevBasicRepo
		extends JpaRepository<TblIotDeviceBasic, String>, JpaSpecificationExecutor<TblIotDeviceBasic> {
	void deleteTblIotDeviceBasicByDeviceid(String id);
	List<TblIotDeviceBasic> findTblIotDeviceBasicByDeviceid(String id);
}
