package com.zhilu.device.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zhilu.device.bean.TblIotDeviceDyn;
import com.zhilu.device.bean.TblIotProductInfo;

public interface TblIotProductRepo
		extends JpaRepository<TblIotProductInfo, String>, JpaSpecificationExecutor<TblIotProductInfo> {

	List<TblIotProductInfo> findTblIotProductInfoById(String prodctId);

}
