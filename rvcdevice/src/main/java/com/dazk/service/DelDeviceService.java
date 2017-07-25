package com.dazk.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2017/7/21.
 */
public interface DelDeviceService {
    public int delValve(JSONObject obj);

    public int delHouseCalorimeter(JSONObject obj);

    public int delConcentrator(JSONObject obj);

    public int delGateway(JSONObject obj);

    public int delBuildingValve(JSONObject obj);

    public int delBuildingCalorimeter(JSONObject obj);
}
