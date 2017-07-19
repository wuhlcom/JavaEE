package com.zhilu.device.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.jpa.repository.Modifying;
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
import com.zhilu.device.repository.TblIotDeviceRepository;
import com.zhilu.device.service.TblIotDeviceService;
import com.zhilu.device.util.PubMethod;
import com.zhilu.device.util.ResultCode;
import com.zhilu.device.util.ResultMsg;
import com.zhilu.device.util.ResultStatusCode;

@RestController
// 这里字母区分大小写
@RequestMapping("device")
public class TblIotDeviceController {

	@Autowired
	private TblIotDeviceRepository tblIotDeviceRepositoy;

	@Autowired
	private TblIotDeviceService tblIotDevService;

	@GetMapping("get")
	public Object get() {
		return "dev";
	}

	// 使用HttpServletRequest方法来获取uri上的参数
	@PostMapping("post")
	public Object post(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody)
			throws Exception {
		System.out.println(request);
		String token = request.getParameter("token");
		System.out.println(token);
		System.out.println("----------------");
		System.out.println(requestBody);

		// 转化为json对象
		com.alibaba.fastjson.JSONObject jobj = JSON.parseObject(requestBody);
		String name = jobj.get("name").toString();
		System.out.println(name);
		String devices = jobj.get("devices").toString();
		System.out.println(devices);

		// 去首尾空格
		devices = devices.trim();
		// 去除两头括号
		String newstr1 = devices.substring(1, devices.length() - 1);
		System.out.println(newstr1);

		// 分割成数组
		String[] sourceStrArray = newstr1.split(",");
		for (int i = 0; i < sourceStrArray.length; i++) {
			System.out.println(sourceStrArray[i]);
		}

		return "post";
	}

	// 使用bean方法来获取uir上的参数
	@PostMapping("postbean")
	public Object postbean(TokenParamModel request) {
		// String token=request.getToken();
		// System.out.println("token is:"+token);
		return "postbean";
	}

	@GetMapping("getdev")
	public Object getDev(String id) {
		TblIotDevice tblIotDevice = tblIotDeviceRepositoy.findTblIotDeviceById(id);
		ResultCode resultMsg = new ResultCode(ResultStatusCode.OK.getErrcode(), PubMethod.getDevId(tblIotDevice));
		return resultMsg;
	}

	// 注意大小写
	@GetMapping("queryAllPage")
	// 注解@RequestHeader限定请求参数
	public List<TblIotDevice> getDevsByParams(@RequestHeader(value = "page", defaultValue = "1") Integer page,
			@RequestHeader(value = "listRows", defaultValue = "10") Integer listRows) {
		List<TblIotDevice> devices = tblIotDevService.getDevsByPage(page, listRows).getContent();
		return devices;
	}

	@GetMapping("query")
	public List<TblIotDevice> queryDevs(@RequestHeader(value = "userid", required = true) String userid,
			@RequestHeader(value = "type", required = true) Integer type,
			@RequestHeader(value = "search", defaultValue = "0", required = true) String search,
			@RequestHeader(value = "page", defaultValue = "1") Integer page,
			@RequestHeader(value = "listRows", defaultValue = "15") Integer listRows) {
		Page<TblIotDevice> devs = null;
		if (type == 0) {
			devs = tblIotDevService.findBySpec(userid, type, page, listRows);
		} else {
			devs = tblIotDevService.findBySpec(userid, type, search, page, listRows);
		}

		List<TblIotDevice> devices = devs.getContent();
		return devices;
	}

	// /**
	// * 添加设备
	// *
	// * @param tblIotDev
	// * @return
	// * userid string 32 是 平台用户编号
	// * name string 64 是 设备名称
	// * product string 16 是 所属产品
	// * protocol int 4 是 通讯协议 HTTP：0 HTTPS：1 MQTT：2* COAP：3
	// * devices List<string> 是 设备IMEI编号
	// if (PubMethod.paramJudge(tblIotDev).isEmpty()) {
	// tblIotDeviceRepositoy.save(tblIotDev);
	// ResultCode resultMsg = new ResultCode(ResultStatusCode.OK.getErrcode(),
	// PubMethod.getDevId(tblIotDev));
	// return resultMsg;
	// } else {
	// return PubMethod.paramJudge(tblIotDev);
	// }
	// */
	@Modifying
	@PostMapping("add")
	public Object addDev(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		System.out.println(requestBody);
		String token = request.getParameter("token");
		System.out.println("token is:"+token);
		
		//獲取參數轉成JsonObject
		com.alibaba.fastjson.JSONObject paramsJson = JSON.parseObject(requestBody);
		String userid = paramsJson.get("userid").toString();
		String name = paramsJson.get("name").toString();
		String product = paramsJson.get("product").toString();
		int protocol = Integer.parseInt(paramsJson.get("protocol").toString());
		String idsStr = paramsJson.get("devices").toString();
		// 去首尾空格
		idsStr = idsStr.trim();
		// 去除两头括号
		String newIdsStr = idsStr.substring(1, idsStr.length() - 1);
		// 分割成数组
		String[] idsArray = newIdsStr.split(",");
		ArrayList<?> devids = tblIotDevService.addDevices(userid, name, product, protocol, idsArray);
		ResultCode resultMsg = new ResultCode(ResultStatusCode.OK.getErrcode(), devids);
		return resultMsg;
	}

	@Modifying
	@DeleteMapping("delete")
	public Object deleteDev(@RequestHeader(value = "userid", required = true) String userid,
			@RequestHeader(value = "devid", required = true) String devid) {
		tblIotDevService.deleteById(userid, devid);
		// tblIotDeviceRepositoy.delete(devid);
		ResultMsg resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(), null);
		return resultMsg;
	}

	@PutMapping(value = "update")
	public TblIotDevice personUpdate(@PathVariable("token") String token, @RequestParam("id") String id,
			@RequestParam("name") String name) {
		return tblIotDevService.updateDev(id, name);
	}
}
