package com.zhilutec.valve.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhilutec.valve.service.DTUService;
import com.zhilutec.valve.util.error.ErrorCode;
import com.zhilutec.valve.util.error.ErrorResponeMsgBody;

@RestController
@RequestMapping("/valve_service/data")
public class DTUController {

	@Autowired
	private DTUService dtuService;

	public final static Logger logger = LoggerFactory.getLogger(BuildingCalorimeterController.class);

	@RequestMapping(value = "/batch/dtu/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
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
			responseRs = dtuService.getDTUsByDeveuis(requestBody);
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
