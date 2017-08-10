package com.zhilu.device.service.secondary;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhilu.device.bean.secondary.LoraGateway;
import com.zhilu.device.repository.secondary.LoraGwRepo;

@Service
public class LoraGwSrv {

	@Autowired
	private LoraGwRepo loraGwRepo;
	
	public int queryLrGw(String mac){	
		int exist = loraGwRepo.countLoraGatewayByMac(mac);
		if (exist>0){
			return 0;
		}
		return exist;
	}
	
	@Transactional
	@Modifying
	public void saveLrGw(List<LoraGateway> devices) {
		loraGwRepo.save(devices);
	}	
	
	public List<LoraGateway> findLrGwByUuid(String uuid){	
		List<LoraGateway> lrGw = loraGwRepo.findLoraGatewayByUuid(uuid);		
		return lrGw;
	}
	
	public int countGw(String eui) {
		int num = loraGwRepo.countLoraGatewayByMac(eui);
		return num;
	}
	
	public void delDevByMac(String mac) {
		loraGwRepo.deleteByMac(mac);
	}
}
