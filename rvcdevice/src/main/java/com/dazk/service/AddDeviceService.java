package com.dazk.service;

import com.alibaba.fastjson.JSONObject;
import com.dazk.db.model.*;

/**
 * 添加设备
 * @param json
 * @return
 * @author sunjd
 */
public interface AddDeviceService {
	public int addValve(JSONObject json);

	public int addHouseCalorimeter(JSONObject json);

	public int addConcentrator(JSONObject json);

	public int addGateway(JSONObject json);

	public int addBuildingValve(JSONObject json);

	public int addBuildingCalorimeter(JSONObject json);

	public void addInRedis(String key,String code,String json);

	public int addHouseValve(HouseValve record);

	public int addHouseCalorimeterByObj(HouseCalorimeter record);

	public int addBuildingValveByObj(BuildingValve record);

	public int addBuildingCalorimeterByObj(BuildingCalorimeter record);

	public int addConcentratorByObj(Concentrator record);

	public int addGatewayByObj(Gateway record);
}
