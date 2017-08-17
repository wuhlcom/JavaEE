package com.dazk.service;

import com.alibaba.fastjson.JSONObject;
import com.dazk.db.model.*;
import com.dazk.db.param.HouseHolderValveParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/7/22.
 */
public interface QueryDeviceService {
    public List<HouseValve> queryValve(List<String> codes,Integer page,Integer listRows,HouseValve condition);

    public int queryValveCount(List<String> codes,HouseValve condition);

    public List<HouseCalorimeter> queryHouseCalorimeter(List<String> codes,Integer page,Integer listRows,HouseCalorimeter condition);

    public int queryHouseCalorimeterCount(List<String> codes,HouseCalorimeter condition);

    public List<Concentrator> queryConcentrator(List<String> codes,Integer page,Integer listRows,Concentrator condition);

    public int queryConcentratorCount(List<String> codes,Concentrator condition);

    public List<Gateway> queryGateway(JSONObject obj);

    public int queryGatewayCount(JSONObject obj);

    public List<BuildingValve> queryBuildingValve(List<String> codes,Integer page,Integer listRows,BuildingValve condition);

    public int queryBuildingValveCount(List<String> codes,BuildingValve condition);

    public List<BuildingCalorimeter> queryBuildingCalorimeter(List<String> codes,Integer page,Integer listRows,BuildingCalorimeter condition);

    public int queryBuildingCalorimeterCount(List<String> codes,BuildingCalorimeter condition);

    public int getCodes(String scope,String scopeType,String scopeStr,List<String> codes);

    public HouseValve getHouseValve(HouseValve record);

    public BuildingValve getBuildingValve(BuildingValve record);

    public Concentrator getConcentrator(Concentrator record);

    public HouseCalorimeter getHouseCalorimeter(HouseCalorimeter record);

    public Gateway getGateway(Gateway record);

    public BuildingCalorimeter getBuildingCalorimeter(BuildingCalorimeter record);

    public String getDeviceUnicode(String device_type,String device_code);

    public  List<HouseHolderValveParam> queryHouseHolderValve(List<String> codes, Integer error, Integer opening, Integer mes_status, String id_card,String err_code, Integer page, Integer listRows);

    public  Integer queryHouseHolderValveCount(List<String> codes, Integer error, Integer opening, Integer mes_status, String id_card,String err_code, Integer page, Integer listRows);

    public List<HouseHolder> getHouseholderByCodes(List<String> codes);

    public List<HouseValve> getHouseValveByCodes(List<String> codes);

    public List<HouseCalorimeter> getHouseCalorimeterByCodes(List<String> codes);

    public List<BuildingValve> getBuildingValveByCodes(List<String> codes);

    public List<BuildingCalorimeter> getBuildingCalorimeterByCodes(List<String> codes);

    public List<Concentrator> getConcentratorByCodes(List<String> codes);

    public List<Gateway> getGatewayByCodes(List<String> codes);
}
