package com.dazk.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dazk.common.SystemConfig;
import com.dazk.common.util.HttpRequest;
import com.dazk.common.util.JsonUtil;
import com.dazk.common.util.RegexUtil;
import com.dazk.db.dao.CompanyMapper;
import com.dazk.db.model.*;
import com.dazk.service.AddDeviceService;
import com.dazk.service.DataPermService;
import com.dazk.service.QueryBuildService;
import com.dazk.service.QueryDeviceService;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.dazk.common.ErrCode;
import com.dazk.validator.JsonParamValidator;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/device")
public class AddDeviceController {
	@Resource
	private AddDeviceService addAddDeviceService;

	@Resource
	private DataPermService dataPermService;

	@Resource
	private CompanyMapper companyMapper;

	@Resource
	private QueryBuildService queryBuildService;

	@Resource
	private QueryDeviceService queryDeviceService;
	
	@RequestMapping(value = "/addValve", method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public String addValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
		JSONObject resultObj = new JSONObject();
		JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
		List<String> codes = new ArrayList<String>();
		try {
			//根据token 获取用户id
			int userid = dataPermService.getUserid(token,resultObj);
			if(userid == -1){
				return resultObj.toJSONString();
			}
			//之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			codes = dataPermService.getCodesScope("house_code",parameter,resultObj);
			if(codes.size() == 0){
				return resultObj.toJSONString();
			}
			//权限过滤后的codes
			codes = dataPermService.permFilter(userid,codes,SystemConfig.dataTypeUpdate);
			if(codes.size() == 0){
				resultObj.put("errcode", ErrCode.noPermission);
				resultObj.put("msg","用户无此修改权限");
				return resultObj.toJSONString();
			}

			//数据校验
			if(!JsonParamValidator.valveAddVal(parameter,resultObj)){
				//非法数据，返回错误码
				resultObj.put("errcode", ErrCode.parameErr);
				return resultObj.toJSONString();
			}

			try{
				HouseHolder record = new HouseHolder();
				record.setCode(parameter.getString("house_code"));
				record.setIsdel(0);
				HouseHolder houseHolder = queryBuildService.geHuseHolder(record);
				if(houseHolder == null){
					resultObj.put("errcode", ErrCode.noData);
					resultObj.put("msg","无对应住户");
					return resultObj.toJSONString();
				}

				if(parameter.getString("type").equals("0")){
					HouseValve hrecord = new HouseValve();
					hrecord.setComm_address(parameter.getString("comm_address"));
					HouseValve houseValve = queryDeviceService.getHouseValve(hrecord);
					if(houseValve != null){
						resultObj.put("errcode", ErrCode.repetition);
						resultObj.put("msg","通信地址重复");
						return resultObj.toJSONString();
					}
				}
			}catch (Exception e){
				resultObj.put("errcode", ErrCode.repetition);
				resultObj.put("msg","存在多个相同住户");
				return resultObj.toJSONString();
			}


			//数据入库，成功后返回.
			int res = addAddDeviceService.addValve(parameter);
			if(res == 1){
				HouseValve record  = JSON.parseObject(parameter.toJSONString(), HouseValve.class);
				if (record.getType() == 0){
					addAddDeviceService.addInRedis("HouseValve",record.getComm_address(),JSON.toJSONString(record));
				}else if(record.getType() == 1){
					addAddDeviceService.addInRedis("HouseValve",record.getHouse_code(),JSON.toJSONString(record));
				}

				resultObj.put("errcode", ErrCode.success);
				return resultObj.toJSONString();
			}else if(res == -1){
				resultObj.put("errcode", ErrCode.repetition);
				resultObj.put("msg","数据重复插入");
				return resultObj.toJSONString();
			}else if(res == -2){
				resultObj.put("errcode", ErrCode.repetition);
				resultObj.put("msg","无对应住户");
				return resultObj.toJSONString();
			}

		} catch (Exception e) {
			e.printStackTrace();

			resultObj.put("errcode", ErrCode.routineErr);
			resultObj.put("msg",e.getMessage());
			return resultObj.toJSONString();
		} finally {

		}
		resultObj.put("errcode", ErrCode.unknowErr);
		resultObj.put("msg","未知错误");
		return resultObj.toJSONString();
	}

