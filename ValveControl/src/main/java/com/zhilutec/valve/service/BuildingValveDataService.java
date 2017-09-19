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
import com.zhilutec.valve.repository.BuildingValveRepo;
import com.zhilutec.valve.bean.models.TblBuildingCalorimeter;
import com.zhilutec.valve.bean.models.TblBuildingValve;
import com.zhilutec.valve.bean.models.TblBuildingValveData;
import com.zhilutec.valve.repository.BuildingValveDataRepo;

import com.zhilutec.valve.util.MultiRecQueryCondition;
import com.zhilutec.valve.util.error.ErrorCode;
import com.zhilutec.valve.util.error.GlobalErrorException;

@Service
public class BuildingValveDataService {

	@Autowired
	private BuildingValveDataRepo buildingValveDataRepo;

	public final static Logger logger = LoggerFactory.getLogger(BuildingValveDataService.class);

	public List<TblBuildingValveData> findByDevId(MultiRecQueryCondition condition) throws GlobalErrorException {

		List<TblBuildingValveData> historyDbList = new ArrayList<TblBuildingValveData>();
		try {
			int pageIndex = condition.getPageIndex() - 1;
			int listRows = condition.getListRows();
			if (condition.getStartTime() == 0 || condition.getEndTime() == 0) {
				historyDbList = buildingValveDataRepo.getPageDataById(
						condition.getCommAddress(), 
						pageIndex * listRows,
						listRows);
			} else {
				historyDbList = buildingValveDataRepo.getPageDataByIdAndTimeRange(
						condition.getCommAddress(), 
						condition.getStartTime(),
						condition.getEndTime(), 
						pageIndex * listRows,
						listRows);		
			}

		} catch (Exception e) {
			logger.error("failed to get records by condition", e);
			throw new GlobalErrorException(ErrorCode.DB_ERROR);
		}

		return historyDbList;
	}

	public int countRecByDevId(MultiRecQueryCondition condition) throws GlobalErrorException {

		int count = 0;
		try {
			if (condition.getStartTime() == 0 || condition.getEndTime() == 0) {
				count = buildingValveDataRepo.countRecByDevId(
						condition.getCommAddress());
			} else {
				count = buildingValveDataRepo.countRecByDevIdAndTimeRange(
						condition.getCommAddress(), 
						condition.getStartTime(),
						condition.getEndTime());				
			}

		} catch (Exception e) {
			logger.error("failed to count householder history.", e);
			throw new GlobalErrorException(ErrorCode.DB_ERROR);
		}

		return count;
	}
	
}
