package com.zhilutec.valve.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhilutec.valve.bean.TblBuildingCalorimeter;
import com.zhilutec.valve.bean.TblBuildingValve;
import com.zhilutec.valve.bean.TblBuildingValveData;
import com.zhilutec.valve.repository.BuildingValveRepo;
import com.zhilutec.valve.repository.BuildingValveDataRepo;

import com.zhilutec.valve.util.MultiRecQueryCondition;
import com.zhilutec.valve.util.error.ErrorCode;
import com.zhilutec.valve.util.error.GlobalErrorException;

@Service
public class BuildingValveService {

	@Autowired
	private BuildingValveDataRepo buildingValveDataRepo;
	@Autowired
	private BuildingValveRepo buildingValveRepo;

	public final static Logger logger = LoggerFactory.getLogger(BuildingValveService.class);

	public List<TblBuildingValveData> findByDevId(MultiRecQueryCondition condition) throws GlobalErrorException {

		List<TblBuildingValveData> historyDbList = new ArrayList<TblBuildingValveData>();
		try {
			historyDbList = buildingValveDataRepo.getPageDataById(condition.getCommAddress(), condition.getStartTime(),
					condition.getEndTime(), condition.getPageIndex() - 1, condition.getListRows());
		} catch (Exception e) {
			logger.error("failed to get records by condition", e);
			throw new GlobalErrorException(ErrorCode.DB_ERROR);
		}

		return historyDbList;
	}

	public int countRecByDevId(MultiRecQueryCondition condition) throws GlobalErrorException {

		int count = 0;
		try {
			count = buildingValveDataRepo.countRecByDevId(condition.getCommAddress(), condition.getStartTime(),
					condition.getEndTime());
		} catch (Exception e) {
			logger.error("failed to count householder history.", e);
			throw new GlobalErrorException(ErrorCode.DB_ERROR);
		}

		return count;
	}
	
	// `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
	// `code` varchar(16) NOT NULL COMMENT '编号',
	// `period_setting` int(11) DEFAULT '-1' COMMENT '上报周期',
	// 4.4.3 批量查询楼栋调节阀最新数据
	public List<TblBuildingValve> getBuildingVavlesByDeveuis(String params) {
		JSONObject json = JSON.parseObject(params);
		JSONArray deveuis = json.getJSONArray("comm_addresses");
		List<TblBuildingValve> devs = buildingValveRepo.getValves(deveuis);
		for (int i = 0; i < devs.size(); i++) {
			TblBuildingValve dev = devs.get(i);
			dev.setId(null);
			dev.setCode(null);
			dev.setPeriod_setting(null);
		}
		return devs;
	}
}
