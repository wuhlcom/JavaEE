package com.dazk.service;

import com.dazk.db.model.ErrorForViewParam;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
public interface ErrorService {
    void insertError(String device_code,String device_type,String event,Long occur_time);

    List<ErrorForViewParam> getDeviceFaultLog(List<String> codes,String device_type,String device_code,String error_type,Long start_time,Long end_time,Integer page,Integer listRows);
    Integer getDeviceFaultLogCount(List<String> codes,String device_type,String device_code,String error_type,Long start_time,Long end_time,Integer page,Integer listRows);
}
