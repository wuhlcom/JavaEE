package com.zhilu.device.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zhilu.device.bean.TblIotDeviceBasic;

public interface TblIotDeviceBasicRepository
		extends JpaRepository<TblIotDeviceBasic, String>, JpaSpecificationExecutor<TblIotDeviceBasic> {
	
}
