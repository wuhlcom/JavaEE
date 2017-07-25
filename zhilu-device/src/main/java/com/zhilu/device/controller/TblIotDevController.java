package com.zhilu.device.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.zhilu.device.bean.TblIotDevice;
import com.zhilu.device.bean.TblIotDeviceDyn;
import com.zhilu.device.repository.TblIotDevRepo;
import com.zhilu.device.service.TblIotDevSrv;
import com.zhilu.device.util.CheckParams;
import com.zhilu.device.util.PubMethod;
import com.zhilu.device.util.Result;
import com.zhilu.device.util.ResultDevAdd;
import com.zhilu.device.util.ResultMsg;
import com.zhilu.device.util.ResultStatusCode;
import com.zhilu.device.util.ResultErr;

@Controller
@RestController
// 这里字母区分大小写
@RequestMapping("device")
public class TblIotDevController {

	public final static String USER_ID = "userid";
	public final static String PRODUCT = "product";
	public final static String NAME = "name";
	public final static String ID = "id";
	public final static String DEV_ID = "devid";
	public final static String PROTOCOL = "protocol";

	@Autowired
	private TblIotDevRepo tblIotDevRepo;

	@Autowired
	private TblIotDevSrv tblIotDevSrv;

	@RequestMapping("getinfo")
	public Object getAllDevInfo(@RequestBody String id) {
		System.out.println("-------------getinfo-----------");
		System.out.println(id.getClass());
		System.out.println(id);
		Object info = tblIotDevSrv.getDevsInfoById("whlq40dts5bwjc4u");
		return info;
	}

	@RequestMapping("getinfo2")
	public Object getAllDevInfo2(@RequestBody String id) {
		System.out.println("-------------getinfo-----------");
		System.out.println(id.getClass());
		System.out.println(id);
		Object info = tblIotDevSrv.getDevsAllInfo();
		return info;
	}

	// 查询单个设备
	// get mac:xxxxxx
	@GetMapping("findmac")
	public Object findByMac(String mac) {
		System.out.println("-------------findmac--------------");
		System.out.println("-------------" + mac + "--------------");
		List<TblIotDevice> dev = tblIotDevSrv.getDevByMac(mac);
		System.out.println(dev);
		return dev;
	}

	// 注意大小写
	@GetMapping("queryAllPage")
	// 注解@RequestHeader限定请求参数
	public List<TblIotDevice> getDevsByParams(@RequestHeader(value = "page", defaultValue = "1") Integer page,
			@RequestHeader(value = "listRows", defaultValue = "10") Integer listRows) {
		List<TblIotDevice> devices = tblIotDevSrv.getDevsByPage(page, listRows).getContent();
		return devices;
	}

	/*
	 * 查询设备
	 * */
	@PostMapping("query")
	public Object queryDevs(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		Result resultMsg = null;
		Long page = 1L;
		String token = request.getParameter("token");
		System.out.println("token is:" + token);
		Boolean rsCheckToken = CheckParams.isToken(token);
		System.out.println(rsCheckToken);
		System.out.println("................." + this.getClass() + "......query.........");
		System.out.println(requestBody);
		rsCheckToken = true;

		// 获取请求参数转为JsonObject
		com.alibaba.fastjson.JSONObject paramsJson = JSON.parseObject(requestBody);
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
	}

	/**
	 * 添加设备
	 *
	 * @param tblIotDev
	 * @return userid string 32 是 平台用户编号 name string 64 是 设备名称 product string 16
	 *         是 所属产品 protocol int 4 是 通讯协议 HTTP：0 HTTPS：1 MQTT：2* COAP：3
	 *         devices List<string> 是 设备IMEI编号
	 */
	@PostMapping("add")
	public Object addDev(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		Result resultMsg = null;
		String token = request.getParameter("token");
		System.out.println("token is:" + token);
		Boolean rsCheckToken = CheckParams.isToken(token);
		System.out.println(rsCheckToken);
		System.out.println("................." + this.getClass() + "......addDev.........");
		rsCheckToken = true;

		if (rsCheckToken == null) {
			resultMsg = new ResultDevAdd(ResultStatusCode.TOKEN_EMP.getCode(), ResultStatusCode.TOKEN_EMP.getErrmsg());
			return resultMsg;
		} else if (rsCheckToken) {

			// 参数检查失败直接返回错误码,参数检查成功,开始添加设备
			Result check = CheckParams.checkAdd(requestBody);

			if (check != null) {
				return check;
			} else {
				// 获取请求参数转为JsonObject
				com.alibaba.fastjson.JSONObject paramsJson = JSON.parseObject(requestBody);
				// 通过json解析参数
				String userid = paramsJson.get("userid").toString();
				String name = paramsJson.get("name").toString();
				String product = paramsJson.get("product").toString();
				int protocol = Integer.parseInt(paramsJson.get("protocol").toString());
				// 得到Id数组
				String[] macsArray = PubMethod.getDevids(requestBody);
				Map<String, List> rsAddInfo = tblIotDevSrv.addDevices(userid, name, product, protocol, macsArray);

				// 添加成功
				if (rsAddInfo.containsKey(TblIotDevSrv.ADDED)) {
					resultMsg = new ResultDevAdd(ResultStatusCode.OK.getCode(), rsAddInfo.values());
					return resultMsg;
				}

				// 设备已存在
				if (rsAddInfo.containsKey(TblIotDevSrv.EXISITED)) {
					resultMsg = new ResultErr(ResultStatusCode.DEVID_EXISTED.getCode(),
							ResultStatusCode.DEVID_EXISTED.getErrmsg());
					return resultMsg;
				}
			}
		} else {
			resultMsg = new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
		}
		return resultMsg;
	}

