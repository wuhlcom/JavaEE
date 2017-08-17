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
import com.dazk.db.model.Gateway;
import com.dazk.service.DataPermService;
import com.dazk.service.DelDeviceService;
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
 * Created by Administrator on 2017/7/21.
 */
@RestController
@RequestMapping("/device")
public class DelDeviceController {
    @Resource
    private DelDeviceService delDeviceService;

    @Resource
    private DataPermService dataPermService;

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private QueryDeviceService queryDeviceService;

    @Resource
    private QueryBuildService queryBuildService;

    @RequestMapping(value = "/delValve", method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String delValve(HttpServletRequest request, HttpServletResponse response,  @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
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

            if(!JsonParamValidator.isHouseCode(parameter.getString("house_code"))){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "非法住户编号");
                return resultObj.toJSONString();
            }

            //数据入库，成功后返回.
            int res = delDeviceService.delValve(parameter);
            if(res == 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }else if(res == -1){
                resultObj.put("errcode", ErrCode.routineErr);
                resultObj.put("msg", "插入时程序出错");
                return resultObj.toJSONString();
            }else if(res == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "删除数据不存在");
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

    @RequestMapping(value = "/delHouseCalorimeter", method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String delHouseCalorimeter(HttpServletRequest request, HttpServletResponse response,  @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
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
            if(!JsonParamValidator.isHouseCode(parameter.getString("house_code"))){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "非法住户编号");
                return resultObj.toJSONString();
            }

            //数据入库，成功后返回.
            int res = delDeviceService.delHouseCalorimeter(parameter);
            if(res == 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }else if(res == -1){
                resultObj.put("errcode", ErrCode.routineErr);
                resultObj.put("msg", "插入时程序出错");
                return resultObj.toJSONString();
            }else if(res == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "删除数据不存在");
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

    @RequestMapping(value = "/delConcentrator", method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String delConcentrator(HttpServletRequest request, HttpServletResponse response,  @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
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
            if(!JsonParamValidator.isBuildingUniqueCode(parameter.getString("building_unique_code"))){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "非法楼栋唯一编号");
                return resultObj.toJSONString();
            }

            //数据入库，成功后返回.
            int res = delDeviceService.delConcentrator(parameter);
            if(res == 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }else if(res == -1){
                resultObj.put("errcode", ErrCode.routineErr);
                resultObj.put("msg", "插入时程序出错");
                return resultObj.toJSONString();
            }else if(res == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "删除数据不存在");
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

    @RequestMapping(value = "/delGateway", method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String delGateway(HttpServletRequest request, HttpServletResponse response,  @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
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
            if(parameter.getString("id") != null && RegexUtil.isDigits(parameter.getString("id"))){
                Gateway record1 = new Gateway();
                record1.setId(Integer.parseInt(parameter.getString("id")));
                Gateway record2 = queryDeviceService.getGateway(record1);
                if (record2 != null){
                    List<String> companyCodes = new ArrayList<String>();
                    companyCodes.add(record2.getCompany_code());
                    List<Company> companys = queryBuildService.getCompanyByCodes(companyCodes);
                    for(Company obj : companys){
                        codes.add(obj.getCode());
                    }
                }else{
                    resultObj.put("errcode", ErrCode.parameErr);
                    resultObj.put("msg","基站不存在");
                    return resultObj.toJSONString();
                }
            }else{
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg","id错误");
                return resultObj.toJSONString();
            }
            //权限过滤后的codes
            codes = dataPermService.permFilter(userid,codes,SystemConfig.dataTypeUpdate);
            if(codes.size() == 0){
                resultObj.put("errcode", ErrCode.noPermission);
                resultObj.put("msg","用户无此修改权限");
                return resultObj.toJSONString();
            }
            if(!RegexUtil.isDigits(parameter.getString("id"))){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "非法id");
                return resultObj.toJSONString();
            }

            //数据入库，成功后返回.
            int res = delDeviceService.delGateway(parameter);
            if(res == 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }else if(res == -1){
                resultObj.put("errcode", ErrCode.routineErr);
                resultObj.put("msg", "插入时程序出错");
                return resultObj.toJSONString();
            }else if(res == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "删除数据不存在");
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

    @RequestMapping(value = "/delBuildingValve", method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String delBuildingValve(HttpServletRequest request, HttpServletResponse response,  @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
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
            if(!JsonParamValidator.isBuildingUniqueCode(parameter.getString("building_unique_code"))){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "非法楼栋唯一编号");
                return resultObj.toJSONString();
            }
            if(!JsonParamValidator.isBuildingUniqueCode(parameter.getString("building_unique_code"))){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "非法楼栋唯一编号");
                return resultObj.toJSONString();
            }

            //数据入库，成功后返回.
            int res = delDeviceService.delBuildingValve(parameter);
            if(res == 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }else if(res == -1){
                resultObj.put("errcode", ErrCode.routineErr);
                resultObj.put("msg", "插入时程序出错");
                return resultObj.toJSONString();
            }else if(res == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "删除数据不存在");
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

    @RequestMapping(value = "/delBuildingCalorimeter", method=RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String delBuildingCalorimeter(HttpServletRequest request, HttpServletResponse response,  @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
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
            if(!JsonParamValidator.isBuildingUniqueCode(parameter.getString("building_unique_code"))){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "非法楼栋唯一编号");
                return resultObj.toJSONString();
            }
            if(!JsonParamValidator.isBuildingUniqueCode(parameter.getString("building_unique_code"))){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "非法楼栋唯一编号");
                return resultObj.toJSONString();
            }

            //数据入库，成功后返回.
            int res = delDeviceService.delBuildingCalorimeter(parameter);
            if(res == 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }else if(res == -1){
                resultObj.put("errcode", ErrCode.routineErr);
                resultObj.put("msg", "插入时程序出错");
                return resultObj.toJSONString();
            }else if(res == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "删除数据不存在");
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
