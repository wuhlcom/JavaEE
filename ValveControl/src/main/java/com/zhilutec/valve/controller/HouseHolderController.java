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
import com.zhilutec.valve.service.HouseHolderHistoryService;
import com.zhilutec.valve.service.HouseHolderService;
import com.zhilutec.valve.util.MultiRecQueryCondition;
import com.zhilutec.valve.util.ResponeMsgBody;
import com.zhilutec.valve.util.error.GlobalErrorException;
import com.zhilutec.valve.util.validator.StealingCondition;
import com.zhilutec.valve.util.error.ErrorCode;
import com.zhilutec.valve.util.error.ErrorResponeMsgBody;

@Controller
@RestController
@RequestMapping("/valve_service/data")
public class HouseHolderController {

	public final static Logger logger = LoggerFactory.getLogger(HouseHolderController.class);

	@Autowired
	private HouseHolderHistoryService historyService;

	@Autowired
	private HouseHolderDataService houseHolderDataService;

	@Autowired
	private HouseHolderService houseHolderService;

	/*
	 * 查询住户历史数据
	 */
	@RequestMapping(value = "/history/householder/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponeMsgBody queryHouseHolderHistory(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) throws GlobalErrorException {

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

	// 批量查询HoseHolder
	@RequestMapping(value = "/batch/householder/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String batchQeury(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.debug("begin to  batchQeury");
		System.out.println("Request=" + requestBody);
		JSONObject resultObj = new JSONObject();
		List responseRs = new ArrayList();
		try {
			// 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理
			// StealingCondition condition = new StealingCondition(requestBody);
			// ErrorCode errCode = condition.getCondition(requestBody);

			// 执行查询数据库操作
			responseRs = houseHolderService.getHouseValvesByDeveuis(requestBody);
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

	/*
	 * 住户数据导出
	 */
	@RequestMapping(value = "/export/householder", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponeMsgBody exportHouseHolderData(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) throws GlobalErrorException {

		logger.debug("begin to export house holder data");

		Map<String, Object> responeMap = new HashMap<String, Object>();

		try {
			// 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理
			// String requestMsg = requestBody;
			MultiRecQueryCondition condition = new MultiRecQueryCondition();
			ErrorCode errCode = condition.getMultiRecCondition(requestBody, false, 4);

			// 执行查询数据库操作
			responeMap.put("total_rows", historyService.countAllRecords(condition));
			responeMap.put("records", historyService.findAllHouseHolder(condition));

		} catch (GlobalErrorException exception) {
			throw exception;
		}

		return new ResponeMsgBody(responeMap);
	}

	// 查询盗热数据
	@RequestMapping(value = "/householder/getStealing", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String getStealing(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody)
			throws GlobalErrorException {

		logger.debug("begin to getStealing");
		List responseRs = new ArrayList();
		JSONObject resultObj = new JSONObject();

		try {
			// 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理
			// StealingCondition condition = new StealingCondition(requestBody);
			// ErrorCode errCode = condition.getCondition(requestBody);
			// if (0 != errCode.getCode()) {
			// throw new GlobalErrorException(errCode);
			// }
			// 执行查询数据库操作
			responseRs = houseHolderDataService.getStealing(requestBody);
			if (responseRs == null) {
				resultObj.put("errcode", ErrorCode.PARAM_ERROR.getCode());
				resultObj.put("msg", ErrorCode.PARAM_ERROR.getCode());
			} else {
				resultObj.put("errcode", ErrorCode.OK.getCode());
				resultObj.put("data", responseRs);
			}
		} catch (GlobalErrorException exception) {
			throw exception;
		}
		return resultObj.toJSONString();
	}

	// 查询盗热住户
	@RequestMapping(value = "/householder/getStealingGroup", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String getStealingGroup(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) throws GlobalErrorException {

		logger.debug("begin to getStealingGroup");

		List responseRs = new ArrayList();
		JSONObject resultObj = new JSONObject();

		try {
			// 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理
			StealingCondition condition = new StealingCondition(requestBody);
			ErrorCode errCode = condition.getCondition(requestBody);

			// 执行查询数据库操作
			responseRs = houseHolderDataService.getStealingGroup(requestBody);
			if (responseRs == null) {
				resultObj.put("errcode", ErrorCode.PARAM_ERROR.getCode());
				resultObj.put("msg", ErrorCode.PARAM_ERROR.getCode());
			} else {
				resultObj.put("errcode", ErrorCode.OK.getCode());
				resultObj.put("data", responseRs);
			}
		} catch (GlobalErrorException exception) {
			throw exception;
		}

		return resultObj.toJSONString();
	}

	// 更新供热季或上报周期
	@RequestMapping(value = "/batch/householder/lora/setting", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ErrorResponeMsgBody loraDeviceSetting(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) throws GlobalErrorException {

		logger.debug("begin to loraDeviceSetting");
		System.out.println("Request=" + requestBody);
		ErrorResponeMsgBody rs = new ErrorResponeMsgBody();
		try {
			// 执行查询数据库操作
			rs = houseHolderService.updateSeasonsAndPeriods(requestBody);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return rs;
	}

	// 批量查询上报周期和供热季
	@RequestMapping(value = "/batch/householder/lora/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String settingQuery(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.debug("begin to  settingQuery");
		System.out.println("Request=" + requestBody);
		JSONObject resultObj = new JSONObject();
		List responseRs = new ArrayList();
		try {
			// 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理
			// StealingCondition condition = new StealingCondition(requestBody);
			// ErrorCode errCode = condition.getCondition(requestBody);

			// 执行查询数据库操作
			responseRs = houseHolderService.getSeansonsAndPeriods(requestBody);
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

	//
	// // 更新住户上报周期
	// @RequestMapping(value = "/lora_device/setting/period", method =
	// RequestMethod.POST, produces = "application/json;charset=UTF-8")
	// public ErrorResponeMsgBody updatePeriod(HttpServletRequest request,
	// HttpServletResponse response,
	// @RequestBody String requestBody) throws GlobalErrorException {
	//
	// logger.debug("begin to export updatePeriod");
	// System.out.println("Request=" + requestBody);
	// ErrorResponeMsgBody rs = new ErrorResponeMsgBody();
	// try {
	// // 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理
	// // StealingCondition condition = new StealingCondition(requestBody);
	// // ErrorCode errCode = condition.getCondition(requestBody);
	//
	// // 执行查询数据库操作
	// rs = houseHolderService.updatePeriodByDeveuis(requestBody);
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw e;
	// }
	// return rs;
	// }
	//
	// // 更新住户供热季
	// @RequestMapping(value = "/lora_device/setting/heating_season", method =
	// RequestMethod.POST, produces = "application/json;charset=UTF-8")
	// public ErrorResponeMsgBody heatingSeason(HttpServletRequest request,
	// HttpServletResponse response,
	// @RequestBody String requestBody) throws GlobalErrorException {
	//
	// logger.debug("begin to export globalSetting");
	// System.out.println("Request=" + requestBody);
	// ErrorResponeMsgBody rs = new ErrorResponeMsgBody();
	// try {
	// // 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理
	// // StealingCondition condition = new StealingCondition(requestBody);
	// // ErrorCode errCode = condition.getCondition(requestBody);
	//
	// // 执行查询数据库操作
	// rs = houseHolderService.updateHotseasonByEuis(requestBody);
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw e;
	// }
	// return rs;
	// }

	// // 上报周期查询
	// @RequestMapping(value = "/period/query", method = RequestMethod.POST,
	// produces = "application/json;charset=UTF-8")
	// public String periodQeury(HttpServletRequest request, HttpServletResponse
	// response,
	// @RequestBody String requestBody) {
	// logger.debug("begin to periodQeury");
	// System.out.println("Request=" + requestBody);
	// JSONObject resultObj = new JSONObject();
	// List responseRs = new ArrayList();
	// try {
	// // 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理
	// // StealingCondition condition = new StealingCondition(requestBody);
	// // ErrorCode errCode = condition.getCondition(requestBody);
	//
	// // 执行查询数据库操作
	// responseRs = houseHolderService.getHouseValvePeriod(requestBody);
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
	//
	// // 供热季查询
	// @RequestMapping(value = "/season/query", method = RequestMethod.POST,
	// produces = "application/json;charset=UTF-8")
	// public String seasonQeury(HttpServletRequest request, HttpServletResponse
	// response,
	// @RequestBody String requestBody) {
	// logger.debug("begin to periodQeury");
	// System.out.println("Request=" + requestBody);
	// JSONObject resultObj = new JSONObject();
	// List responseRs = new ArrayList();
	// try {
	// // 请求参数有效性检查，如果参数异常，抛出异常，有异常处理机制统一处理
	// // StealingCondition condition = new StealingCondition(requestBody);
	// // ErrorCode errCode = condition.getCondition(requestBody);
	//
	// // 执行查询数据库操作
	// responseRs = houseHolderService.getHouseValveSeason(requestBody);
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
