package com.dazk.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2017/7/21.
 */
public interface UpdateDeviceService {
    public int updateValve(JSONObject obj);

    public int updateHouseCalorimeter(JSONObject obj);

    public int updateConcentrator(JSONObject obj);

    public int updateGateway(JSONObject obj);

    public int updateBuildingValve(JSONObject obj);

    public int updateBuildingCalorimeter(JSONObject obj);

    public int setValveState(String house_code,Integer open,Integer opening,Integer islock);
}
