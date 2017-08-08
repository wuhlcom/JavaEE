package com.zhilu.device.service.primary;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhilu.device.bean.primary.TblIotProductInfo;
import com.zhilu.device.repository.primary.TblIotProductRepo;


@Service
public class TbIotProductSrv {
	@Autowired
	private TblIotProductRepo tblIotProRepo;	
	
	public List<TblIotProductInfo> findProductById(String prodctId){
		List<TblIotProductInfo> products = tblIotProRepo.findTblIotProductInfoById(prodctId);
		return products;
	}

}
