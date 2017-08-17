package com.dazk.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/7.
 */
public interface DeviceOperateService {
    public int opValve(Integer userid,String house_code,Integer open,Integer islock,JSONObject resultObj);

    public int setBuildingValve(Integer userid, Map<String,String> paramters,JSONObject resultObj);

    public int opBuildingValve(Integer userid, Map<String,String> paramters,JSONObject resultObj);

    public int readBuildingValveSet(Integer userid, Map<String,String> paramters,JSONObject resultObj);
}
