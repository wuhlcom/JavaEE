package com.dazk.service;

import com.dazk.db.model.BuildingValveData;
import com.dazk.db.model.HouseValve;
import com.dazk.db.model.HouseValveData;
import com.dazk.db.param.BatchStatusDataParam;
import com.dazk.db.param.BatchStatusDataResParam;
import com.dazk.db.param.BuildingValveStatus;

import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */
public interface BatchStatusDataService {
    List<BatchStatusDataResParam> getBatchStatusData(List<BatchStatusDataParam> param,Integer type);
}
