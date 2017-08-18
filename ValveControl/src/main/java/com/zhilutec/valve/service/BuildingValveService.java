package com.zhilutec.valve.service;

import java.util.List;
import java.util.ArrayList; 

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhilutec.valve.bean.TblBuildingValveHistory;
import com.zhilutec.valve.repository.TblBuildingValveHistoryRepo;

import com.zhilutec.valve.util.MultiRecQueryCondition;
import com.zhilutec.valve.util.error.ErrorCode;
import com.zhilutec.valve.util.error.GlobalErrorException;

@Service
public class BuildingValveService {

	@Autowired
	private TblBuildingValveHistoryRepo dbHandler;
	
	public final static Logger logger = LoggerFactory.getLogger(BuildingValveService.class);
	
	public List<TblBuildingValveHistory> findByDevId(MultiRecQueryCondition condition) 
			throws GlobalErrorException {
		
		List<TblBuildingValveHistory> historyDbList = new ArrayList<TblBuildingValveHistory>();
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
}
