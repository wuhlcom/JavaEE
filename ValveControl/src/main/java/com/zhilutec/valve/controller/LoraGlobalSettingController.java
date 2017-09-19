/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月25日 下午3:50:42 * 
*/
package com.zhilutec.valve.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.alibaba.fastjson.JSONObject;
import com.zhilutec.valve.service.LoraGlobalSettingService;
import com.zhilutec.valve.service.TService;
import com.zhilutec.valve.util.error.ErrorCode;
import com.zhilutec.valve.util.error.ErrorResponeMsgBody;
import com.zhilutec.valve.util.error.GlobalErrorException;

@Controller
@RestController
@RequestMapping("/valve_service/data")
public class LoraGlobalSettingController {

	public final static Logger logger = LoggerFactory.getLogger(HouseHolderController.class);
	@Autowired
	private LoraGlobalSettingService loraGlobalSettingService;

	@Autowired
	private TService tService;

	@RequestMapping(value = "/global/lora/setting", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ErrorResponeMsgBody globalUpdate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) throws GlobalErrorException {

		logger.debug("begin to globalUpdate");
		System.out.println("Request=" + requestBody);
		ErrorResponeMsgBody rs = new ErrorResponeMsgBody();
		try {
			// 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理
			// StealingCondition condition = new StealingCondition(requestBody);
			// ErrorCode errCode = condition.getCondition(requestBody);

			// 执行查询数据库操作
			rs = loraGlobalSettingService.save(requestBody);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return rs;
	}

	@RequestMapping(value = "/global/lora/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String globalQuery(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {

		logger.debug("begin to globalQuery");
		System.out.println("Request=" + requestBody);
		JSONObject resultObj = new JSONObject();
		List responseRs = new ArrayList();
		try {
			// 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理
			// StealingCondition condition = new StealingCondition(requestBody);
			// ErrorCode errCode = condition.getCondition(requestBody);

			// 执行查询数据库操作
			responseRs = loraGlobalSettingService.findAll();
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

	// 测试用代码请暂时保留 2017-9-19
	// @RequestMapping(value = "/global/lora/heating", method =
	// RequestMethod.POST, produces = "application/json;charset=UTF-8")
	// public String heating(HttpServletRequest request, HttpServletResponse
	// response,
	// @RequestBody String requestBody) {
	// List responseRs = new ArrayList();
	// logger.debug("begin to globalQuery");
	// System.out.println("Request=" + requestBody);
	// JSONObject resultObj = new JSONObject();
	// try {
	// // 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理
	// // StealingCondition condition = new StealingCondition(requestBody);
	// // ErrorCode errCode = condition.getCondition(requestBody);
	//
	// // 执行查询数据库操作
	// responseRs =tService.heating(requestBody);
	// resultObj.put("errcode", ErrorCode.OK.getCode());
	// resultObj.put("result", responseRs);
	// return resultObj.toJSONString();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// ErrorResponeMsgBody err = new ErrorResponeMsgBody(ErrorCode.UNKNOW_ERR);
	// resultObj = JSONObject.parseObject(err.toString());
	// return resultObj.toJSONString();
	// }

}
