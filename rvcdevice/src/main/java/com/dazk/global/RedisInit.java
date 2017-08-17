package com.dazk.global;

import com.alibaba.fastjson.JSON;
import com.dazk.db.dao.*;
import com.dazk.db.model.*;
import com.dazk.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 */
@Component
public class RedisInit implements CommandLineRunner{
    @Autowired
    private RedisService redisService;

    @Autowired
    private HouseValveMapper houseValveMapper;

    @Autowired
    private HouseCalorimeterMapper houseCalorimeterMapper;

    @Autowired
    private ConcentratorMapper concentratorMapper;

    @Autowired
    private BuildingValveMapper buildingValveMapper;

    @Autowired
    private BuildingCalorimeterMapper buildingCalorimeterMapper;

    @Autowired
    private GatewayMapper gatewayMapper;

    @Autowired
    private DeviceStatusMapper deviceStatusMapper;

    @Override
    public void run(String... args) throws Exception {
        HouseValve record1 = new HouseValve();record1.setIsdel(0);
        List<HouseValve> houseValveList = houseValveMapper.select(record1);
        HouseCalorimeter record2 = new HouseCalorimeter();record2.setIsdel(0);
        List<HouseCalorimeter> houseCalorimeterList = houseCalorimeterMapper.select(record2);
        Concentrator record3 = new Concentrator();record3.setIsdel(0);
        List<Concentrator> concentratorList = concentratorMapper.select(record3);
        BuildingValve record4 = new BuildingValve();record4.setIsdel(0);
        List<BuildingValve> buildingValveList = buildingValveMapper.select(record4);
        BuildingCalorimeter record5 = new BuildingCalorimeter();record5.setIsdel(0);
        List<BuildingCalorimeter> buildingCalorimeterList = buildingCalorimeterMapper.select(record5);
        Gateway record6 = new Gateway();record6.setIsdel(0);
        List<Gateway> gatewayList = gatewayMapper.select(record6);

        //初始化redis
        for (HouseValve houseValve : houseValveList){
            if(houseValve.getType() == 0){
                redisService.hmSet("HouseValve",houseValve.getComm_address(),JSON.toJSONString(new HouseValveData(houseValve)));
            }else if (houseValve.getType() == 1){
                redisService.hmSet("HouseValve",houseValve.getHouse_code(),JSON.toJSONString(new HouseValveData(houseValve)));
            }
        }

        for (HouseCalorimeter houseCalorimeter : houseCalorimeterList){
            if (houseCalorimeter.getType() == 0){
                redisService.hmSet("HouseCalorimeter",houseCalorimeter.getComm_address(),JSON.toJSONString(new HouseCalorimeterData(houseCalorimeter)));
            }else if (houseCalorimeter.getType() == 1){
                redisService.hmSet("HouseCalorimeter",houseCalorimeter.getHouse_code(),JSON.toJSONString(new HouseCalorimeterData(houseCalorimeter)));
            }

        }

        for (Concentrator concentrator : concentratorList){
            redisService.hmSet("Concentrator",concentrator.getCode(),JSON.toJSONString(concentrator));
        }

        for (BuildingValve buildingValve : buildingValveList){
            redisService.hmSet("BuildingValve",buildingValve.getComm_address(),JSON.toJSONString(new BuildingValveData(buildingValve)));
        }

        for (BuildingCalorimeter buildingCalorimeter : buildingCalorimeterList){
            redisService.hmSet("BuildingCalorimeter",buildingCalorimeter.getComm_address(),JSON.toJSONString(new BuildingCalorimeterData(buildingCalorimeter)));
        }

        for (Gateway gateway : gatewayList){
            redisService.hmSet("Gateway",gateway.getMac(),JSON.toJSONString(gateway));
        }
    }
}
