package com.dazk.service;

import com.alibaba.fastjson.JSONObject;
import com.dazk.db.model.HouseValveData;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */
public interface QueryDeviceStatusService {
    public List<HouseValveData> queryValveStatus(JSONObject obj);

    public int queryValveStatusCount(JSONObject obj);
}
