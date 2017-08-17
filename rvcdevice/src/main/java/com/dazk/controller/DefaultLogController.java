package com.dazk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.ErrCode;
import com.dazk.common.SystemConfig;
import com.dazk.common.util.JsonUtil;
import com.dazk.common.util.RegexUtil;
import com.dazk.common.util.StrUtil;
import com.dazk.db.model.*;
import com.dazk.service.DataPermService;
import com.dazk.service.ErrorService;
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
public class DefaultLogController {
    @Resource
    private QueryBuildService queryBuildService;

    @Resource
    private QueryDeviceService queryDeviceService;

    @Resource
    private DataPermService dataPermService;

    @Resource
    private ErrorService errorService;

    @RequestMapping(value = "/faultLog", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String faultLog(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        JSONObject resultObj = new JSONObject();
        JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
        List<String> codes = new ArrayList<String>();
        try {
            //根据token 获取用户id
            int userid = dataPermService.getUserid(token,resultObj);
            if(userid == -1){
                return resultObj.toJSONString();
            }
            //获取用户所有权限
            codes = dataPermService.permFilter(userid,null,null);
            if(codes.size() == 0){
                resultObj.put("errcode", ErrCode.noPermission);
                resultObj.put("msg","用户无此修改权限");
                return resultObj.toJSONString();
            }
            if(codes.size() == 0){
                resultObj.put("errcode", ErrCode.noPermission);
                resultObj.put("msg","用户无权限");
                return resultObj.toJSONString();
            }
            //数据校验
            String build_code = parameter.getString("build_code");
            if(build_code != null && (!RegexUtil.isDigits(build_code) || build_code.length() > 32 || build_code.length() < 3)){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg","建筑编号错误");
                return resultObj.toJSONString();
            }
            String device_type = parameter.getString("device_type");
            if(!device_type.equals("01") && !device_type.equals("02") && !device_type.equals("03") && !device_type.equals("04") && !device_type.equals("05") && !device_type.equals("06") && !device_type.equals("07")){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg","设备类型错误");
                return resultObj.toJSONString();
            }
            String device_code = parameter.getString("device_code");
            if(device_code.length() > 32){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg","通信地址错误");
                return resultObj.toJSONString();
            }
            String error_type = parameter.getString("error_type");
            if(error_type != null && error_type.length() > 32){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg","故障类型错误");
                return resultObj.toJSONString();
            }
            String start_time = parameter.getString("start_time");
            String end_time = parameter.getString("end_time");
            if(!(RegexUtil.isDigits(start_time) && RegexUtil.isDigits(end_time))){
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg","时间错误");
                return resultObj.toJSONString();
            }
            Integer page = JsonParamValidator.str2Digits(parameter.getString("page"));
            Integer listRows = JsonParamValidator.str2Digits(parameter.getString("listRows"));
            //验证build_code权限,codes 为build_code限定的值
            if(build_code != null){
                codes = StrUtil.filterWithPer(codes,build_code);
            }
            if(codes.size() == 0){
                resultObj.put("errcode", ErrCode.noPermission);
                resultObj.put("msg","用户无权限");
                return resultObj.toJSONString();
            }

            List<ErrorForViewParam> result = errorService.getDeviceFaultLog(codes,device_type,device_code,error_type,Long.parseLong(start_time),Long.parseLong(end_time),page,listRows);
            int totalRows = errorService.getDeviceFaultLogCount(codes,device_type,device_code,error_type,Long.parseLong(start_time),Long.parseLong(end_time),page,listRows);

            resultObj.put("errcode", ErrCode.success);
            resultObj.put("totalRows",totalRows);
            resultObj.put("result",result);
            return resultObj.toJSONString();
            //这有个bug只有拥有这热力公司权限的人才能看到基站故障信息
            //String device_unicode = queryDeviceService.getDeviceUnicode(device_type,device_code);
        }catch (Exception e){
            e.printStackTrace();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }
}
