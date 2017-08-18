package com.zhilutec.valve.service;

import java.util.List;
import java.util.ArrayList; 

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhilutec.valve.bean.TblCalorimeterHistory;
import com.zhilutec.valve.bean.TblHouseHolderHistory;
import com.zhilutec.valve.repository.TblCalorimeterHistoryRepo;

import com.zhilutec.valve.util.MultiRecQueryCondition;
import com.zhilutec.valve.util.error.ErrorCode;
import com.zhilutec.valve.util.error.GlobalErrorException;

@Service
public class CalorimeterService {

	@Autowired
	private TblCalorimeterHistoryRepo dbHandler;
	
	public final static Logger logger = LoggerFactory.getLogger(TblCalorimeterHistoryRepo.class);
	
	public List<TblCalorimeterHistory> findByDevId(MultiRecQueryCondition condition) 
			throws GlobalErrorException {
		
		List<TblCalorimeterHistory> historyDbList = new ArrayList<TblCalorimeterHistory>();
		try {
			historyDbList = dbHandler.getPageDataById(condition.getCommAddress(), 
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
	
	public List<TblCalorimeterHistory> findAllBuildingValve(MultiRecQueryCondition condition) throws GlobalErrorException {
		
		List<TblCalorimeterHistory> historyDbList = new ArrayList<TblCalorimeterHistory>();
		try {
			historyDbList = dbHandler.getAllData(condition.getStartTime(),
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
			count = dbHandler.countRecByDevId(condition.getCommAddress(), 
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
			count = dbHandler.countRecords(condition.getStartTime(),
			  							   condition.getEndTime());
		} catch (Exception e) {
			logger.error("failed to count householder history.", e);
			throw new GlobalErrorException(ErrorCode.DB_ERROR);
		}
		
		return count;
	}
}
