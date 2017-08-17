package com.dazk.service.impl;

import com.alibaba.fastjson.JSON;
import com.dazk.common.ErrCode;
import com.dazk.db.dao.*;
import com.dazk.db.model.*;
import com.dazk.service.AddDeviceService;
import com.dazk.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

@Service
public class AddDeviceServiceImpl implements AddDeviceService {

	@Autowired
	private HouseValveMapper houseValveMapper;

    @Autowired
    private HouseCalorimeterMapper houseCalorimeterMapper;

    @Autowired
    private ConcentratorMapper concentratorMapper;

    @Autowired
    private GatewayMapper gatewayMapper;

    @Autowired
    private BuildingValveMapper buildingValveMapper;

    @Autowired
    private BuildingCalorimeterMapper buildingCalorimeterMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private HouseHolderMapper houseHolderMapper;

    @Autowired
    private RedisService redisService;

	public int addValve(JSONObject obj){
	    HouseHolder houseHolder = new HouseHolder();
	    houseHolder.setCode(obj.getString("house_code"));
        int houseNum = houseHolderMapper.selectCount(houseHolder);
        if(houseNum == 0){
            return -2;
        }

		HouseValve record = new HouseValve();
        record.setHouse_code(obj.getString("house_code"));
        record.setIsdel(0);
		int exist = houseValveMapper.selectCount(record);
		if(exist > 0){
			return -1;
		}
		record = JSON.parseObject(obj.toJSONString(), HouseValve.class);
		record.setCreated_at(System.currentTimeMillis()/1000);
		return houseValveMapper.insertSelective(record);
	}

    public int addHouseCalorimeter(JSONObject obj){
        HouseCalorimeter record = new HouseCalorimeter();
        record.setHouse_code(obj.getString("house_code"));
        record.setIsdel(0);
        int exist = houseCalorimeterMapper.selectCount(record);
        if(exist > 0){
            return -1;
        }
        record = JSON.parseObject(obj.toJSONString(), HouseCalorimeter.class);
        record.setCreated_at(System.currentTimeMillis()/1000);
        return houseCalorimeterMapper.insertSelective(record);
    }

    public int addConcentrator(JSONObject obj){
        Concentrator record = new Concentrator();
        record.setBuilding_unique_code(obj.getString("building_unique_code"));
        record.setIsdel(0);
        int exist = concentratorMapper.selectCount(record);
        if(exist > 0){
            return -1;
        }
        record = JSON.parseObject(obj.toJSONString(), Concentrator.class);
        record.setCreated_at(System.currentTimeMillis()/1000);
        return concentratorMapper.insertSelective(record);
    }

    public int addGateway(JSONObject obj){
        Gateway record = new Gateway();
        record = JSON.parseObject(obj.toJSONString(), Gateway.class);
        record.setCreated_at(System.currentTimeMillis()/1000);
        return gatewayMapper.insertSelective(record);
    }

    public int addBuildingValve(JSONObject obj){
        BuildingValve record = new BuildingValve();
        record.setBuilding_unique_code(obj.getString("building_unique_code"));
        record.setIsdel(0);
        int exist = buildingValveMapper.selectCount(record);
        if(exist > 0){
            return -1;
        }
        record = JSON.parseObject(obj.toJSONString(), BuildingValve.class);
        record.setCreated_at(System.currentTimeMillis()/1000);
        return buildingValveMapper.insertSelective(record);
    }

    public int addBuildingCalorimeter(JSONObject obj){
        BuildingCalorimeter record = new BuildingCalorimeter();
        record.setBuilding_unique_code(obj.getString("building_unique_code"));
        record.setIsdel(0);
        int exist = buildingCalorimeterMapper.selectCount(record);
        if(exist > 0){
            return -1;
        }
        record = JSON.parseObject(obj.toJSONString(), BuildingCalorimeter.class);
        record.setCreated_at(System.currentTimeMillis()/1000);
        return buildingCalorimeterMapper.insertSelective(record);
    }

    public void addInRedis(String key,String code,String json){
        redisService.hmSet(key,code,json);
    }

    @Override
    public int addHouseValve(HouseValve record) {
        record.setCreated_at(System.currentTimeMillis()/1000);
        return houseValveMapper.insertSelective(record);
    }

    @Override
    public int addHouseCalorimeterByObj(HouseCalorimeter record) {
        record.setCreated_at(System.currentTimeMillis()/1000);
        return houseCalorimeterMapper.insertSelective(record);
    }

    @Override
    public int addBuildingValveByObj(BuildingValve record) {
        record.setCreated_at(System.currentTimeMillis()/1000);
        return buildingValveMapper.insertSelective(record);
    }

    @Override
    public int addBuildingCalorimeterByObj(BuildingCalorimeter record) {
        record.setCreated_at(System.currentTimeMillis()/1000);
        return buildingCalorimeterMapper.insertSelective(record);
    }

    @Override
    public int addConcentratorByObj(Concentrator record) {
        record.setCreated_at(System.currentTimeMillis()/1000);
        return concentratorMapper.insertSelective(record);
    }

    @Override
    public int addGatewayByObj(Gateway record) {
        record.setCreated_at(System.currentTimeMillis()/1000);
        return gatewayMapper.insertSelective(record);
    }
}
