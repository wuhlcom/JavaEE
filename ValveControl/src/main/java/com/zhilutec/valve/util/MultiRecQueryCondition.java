package com.zhilutec.valve.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.zhilutec.valve.service.HouseHolderHistoryService;
import com.zhilutec.valve.util.error.ErrorCode;
import com.zhilutec.valve.util.error.GlobalErrorException;

public class MultiRecQueryCondition {
	
	private String commAddress; // 通信地址。Lora通信为deveui，有线通信为设备编号
	private int startTime = 0;	// 起始时间
	private int endTime = 0;	// 终止时间
	private int pageIndex;		// 页码
	private int listRows;		// 每页显示记录数
	
	private int MAX_COMM_ADDR = 16;
	private int INVALID_TIME  = 0;
	
	public final static Logger logger = LoggerFactory.getLogger(HouseHolderHistoryService.class);
	
	public ErrorCode getMultiRecCondition(JSONObject object) {
		try {
			commAddress = object.getString("comm_address");
			startTime = object.getInteger("start_time");
			endTime = object.getInteger("end_time");
			pageIndex = object.getInteger("page");
			listRows = object.getInteger("list_rows");
			
			printRequestParam();
			if ((commAddress.isEmpty()) || (commAddress.length() > MAX_COMM_ADDR)
				|| (INVALID_TIME == startTime)
				|| (INVALID_TIME == endTime)
				|| (pageIndex < 1)){
				
				return ErrorCode.PARAM_ERROR;
			}
						
		} catch (Exception e) {
			logger.error("failed to parse query condition", e);
			return ErrorCode.JSON_FORMAT_ERROR;
		}
		
		return ErrorCode.OK;
	}
	
	public ErrorCode getMultiRecCondition(String requestBody, boolean isQueryFlag) throws GlobalErrorException{
		
		JSONObject object;
		ErrorCode  errCode;
		
		try {
			object = JSON.parseObject(requestBody);
			
			commAddress = object.getString("comm_address");
			startTime = object.getInteger("start_time");
			endTime = object.getInteger("end_time");
			pageIndex = object.getInteger("page");
			listRows = object.getInteger("list_rows");
			
		} catch (Exception e) {
			logger.error("invalid json format.");
			throw new GlobalErrorException(ErrorCode.JSON_FORMAT_ERROR);
			//return ErrorCode.JSON_FORMAT_ERROR;
		}
			
		printRequestParam();
		
		if (true == isQueryFlag) {
			if ((commAddress.isEmpty()) || (commAddress.length() > MAX_COMM_ADDR)) {
				throw new GlobalErrorException(ErrorCode.PARAM_ERROR);
			}
		}
		
		if ((INVALID_TIME == startTime)
			|| (INVALID_TIME == endTime)
			|| (startTime >= endTime)
			|| (pageIndex < 1)){
			logger.error("invalid request param.");
			throw new GlobalErrorException(ErrorCode.PARAM_ERROR);
			// return ErrorCode.PARAM_ERROR;
		}
		
		return ErrorCode.OK;
	}
	
	private void printRequestParam() {
		/*
		logger.debug("****************************");
		logger.debug("  address: ", commAddress);
		logger.debug("  start: ", startTime);
		logger.debug("  end: ", endTime);
		logger.debug("  page: ", pageIndex);
		logger.debug("  listRows: ", listRows);
		logger.debug("****************************");
		*/
		
		System.out.println("****************************");
		System.out.println("  address: " + commAddress);
		System.out.println("  start: " + startTime);
		System.out.println("  end: " + endTime);
		System.out.println("  page: " + pageIndex);
		System.out.println("  listRows: " + listRows);
		System.out.println("****************************");
	}

	public String getCommAddress() {
		return commAddress;
	}

	public int getStartTime() {
		return startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public int getListRows() {
		return listRows;
	}
}
