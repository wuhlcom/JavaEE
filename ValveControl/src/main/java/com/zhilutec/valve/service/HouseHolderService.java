package com.zhilutec.valve.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList; 

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolationException;
import java.util.Collections;

import com.zhilutec.valve.bean.TblHouseHolderHistory;
import com.zhilutec.valve.repository.TblHouseHolderHistoryRepo;
import com.zhilutec.valve.util.MultiRecQueryCondition;
import com.zhilutec.valve.util.error.ErrorCode;

import com.zhilutec.valve.util.error.GlobalErrorException;

@Service
public class HouseHolderService {
	
	public final static Logger logger = LoggerFactory.getLogger(HouseHolderService.class);
	
	@Autowired
	private TblHouseHolderHistoryRepo houseHolderDataRepo;
	
	public List<TblHouseHolderHistory> findByDevId(MultiRecQueryCondition condition) 
			throws GlobalErrorException {
		
		List<TblHouseHolderHistory> historyDbList = new ArrayList<TblHouseHolderHistory>();
		try {
			historyDbList = houseHolderDataRepo.getPageDataById(condition.getCommAddress(), 
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
	
	public List<TblHouseHolderHistory> findAllHouseHolder(MultiRecQueryCondition condition) throws GlobalErrorException {
		
		List<TblHouseHolderHistory> historyDbList = new ArrayList<TblHouseHolderHistory>();
		try {
			historyDbList = houseHolderDataRepo.getAllData(condition.getStartTime(),
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
			count = houseHolderDataRepo.countRecByDevId(condition.getCommAddress(), 
						  condition.getStartTime(),
						  condition.getEndTime());
		} catch (Exception e) {
			logger.error("failed to count householder history.", e);
			throw new GlobalErrorException(ErrorCode.DB_ERROR);
		}
		
		return count;
	}
	
	public int countAllRecords(MultiRecQueryCondition condition) throws GlobalErrorException {
		int count = 0;
		try {
			count = houseHolderDataRepo.countRecords(condition.getStartTime(),
						  							 condition.getEndTime());
		} catch (Exception e) {
			logger.error("failed to count householder history.", e);
			throw new GlobalErrorException(ErrorCode.DB_ERROR);
		}
		
		return count;
	}
}
