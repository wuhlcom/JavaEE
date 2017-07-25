package com.dazk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.db.dao.*;
import com.dazk.db.model.*;
import com.dazk.service.UpdateDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * Created by Administrator on 2017/7/21.
 */
@Service
public class UpdateDeviceServiceImpl implements UpdateDeviceService {
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

    @Override
    public int updateValve(JSONObject obj) {
        String code = obj.getString("house_code");
        if(code == null) return 0;
        HouseValve record = new HouseValve();
        record.setHouse_code(obj.getString("house_code"));
        record.setIsdel(0);
        int exist = houseValveMapper.selectCount(record);
        if(exist > 1){
            return -2;
        }
        record = JSON.parseObject(obj.toJSONString(), HouseValve.class);
        try{
            //创建example
            Example example = new Example(HouseValve.class);
            //创建查询条件
            Example.Criteria criteria = example.createCriteria();
            //设置查询条件 多个andEqualTo串联表示 and条件查询
            criteria.andEqualTo("house_code",code).andEqualTo("isdel",0);
            return houseValveMapper.updateByExampleSelective(record,example);
        }catch (Exception e){
            return -1;
        }
    }

    @Override
    public int updateHouseCalorimeter(JSONObject obj) {
        String code = obj.getString("house_code");
        if(code == null) return 0;
        HouseCalorimeter record = new HouseCalorimeter();
        record.setHouse_code(obj.getString("house_code"));
        record.setIsdel(0);
        int exist = houseCalorimeterMapper.selectCount(record);
        if(exist > 1){
            return -2;
        }
        record = JSON.parseObject(obj.toJSONString(), HouseCalorimeter.class);
        try{
            //创建example
            Example example = new Example(HouseCalorimeter.class);
            //创建查询条件
            Example.Criteria criteria = example.createCriteria();
            //设置查询条件 多个andEqualTo串联表示 and条件查询
            criteria.andEqualTo("house_code",code).andEqualTo("isdel",0);
            return houseCalorimeterMapper.updateByExampleSelective(record,example);
        }catch (Exception e){
            return -1;
        }
    }

    @Override
    public int updateConcentrator(JSONObject obj) {
        String code = obj.getString("building_unique_code");
        if(code == null) return 0;
        Concentrator record = new Concentrator();
        record.setBuilding_unique_code(obj.getString("building_unique_code"));
        record.setIsdel(0);
        int exist = concentratorMapper.selectCount(record);
        if(exist > 1){
            return -2;
        }
        record = JSON.parseObject(obj.toJSONString(), Concentrator.class);
        try{
            //创建example
            Example example = new Example(Concentrator.class);
            //创建查询条件
            Example.Criteria criteria = example.createCriteria();
            //设置查询条件 多个andEqualTo串联表示 and条件查询
            criteria.andEqualTo("building_unique_code",code).andEqualTo("isdel",0);
            return concentratorMapper.updateByExampleSelective(record,example);
        }catch (Exception e){
            return -1;
        }
    }

    @Override
    public int updateGateway(JSONObject obj) {
        String code = obj.getString("id");
        if(code == null) return 0;
        Gateway record = new Gateway();
        record = JSON.parseObject(obj.toJSONString(), Gateway.class);
        try{
            //创建example
            Example example = new Example(Gateway.class);
            //创建查询条件
            Example.Criteria criteria = example.createCriteria();
            //设置查询条件 多个andEqualTo串联表示 and条件查询
            criteria.andEqualTo("id",code).andEqualTo("isdel",0);
            return gatewayMapper.updateByExampleSelective(record,example);
        }catch (Exception e){
            return -1;
        }
    }

    @Override
    public int updateBuildingValve(JSONObject obj) {
        String code = obj.getString("building_unique_code");
        if(code == null) return 0;
        BuildingValve record = new BuildingValve();
        record.setBuilding_unique_code(obj.getString("building_unique_code"));
        record.setIsdel(0);
        int exist = buildingValveMapper.selectCount(record);
        if(exist > 1){
            return -2;
        }
        record = JSON.parseObject(obj.toJSONString(), BuildingValve.class);
        try{
            //创建example
            Example example = new Example(BuildingValve.class);
            //创建查询条件
            Example.Criteria criteria = example.createCriteria();
            //设置查询条件 多个andEqualTo串联表示 and条件查询
            criteria.andEqualTo("building_unique_code",code).andEqualTo("isdel",0);
            return buildingValveMapper.updateByExampleSelective(record,example);
        }catch (Exception e){
            return -1;
        }
    }

    @Override
    public int updateBuildingCalorimeter(JSONObject obj) {
        String code = obj.getString("building_unique_code");
        if(code == null) return 0;
        BuildingCalorimeter record = new BuildingCalorimeter();
        record.setBuilding_unique_code(obj.getString("building_unique_code"));
        record.setIsdel(0);
        int exist = buildingCalorimeterMapper.selectCount(record);
        if(exist > 1){
            return -2;
        }
        record = JSON.parseObject(obj.toJSONString(), BuildingCalorimeter.class);
        try{
            //创建example
            Example example = new Example(BuildingCalorimeter.class);
            //创建查询条件
            Example.Criteria criteria = example.createCriteria();
            //设置查询条件 多个andEqualTo串联表示 and条件查询
            criteria.andEqualTo("building_unique_code",code).andEqualTo("isdel",0);
            return buildingCalorimeterMapper.updateByExampleSelective(record,example);
        }catch (Exception e){
            return -1;
        }
    }
}