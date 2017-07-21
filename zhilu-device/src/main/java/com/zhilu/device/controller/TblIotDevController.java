package com.zhilu.device.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.zhilu.device.repository.TblIotDevRepo;
import com.zhilu.device.service.TblIotDevSrv;
import com.zhilu.device.util.CheckParams;
import com.zhilu.device.util.PubMethod;
import com.zhilu.device.util.Result;
import com.zhilu.device.util.ResultDevAdd;
import com.zhilu.device.util.ResultMsg;
import com.zhilu.device.util.ResultStatusCode;
import com.zhilu.device.util.ResultErr;

@RestController
// 这里字母区分大小写
@RequestMapping("device")
public class TblIotDevController {

	@Autowired
	private TblIotDevRepo tblIotDevRepo;

	@Autowired
	private TblIotDevSrv tblIotDevSrv;

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

	@GetMapping("getdev")
	public Object getDev(String id) {
		List<TblIotDevice> tblIotDevice = tblIotDevRepo.findTblIotDeviceById(id);
		ResultDevAdd resultMsg = new ResultDevAdd(ResultStatusCode.OK.getCode(), CheckParams.getDevId(tblIotDevice));
		return resultMsg;
	}

	// 注意大小写
	@GetMapping("queryAllPage")
	// 注解@RequestHeader限定请求参数
	public List<TblIotDevice> getDevsByParams(@RequestHeader(value = "page", defaultValue = "1") Integer page,
			@RequestHeader(value = "listRows", defaultValue = "10") Integer listRows) {
		List<TblIotDevice> devices = tblIotDevSrv.getDevsByPage(page, listRows).getContent();
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
			devs = tblIotDevSrv.findBySpec(userid, type, page, listRows);
		} else {
			devs = tblIotDevSrv.findBySpec(userid, type, search, page, listRows);
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
		Result resultMsg = null;
		System.out.println("................." + this.getClass() + "......addDev.........");
		System.out.println(requestBody);
		String token = request.getParameter("token");
		System.out.println("token is:" + token);
		Boolean rsCheckToken = CheckParams.checkToken(token);
		System.out.println(rsCheckToken.getClass());
		System.out.println(rsCheckToken);
		System.out.println("................." + this.getClass() + "......addDev.........");
		rsCheckToken = true;

		if (rsCheckToken == null) {
			resultMsg = new ResultDevAdd(ResultStatusCode.TOKEN_EMP.getCode(), ResultStatusCode.TOKEN_EMP.getErrmsg());
			return resultMsg;
		} else if (rsCheckToken) {
			
			//参数检查失败直接返回错误码,参数检查成功,开始添加设备
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
				
				//添加成功
				if (rsAddInfo.containsKey(TblIotDevSrv.ADDED)) {
					resultMsg = new ResultDevAdd(ResultStatusCode.OK.getCode(), rsAddInfo.values());
					return resultMsg;
				}
				
				//设备已存在
				if (rsAddInfo.containsKey(TblIotDevSrv.EXISITED)) {
					resultMsg = new ResultErr(ResultStatusCode.DEVID_EXISTED.getCode(), ResultStatusCode.DEVID_EXISTED.getErrmsg());
					return resultMsg;
				}
			}
		} else {
			resultMsg = new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
		}
		return resultMsg;

	}

	@Modifying
	@DeleteMapping("delete")
	public Object deleteDev(@RequestHeader(value = "userid", required = true) String userid,
			@RequestHeader(value = "devid", required = true) String devid) {
		tblIotDevSrv.deleteById(userid, devid);
		// tblIotDeviceRepositoy.delete(devid);
		ResultMsg resultMsg = new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getErrmsg(), null);
		return resultMsg;
	}

	@PutMapping(value = "update")
	public TblIotDevice personUpdate(@PathVariable("token") String token, @RequestParam("id") String id,
			@RequestParam("name") String name) {
		return tblIotDevSrv.updateDev(id, name);
	}
}
