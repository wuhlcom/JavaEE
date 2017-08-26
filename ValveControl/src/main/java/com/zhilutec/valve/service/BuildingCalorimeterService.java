/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月25日 上午9:51:53 * 
*/
package com.zhilutec.valve.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhilutec.valve.bean.TblBuildingCalorimeter;
import com.zhilutec.valve.bean.TblHouseHolder;
import com.zhilutec.valve.repository.BuildingCalorimeterRepo;

@Service
public class BuildingCalorimeterService {
	@Autowired
	private BuildingCalorimeterRepo buildingCaloriRepo;

	public final static Logger logger = LoggerFactory.getLogger(BuildingValveService.class);

	// id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
	// `period_setting` int(11) DEFAULT '-1' COMMENT '上报周期',
	// `comm_status` tinyint(4) NOT NULL,
	// `ifpush` tinyint(4) DEFAULT NULL,
	// 4.4.2 批量查询楼栋热表最新数据
	public List<TblBuildingCalorimeter> getCalorimeterByEuis(String params) {

		JSONObject json = JSON.parseObject(params);
		JSONArray deveuis = json.getJSONArray("comm_addresses");
		// List<TblValveHouseHolder> devList = new ArrayList<>();
		// 判断设备是否添加
		List<TblBuildingCalorimeter> devs = buildingCaloriRepo.getCalorimeters(deveuis);
		for (int i = 0; i < devs.size(); i++) {
			TblBuildingCalorimeter dev = devs.get(i);
			dev.setId(null);
			dev.setPeriod_setting(null);
			dev.setComm_status(null);
			dev.setIfpush(null);
		}
		return devs;
	}
}
