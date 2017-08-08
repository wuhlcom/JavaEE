package com.zhilu.device.service.secondary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zhilu.device.repository.secondary.LoraGwRepo;

@Service
public class LoraGwSrv {

	@Autowired
	private LoraGwRepo loraGwRepo;
	
	public int addLoraGw(JSONObject json){
		return 0;
				
	}


}
