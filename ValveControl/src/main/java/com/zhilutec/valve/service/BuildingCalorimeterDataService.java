package com.zhilutec.valve.service;

import java.util.List;
import java.util.ArrayList; 

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhilutec.valve.bean.models.TblBuidingCalorimeterData;
import com.zhilutec.valve.repository.BuildingCalorimeterDataRepo;

import com.zhilutec.valve.util.MultiRecQueryCondition;
import com.zhilutec.valve.util.error.ErrorCode;
import com.zhilutec.valve.util.error.GlobalErrorException;

@Service
public class BuildingCalorimeterDataService {

	@Autowired
	private BuildingCalorimeterDataRepo buildingCalorimeterDataRepo;
	
	public final static Logger logger = LoggerFactory.getLogger(BuildingCalorimeterDataRepo.class);
	
	public List<TblBuidingCalorimeterData> findByDevId(MultiRecQueryCondition condition) 
			throws GlobalErrorException {
		
		List<TblBuidingCalorimeterData> historyDbList = new ArrayList<TblBuidingCalorimeterData>();
		try {
			int pageIndex = condition.getPageIndex() - 1;
			int listRows = condition.getListRows();
			if (condition.getStartTime() == 0 || condition.getEndTime() == 0) {
				historyDbList = buildingCalorimeterDataRepo.getPageDataById(
						  condition.getCommAddress(), 
						  pageIndex * listRows,
						  listRows);
			} else {
				historyDbList = buildingCalorimeterDataRepo.getPageDataByIdAndTimeRange(
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
	
	public List<TblBuidingCalorimeterData> findAllBuildingValve(MultiRecQueryCondition condition) throws GlobalErrorException {
		
		List<TblBuidingCalorimeterData> historyDbList = new ArrayList<TblBuidingCalorimeterData>();
		try {
			historyDbList = buildingCalorimeterDataRepo.getAllData(condition.getStartTime(),
															  condition.getEndTime(),
															  condition.getPageIndex() - 1,
															  condition.getListRows());
		} catch (Exception e) {
			logger.error("failed to get records by condition", e);
			throw new GlobalErrorException(ErrorCode.DB_ERROR);
		}
		
		return historyDbList;
	}
	
	public int countRecByDevId(MultiRecQueryCondition condition) 
			throws GlobalErrorException {
		
		int count = 0;
		try {
			if (condition.getStartTime() == 0 || condition.getEndTime() == 0) {
				count = buildingCalorimeterDataRepo.countRecByDevId(
						  condition.getCommAddress());
			} else {
				count = buildingCalorimeterDataRepo.countRecByDevIdAndTimeRange(
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
	
	public int countAllRecords(MultiRecQueryCondition condition) throws GlobalErrorException {
		int count = 0;
		try {
			count = buildingCalorimeterDataRepo.countRecords(condition.getStartTime(),
			  							   condition.getEndTime());
		} catch (Exception e) {
			logger.error("failed to count householder history.", e);
			throw new GlobalErrorException(ErrorCode.DB_ERROR);
		}
		
		return count;
	}
}
