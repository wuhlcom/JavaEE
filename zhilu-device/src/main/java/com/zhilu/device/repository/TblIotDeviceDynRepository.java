package com.zhilu.device.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zhilu.device.bean.TblIotDeviceDyn;

public interface TblIotDeviceDynRepository
		extends JpaRepository<TblIotDeviceDyn, String>, JpaSpecificationExecutor<TblIotDeviceDyn> {
	
}