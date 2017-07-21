package com.zhilu.device.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhilu.device.bean.TblIotUser;
import com.zhilu.device.repository.TblIotUsrRepo;

@Service
public class TblIotUsrSrv {
	
	@Autowired
	private TblIotUsrRepo tblIotUsrRepo;

	public List <TblIotUser> findUserByUserid(String userid) {
		List<TblIotUser> users = tblIotUsrRepo.findTblIotUserByUserid(userid);
		return users;
	}	


}
