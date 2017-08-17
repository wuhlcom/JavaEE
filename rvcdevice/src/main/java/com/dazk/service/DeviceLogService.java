package com.dazk.service;

import com.alibaba.fastjson.JSONObject;
import com.dazk.db.model.BuildingValveLog;
import com.dazk.db.model.OpenLog;
import com.dazk.db.param.BuildingValveLogParam;
import com.dazk.db.param.OpenLogParam;

import java.util.List;

/**
 * Created by Administrator on 2017/7/29.
 */
public interface DeviceLogService {
    public List<OpenLog> queryOpenLog(OpenLogParam obj);

    public int queryOpenLogCount(OpenLogParam obj);

    public List<BuildingValveLog> queryBuildingValveLog(BuildingValveLogParam obj);

    public int queryBuildingValveLogCount(BuildingValveLogParam obj);
}
