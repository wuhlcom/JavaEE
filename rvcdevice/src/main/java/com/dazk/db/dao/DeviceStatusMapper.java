package com.dazk.db.dao;

import com.dazk.db.model.HouseValveData;

import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */
public interface DeviceStatusMapper
{
    List<HouseValveData> findLatestHouseValveStatus(List<String> codes);

    int findLatestHouseValveStatusCount(List<String> codes);
}