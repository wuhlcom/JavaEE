package com.dazk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.ErrCode;
import com.dazk.common.SystemConfig;
import com.dazk.common.util.HttpRequest;
import com.dazk.common.util.JsonUtil;
import com.dazk.common.util.RegexUtil;
import com.dazk.db.dao.ConcentratorMapper;
import com.dazk.db.dao.DeviceLogExampleMapper;
import com.dazk.db.model.*;
import com.dazk.service.DataPermService;
import com.dazk.service.DeviceOperateService;
import com.dazk.service.QueryDeviceService;
import com.dazk.service.TokenService;
import com.dazk.validator.JsonParamValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/26.
 */
@RestController
@RequestMapping("/device")
public class DeviceOperateController {
    @Resource
    private QueryDeviceService queryDeviceService;

    @Resource
    private DataPermService dataPermService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private DeviceOperateService deviceOperateService;


    @Autowired
    private ConcentratorMapper concentratorMapper;

    @Autowired
    private DeviceLogExampleMapper deviceLogExampleMapper;

    @RequestMapping(value = "/opValve", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String opValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        JSONObject resultObj = new JSONObject();
        JSONObject parameter = JSON.parseObject(requestBody);
        JsonUtil.filterNull(parameter);
        List<String> codes = new ArrayList<String>();
        try {
            //根据token 获取用户id
            int userid = tokenService.getUserid(token,resultObj);
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
            String house_code = parameter.getString("house_code");
            if(!JsonParamValidator.isHouseCode(house_code)){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "住户编号错误");
                return resultObj.toJSONString();
            }
            String period = parameter.getString("period");
            if(period != null && !RegexUtil.isDigits(period)){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "period不是数字");
                return resultObj.toJSONString();
            }
            String open = parameter.getString("open");
            if(!RegexUtil.isDigits(open)){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "open不是数字");
                return resultObj.toJSONString();
            }else  if(Integer.parseInt(open) > 100 || Integer.parseInt(open) < 0){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "open范围0-100");
                return resultObj.toJSONString();
            }
            String islock = parameter.getString("islock");
            if(islock != null && (!RegexUtil.isDigits(islock) || islock.length() > 2)){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "islock 错误");
                return resultObj.toJSONString();
            }

            if(islock == null){
                islock = "0";
            }

            HouseValve condition = new HouseValve();
            condition.setHouse_code(house_code);
            List<HouseValve> houseValves = queryDeviceService.queryValve(codes,null,null,condition);
            if(houseValves.size() == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "无可操作设备");
                return resultObj.toJSONString();
            }

            //阀门操作
            int opRes = deviceOperateService.opValve(userid,house_code,Integer.parseInt(open),Integer.parseInt(islock),resultObj);

            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/readValve", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String readValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
            //数据校验

            //权限验证
             
            //通过权限过滤得到小区code列表  codes
            List<String> codes = new ArrayList<String>();
            String scope = parameter.getString("scope");
            String scopeType = parameter.getString("scopeType");
            String scopeStr = parameter.getString("scopeStr");
            int codeNum = queryDeviceService.getCodes(scope,scopeType,scopeStr,codes);
            if(codeNum == -1){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg","scope参数错误");
                return resultObj.toJSONString();
            }

            //数据查询，成功后返回.
            Integer page = JsonParamValidator.str2Digits(parameter.getString("page"));
            Integer listRows = JsonParamValidator.str2Digits(parameter.getString("listRows"));

            //数据查询，成功后返回.
            List<HouseValve> entitys = queryDeviceService.queryValve(codes,page,listRows,null);

            List<HouseValveData> result = new ArrayList<HouseValveData>();
            //调用知路接口得到 阀门状态，进水温度，回水温度 后存入HouseValveData

            List<String> commonAdresses = new ArrayList<String>();

            for (HouseValve houseValve:entitys) {
                commonAdresses.add(houseValve.getComm_address());
                result.add(new HouseValveData(houseValve));
            }

            int totalRows = queryDeviceService.queryValveCount(codes,null);
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

    @RequestMapping(value = "/setBuildingValve", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String setBuildingValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        JSONObject resultObj = new JSONObject();
        JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
        List<String> codes = new ArrayList<String>();
        try {
            //根据token 获取用户id
            int userid = tokenService.getUserid(token,resultObj);
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
            String building_unique_code = parameter.getString("building_unique_code");
            if(!JsonParamValidator.isBuildingUniqueCode(building_unique_code)){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "楼栋编号错误");
                return resultObj.toJSONString();
            }
            Map<String,String> parameters = new HashMap<String,String>();
            for(int i = 1;i < 24;i++){
                String open = parameter.getString("opening"+i);
                if(open == null){
                    resultObj.put("errcode", ErrCode.parameErr);
                    resultObj.put("msg", "开度不能为空");
                    return resultObj.toJSONString();
                }else if(open != null && !RegexUtil.isDigits(open)){
                    resultObj.put("errcode", ErrCode.parameErr);
                    resultObj.put("msg", "open不是数字");
                    return resultObj.toJSONString();
                }else  if(Integer.parseInt(open) > 100 || Integer.parseInt(open) < 0){
                    resultObj.put("errcode", ErrCode.parameErr);
                    resultObj.put("msg", "open范围0-100");
                    return resultObj.toJSONString();
                }
                parameters.put("opening"+i,open);
            }

            String comm_address = parameter.getString("comm_address");
            if(comm_address == null) {
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "通信地址不能为空");
            }

            parameters.put("building_unique_code",building_unique_code);
            parameters.put("comm_address",comm_address);

            //阀门操作
            int opRes = deviceOperateService.setBuildingValve(userid,parameters,resultObj);

            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/readBuildingValveSet", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String readBuildingValveSet(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        JSONObject resultObj = new JSONObject();
        JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
        List<String> codes = new ArrayList<String>();
        try {
            //根据token 获取用户id
            int userid = tokenService.getUserid(token,resultObj);
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
            String building_unique_code = parameter.getString("building_unique_code");
            if(!JsonParamValidator.isBuildingUniqueCode(building_unique_code)){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "楼栋编号错误");
                return resultObj.toJSONString();
            }
            Map<String,String> parameters = new HashMap<String,String>();

            String comm_address = parameter.getString("comm_address");
            if(comm_address == null) {
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "通信地址不能为空");
            }

            parameters.put("building_unique_code",building_unique_code);
            parameters.put("comm_address",comm_address);

            int opRes = deviceOperateService.readBuildingValveSet(userid,parameters,resultObj);

            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/opBuildingValve", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String opBuildingValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        JSONObject resultObj = new JSONObject();
        JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
        List<String> codes = new ArrayList<String>();
        try {
            //根据token 获取用户id
            int userid = tokenService.getUserid(token,resultObj);
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
            String building_unique_code = parameter.getString("building_unique_code");
            if(!JsonParamValidator.isBuildingUniqueCode(building_unique_code)){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "楼栋编号错误");
                return resultObj.toJSONString();
            }
            String open = parameter.getString("open");
            if(open == null || !RegexUtil.isDigits(open) || open.length() > 2){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "开度设置错误");
                return resultObj.toJSONString();
            }

            String comm_address = parameter.getString("comm_address");
            if(comm_address == null) {
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "通信地址不能为空");
            }
            Map<String,String> parameters = new HashMap<String,String>();
            parameters.put("building_unique_code",building_unique_code);
            parameters.put("comm_address",comm_address);
            parameters.put("open",open);

            //阀门操作
            int opRes = deviceOperateService.opBuildingValve(userid,parameters,resultObj);

            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    /*@RequestMapping(value = "/readBuildingValve", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String readBuildingValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
            //数据校验

            //权限验证
            //通过权限过滤得到小区code列表  codes
            List<String> codes = new ArrayList<String>();
            String scope = parameter.getString("scope");
            String scopeType = parameter.getString("scopeType");
            String scopeStr = parameter.getString("scopeStr");
            int codeNum = queryDeviceService.getCodes(scope,scopeType,scopeStr,codes);
            if(codeNum == -1){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg","scope参数错误");
                return resultObj.toJSONString();
            }

            //数据查询，成功后返回.
            Integer page = JsonParamValidator.str2Digits(parameter.getString("page"));
            Integer listRows = JsonParamValidator.str2Digits(parameter.getString("listRows"));

            //数据查询，成功后返回.
            List<BuildingValve> entitys = queryDeviceService.queryBuildingValve(codes,page,listRows,null);

            List<BuildingValveData> result = new ArrayList<BuildingValveData>();
            //调用知路接口得到 阀门状态，进水温度，回水温度 后存入HouseValveData

            List<String> commonAdresses = new ArrayList<String>();

            for (BuildingValve buildingValve:entitys) {
                commonAdresses.add(buildingValve.getComm_address());
                result.add(new BuildingValveData(buildingValve));
            }

            int totalRows = queryDeviceService.queryValveCount(codes,null);
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
    }*/
}