	@PostMapping("delete")
	public Object deleteDev(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		Result rs = null;
		System.out.println("1................." + this.getClass() + "......deleteDev.........");
		String token = request.getParameter("token");
		System.out.println("token is:" + token);
		Boolean rsCheckToken = CheckParams.isToken(token);
		System.out.println(rsCheckToken);
		System.out.println(requestBody);
		System.out.println("2................." + this.getClass() + "......deleteDev.........");
		// 获取请求参数转为JsonObject
		com.alibaba.fastjson.JSONObject paramsJson = JSON.parseObject(requestBody);
		// 通过json解析参数
		String userid = paramsJson.get(USER_ID).toString();
		String devid = paramsJson.get(DEV_ID).toString();
		rs = CheckParams.checkDelete(userid, devid);
		if (rs != null) {
			return rs;
		}

		String id = tblIotDevSrv.deleteByIds(userid, devid);
		if (id == null) {
			rs = new ResultErr(ResultStatusCode.DEVID_NOT_EXISTED.getCode(),
					ResultStatusCode.DEVID_NOT_EXISTED.getErrmsg());
			return rs;
		}

		rs = CheckParams.checkDelResult(devid);
		if (rs != null) {
			return rs;
		} else {
			rs = new ResultErr(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getErrmsg());
		}
		return rs;
	}

	@PostMapping(value = "update")
	public Object updateDev(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		Result resultMsg = null;
		String token = request.getParameter("token");
		System.out.println("token is:" + token);
		Boolean rsCheckToken = CheckParams.isToken(token);
		System.out.println(rsCheckToken);
		System.out.println("................." + this.getClass() + "......updateDev.........");
		rsCheckToken = true;

		if (rsCheckToken == null) {
			resultMsg = new ResultDevAdd(ResultStatusCode.TOKEN_EMP.getCode(), ResultStatusCode.TOKEN_EMP.getErrmsg());
			return resultMsg;
		} else if (rsCheckToken) {

			// 参数检查失败直接返回错误码,参数检查成功,开始添加设备
			Result check = CheckParams.checkAdd(requestBody);

			if (check != null) {
				return check;
			} else {
				// 获取请求参数转为JsonObject
				com.alibaba.fastjson.JSONObject paramsJson = JSON.parseObject(requestBody);
				// 通过json解析参数
				String userid = paramsJson.get("userid").toString();
				String name = paramsJson.get("name").toString();
				String product = paramsJson.get("product").toString();
				int protocol = Integer.parseInt(paramsJson.get("protocol").toString());
				// 得到Id数组
				String[] macsArray = PubMethod.getDevids(requestBody);

				for (String mac : macsArray) {
					System.out.println("where--------------------------------------------------------------------");
					System.out.println(mac);
					TblIotDevice dev = tblIotDevSrv.findDevbyUidAndMac(userid, mac);

					System.out.println(dev);
					System.out.println("where--------------------------------------------------------------------");
					if (dev == null) {
						resultMsg = new ResultErr(ResultStatusCode.DEVID_NOT_EXISTED.getCode(),
								ResultStatusCode.DEVID_NOT_EXISTED.getErrmsg());
						return resultMsg;
					} else {
						tblIotDevSrv.updateDev(userid, mac, name, product, protocol);
					}
				}
				resultMsg = new ResultDevAdd(ResultStatusCode.OK.getCode(), macsArray);
				return resultMsg;
			}
		} else {
			resultMsg = new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
		}
		return resultMsg;
	}
}
