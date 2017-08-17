package com.dazk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.ErrCode;
import com.dazk.common.util.JsonUtil;
import com.dazk.db.model.*;
import com.dazk.service.DataPermService;
import com.dazk.service.QueryBuildService;
import com.dazk.service.QueryDeviceService;
import com.dazk.validator.JsonParamValidator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
@RestController
@RequestMapping("/device")
public class DeviceOnlineController {
    @Resource
    private QueryBuildService queryBuildService;

    @Resource
    private QueryDeviceService queryDeviceService;

    @Resource
    private DataPermService dataPermService;

    @RequestMapping(value = "/onlineValve", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String onlineValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
            List<String> codes = new ArrayList<String>();
            //数据校验
            if(!JsonParamValidator.valveQueryVal(parameter,resultObj)){
                return resultObj.toJSONString();
            }

            //用户校验及权限验证
            int isLiberty  = dataPermService.userPerm(codes,token,parameter,resultObj);

            if(isLiberty < 0){
                return resultObj.toJSONString();
            }

            //数据查询，成功后返回.
            Integer page = JsonParamValidator.str2Digits(parameter.getString("page"));
            Integer listRows = JsonParamValidator.str2Digits(parameter.getString("listRows"));

            //查询条件
            HouseValve condition = JSON.parseObject(parameter.toJSONString(), HouseValve.class);
             
            //数据查询，成功后返回.
            List<HouseValve> result = queryDeviceService.queryValve(codes,page,listRows,condition);

            int totalRows = queryDeviceService.queryValveCount(codes,condition);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
            }
            resultObj.put("errcode", ErrCode.success);
            resultObj.put("totalRows",totalRows);
            resultObj.put("result",result);
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/onlineHouseCalorimeter", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String onlineHouseCalorimeter(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
            List<String> codes = new ArrayList<String>();
            //数据校验
            if(!JsonParamValidator.houseCalorimeterQueryVal(parameter,resultObj)){
                return resultObj.toJSONString();
            }
            //用户校验及权限验证
            int isLiberty  =  dataPermService.userPerm(codes,token,parameter,resultObj);

            if(isLiberty < 0){
                return resultObj.toJSONString();
            }

            //数据查询，成功后返回.
            Integer page = JsonParamValidator.str2Digits(parameter.getString("page"));
            Integer listRows = JsonParamValidator.str2Digits(parameter.getString("listRows"));
            System.out.println("page" + page+" listRows"+listRows);
            //查询条件
            HouseCalorimeter condition = JSON.parseObject(parameter.toJSONString(), HouseCalorimeter.class);
            //数据查询，成功后返回.
            List<HouseCalorimeter> result = queryDeviceService.queryHouseCalorimeter(codes,page,listRows,condition);

            int totalRows = queryDeviceService.queryHouseCalorimeterCount(codes,condition);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
            }
            resultObj.put("errcode", ErrCode.success);
            resultObj.put("totalRows",totalRows);
            resultObj.put("result",result);
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/onlineConcentrator", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String onlineConcentrator(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
            List<String> codes = new ArrayList<String>();
            //数据校验
            if(!JsonParamValidator.concentratorQueryVal(parameter,resultObj)){
                return resultObj.toJSONString();
            }
            //用户校验及权限验证
            int isLiberty  = dataPermService.userPerm(codes,token,parameter,resultObj);

            if(isLiberty < 0){
                return resultObj.toJSONString();
            }

            //数据查询，成功后返回.
            Integer page = JsonParamValidator.str2Digits(parameter.getString("page"));
            Integer listRows = JsonParamValidator.str2Digits(parameter.getString("listRows"));

            //查询条件
            Concentrator condition = JSON.parseObject(parameter.toJSONString(), Concentrator.class);
            //数据查询，成功后返回.
            List<Concentrator> result = queryDeviceService.queryConcentrator(codes,page,listRows,condition);

            //调用知路接口得到 阀门状态，进水温度，回水温度 后存入HouseValveData

            int totalRows = queryDeviceService.queryConcentratorCount(codes,condition);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
            }
            resultObj.put("errcode", ErrCode.success);
            resultObj.put("totalRows",totalRows);
            resultObj.put("result",result);
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/onlineGateway", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String onlineGateway(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
            //数据校验
            if(!JsonParamValidator.gatewayQueryVal(parameter,resultObj)){
                resultObj.put("errcode", ErrCode.parameErr);
                return resultObj.toJSONString();
            }

            //根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
            Integer userid = dataPermService.getUserid(token,resultObj);
            if(userid == -1){
                return resultObj.toJSONString();
            }

            //数据查询，成功后返回.
            List<Gateway> result = queryDeviceService.queryGateway(parameter);
            int totalRows = queryDeviceService.queryGatewayCount(parameter);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
            }
            resultObj.put("errcode", ErrCode.success);
            resultObj.put("totalRows",totalRows);
            resultObj.put("result",result);
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/onlineBuildingValve", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String onlineBuildingValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
            List<String> codes = new ArrayList<String>();
            //数据校验
            if(!JsonParamValidator.buildingValveQueryVal(parameter,resultObj)){
                return resultObj.toJSONString();
            }
            //用户校验及权限验证

            int isLiberty  =  dataPermService.userPerm(codes,token,parameter,resultObj);

            if(isLiberty < 0){
                return resultObj.toJSONString();
            }

            //数据查询，成功后返回.
            Integer page = JsonParamValidator.str2Digits(parameter.getString("page"));
            Integer listRows = JsonParamValidator.str2Digits(parameter.getString("listRows"));

            //查询条件
            BuildingValve condition = JSON.parseObject(parameter.toJSONString(), BuildingValve.class);
            //数据查询，成功后返回.
            List<BuildingValve> result = queryDeviceService.queryBuildingValve(codes,page,listRows,condition);

            int totalRows = queryDeviceService.queryBuildingValveCount(codes,condition);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
            }
            resultObj.put("errcode", ErrCode.success);
            resultObj.put("totalRows",totalRows);
            resultObj.put("result",result);
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/onlineBuildingCalorimeter", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String onlineBuildingCalorimeter(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
            List<String> codes = new ArrayList<String>();
            //数据校验
            if(!JsonParamValidator.buildingCalorimeterQueryVal(parameter,resultObj)){
                return resultObj.toJSONString();
            }
            //用户校验及权限验证

            int isLiberty  = dataPermService.userPerm(codes,token,parameter,resultObj);

            if(isLiberty < 0){
                return resultObj.toJSONString();
            }

            //数据查询，成功后返回.
            Integer page = JsonParamValidator.str2Digits(parameter.getString("page"));
            Integer listRows = JsonParamValidator.str2Digits(parameter.getString("listRows"));

            //查询条件
            BuildingCalorimeter condition = JSON.parseObject(parameter.toJSONString(), BuildingCalorimeter.class);
            //数据查询，成功后返回.
            List<BuildingCalorimeter> result = queryDeviceService.queryBuildingCalorimeter(codes,page,listRows,condition);

            int totalRows = queryDeviceService.queryBuildingCalorimeterCount(codes,condition);

            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
            }
            resultObj.put("errcode", ErrCode.success);
            resultObj.put("totalRows",totalRows);
            resultObj.put("result",result);
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

}
