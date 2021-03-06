package com.zhilu.device.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhilu.device.bean.TblIotDevice;
import com.zhilu.device.repository.TblIotDeviceRepository;
import com.zhilu.device.util.PubMethod;
import com.zhilu.device.util.ResultCode;
import com.zhilu.device.util.ResultMsg;
import com.zhilu.device.util.ResultStatusCode;

@Configuration
@EnableAutoConfiguration
@EntityScan
@RestController
@RequestMapping("device")
public class TblIotDeviceController {

	@Autowired
	private TblIotDeviceRepository tblIotDeviceRepositoy;

	@GetMapping("getdev")
	public Object getDev(String id) {
		TblIotDevice tblIotDeviceEntity = tblIotDeviceRepositoy.findTblIotDeviceById(id);
		ResultMsg resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(), PubMethod.getDevId(tblIotDeviceEntity));
		return resultMsg;
	}

	@GetMapping(value = "/query")
	public Page<TblIotDevice> getEntryByParams(@RequestParam(value = "userid", required = true) String uid,
			@RequestParam(value = "type", required = true) Integer type,
			@RequestParam(value = "search", required = true) String search,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "listRows", defaultValue = "15") Integer listRows) {
		// 指定排序方式
		Order order1 = new Order(Direction.DESC, "id");
		Sort sort = new Sort(order1);
		Pageable pageable = new PageRequest(page, listRows, sort);
		return tblIotDeviceRepositoy.findAll(pageable);
	}

	@GetMapping("getdevs")
	public Object getDevs(String name) {
		List<TblIotDevice> tblIotDeviceEntities = tblIotDeviceRepositoy.findTblIotDeviceByName(name);
		ResultMsg resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(),
				tblIotDeviceEntities);
		return resultMsg;
	}

	/**
	 * 添加设备
	 * 
	 * @param tblIotDeviceEntity
	 * @return
	 */
	@Modifying
	@PostMapping("add")
	public Object addDev(@RequestBody TblIotDevice tblIotDeviceEntity) {
		if (PubMethod.paramJudge(tblIotDeviceEntity).isEmpty()) {
			tblIotDeviceRepositoy.save(tblIotDeviceEntity);
			ResultCode resultMsg = new ResultCode(ResultStatusCode.OK.getErrcode(),
					PubMethod.getDevId(tblIotDeviceEntity));
			return resultMsg;
		} else {
			return PubMethod.paramJudge(tblIotDeviceEntity);
		}
	}

	@Modifying
	@RequestMapping("updatedev")
	public Object updateDev(@RequestBody TblIotDevice tblIotDeviceEntity) {
		TblIotDevice dev = tblIotDeviceRepositoy.findTblIotDeviceById(tblIotDeviceEntity.getId());
		if (dev != null) {
			dev.setName(tblIotDeviceEntity.getName());
			tblIotDeviceRepositoy.save(dev);
		}
		ResultMsg resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(), null);
		return resultMsg;
	}

	@Modifying
	@DeleteMapping("delete")
	public Object deleteDev(String userid,String devid) {
		tblIotDeviceRepositoy.delete(devid);
		ResultMsg resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(), null);
		return resultMsg;
	}

}
