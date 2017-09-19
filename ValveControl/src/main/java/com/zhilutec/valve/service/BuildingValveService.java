/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月28日 下午3:23:00 * 
*/
package com.zhilutec.valve.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhilutec.valve.bean.models.TblBuildingValve;
import com.zhilutec.valve.repository.BuildingValveRepo;

@Service
public class BuildingValveService {
	@Autowired
	private BuildingValveRepo buildingValveRepo;

	// `code` varchar(16) NOT NULL COMMENT '编号',
	// `period_setting` int(11) DEFAULT '-1' COMMENT '上报周期',
	// 4.4.3 批量查询楼栋调节阀最新数据
	public List<TblBuildingValve> getBuildingVavlesByDeveuis(String params) {
		JSONObject json = JSON.parseObject(params);
		JSONArray deveuis = json.getJSONArray("comm_addresses");
		List<TblBuildingValve> devs = buildingValveRepo.getValves(deveuis);
		for (int i = 0; i < devs.size(); i++) {
			TblBuildingValve dev = devs.get(i);			
			dev.setPeriod_setting(null);
		}
		return devs;
	}
}
