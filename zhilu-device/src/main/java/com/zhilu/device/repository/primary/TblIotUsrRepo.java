package com.zhilu.device.repository.primary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zhilu.device.bean.primary.TblIotUser;

public interface TblIotUsrRepo extends JpaRepository<TblIotUser, String>, JpaSpecificationExecutor<TblIotUser> {
	List<TblIotUser> findTblIotUserByUserid(String userid);
}
