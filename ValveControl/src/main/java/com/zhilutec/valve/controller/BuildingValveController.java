package com.zhilutec.valve.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zhilutec.valve.service.BuildingValveService;
import com.zhilutec.valve.util.MultiRecQueryCondition;
import com.zhilutec.valve.util.ResponeMsgBody;
import com.zhilutec.valve.util.error.GlobalErrorException;
import com.zhilutec.valve.util.error.ErrorCode;

@RestController
@RequestMapping("valve/buidingvalve")
public class BuildingValveController {
	@Autowired
	private BuildingValveService dbService;
	public final static Logger logger = LoggerFactory.getLogger(CalorimeterController.class);
	
	/*
	 * 查询楼栋热表历史记录
	 */
	@RequestMapping(value = "/history/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponeMsgBody queryBuildingValveHistory(HttpServletRequest request, 
											      HttpServletResponse response, 
											      @RequestBody String requestBody) throws GlobalErrorException{
		
		logger.debug("begin to query buidingvalve history data");

		Map<String, Object> responeMap = new HashMap<String, Object>();
		
		try {
			// 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理
			//String requestMsg = requestBody;
			MultiRecQueryCondition condition = new MultiRecQueryCondition();
			ErrorCode errCode = condition.getMultiRecCondition(requestBody, true);
			
			// 执行查询数据库操作
			responeMap.put("total_rows", dbService.countRecByDevId(condition));
			responeMap.put("records", dbService.findByDevId(condition));
			
		} catch (GlobalErrorException exception) {
			throw exception;
		}
		
		return new ResponeMsgBody(responeMap);
	}
}
