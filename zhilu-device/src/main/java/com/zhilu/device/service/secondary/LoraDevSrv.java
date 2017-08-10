package com.zhilu.device.service.secondary;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zhilu.device.bean.secondary.LoraDevice;
import com.zhilu.device.repository.secondary.LoraDevRepo;

@Service
public class LoraDevSrv {

	@Autowired
	private LoraDevRepo loraDevRepo;

	public int queryLrDev(String eui) {
		int exist = loraDevRepo.countLoraDeviceByDeveui(eui);
		if (exist > 0) {
			return 0;
		}
		return exist;
	}

	@Transactional
	@Modifying
	public void saveLrDev(List<LoraDevice> devices) {
		loraDevRepo.save(devices);
	}

	public List<LoraDevice> findLrDevByUuid(String uuid) {
		List<LoraDevice> devs = loraDevRepo.findLoraDeviceByUuid(uuid);
		return devs;
	}

	public int countDev(String eui) {
		int num = loraDevRepo.countLoraDeviceByDeveui(eui);
		return num;
	}

	public void delDevByEui(String deveui) {
		loraDevRepo.deleteByDeveui(deveui);
	}

}
