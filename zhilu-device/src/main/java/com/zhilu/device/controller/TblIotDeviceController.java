package com.zhilu.device.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	@RequestMapping("dev")
	public Object dev(String id) {
		return "dev";
	}

	@GetMapping("getdev")
	public Object getDev(String id) {
		TblIotDevice tblIotDevice = tblIotDeviceRepositoy.findTblIotDeviceById(id);
		System.out.println(tblIotDevice);
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
			System.out.println("11111111111111111111111");
			devs = tblIotDevService.findBySpec(userid, type, page, listRows);
		} else {
			devs = tblIotDevService.findBySpec(userid, type, search, page, listRows);
		}

		List<TblIotDevice> devices = devs.getContent();
		return devices;
	}

	/**
	 * 添加设备
	 * 
	 * @param tblIotDev
	 * @return
	 */
	@Modifying
	@PostMapping("add")
	public Object addDev(@RequestBody TblIotDevice tblIotDev) {
		if (PubMethod.paramJudge(tblIotDev).isEmpty()) {
			tblIotDeviceRepositoy.save(tblIotDev);
			ResultCode resultMsg = new ResultCode(ResultStatusCode.OK.getErrcode(), PubMethod.getDevId(tblIotDev));
			return resultMsg;
		} else {
			return PubMethod.paramJudge(tblIotDev);
		}
	}

}
