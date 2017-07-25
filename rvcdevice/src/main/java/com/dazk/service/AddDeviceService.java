package com.dazk.service;

import com.alibaba.fastjson.JSONObject;
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
}
