package com.dazk.service;

/**
 * Created by Administrator on 2017/8/2.
 */
public interface StatePushService {
    void spHouseValve(String device_type,Long errTime,String comm_address,String event);

    void spHouseCalorimeter(String device_type,Long errTime,String comm_address,String event);

    void spConcentrator(String device_type,Long errTime,String comm_address,String event);

    void spBuildingValve(String device_type,Long errTime,String comm_address,String event);

    void spBuildingCalorimeter(String device_type,Long errTime,String comm_address,String event);

    void spGateway(String device_type,Long errTime,String comm_address,String event);

}
