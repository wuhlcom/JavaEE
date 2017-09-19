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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhilutec.valve.service.BuildingValveDataService;
import com.zhilutec.valve.service.BuildingValveService;
import com.zhilutec.valve.util.MultiRecQueryCondition;
import com.zhilutec.valve.util.ResponeMsgBody;
import com.zhilutec.valve.util.error.GlobalErrorException;
import com.zhilutec.valve.util.error.ErrorCode;
import com.zhilutec.valve.util.error.ErrorResponeMsgBody;

@RestController
@RequestMapping("/valve_service/data")
public class BuildingValveController {
	@Autowired
	private BuildingValveDataService buildingValveDataService;
	
	@Autowired
	private BuildingValveService buildingValveService;

	public final static Logger logger = LoggerFactory.getLogger(BuildingCalorimeterController.class);

	/*
	 * 查询楼栋热表历史记录
	 */
	@RequestMapping(value = "/history/building_valve/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponeMsgBody queryBuildingValveHistory(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) throws GlobalErrorException {

		logger.debug("begin to query buidingvalve history data");

		Map<String, Object> responeMap = new HashMap<String, Object>();

		try {
			// 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理
			// String requestMsg = requestBody;
			MultiRecQueryCondition condition = new MultiRecQueryCondition();
			ErrorCode errCode = condition.getMultiRecCondition(requestBody, true);

			// 执行查询数据库操作
			responeMap.put("total_rows", buildingValveDataService.countRecByDevId(condition));
			responeMap.put("records", buildingValveDataService.findByDevId(condition));

		} catch (GlobalErrorException exception) {
			throw exception;
		}

		return new ResponeMsgBody(responeMap);
	}

	@RequestMapping(value = "/batch/building_valve/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String batchQuery(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.debug("begin to batchQuery");
		System.out.println("Request=" + requestBody);
		JSONObject resultObj = new JSONObject();
		List<?> responseRs = new ArrayList<>();
		try {
			// 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理
			// StealingCondition condition = new StealingCondition(requestBody);
			// ErrorCode errCode = condition.getCondition(requestBody);

			// 执行查询数据库操作
			responseRs = buildingValveService.getBuildingVavlesByDeveuis(requestBody);
			resultObj.put("errcode", ErrorCode.OK.getCode());
			resultObj.put("result", responseRs);
			return resultObj.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ErrorResponeMsgBody err = new ErrorResponeMsgBody(ErrorCode.UNKNOW_ERR);
		resultObj = JSONObject.parseObject(err.toString());
		return resultObj.toJSONString();
	}
}
