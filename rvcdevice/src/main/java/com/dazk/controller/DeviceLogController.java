package com.dazk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.ErrCode;
import com.dazk.common.SystemConfig;
import com.dazk.common.util.HttpRequest;
import com.dazk.common.util.JsonUtil;
import com.dazk.common.util.RegexUtil;
import com.dazk.db.model.BuildingValveLog;
import com.dazk.db.model.OpenLog;
import com.dazk.db.param.BuildingValveLogParam;
import com.dazk.db.param.OpenLogParam;
import com.dazk.service.DataPermService;
import com.dazk.service.DeviceLogService;
import com.dazk.service.QueryDeviceService;
import com.dazk.validator.JsonParamValidator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/29.
 */
@RestController
@RequestMapping("/device")
public class DeviceLogController {
    @Resource
    private QueryDeviceService queryDeviceService;

    @Resource
    private DeviceLogService deviceLogService;

    @Resource
    private DataPermService dataPermService;

    @RequestMapping(value = "/openLog", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String openLog(HttpServletRequest request, HttpServletResponse response,  @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);
            JsonUtil.filterNull(parameter);
            List<String> codes = new ArrayList<String>();
            //数据校验
            //用户校验及权限验证
            int isLiberty  = dataPermService.userPerm(codes,token,parameter,resultObj);

            if(isLiberty < 0){
                return resultObj.toJSONString();
            }

            Long start_time = JsonParamValidator.str2long(parameter.getString("start_time"));
            Long end_time = JsonParamValidator.str2long(parameter.getString("end_time"));
            Integer page = JsonParamValidator.str2Digits(parameter.getString("page"));
            Integer listRows = JsonParamValidator.str2Digits(parameter.getString("listRows"));
            Integer operate = JsonParamValidator.str2Digits(parameter.getString("operate"));
            String username = parameter.getString("username");
            if(username != null && username.length() > 32){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg","住户姓名最长32字符");
            }
            String name = parameter.getString("name");
            if(name != null && name.length() > 32){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg","操作员姓名最长32字符");
            }

            OpenLogParam paramBean = new OpenLogParam(codes,start_time,end_time,username,name,operate,page,listRows);

            List<OpenLog> result = deviceLogService.queryOpenLog(paramBean);
            Integer totalRows = deviceLogService.queryOpenLogCount(paramBean);

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

    @RequestMapping(value = "/buildingValveLog", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String buildingValveLog(HttpServletRequest request, HttpServletResponse response,  @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
            List<String> codes = new ArrayList<String>();
            //数据校验
            //用户校验及权限验证
            int isLiberty  = dataPermService.userPerm(codes,token,parameter,resultObj);

            if(isLiberty < 0){
                return resultObj.toJSONString();
            }

            Long start_time = JsonParamValidator.str2long(parameter.getString("start_time"));
            Long end_time = JsonParamValidator.str2long(parameter.getString("end_time"));
            Integer page = JsonParamValidator.str2Digits(parameter.getString("page"));
            Integer listRows = JsonParamValidator.str2Digits(parameter.getString("listRows"));

            BuildingValveLogParam paramBean = new BuildingValveLogParam(codes,start_time,end_time,page,listRows);

            List<BuildingValveLog> result = deviceLogService.queryBuildingValveLog(paramBean);
            Integer totalRows = deviceLogService.queryBuildingValveLogCount(paramBean);

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
