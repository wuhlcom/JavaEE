package com.dazk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.ErrCode;
import com.dazk.db.model.*;
import com.dazk.service.QueryDeviceService;
import com.dazk.validator.JsonParamValidator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2017/7/22.
 */
@RestController
@RequestMapping("/device")
public class QueryDeviceController {
    @Resource
    private QueryDeviceService queryDeviceService;

    @RequestMapping(value = "/queryValve", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String queryValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);
            //数据校验

            //权限验证
            String token  = request.getParameter("token");
            //根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

            //数据查询，成功后返回.
            List<HouseValve> result = queryDeviceService.queryValve(parameter);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
            }
            int totalRows = queryDeviceService.queryValveCount(parameter);
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

    @RequestMapping(value = "/queryHouseCalorimeter", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String queryHouseCalorimeter(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);
            //数据校验

            //权限验证
            String token  = request.getParameter("token");
            //根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

            //数据查询，成功后返回.
            List<HouseCalorimeter> result = queryDeviceService.queryHouseCalorimeter(parameter);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
            }
            int totalRows = queryDeviceService.queryHouseCalorimeterCount(parameter);
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

    @RequestMapping(value = "/queryConcentrator", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String queryConcentrator(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);
            //数据校验

            //权限验证
            String token  = request.getParameter("token");
            //根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

            //数据查询，成功后返回.
            List<Concentrator> result = queryDeviceService.queryConcentrator(parameter);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
            }
            int totalRows = queryDeviceService.queryConcentratorCount(parameter);
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

    @RequestMapping(value = "/queryGateway", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String queryGateway(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);
            //数据校验

            //权限验证
            String token  = request.getParameter("token");
            //根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

            //数据查询，成功后返回.
            List<Gateway> result = queryDeviceService.queryGateway(parameter);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
            }
            int totalRows = queryDeviceService.queryGatewayCount(parameter);
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

    @RequestMapping(value = "/queryBuildingValve", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String queryBuildingValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);
            //数据校验

            //权限验证
            String token  = request.getParameter("token");
            //根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

            //数据查询，成功后返回.
            List<BuildingValve> result = queryDeviceService.queryBuildingValve(parameter);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
            }
            int totalRows = queryDeviceService.queryBuildingValveCount(parameter);
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

    @RequestMapping(value = "/queryBuildingCalorimeter", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String queryBuildingCalorimeter(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);
            //数据校验

            //权限验证
            String token  = request.getParameter("token");
            //根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

            //数据查询，成功后返回.
            List<BuildingCalorimeter> result = queryDeviceService.queryBuildingCalorimeter(parameter);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
            }
            int totalRows = queryDeviceService.queryBuildingCalorimeterCount(parameter);
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
