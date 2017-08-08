package com.zhilu.device.repository.primary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zhilu.device.bean.primary.TblIotProductInfo;

public interface TblIotProductRepo
		extends JpaRepository<TblIotProductInfo, String>, JpaSpecificationExecutor<TblIotProductInfo> {

	List<TblIotProductInfo> findTblIotProductInfoById(String prodctId);

}
