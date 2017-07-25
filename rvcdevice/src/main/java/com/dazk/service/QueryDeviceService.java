package com.dazk.service;

import com.alibaba.fastjson.JSONObject;
import com.dazk.db.model.*;

import java.util.List;

/**
 * Created by Administrator on 2017/7/22.
 */
public interface QueryDeviceService {
    public List<HouseValve> queryValve(JSONObject obj);

    public int queryValveCount(JSONObject obj);

    public List<HouseCalorimeter> queryHouseCalorimeter(JSONObject obj);

    public int queryHouseCalorimeterCount(JSONObject obj);

    public List<Concentrator> queryConcentrator(JSONObject obj);

    public int queryConcentratorCount(JSONObject obj);

    public List<Gateway> queryGateway(JSONObject obj);

    public int queryGatewayCount(JSONObject obj);

    public List<BuildingValve> queryBuildingValve(JSONObject obj);

    public int queryBuildingValveCount(JSONObject obj);

    public List<BuildingCalorimeter> queryBuildingCalorimeter(JSONObject obj);

    public int queryBuildingCalorimeterCount(JSONObject obj);
}
