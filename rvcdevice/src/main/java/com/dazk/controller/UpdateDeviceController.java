package com.dazk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.ErrCode;
import com.dazk.common.SystemConfig;
import com.dazk.common.util.HttpRequest;
import com.dazk.common.util.JsonUtil;
import com.dazk.common.util.RegexUtil;
import com.dazk.db.dao.CompanyMapper;
import com.dazk.db.model.Company;
import com.dazk.service.DataPermService;
import com.dazk.service.QueryBuildService;
import com.dazk.service.UpdateDeviceService;
import com.dazk.validator.JsonParamValidator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */
@RestController
@RequestMapping("/device")
public class UpdateDeviceController {
    @Resource
    private UpdateDeviceService updateDeviceService;

    @Resource
    private DataPermService dataPermService;

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private QueryBuildService queryBuildService;

    @RequestMapping(value = "/updateValve", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String updateValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        JSONObject resultObj = new JSONObject();
        JSONObject parameter = JSON.parseObject(requestBody);
        JsonUtil.filterNull(parameter);
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

            if(!JsonParamValidator.valveEditVal(parameter,resultObj)){
                //非法数据，返回错误码
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "参数错误");
                return resultObj.toJSONString();
            }

            //数据入库，成功后返回.
            int res = updateDeviceService.updateValve(parameter);
            if(res == 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }else if(res == -1){
                resultObj.put("errcode", ErrCode.routineErr);
                resultObj.put("msg", "更新时程序出错");
                return resultObj.toJSONString();
            }else if(res == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "更新数据不存在");
                return resultObj.toJSONString();
            }else if(res > 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }
            resultObj.put("errcode", ErrCode.unknowErr);
            resultObj.put("msg", "未知错误");
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/updateHouseCalorimeter", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String updateHouseCalorimeter(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
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
            if(!JsonParamValidator.houseCalorimeterEditVal(parameter,resultObj)){
                //非法数据，返回错误码
                resultObj.put("errcode", ErrCode.parameErr);
                return resultObj.toJSONString();
            }

            //数据入库，成功后返回.
            int res = updateDeviceService.updateHouseCalorimeter(parameter);
            if(res == 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }else if(res == -1){
                resultObj.put("errcode", ErrCode.routineErr);
                resultObj.put("msg", "更新时程序出错");
                return resultObj.toJSONString();
            }else if(res == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "更新数据不存在");
                return resultObj.toJSONString();
            }else if(res > 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }
            resultObj.put("errcode", ErrCode.unknowErr);
            resultObj.put("msg", "未知错误");
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/updateConcentrator", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String updateConcentrator(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
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
            if(!JsonParamValidator.concentratorEditVal(parameter,resultObj)){
                //非法数据，返回错误码
                resultObj.put("errcode", ErrCode.parameErr);
                return resultObj.toJSONString();
            }


            //数据入库，成功后返回.
            int res = updateDeviceService.updateConcentrator(parameter);
            if(res == 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }else if(res == -1){
                resultObj.put("errcode", ErrCode.routineErr);
                resultObj.put("msg", "更新时程序出错");
                return resultObj.toJSONString();
            }else if(res == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "更新数据不存在");
                return resultObj.toJSONString();
            }else if(res > 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }
            resultObj.put("errcode", ErrCode.unknowErr);
            resultObj.put("msg", "未知错误");
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/updateGateway", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String updateGateway(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
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
            if(parameter.getString("company_code") != null && JsonParamValidator.isCompanyCode(parameter.getString("company_code"))){
                List<Company> companys = queryBuildService.getCompanyByCode(parameter.getString("company_code"));
                for(Company obj : companys){
                    codes.add(obj.getCode());
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
            if(!JsonParamValidator.gatewayEditVal(parameter,resultObj)){
                //非法数据，返回错误码
                resultObj.put("errcode", ErrCode.parameErr);
                return resultObj.toJSONString();
            }

            //数据入库，成功后返回.
            int res = updateDeviceService.updateGateway(parameter);
            if(res == 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }else if(res == -1){
                resultObj.put("errcode", ErrCode.routineErr);
                resultObj.put("msg", "更新时程序出错");
                return resultObj.toJSONString();
            }else if(res == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "更新数据不存在");
                return resultObj.toJSONString();
            }else if(res > 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }
            resultObj.put("errcode", ErrCode.unknowErr);
            resultObj.put("msg", "未知错误");
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/updateBuildingValve", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String updateBuildingValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
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
            if(!JsonParamValidator.buildingValveEditVal(parameter,resultObj)){
                //非法数据，返回错误码
                resultObj.put("errcode", ErrCode.parameErr);
                return resultObj.toJSONString();
            }

            //数据入库，成功后返回.
            int res = updateDeviceService.updateBuildingValve(parameter);
            if(res == 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }else if(res == -1){
                resultObj.put("errcode", ErrCode.routineErr);
                resultObj.put("msg", "更新时程序出错");
                return resultObj.toJSONString();
            }else if(res == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "更新数据不存在");
                return resultObj.toJSONString();
            }else if(res > 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }
            resultObj.put("errcode", ErrCode.unknowErr);
            resultObj.put("msg", "未知错误");
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/updateBuildingCalorimeter", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String updateBuildingCalorimeter(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
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
            if(!JsonParamValidator.buildingCalorimeterEditVal(parameter,resultObj)){
                //非法数据，返回错误码
                resultObj.put("errcode", ErrCode.parameErr);
                return resultObj.toJSONString();
            }

            //数据入库，成功后返回.
            int res = updateDeviceService.updateBuildingCalorimeter(parameter);
            if(res == 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }else if(res == -1){
                resultObj.put("errcode", ErrCode.routineErr);
                resultObj.put("msg", "更新时程序出错");
                return resultObj.toJSONString();
            }else if(res == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "更新数据不存在");
                return resultObj.toJSONString();
            }else if(res > 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }
            resultObj.put("errcode", ErrCode.unknowErr);
            resultObj.put("msg", "未知错误");
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }
}
