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

@Controller
@RestController

@RequestMapping("device")
public class DeviceController {

	public final static String USER_ID = "userid";
	public final static String PRODUCT = "product";
	public final static String NAME = "name";
	public final static String ID = "id";
	public final static String DEV_ID = "devid";
	public final static String PROTOCOL = "protocol";

	@Autowired
	private TblIotDevSrv tblIotDevSrv;

	/**
	 * 查询设备
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Object queryDevs(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		try {
			Long page = 1L;
			String token = request.getParameter("token");
			Boolean rsCheckToken = CheckParams.isToken(token);

			if (rsCheckToken) {
				// 获取请求参数转为JsonObject
				JSONObject paramsJson = JSON.parseObject(requestBody);
				Result checkQuery = CheckParams.checkQuery(paramsJson);
				if (checkQuery != null) {
					return checkQuery;
				}
				// 通过json解析参数
				String userid = paramsJson.get("userid").toString();
				int type = Integer.parseInt(paramsJson.get("type").toString());
				String search = paramsJson.get("search").toString();

				if (paramsJson.get("page") != null) {
					page = Long.parseLong(paramsJson.get("page").toString());
				}

				Long listRows = Long.parseLong(paramsJson.get("listRows").toString());

				Map<String, Object> devs = new HashMap<String, Object>();

				devs = tblIotDevSrv.pageInfo(type, userid, search, page, listRows);
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
	 * 添加设备
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Object addDev(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		try {
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
				if (rsAddInfo.containsKey(TblIotDevSrv.ADDED)) {
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
	 * 查询设备状态
	 */
	@RequestMapping(value = "/onlineStatus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Object onlineStatus(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
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

}
