package com.zhilu.device.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhilu.device.bean.TblIotProductInfo;
import com.zhilu.device.bean.TblIotUser;
import com.zhilu.device.repository.TblIotProductRepo;


@Service
public class TbIotProductSrv {
	@Autowired
	private TblIotProductRepo tblIotProRepo;	
	
	public List<TblIotProductInfo> findProductById(String prodctId){
		List<TblIotProductInfo> products = tblIotProRepo.findTblIotProductInfoById(prodctId);
		return products;
	}

}
