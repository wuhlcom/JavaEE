package com.zhilutec.valve.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.zhilutec.valve.service.HouseHolderDataService;
import com.zhilutec.valve.service.HouseHolderService;

import com.zhilutec.valve.util.MultiRecQueryCondition;
import com.zhilutec.valve.util.ResponeMsgBody;
import com.zhilutec.valve.util.error.GlobalErrorException;
import com.zhilutec.valve.util.validator.StealingCondition;
import com.zhilutec.valve.util.error.ErrorCode;

@Controller
@RestController
@RequestMapping("valve/householder")
public class HouseHolderController {
	
	public final static Logger logger = LoggerFactory.getLogger(HouseHolderController.class);
	
	@Autowired
	private HouseHolderService historyService;
	
	@Autowired
	private HouseHolderDataService houseHolderDataService;
	
	/*
	 * 查询住户历史数据
	 */
	@RequestMapping(value = "/history/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponeMsgBody queryHouseHolderHistory(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody String requestBody) throws GlobalErrorException {
		
		logger.debug("begin to query house holder data");
		
		JSONObject paramsJson = JSON.parseObject(requestBody);	
		MultiRecQueryCondition condition = new MultiRecQueryCondition();
		
		// 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理
		ErrorCode errCode = condition.getMultiRecCondition(paramsJson);
		if (0 != errCode.getCode()) {
			throw new GlobalErrorException(errCode);
		}
		
		// 执行查询数据库操作
		Map<String, Object> responeMap = new HashMap<String, Object>();
		
		try {
			responeMap.put("total_rows", historyService.countRecByDevId(condition));
			responeMap.put("records", historyService.findByDevId(condition));
		} catch (GlobalErrorException exception) {
			throw exception;
		}

		// responeMap.put("errcode", 0);
		return new ResponeMsgBody(responeMap);
	}
		
	
	/*
	 * 住户数据导出
	 */
	@RequestMapping(value = "/data/output", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponeMsgBody exportHouseHolderData(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody String requestBody) throws GlobalErrorException {
		
		logger.debug("begin to export house holder data");
		
		Map<String, Object> responeMap = new HashMap<String, Object>();
		
		try {
			// 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理
			//String requestMsg = requestBody;
			MultiRecQueryCondition condition = new MultiRecQueryCondition();
			ErrorCode errCode = condition.getMultiRecCondition(requestBody, false);
			
			// 执行查询数据库操作
			responeMap.put("total_rows", historyService.countAllRecords(condition));
			responeMap.put("records", historyService.findAllHouseHolder(condition));
			
		} catch (GlobalErrorException exception) {
			throw exception;
		}
		
		return new ResponeMsgBody(responeMap);
	}
	
	@RequestMapping(value = "/data/getStealing", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponeMsgBody getStealing(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody String requestBody) throws GlobalErrorException {
		
		logger.debug("begin to export house holder data");
		List responseRs=new ArrayList(); 
		
		try {
			// 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理		
			StealingCondition condition = new StealingCondition(requestBody);
			ErrorCode errCode = condition.getCondition(requestBody);
			if (0 != errCode.getCode()) {
				throw new GlobalErrorException(errCode);
			}		
		
			// 执行查询数据库操作
			responseRs = houseHolderDataService.getStealing(condition);
			
		} catch (GlobalErrorException exception) {
			throw exception;
		}		
		return new ResponeMsgBody(responseRs);
	}
	
	@RequestMapping(value = "/data/getStealingGroup", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponeMsgBody getStealingGroup(HttpServletRequest request, 
			HttpServletResponse response, @RequestBody String requestBody) throws GlobalErrorException {
		
		logger.debug("begin to export house holder data");
		
		List responseRs=new ArrayList(); 
		
		try {
			// 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理		
			StealingCondition condition = new StealingCondition(requestBody);
			ErrorCode errCode = condition.getCondition(requestBody);
			
			// 执行查询数据库操作
			responseRs = houseHolderDataService.getStealingGroup(condition);
			
		} catch (GlobalErrorException exception) {
			throw exception;
		}
		
		return new ResponeMsgBody(responseRs);
	}
	
	
	
}
