package com.dazk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.ErrCode;
import com.dazk.common.SystemConfig;
import com.dazk.common.util.HttpRequest;
import com.dazk.common.util.RegexUtil;
import com.dazk.db.model.HouseValveData;
import com.dazk.db.param.BatchStatusDataParam;
import com.dazk.db.param.BatchStatusDataResParam;
import com.dazk.db.param.BatchStatusListParam;
import com.dazk.db.param.BuildingValveStatus;
import com.dazk.service.BatchStatusDataService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */
@Service
public class BatchStatusDataServiceImpl implements BatchStatusDataService {

    @Override
    public List<BatchStatusDataResParam> getBatchStatusData(List<BatchStatusDataParam> param, Integer type) {
        Gson gs = new Gson();
        JSONObject paramObj = new JSONObject();
        String url = "url";
        switch (type){
            //住户
            case 0:url = SystemConfig.HouseBatchUrl;break;
            //热表
            case 1:url = SystemConfig.BuildCalorimeterBatchUrl;break;
            //调节阀
            case 2:url = SystemConfig.BuildValveBatchUrl;break;
        }


        paramObj.put("data",param);
        String restr = HttpRequest.sendJsonPost(url, paramObj.toJSONString());

        BatchStatusListParam batchStatusListParam = gs.fromJson(restr, BatchStatusListParam.class);//把JSON字符串转为对象
        return batchStatusListParam.getData();
    }
}