	@RequestMapping(value = "/addHouseCalorimeter", method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public String addHouseCalorimeter(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
		JSONObject resultObj = new JSONObject();
		JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
		List<String> codes = new ArrayList<String>();
		try {
			//根据token 获取用户id
			int userid = dataPermService.getUserid(token,resultObj);
			if(userid == -1){
				return resultObj.toJSONString();
			}
			//之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			codes = dataPermService.getCodesScope("house_code",parameter,resultObj);
			if(codes.size() == 0){
				return resultObj.toJSONString();
			}
			//权限过滤后的codes
			codes = dataPermService.permFilter(userid,codes,SystemConfig.dataTypeUpdate);
			if(codes.size() == 0){
				resultObj.put("errcode", ErrCode.noPermission);
				resultObj.put("msg","用户无此修改权限");
				return resultObj.toJSONString();
			}
			//数据校验
			if(!JsonParamValidator.houseCalorimeterAddVal(parameter,resultObj)){
				//非法数据，返回错误码
				resultObj.put("errcode", ErrCode.parameErr);
				return resultObj.toJSONString();
			}

			try{
				HouseHolder record = new HouseHolder();
				record.setCode(parameter.getString("house_code"));
				record.setIsdel(0);
				HouseHolder houseHolder = queryBuildService.geHuseHolder(record);
				if(houseHolder == null){
					resultObj.put("errcode", ErrCode.noData);
					resultObj.put("msg","无对应住户");
					return resultObj.toJSONString();
				}

				if(parameter.getString("type").equals("0")){
					HouseCalorimeter record1 = new HouseCalorimeter();
					record1.setComm_address(parameter.getString("comm_address"));
					HouseCalorimeter rocord2 = queryDeviceService.getHouseCalorimeter(record1);
					if(rocord2 != null){
						resultObj.put("errcode", ErrCode.repetition);
						resultObj.put("msg","通信地址重复");
						return resultObj.toJSONString();
					}
				}
			}catch (Exception e){
				resultObj.put("errcode", ErrCode.repetition);
				resultObj.put("msg","存在多个相同住户");
				return resultObj.toJSONString();
			}

			//数据入库，成功后返回.
			int res = addAddDeviceService.addHouseCalorimeter(parameter);
			if(res == 1){
				HouseCalorimeter record  = JSON.parseObject(parameter.toJSONString(), HouseCalorimeter.class);
				if (record.getType() == 0){
					addAddDeviceService.addInRedis("HouseCalorimeter",record.getComm_address(),JSON.toJSONString(record));
				}else if(record.getType() == 1){
					addAddDeviceService.addInRedis("HouseCalorimeter",record.getHouse_code(),JSON.toJSONString(record));
				}

				resultObj.put("errcode", ErrCode.success);
				return resultObj.toJSONString();
			}else if(res == -1){
				resultObj.put("errcode", ErrCode.repetition);
				resultObj.put("msg","数据重复插入");
				return resultObj.toJSONString();
			}

		} catch (Exception e) {
			e.printStackTrace();

			resultObj.put("errcode", ErrCode.routineErr);
			resultObj.put("msg",e.getMessage());
			return resultObj.toJSONString();
		} finally {

		}
		resultObj.put("errcode", ErrCode.unknowErr);
		resultObj.put("msg","未知错误");
		return resultObj.toJSONString();
	}

	@RequestMapping(value = "/addConcentrator", method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public String addConcentrator(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
		JSONObject resultObj = new JSONObject();
		JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
		List<String> codes = new ArrayList<String>();
		try {
			//根据token 获取用户id
			int userid = dataPermService.getUserid(token,resultObj);
			if(userid == -1){
				return resultObj.toJSONString();
			}
			//之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			codes = dataPermService.getCodesScope("building_unique_code",parameter,resultObj);
			if(codes.size() == 0){
				return resultObj.toJSONString();
			}
			//权限过滤后的codes
			codes = dataPermService.permFilter(userid,codes,SystemConfig.dataTypeUpdate);
			if(codes.size() == 0){
				resultObj.put("errcode", ErrCode.noPermission);
				resultObj.put("msg","用户无此修改权限");
				return resultObj.toJSONString();
			}
			//数据校验
			if(!JsonParamValidator.concentratorAddVal(parameter,resultObj)){
				//非法数据，返回错误码
				resultObj.put("errcode", ErrCode.parameErr);
				return resultObj.toJSONString();
			}

			try{
				Building record = new Building();
				record.setUnique_code(parameter.getString("building_unique_code"));
				record.setIsdel(0);
				Building building = queryBuildService.getBuilding(record);
				if(building == null){
					resultObj.put("errcode", ErrCode.noData);
					resultObj.put("msg","无对应楼栋");
					return resultObj.toJSONString();
				}

				Concentrator record1 = new Concentrator();
				record1.setCode(parameter.getString("code"));
				Concentrator rocord2 = queryDeviceService.getConcentrator(record1);
				if(rocord2 != null){
					resultObj.put("errcode", ErrCode.repetition);
					resultObj.put("msg","采集器编号重复");
					return resultObj.toJSONString();
				}
			}catch (Exception e){
				resultObj.put("errcode", ErrCode.repetition);
				resultObj.put("msg","存在多个相同楼栋");
				return resultObj.toJSONString();
			}

			//数据入库，成功后返回.
			int res = addAddDeviceService.addConcentrator(parameter);
			if(res == 1){
				Concentrator record  = JSON.parseObject(parameter.toJSONString(), Concentrator.class);
				addAddDeviceService.addInRedis("Concentrator",record.getCode(),JSON.toJSONString(record));

				resultObj.put("errcode", ErrCode.success);
				return resultObj.toJSONString();
			}else if(res == -1){
				resultObj.put("errcode", ErrCode.repetition);
				resultObj.put("msg","数据重复插入");
				return resultObj.toJSONString();
			}

		} catch (Exception e) {
			e.printStackTrace();
			resultObj.put("errcode", ErrCode.routineErr);
			resultObj.put("msg",e.getMessage());
			return resultObj.toJSONString();
		} finally {

		}
		resultObj.put("errcode", ErrCode.unknowErr);
		resultObj.put("msg","未知错误");
		return resultObj.toJSONString();
	}

	@RequestMapping(value = "/addGateway", method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public String addGateway(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
		JSONObject resultObj = new JSONObject();
		JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
		List<String> codes = new ArrayList<String>();
		try {
			//根据token 获取用户id
			int userid = dataPermService.getUserid(token,resultObj);
			if(userid == -1){
				return resultObj.toJSONString();
			}
			//之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			if(parameter.getString("company_code") != null && RegexUtil.isDigits(parameter.getString("company_code"))){
				List<String> companyCodes = new ArrayList<String>();
				companyCodes.add(parameter.getString("company_code"));
				List<Company> companys = queryBuildService.getCompanyByCodes(companyCodes);
				for(Company obj : companys){
					codes.add(obj.getCode());
				}
				if(codes.size() == 0){
					resultObj.put("errcode", ErrCode.noData);
					resultObj.put("msg","无此公司");
					return resultObj.toJSONString();
				}
			}else{
				resultObj.put("errcode", ErrCode.parameErr);
				resultObj.put("msg","公司id错误");
				return resultObj.toJSONString();
			}
			//权限过滤后的codes
			codes = dataPermService.permFilter(userid,codes,SystemConfig.dataTypeUpdate);
			if(codes.size() == 0){
				resultObj.put("errcode", ErrCode.noPermission);
				resultObj.put("msg","用户无此修改权限");
				return resultObj.toJSONString();
			}

			//数据校验
			if(!JsonParamValidator.gatewayAddVal(parameter,resultObj)){
				//非法数据，返回错误码
				resultObj.put("errcode", ErrCode.parameErr);
				return resultObj.toJSONString();
			}
			try{
				Gateway record1 = new Gateway();
				record1.setMac(parameter.getString("mac"));
				Gateway rocord2 = queryDeviceService.getGateway(record1);
				if(rocord2 != null){
					resultObj.put("errcode", ErrCode.repetition);
					resultObj.put("msg","mac地址重复");
					return resultObj.toJSONString();
				}
			}catch (Exception e){
				resultObj.put("errcode", ErrCode.repetition);
				resultObj.put("msg","mac地址重复");
				return resultObj.toJSONString();
			}
			//数据入库，成功后返回.
			int res = addAddDeviceService.addGateway(parameter);
			if(res == 1){
				Gateway record  = JSON.parseObject(parameter.toJSONString(), Gateway.class);
				addAddDeviceService.addInRedis("Gateway",record.getMac(),JSON.toJSONString(record));

				resultObj.put("errcode", ErrCode.success);
				return resultObj.toJSONString();
			}else if(res == -1){
				resultObj.put("errcode", ErrCode.repetition);
				resultObj.put("msg","数据重复插入");
				return resultObj.toJSONString();
			}

		} catch (Exception e) {
			e.printStackTrace();

			resultObj.put("errcode", ErrCode.routineErr);
			resultObj.put("msg",e.getMessage());
			return resultObj.toJSONString();
		} finally {

		}
		resultObj.put("errcode", ErrCode.unknowErr);
		resultObj.put("msg","未知错误");
		return resultObj.toJSONString();
	}

	@RequestMapping(value = "/addBuildingValve", method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public String addBuildingValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
		JSONObject resultObj = new JSONObject();
		JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
		List<String> codes = new ArrayList<String>();
		try {
			//根据token 获取用户id
			int userid = dataPermService.getUserid(token,resultObj);
			if(userid == -1){
				return resultObj.toJSONString();
			}
			//之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			codes = dataPermService.getCodesScope("building_unique_code",parameter,resultObj);
			if(codes.size() == 0){
				return resultObj.toJSONString();
			}
			//权限过滤后的codes
			codes = dataPermService.permFilter(userid,codes,SystemConfig.dataTypeUpdate);
			if(codes.size() == 0){
				resultObj.put("errcode", ErrCode.noPermission);
				resultObj.put("msg","用户无此修改权限");
				return resultObj.toJSONString();
			}
			//数据校验
			if(!JsonParamValidator.buildingValveAddVal(parameter,resultObj)){
				//非法数据，返回错误码
				resultObj.put("errcode", ErrCode.parameErr);
				return resultObj.toJSONString();
			}

			try{
				Building record = new Building();
				record.setUnique_code(parameter.getString("building_unique_code"));
				record.setIsdel(0);
				Building building = queryBuildService.getBuilding(record);
				if(building == null){
					resultObj.put("errcode", ErrCode.noData);
					resultObj.put("msg","无对应楼栋");
					return resultObj.toJSONString();
				}

				BuildingValve record1 = new BuildingValve();
				record1.setComm_address(parameter.getString("comm_address"));
				BuildingValve rocord2 = queryDeviceService.getBuildingValve(record1);
				if(rocord2 != null){
					resultObj.put("errcode", ErrCode.repetition);
					resultObj.put("msg","通信地址重复");
					return resultObj.toJSONString();
				}
			}catch (Exception e){
				resultObj.put("errcode", ErrCode.repetition);
				resultObj.put("msg","存在多个相同楼栋");
				return resultObj.toJSONString();
			}

			//数据入库，成功后返回.
			int res = addAddDeviceService.addBuildingValve(parameter);
			if(res == 1){
				BuildingValve record  = JSON.parseObject(parameter.toJSONString(), BuildingValve.class);
				addAddDeviceService.addInRedis("BuildingValve",record.getComm_address(),JSON.toJSONString(record));
				resultObj.put("errcode", ErrCode.success);
				return resultObj.toJSONString();
			}else if(res == -1){
				resultObj.put("errcode", ErrCode.repetition);
				resultObj.put("msg","数据重复插入");
				return resultObj.toJSONString();
			}

		} catch (Exception e) {
			e.printStackTrace();

			resultObj.put("errcode", ErrCode.routineErr);
			resultObj.put("msg",e.getMessage());
			return resultObj.toJSONString();
		} finally {

		}
		resultObj.put("errcode", ErrCode.unknowErr);
		resultObj.put("msg","未知错误");
		return resultObj.toJSONString();
	}

	@RequestMapping(value = "/addBuildingCalorimeter", method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public String addBuildingCalorimeter(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
		JSONObject resultObj = new JSONObject();
		JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
		List<String> codes = new ArrayList<String>();
		try {
			//根据token 获取用户id
			int userid = dataPermService.getUserid(token,resultObj);
			if(userid == -1){
				return resultObj.toJSONString();
			}
			//之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			codes = dataPermService.getCodesScope("building_unique_code",parameter,resultObj);
			if(codes.size() == 0){
				return resultObj.toJSONString();
			}
			//权限过滤后的codes
			codes = dataPermService.permFilter(userid,codes,SystemConfig.dataTypeUpdate);
			if(codes.size() == 0){
				resultObj.put("errcode", ErrCode.noPermission);
				resultObj.put("msg","用户无此修改权限");
				return resultObj.toJSONString();
			}
			//数据校验
			if(!JsonParamValidator.buildingCalorimeterAddVal(parameter,resultObj)){
				//非法数据，返回错误码
				resultObj.put("errcode", ErrCode.parameErr);
				return resultObj.toJSONString();
			}

			try{
				Building record = new Building();
				record.setUnique_code(parameter.getString("building_unique_code"));
				record.setIsdel(0);
				Building building = queryBuildService.getBuilding(record);
				if(building == null){
					resultObj.put("errcode", ErrCode.noData);
					resultObj.put("msg","无对应楼栋");
					return resultObj.toJSONString();
				}

				BuildingCalorimeter record1 = new BuildingCalorimeter();
				record1.setComm_address(parameter.getString("comm_address"));
				BuildingCalorimeter rocord2 = queryDeviceService.getBuildingCalorimeter(record1);
				if(rocord2 != null){
					resultObj.put("errcode", ErrCode.repetition);
					resultObj.put("msg","通信地址重复");
					return resultObj.toJSONString();
				}
			}catch (Exception e){
				resultObj.put("errcode", ErrCode.repetition);
				resultObj.put("msg","存在多个相同楼栋");
				return resultObj.toJSONString();
			}

			//数据入库，成功后返回.
			int res = addAddDeviceService.addBuildingCalorimeter(parameter);
			if(res == 1){
				BuildingCalorimeter record  = JSON.parseObject(parameter.toJSONString(), BuildingCalorimeter.class);
				addAddDeviceService.addInRedis("BuildingValve",record.getComm_address(),JSON.toJSONString(record));
				resultObj.put("errcode", ErrCode.success);
				return resultObj.toJSONString();
			}else if(res == -1){
				resultObj.put("errcode", ErrCode.repetition);
				resultObj.put("msg","数据重复插入");
				return resultObj.toJSONString();
			}

		} catch (Exception e) {
			e.printStackTrace();

			resultObj.put("errcode", ErrCode.routineErr);
			resultObj.put("msg",e.getMessage());
			return resultObj.toJSONString();
		} finally {

		}
		resultObj.put("errcode", ErrCode.unknowErr);
		resultObj.put("msg","未知错误");
		return resultObj.toJSONString();
	}
}
