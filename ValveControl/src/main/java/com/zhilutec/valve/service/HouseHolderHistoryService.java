package com.zhilutec.valve.service;

import java.util.List;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhilutec.valve.bean.models.TblHouseHolderData;
import com.zhilutec.valve.repository.HouseHolderHistoryRepo;
import com.zhilutec.valve.util.MultiRecQueryCondition;
import com.zhilutec.valve.util.error.ErrorCode;

import com.zhilutec.valve.util.error.GlobalErrorException;

@Service
public class HouseHolderHistoryService {

	public final static Logger logger = LoggerFactory.getLogger(HouseHolderHistoryService.class);

	@Autowired
	private HouseHolderHistoryRepo houseHolderHistoryRepo;

	public List<TblHouseHolderData> findByDevId(MultiRecQueryCondition condition) throws GlobalErrorException {

		List<TblHouseHolderData> historyDbList = new ArrayList<TblHouseHolderData>();
		try {
			int pageIndex = condition.getPageIndex() - 1;
			int listRows = condition.getListRows();
			if (condition.getStartTime() == 0 || condition.getEndTime() == 0) {
				historyDbList = houseHolderHistoryRepo.getPageDataById(
						condition.getCommAddress(),
						pageIndex * listRows,
						listRows);		
			} else {
				historyDbList = houseHolderHistoryRepo.getPageDataByIdAndTimeRange(
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

	public List<TblHouseHolderData> findAllHouseHolder(MultiRecQueryCondition condition)
			throws GlobalErrorException {

		List<TblHouseHolderData> historyDbList = new ArrayList<TblHouseHolderData>();
		try {
			historyDbList = houseHolderHistoryRepo.getAllData(
					condition.getStartTime(), 
					condition.getEndTime(),
					condition.getPageIndex() - 1, 
					condition.getListRows());
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
				count = houseHolderHistoryRepo.countRecByDevId(
						condition.getCommAddress());	
			} else {
				count = houseHolderHistoryRepo.countRecByDevIdAndTimeRange(
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
			count = houseHolderHistoryRepo.countRecords(condition.getStartTime(), condition.getEndTime());
		} catch (Exception e) {
			logger.error("failed to count householder history.", e);
			throw new GlobalErrorException(ErrorCode.DB_ERROR);
		}

		return count;
	}

}
