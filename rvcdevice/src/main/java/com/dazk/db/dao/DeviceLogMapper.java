package com.dazk.db.dao;

import com.dazk.db.model.BuildingValveLog;
import com.dazk.db.model.OpenLog;
import com.dazk.db.param.BuildingValveLogParam;
import com.dazk.db.param.OpenLogParam;

import java.util.List;

/**
 * Created by Administrator on 2017/7/29.
 */
public interface DeviceLogMapper {
    List<OpenLog> queryOpenLog(OpenLogParam obj);

    int queryOpenLogCount(OpenLogParam obj);

    List<BuildingValveLog> queryBuildingValveLog(BuildingValveLogParam obj);

    int queryBuildingValveLogCount(BuildingValveLogParam obj);
}
