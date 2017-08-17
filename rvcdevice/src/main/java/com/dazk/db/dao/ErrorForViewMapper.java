package com.dazk.db.dao;

import com.dazk.db.model.ErrorForViewParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */
public interface ErrorForViewMapper {
    List<ErrorForViewParam> getDeviceFault(@Param("codes") List<String> codes,@Param("device_type") String device_type,@Param("device_code") String device_code,@Param("error_type") String error_type,@Param("start_time") Long start_time,@Param("end_time") Long end_time,@Param("start") Integer start,@Param("listRows") Integer listRows);
    Integer getDeviceFaultCount(@Param("codes") List<String> codes,@Param("device_type") String device_type,@Param("device_code") String device_code,@Param("error_type") String error_type,@Param("start_time") Long start_time,@Param("end_time") Long end_time,@Param("start") Integer start,@Param("listRows") Integer listRows);
    /*List<ErrorForViewParam> getBuildingCalorimeterFault(@Param("codes") List<String> codes,@Param("device_type") String device_type,@Param("device_code") String device_code,@Param("start_time") Long start_time,@Param("end_time") Long end_time,@Param("page") Integer page,@Param("listRows") Integer listRows);
    List<ErrorForViewParam> getHouseValveFault(@Param("codes") List<String> codes,@Param("device_type") String device_type,@Param("device_code") String device_code,@Param("start_time") Long start_time,@Param("end_time") Long end_time,@Param("page") Integer page,@Param("listRows") Integer listRows);
    List<ErrorForViewParam> getBuildingValveFault(@Param("codes") List<String> codes,@Param("device_type") String device_type,@Param("device_code") String device_code,@Param("start_time") Long start_time,@Param("end_time") Long end_time,@Param("page") Integer page,@Param("listRows") Integer listRows);
    List<ErrorForViewParam> getConcentratoFault(@Param("codes") List<String> codes,@Param("device_type") String device_type,@Param("device_code") String device_code,@Param("start_time") Long start_time,@Param("end_time") Long end_time,@Param("page") Integer page,@Param("listRows") Integer listRows);
    List<ErrorForViewParam> getGateWayFault(@Param("codes") List<String> codes,@Param("device_type") String device_type,@Param("device_code") String device_code,@Param("start_time") Long start_time,@Param("end_time") Long end_time,@Param("page") Integer page,@Param("listRows") Integer listRows);
    List<ErrorForViewParam> getHouseCalorimeterFault(@Param("codes") List<String> codes,@Param("device_type") String device_type,@Param("device_code") String device_code,@Param("start_time") Long start_time,@Param("end_time") Long end_time,@Param("page") Integer page,@Param("listRows") Integer listRows);
*/
}
