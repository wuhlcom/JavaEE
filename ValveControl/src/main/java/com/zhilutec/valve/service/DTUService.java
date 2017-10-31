package com.zhilutec.valve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhilutec.valve.bean.models.DTU;
import com.zhilutec.valve.repository.DTURepo;

import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional
public class DTUService {
	@Autowired
	private DTURepo dtuRepo;
	
	public DTU findOne(String comm_address) {
		return dtuRepo.findOne(comm_address);
	}
	
	public void save(DTU obj) {
		dtuRepo.saveAndFlush(obj);
	}
	
	// `code` varchar(16) NOT NULL COMMENT '编号',
	// `period_setting` int(11) DEFAULT '-1' COMMENT '上报周期',
	// 4.4.3 批量查询楼栋调节阀最新数据
	public List<DTU> getDTUsByDeveuis(String params) {
		JSONObject json = JSON.parseObject(params);
		JSONArray deveuis = json.getJSONArray("comm_addresses");
		List<DTU> devs = dtuRepo.getValves(deveuis);
//		for (int i = 0; i < devs.size(); i++) {
//			DTU dev = devs.get(i);			
//		}
		return devs;
	}
}
