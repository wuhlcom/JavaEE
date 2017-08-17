package com.dazk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.util.JsonUtil;
import com.dazk.common.util.RegexUtil;
import com.dazk.db.dao.DeviceFaultLogMapper;
import com.dazk.db.dao.HouseValveMapper;
import com.dazk.db.model.BuildingCalorimeterData;
import com.dazk.db.model.DeviceFaultLog;
import com.dazk.db.model.HouseValve;
import com.dazk.db.model.HouseValveData;
import com.dazk.service.ErrorService;
import com.dazk.service.RedisService;
import com.dazk.service.StatePushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/8/1.
 */
@RestController
@RequestMapping("/device")
public class StatePushController {
    @Autowired
    private RedisService redisService;

    @Autowired
    private DeviceFaultLogMapper deviceFaultLogMapper;

    @Autowired
    private StatePushService statePushService;

    @Autowired
    private ErrorService errorService;


    @RequestMapping(value = "/statepush", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public void statepush(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);
            JsonUtil.filterNull(parameter);

            String time = parameter.getString("time");
            Long errTime =  0L;
            if(time == null || !RegexUtil.isDigits(time)){
                errTime = System.currentTimeMillis()/1000;
            }else{
                errTime = Long.parseLong(time);
            }
            String comm_address = parameter.getString("comm_address");
            String device_type = parameter.getString("device_type");
            String event = parameter.getString("event");

            if(comm_address != null){
                errorService.insertError(comm_address,device_type,event,errTime);
            }

            if(comm_address != null){
                switch(device_type){
                    case "01":
                        statePushService.spBuildingCalorimeter(device_type,errTime,comm_address,event);
                        break;
                    case "02":
                        statePushService.spHouseValve(device_type,errTime,comm_address,event);
                        break;
                    case "03":
                        statePushService.spBuildingValve(device_type,errTime,comm_address,event);
                        break;
                    case "04":
                        break;
                    case "05":
                        statePushService.spConcentrator(device_type,errTime,comm_address,event);
                        break;
                    case "06":
                        statePushService.spGateway(device_type,errTime,comm_address,event);
                        break;
                    default:;break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
