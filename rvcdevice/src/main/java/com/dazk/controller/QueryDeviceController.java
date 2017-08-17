package com.dazk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.ErrCode;
import com.dazk.common.SystemConfig;
import com.dazk.common.util.HttpRequest;
import com.dazk.common.util.JsonUtil;
import com.dazk.common.util.RegexUtil;
import com.dazk.db.model.*;
import com.dazk.db.param.HouseHolderValveParam;
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
 * Created by Administrator on 2017/7/22.
 */
@RestController
@RequestMapping("/device")
public class QueryDeviceController {
    @Resource
    private QueryBuildService queryBuildService;

    @Resource
    private QueryDeviceService queryDeviceService;

    @Resource
    private DataPermService dataPermService;

    @RequestMapping(value = "/queryHouseMeter", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String queryHouseMeter(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);
            JsonUtil.filterNull(parameter);
            List<String> codes = new ArrayList<String>();
            //数据校验
            String name = parameter.getString("name");
            if (name != null && name.length() > 32){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg","住户名称不合法");
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
            List<HouseHolder> entitys = queryBuildService.queryHouseHolder(codes,name,page,listRows);
            List<HouseMeter> result = new ArrayList<HouseMeter>();
            //调用知路接口得到 阀门状态，进水温度，回水温度 后存入HouseValveData

            for (HouseHolder houseHolder:entitys) {
                try{
                    HouseMeter houseMeter = new HouseMeter(houseHolder);
                    result.add(houseMeter);
                    HouseValve record1 = new HouseValve();
                    record1.setHouse_code(houseHolder.getCode());
                    HouseValve houseValve = queryDeviceService.getHouseValve(record1);
                    if(houseValve != null){
                        if(houseValve.getType() == 0){
                            houseMeter.setValve_comm_addr(houseValve.getComm_address());
                        }
                    }

                    HouseCalorimeter record2 = new HouseCalorimeter();
                    record1.setHouse_code(houseHolder.getCode());
                    HouseCalorimeter houseCalorimeter = queryDeviceService.getHouseCalorimeter(record2);
                    if(houseCalorimeter != null){
                        if(houseCalorimeter.getType() == 0){
                            houseMeter.setCal_comm_addr(houseCalorimeter.getComm_address());
                        }
                    }
                }catch (Exception e){
                    //住户存在多个热表或阀门
                    //此部分查询效率低以后要改进
                }
            }

            int totalRows = queryBuildService.queryHouseHolderCount(codes);
            for(int i = 0;i < result.size();i++){
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

    @RequestMapping(value = "/queryValve", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String queryValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
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
            condition = JSON.parseObject(parameter.toJSONString(), HouseValve.class);
            int totalRows = queryDeviceService.queryValveCount(codes,condition);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
                result.get(i).setOnline(null);
                result.get(i).setErr_code(null);
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

    @RequestMapping(value = "/queryHouseCalorimeter", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String queryHouseCalorimeter(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
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
            condition = JSON.parseObject(parameter.toJSONString(), HouseCalorimeter.class);
            int totalRows = queryDeviceService.queryHouseCalorimeterCount(codes,condition);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
                result.get(i).setOnline(null);
                result.get(i).setErr_code(null);
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

    @RequestMapping(value = "/queryConcentrator", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String queryConcentrator(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
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
            condition = JSON.parseObject(parameter.toJSONString(), Concentrator.class);
            int totalRows = queryDeviceService.queryConcentratorCount(codes,condition);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
                result.get(i).setOnline(null);
                result.get(i).setErr_code(null);
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

    @RequestMapping(value = "/queryGateway", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String queryGateway(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
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
                result.get(i).setOnline(null);
                result.get(i).setErr_code(null);
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

    @RequestMapping(value = "/queryBuildingValve", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String queryBuildingValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
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

            //调用知路接口得到 阀门状态，进水温度，回水温度 后存入HouseValveData
            condition = JSON.parseObject(parameter.toJSONString(), BuildingValve.class);
            int totalRows = queryDeviceService.queryBuildingValveCount(codes,condition);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
                result.get(i).setOnline(null);
                result.get(i).setErr_code(null);
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

    @RequestMapping(value = "/queryBuildingCalorimeter", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String queryBuildingCalorimeter(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
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
            condition = JSON.parseObject(parameter.toJSONString(), BuildingCalorimeter.class);
            int totalRows = queryDeviceService.queryBuildingCalorimeterCount(codes,condition);

            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
                result.get(i).setOnline(null);
                result.get(i).setErr_code(null);
            }
            resultObj.put("errcode", ErrCode.success);
            resultObj.put("totalRows",totalRows);
            resultObj.put("result",result);
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.getMessage());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/queryHouseValve", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String queryHouseValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
            List<String> codes = new ArrayList<String>();
            //数据校验
            if(!JsonParamValidator.houseHolderValveQueryVal(parameter,resultObj)){
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
            Integer error = null;
            String errorstr = parameter.getString("error");
            if(errorstr != null){
                error = Integer.parseInt(errorstr);
            }

            Integer opening = null;
            String openingstr = parameter.getString("opening");
            if(openingstr != null){
                opening = Integer.parseInt(openingstr);
            }

            Integer mes_status = null;
            String mes_statusstr = parameter.getString("mes_status");
            if(mes_statusstr != null){
                mes_status = Integer.parseInt(mes_statusstr);
            }

            String id_card = parameter.getString("id_card");
            String err_code = parameter.getString("err_code");

            List<HouseHolderValveParam> result = queryDeviceService.queryHouseHolderValve(codes,  error,  opening,  mes_status,  id_card,err_code,  page,  listRows);

            Integer totalRows = queryDeviceService.queryHouseHolderValveCount(codes,  error,  opening,  mes_status,  id_card, err_code, page,  listRows);

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
