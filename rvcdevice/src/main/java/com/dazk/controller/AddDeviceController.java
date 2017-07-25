package com.dazk.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dazk.service.AddDeviceService;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.dazk.common.ErrCode;
import com.dazk.validator.JsonParamValidator;

@RestController
@RequestMapping("/device")
public class AddDeviceController {
	@Resource
	private AddDeviceService addAddDeviceService;
	
	@RequestMapping(value = "/addValve", method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public String addValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		JSONObject resultObj = new JSONObject();
		//权限验证
		String token  = request.getParameter("token");
		//根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
		try {
			System.out.println("result="+requestBody);
			JSONObject parameter = JSON.parseObject(requestBody);
			//数据校验
			if(!JsonParamValidator.valveVal(parameter)){
				//非法数据，返回错误码
				resultObj.put("errcode", ErrCode.parameErr);
				return resultObj.toJSONString();
			}

			//数据入库，成功后返回.
			int res = addAddDeviceService.addValve(parameter);
			if(res == 1){
				resultObj.put("errcode", ErrCode.success);
				return resultObj.toJSONString();
			}else if(res == -1){
				resultObj.put("errcode", ErrCode.repetition);
				return resultObj.toJSONString();
			}

		} catch (Exception e) {
			e.printStackTrace();

			resultObj.put("errcode", ErrCode.routineErr);
			return resultObj.toJSONString();
		} finally {

		}
		resultObj.put("errcode", ErrCode.unknowErr);
		return resultObj.toJSONString();
	}

	@RequestMapping(value = "/addHouseCalorimeter", method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public String addHouseCalorimeter(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		JSONObject resultObj = new JSONObject();
		//权限验证
		String token  = request.getParameter("token");
		//根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
		try {
			System.out.println("result="+requestBody);
			JSONObject parameter = JSON.parseObject(requestBody);
			//数据校验
			if(!JsonParamValidator.houseCalorimeterVal(parameter)){
				//非法数据，返回错误码
				resultObj.put("errcode", ErrCode.parameErr);
				return resultObj.toJSONString();
			}

			//数据入库，成功后返回.
			int res = addAddDeviceService.addHouseCalorimeter(parameter);
			if(res == 1){
				resultObj.put("errcode", ErrCode.success);
				return resultObj.toJSONString();
			}else if(res == -1){
				resultObj.put("errcode", ErrCode.repetition);
				return resultObj.toJSONString();
			}

		} catch (Exception e) {
			e.printStackTrace();

			resultObj.put("errcode", ErrCode.routineErr);
			return resultObj.toJSONString();
		} finally {

		}
		resultObj.put("errcode", ErrCode.unknowErr);
		return resultObj.toJSONString();
	}

	@RequestMapping(value = "/addConcentrator", method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public String addConcentrator(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		JSONObject resultObj = new JSONObject();
		//权限验证
		String token  = request.getParameter("token");
		//根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
		try {
			System.out.println("result="+requestBody);
			JSONObject parameter = JSON.parseObject(requestBody);
			//数据校验
			if(!JsonParamValidator.concentratorVal(parameter)){
				//非法数据，返回错误码
				resultObj.put("errcode", ErrCode.parameErr);
				return resultObj.toJSONString();
			}

			//数据入库，成功后返回.
			int res = addAddDeviceService.addConcentrator(parameter);
			if(res == 1){
				resultObj.put("errcode", ErrCode.success);
				return resultObj.toJSONString();
			}else if(res == -1){
				resultObj.put("errcode", ErrCode.repetition);
				return resultObj.toJSONString();
			}

		} catch (Exception e) {
			e.printStackTrace();

			resultObj.put("errcode", ErrCode.routineErr);
			return resultObj.toJSONString();
		} finally {

		}
		resultObj.put("errcode", ErrCode.unknowErr);
		return resultObj.toJSONString();
	}

	@RequestMapping(value = "/addGateway", method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public String addGateway(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		JSONObject resultObj = new JSONObject();
		//权限验证
		String token  = request.getParameter("token");
		//根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
		try {
			System.out.println("result="+requestBody);
			JSONObject parameter = JSON.parseObject(requestBody);
			//数据校验
			if(!JsonParamValidator.gatewayVal(parameter)){
				//非法数据，返回错误码
				resultObj.put("errcode", ErrCode.parameErr);
				return resultObj.toJSONString();
			}

			//数据入库，成功后返回.
			int res = addAddDeviceService.addGateway(parameter);
			if(res == 1){
				resultObj.put("errcode", ErrCode.success);
				return resultObj.toJSONString();
			}else if(res == -1){
				resultObj.put("errcode", ErrCode.repetition);
				return resultObj.toJSONString();
			}

		} catch (Exception e) {
			e.printStackTrace();

			resultObj.put("errcode", ErrCode.routineErr);
			return resultObj.toJSONString();
		} finally {

		}
		resultObj.put("errcode", ErrCode.unknowErr);
		return resultObj.toJSONString();
	}

	@RequestMapping(value = "/addBuildingValve", method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public String addBuildingValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		JSONObject resultObj = new JSONObject();
		//权限验证
		String token  = request.getParameter("token");
		//根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
		try {
			System.out.println("result="+requestBody);
			JSONObject parameter = JSON.parseObject(requestBody);
			//数据校验
			if(!JsonParamValidator.buildingValveVal(parameter)){
				//非法数据，返回错误码
				resultObj.put("errcode", ErrCode.parameErr);
				return resultObj.toJSONString();
			}

			//数据入库，成功后返回.
			int res = addAddDeviceService.addBuildingValve(parameter);
			if(res == 1){
				resultObj.put("errcode", ErrCode.success);
				return resultObj.toJSONString();
			}else if(res == -1){
				resultObj.put("errcode", ErrCode.repetition);
				return resultObj.toJSONString();
			}

		} catch (Exception e) {
			e.printStackTrace();

			resultObj.put("errcode", ErrCode.routineErr);
			return resultObj.toJSONString();
		} finally {

		}
		resultObj.put("errcode", ErrCode.unknowErr);
		return resultObj.toJSONString();
	}

	@RequestMapping(value = "/addBuildingCalorimeter", method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
	public String addBuildingCalorimeter(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		JSONObject resultObj = new JSONObject();
		//权限验证
		String token  = request.getParameter("token");
		//根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
		try {
			System.out.println("result="+requestBody);
			JSONObject parameter = JSON.parseObject(requestBody);
			//数据校验
			if(!JsonParamValidator.buildingCalorimeterVal(parameter)){
				//非法数据，返回错误码
				resultObj.put("errcode", ErrCode.parameErr);
				return resultObj.toJSONString();
			}

			//数据入库，成功后返回.
			int res = addAddDeviceService.addBuildingCalorimeter(parameter);
			if(res == 1){
				resultObj.put("errcode", ErrCode.success);
				return resultObj.toJSONString();
			}else if(res == -1){
				resultObj.put("errcode", ErrCode.repetition);
				return resultObj.toJSONString();
			}

		} catch (Exception e) {
			e.printStackTrace();

			resultObj.put("errcode", ErrCode.routineErr);
			return resultObj.toJSONString();
		} finally {

		}
		resultObj.put("errcode", ErrCode.unknowErr);
		return resultObj.toJSONString();
	}
}
