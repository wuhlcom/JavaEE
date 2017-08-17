package com.dazk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.SystemConfig;
import com.dazk.common.util.HttpRequest;
import com.dazk.db.model.HouseValveData;
import com.dazk.db.model.StealHeatData;
import com.dazk.db.param.*;
import com.dazk.service.RedisService;
import com.dazk.service.StealHeatService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/13.
 */
@Service
public class StealHeatServiceImpl implements StealHeatService {

    @Autowired
    private RedisService redisService;

    @Override
    public List<StealHeatData> getAll(Double wit_min, Double wit_max, Double wot_min, Double wot_max, String condition1, String condition2) {
        List<StealHeatData> result = new ArrayList<StealHeatData>();
        JSONObject paramJson = new JSONObject();
        paramJson.put("wit_min",wit_min);
        paramJson.put("wit_max",wit_max);
        paramJson.put("wot_min",wot_min);
        paramJson.put("wot_max",wot_max);
        paramJson.put("wit_min",wit_min);
        paramJson.put("wit_min",wit_min);
        paramJson.put("condition1",condition1);
        paramJson.put("condition2",condition2);
        String resStr = HttpRequest.sendJsonPost(SystemConfig.StealHeatListUrl, paramJson.toJSONString());
        Gson gs = new Gson();
        StealHeatListParam stealHeatListParam = gs.fromJson(resStr, StealHeatListParam.class);//把JSON字符串转为对象
        if(stealHeatListParam.getErrcode().equals("0")){
            List<StealHeatParam> stealHeatParamList = stealHeatListParam.getResult();
            for(StealHeatParam obj : stealHeatParamList){
                String json = (String)redisService.hmGet("HouseValve",obj.getComm_address());
                if(json == null) continue;
                HouseValveData houseValveData = JSON.parseObject(json,HouseValveData.class);
                StealHeatData stealHeatData = new StealHeatData();
                stealHeatData.setHouse_code(houseValveData.getHouse_code());
                stealHeatData.setCollect_time(obj.getCollect_time());
                stealHeatData.setReturn_temp(obj.getWit());
                stealHeatData.setSupply_temp(obj.getWit());
                stealHeatData.setValve_state(obj.getOpening());
                result.add(stealHeatData);
            }
        }
        return result;
    }

    @Override
    public List<StealHeatCodeParam> getAllHousecodeList(Double wit_min, Double wit_max, Double wot_min, Double wot_max, String condition1, String condition2) {
        List<StealHeatCodeParam> result =  new ArrayList<StealHeatCodeParam>();
        JSONObject paramJson = new JSONObject();
        paramJson.put("wit_min",wit_min);
        paramJson.put("wit_max",wit_max);
        paramJson.put("wot_min",wot_min);
        paramJson.put("wot_max",wot_max);
        paramJson.put("wit_min",wit_min);
        paramJson.put("wit_min",wit_min);
        paramJson.put("condition1",condition1);
        paramJson.put("condition2",condition2);
        String resStr = HttpRequest.sendJsonPost(SystemConfig.StealHeatCodeListUrl, paramJson.toJSONString());
        Gson gs = new Gson();
        StealHeatCodeListParam stealHeatCodeListParam = gs.fromJson(resStr, StealHeatCodeListParam.class);//把JSON字符串转为对象
        if (stealHeatCodeListParam.getErrcode().equals("0")){
            result = stealHeatCodeListParam.getResult();
        }
        return result;
    }
}
