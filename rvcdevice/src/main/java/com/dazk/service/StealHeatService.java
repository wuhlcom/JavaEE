package com.dazk.service;

import com.alibaba.fastjson.JSONObject;
import com.dazk.db.model.StealHeatData;
import com.dazk.db.param.StealHeatCodeParam;

import java.util.List;

/**
 * Created by Administrator on 2017/8/13.
 */
public interface StealHeatService {
    List<StealHeatData> getAll(Double wit_min,Double wit_max,Double wot_min,Double wot_max,String condition1,String condition2);

    List<StealHeatCodeParam> getAllHousecodeList(Double wit_min, Double wit_max, Double wot_min, Double wot_max, String condition1, String condition2);


}
