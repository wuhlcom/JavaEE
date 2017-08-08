package com.zhilu.device.service.secondary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zhilu.device.repository.secondary.LoraDevRepo;

@Service
public class LoraDeviceSrv {

	@Autowired
	private LoraDevRepo loraDevRepo;
	
	public int addLoraDev(JSONObject json){
		return 0;
				
	}


}
