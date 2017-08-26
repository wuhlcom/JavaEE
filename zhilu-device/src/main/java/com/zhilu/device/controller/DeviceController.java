package com.zhilu.device.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhilu.device.service.primary.TblIotDevSrv;
import com.zhilu.device.util.errorcode.Result;
import com.zhilu.device.util.errorcode.ResultDevAdd;
import com.zhilu.device.util.errorcode.ResultErr;
import com.zhilu.device.util.errorcode.ResultStatusCode;
import com.zhilu.device.util.validator.CheckParams;
import com.zhilu.device.util.validator.DeviceValidator;

@Controller
@RestController

@RequestMapping("device")
public class DeviceController {
	@Autowired
	private TblIotDevSrv tblIotDevSrv;

	/**
	 * 添加设备
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Object addDev(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {

		try {
			System.out.println("Request=" + requestBody);
			Result rs = null;
			String token = request.getParameter("token");
			Boolean rsCheckToken = CheckParams.isToken(token);

			if (rsCheckToken) {
				// 获取请求参数转为JsonObject
				JSONObject paramsJson = JSON.parseObject(requestBody);
				// 参数检查失败直接返回错误码,参数检查成功,开始添加设备
				rs = CheckParams.checkAdd(paramsJson);

				if (rs != null) {
					return rs;
				}

				Map<String, List> rsAddInfo = tblIotDevSrv.addDevices(paramsJson);

				// 添加成功
				if (rsAddInfo.containsKey("add")) {
					rs = new ResultDevAdd(ResultStatusCode.OK.getCode(), rsAddInfo.values());
				} else {
					// 设备已存在
					// rsAddInfo.containsKey(TblIotDevSrv.EXISITED
					rs = new ResultErr(ResultStatusCode.DEVID_EXISTED.getCode(),
							ResultStatusCode.DEVID_EXISTED.getErrmsg());
				}
				return rs;

			} else if (rsCheckToken == null) {
				return new ResultDevAdd(ResultStatusCode.TOKEN_EMP.getCode(), ResultStatusCode.TOKEN_EMP.getErrmsg());
			} else {
				return new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.SYSTEM_ERR.getCode(), ResultStatusCode.SYSTEM_ERR.getErrmsg());
		}
	}

	/**
	 * 删除设备
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Object deleteDev(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);
			Result rs = null;
			String token = request.getParameter("token");
			Boolean rsCheckToken = CheckParams.isToken(token);

			if (rsCheckToken) {
				// 获取请求参数转为JsonObject
				JSONObject paramsJson = JSON.parseObject(requestBody);
				rs = CheckParams.checkDelete(paramsJson);

				if (rs != null) {
					return rs;
				}

				String mac = paramsJson.get("devid").toString();
				String id = tblIotDevSrv.deleteByIds(paramsJson);
				if (id == null) {
					return new ResultErr(ResultStatusCode.DEVID_NOT_EXISTED.getCode(),
							ResultStatusCode.DEVID_NOT_EXISTED.getErrmsg());
				}
				rs = CheckParams.checkDelResult(mac);
				if (rs != null) {
					return rs;
				} else {
					return new ResultErr(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getErrmsg());
				}
			} else if (rsCheckToken == null) {
				return new ResultDevAdd(ResultStatusCode.TOKEN_EMP.getCode(), ResultStatusCode.TOKEN_EMP.getErrmsg());

			} else {
				return new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.SYSTEM_ERR.getCode(), ResultStatusCode.SYSTEM_ERR.getErrmsg());
		}
	}

	/**
	 * 更新设备
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Object updateDev(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);
			Result rs = null;
			String token = request.getParameter("token");
			Boolean rsCheckToken = CheckParams.isToken(token);

			if (rsCheckToken) {
				// 获取请求参数转为JsonObject
				JSONObject paramsJson = JSON.parseObject(requestBody);

				// 参数检查失败直接返回错误码,参数检查成功更新设备
				rs = CheckParams.checkAdd(paramsJson);

				if (rs != null) {
					return rs;
				}
				rs = tblIotDevSrv.updateDev(paramsJson);
				return rs;
			} else if (rsCheckToken == null) {
				rs = new ResultDevAdd(ResultStatusCode.TOKEN_EMP.getCode(), ResultStatusCode.TOKEN_EMP.getErrmsg());
				return rs;
			} else {
				rs = new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
			}
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.SYSTEM_ERR.getCode(), ResultStatusCode.SYSTEM_ERR.getErrmsg());
		}
	}

	/**
	 * 查询设备
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Object queryDevs(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);
			String token = request.getParameter("token");
			Boolean rsCheckToken = CheckParams.isToken(token);

			if (rsCheckToken) {
				// 获取请求参数转为JsonObject
				JSONObject paramsJson = JSON.parseObject(requestBody);
				Result checkQuery = CheckParams.checkQuery(paramsJson);
				if (checkQuery != null) {
					return checkQuery;
				}

				Map<String, Object> devs = new HashMap<String, Object>();

				devs = tblIotDevSrv.pageInfo(paramsJson);
				return devs;
			} else if (rsCheckToken == null) {
				return new ResultDevAdd(ResultStatusCode.TOKEN_EMP.getCode(), ResultStatusCode.TOKEN_EMP.getErrmsg());
			} else {
				return new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.SYSTEM_ERR.getCode(), ResultStatusCode.SYSTEM_ERR.getErrmsg());
		}

	}

	/**
	 * 查询设备状态
	 */
	@RequestMapping(value = "/onlineStatus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Object onlineStatus(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);
			Result rs = null;
			String token = request.getParameter("token");
			Boolean rsCheckToken = CheckParams.isToken(token);
			if (rsCheckToken) {
				// 获取请求参数转为JsonObject
				JSONObject paramsJson = JSON.parseObject(requestBody);

				// 参数检查失败直接返回错误码,参数检查成功,开始添加设备
				rs = CheckParams.checkOnlineStatus(paramsJson);

				if (rs != null) {
					return rs;
				}

				return tblIotDevSrv.getDevStatusInfo(paramsJson);

			} else if (rsCheckToken == null) {
				return new ResultDevAdd(ResultStatusCode.TOKEN_EMP.getCode(), ResultStatusCode.TOKEN_EMP.getErrmsg());
			} else {
				return new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.SYSTEM_ERR.getCode(), ResultStatusCode.SYSTEM_ERR.getErrmsg());
		}
	}

	/**
	 * 获取设备id,供lora服务器使用
	 */
	@RequestMapping(value = "/getDevId", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Object getDevId(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);
				// 获取请求参数转为JsonObject
				JSONObject paramsJson = JSON.parseObject(requestBody);				
				// 参数检查失败直接返回错误码,参数检查成功,开始添加设备
                if(!DeviceValidator.getDevIdValidate(paramsJson)){
                	return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
                };
				return tblIotDevSrv.getDevId(paramsJson);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.SYSTEM_ERR.getCode(), ResultStatusCode.SYSTEM_ERR.getErrmsg());
		}
	}

}
