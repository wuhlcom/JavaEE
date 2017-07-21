package com.zhilu.device.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zhilu.device.bean.TblIotDeviceDyn;
import com.zhilu.device.bean.TblIotUser;

public interface TblIotUsrRepo extends JpaRepository<TblIotUser, String>, JpaSpecificationExecutor<TblIotUser> {
	List<TblIotUser> findTblIotUserByUserid(String userid);
}
