package com.dazk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dazk.db.dao.DeviceLogMapper;
import com.dazk.db.model.BuildingValveLog;
import com.dazk.db.model.OpenLog;
import com.dazk.db.param.BuildingValveLogParam;
import com.dazk.db.param.OpenLogParam;
import com.dazk.service.DeviceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/29.
 */
@Service
public class DeviceLogServiceImpl implements DeviceLogService {
    @Autowired
    private DeviceLogMapper deviceLogMapper;

    @Override
    public List<OpenLog> queryOpenLog(OpenLogParam obj) {
        return deviceLogMapper.queryOpenLog(obj);
    }

    @Override
    public int queryOpenLogCount(OpenLogParam obj) {
        return deviceLogMapper.queryOpenLogCount(obj);
    }

    @Override
    public List<BuildingValveLog> queryBuildingValveLog(BuildingValveLogParam obj) {
        return deviceLogMapper.queryBuildingValveLog(obj);
    }

    @Override
    public int queryBuildingValveLogCount(BuildingValveLogParam obj) {
        return deviceLogMapper.queryBuildingValveLogCount(obj);
    }
}
