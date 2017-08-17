package com.dazk.service.impl;

import com.alibaba.fastjson.JSON;
import com.dazk.db.dao.*;
import com.dazk.db.model.*;
import com.dazk.service.RedisService;
import com.dazk.service.StatePushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/2.
 */
@Service
public class StatePushServiceImpl implements StatePushService{
    @Autowired
    private RedisService redisService;

    @Autowired
    private DeviceFaultLogMapper deviceFaultLogMapper;

    @Autowired
    private HouseValveMapper houseValveMapper;

    @Autowired
    private HouseCalorimeterMapper houseCalorimeterMapper;

    @Autowired
    private ConcentratorMapper concentratorMapper;

    @Autowired
    private BuildingValveMapper buildingValveMapper;

    @Autowired
    private GatewayMapper gatewayMapper;

    @Autowired
    private BuildingCalorimeterMapper buildingCalorimeterMapper;


    @Override
    public void spHouseValve(String device_type,Long errTime, String comm_address, String event) {
        Integer online = null;
        String deviceStr = "户通断阀";
        String errEvent = new String();
        String err_code = event;
        Integer opening = null;
        if(comm_address != null){
            String jsonObj = (String)redisService.hmGet("HouseValve",comm_address);
            if(jsonObj == null) return;
            HouseValveData houseValveData = JSON.parseObject(jsonObj,HouseValveData.class);

            Map<String,Object> status = getStatus(event);
            if(status.get("online") != null){online = (Integer) status.get("online");}
            if(status.get("errEvent") != null){errEvent = (String) status.get("errEvent");}
            if(status.get("opening") != null){opening = (Integer) status.get("opening");}
            if(status.get("err_code") != null){err_code = (String) status.get("err_code");}

            Example example = new Example(HouseValve.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("huse_code",houseValveData.getHouse_code());

            HouseValve record = new HouseValve();
            record.setErr_code(err_code);record.setOpening(opening);record.setOnline(online);
            houseValveMapper.updateByExampleSelective(record,example);

            houseValveData.setErr_state(errEvent);
            houseValveData.setErr_time(errTime);
            redisService.hmSet("HouseValve",comm_address,JSON.toJSONString(houseValveData));
        };
    }

    @Override
    public void spHouseCalorimeter(String device_type,Long errTime, String comm_address, String event) {
        String deviceStr = "户用热表";
        String errEvent = new String();
        Integer online = null;
        String err_code = event;
        Integer opening = null;
        if(comm_address != null){
            String jsonObj = (String)redisService.hmGet("HouseCalorimeter",comm_address);
            if(jsonObj == null) return;
            HouseCalorimeterData houseCalorimeterData = JSON.parseObject(jsonObj,HouseCalorimeterData.class);

            Map<String,Object> status = getStatus(event);
            if(status.get("online") != null){online = (Integer) status.get("online");}
            if(status.get("errEvent") != null){errEvent = (String) status.get("errEvent");}
            if(status.get("opening") != null){opening = (Integer) status.get("opening");}
            if(status.get("err_code") != null){err_code = (String) status.get("err_code");}

            Example example = new Example(HouseCalorimeter.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("huse_code",houseCalorimeterData.getHouse_code());

            HouseCalorimeter record = new HouseCalorimeter();
            record.setErr_code(err_code);record.setOnline(online);
            houseCalorimeterMapper.updateByExampleSelective(record,example);

            houseCalorimeterData.setErr_state(errEvent);
            houseCalorimeterData.setErr_time(errTime);
            redisService.hmSet("HouseCalorimeter",comm_address,JSON.toJSONString(houseCalorimeterData));
        };
    }

    @Override
    public void spConcentrator(String device_type,Long errTime, String comm_address, String event) {
        String deviceStr = "采集器";
        String errEvent = new String();
        Integer online = null;
        String err_code = event;
        Integer opening = null;
        if(comm_address != null){
            String jsonObj = (String)redisService.hmGet("Concentrator",comm_address);
            if(jsonObj == null) return;
            Concentrator concentrator = JSON.parseObject(jsonObj,Concentrator.class);

            Map<String,Object> status = getStatus(event);
            if(status.get("online") != null){online = (Integer) status.get("online");}
            if(status.get("errEvent") != null){errEvent = (String) status.get("errEvent");}
            if(status.get("opening") != null){opening = (Integer) status.get("opening");}
            if(status.get("err_code") != null){err_code = (String) status.get("err_code");}

            Example example = new Example(Concentrator.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("code",comm_address);

            Concentrator record = new Concentrator();
            record.setErr_code(err_code);record.setOnline(online);
            concentratorMapper.updateByExampleSelective(record,example);

        }
    }

    @Override
    public void spBuildingValve(String device_type,Long errTime, String comm_address, String event) {
        String deviceStr = "楼栋调节阀";
        String errEvent = new String();
        Integer online = null;
        String err_code = event;
        Integer opening = null;
        if(comm_address != null){
            String jsonObj = (String)redisService.hmGet("BuildingValve",comm_address);
            if(jsonObj == null) return;
            BuildingValveData buildingValveData = JSON.parseObject(jsonObj,BuildingValveData.class);

            Map<String,Object> status = getStatus(event);
            if(status.get("online") != null){online = (Integer) status.get("online");}
            if(status.get("errEvent") != null){errEvent = (String) status.get("errEvent");}
            if(status.get("opening") != null){opening = (Integer) status.get("opening");}
            if(status.get("err_code") != null){err_code = (String) status.get("err_code");}

            Example example = new Example(BuildingValve.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("comm_address",comm_address);

            BuildingValve record = new BuildingValve();
            record.setErr_code(err_code);record.setOnline(online);
            buildingValveMapper.updateByExampleSelective(record,example);

            buildingValveData.setErr_state(errEvent);
            buildingValveData.setErr_time(errTime);
            redisService.hmSet("BuildingValve",comm_address,JSON.toJSONString(buildingValveData));
        };
    }

    @Override
    public void spBuildingCalorimeter(String device_type,Long errTime, String comm_address, String event) {
        String deviceStr = "楼栋热表";
        String errEvent = new String();
        Integer online = null;
        String err_code = event;
        Integer opening = null;
        if(comm_address != null){
            String jsonObj = (String)redisService.hmGet("BuildingCalorimeter",comm_address);
            if(jsonObj == null) return;
            BuildingCalorimeterData buildingCalorimeterData = JSON.parseObject(jsonObj,BuildingCalorimeterData.class);

            Map<String,Object> status = getStatus(event);
            if(status.get("online") != null){online = (Integer) status.get("online");}
            if(status.get("errEvent") != null){errEvent = (String) status.get("errEvent");}
            if(status.get("opening") != null){opening = (Integer) status.get("opening");}
            if(status.get("err_code") != null){err_code = (String) status.get("err_code");}

            Example example = new Example(BuildingCalorimeter.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("comm_address",comm_address);

            BuildingCalorimeter record = new BuildingCalorimeter();
            record.setErr_code(err_code);record.setOnline(online);
            buildingCalorimeterMapper.updateByExampleSelective(record,example);

            buildingCalorimeterData.setErr_state(errEvent);
            buildingCalorimeterData.setErr_time(errTime);
            redisService.hmSet("BuildingCalorimeter",comm_address,JSON.toJSONString(buildingCalorimeterData));
        };
    }

    @Override
    public void spGateway(String device_type,Long errTime, String comm_address, String event) {
        String deviceStr = "基站";
        String errEvent = new String();
        Integer online = null;
        String err_code = event;
        Integer opening = null;
        if(comm_address != null){
            String jsonObj = (String)redisService.hmGet("Gateway",comm_address);
            if(jsonObj == null) return;
            Gateway gategway = JSON.parseObject(jsonObj,Gateway.class);

            Map<String,Object> status = getStatus(event);
            if(status.get("online") != null){online = (Integer) status.get("online");}
            if(status.get("errEvent") != null){errEvent = (String) status.get("errEvent");}
            if(status.get("opening") != null){opening = (Integer) status.get("opening");}
            if(status.get("err_code") != null){err_code = (String) status.get("err_code");}

            Example example = new Example(Gateway.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("mac",comm_address);

            Gateway record = new Gateway();
            record.setErr_code(err_code);record.setOnline(online);
            gatewayMapper.updateByExampleSelective(record,example);

            redisService.hmSet("Gateway",comm_address,JSON.toJSONString(gategway));
        };
    }

    private Map<String,Object> getStatus(String event){
        String errEvent = null;
        Integer online = null;
        String err_code = null;
        Integer opening = null;
        switch (event){
            case "01":errEvent = "MBUS通信异常";online = 0;break;
            case "81":errEvent = "MBUS通信异常恢复";online=1;err_code="00";break;
            case "02":errEvent = "阀异常";break;
            case "82":errEvent = "阀异常恢复";err_code="00";break;
            case "03":errEvent = "电池故障";break;
            case "83":errEvent = "电池故障恢复";err_code="00";break;
            case "04":errEvent = "无线通信故障";online=0;break;
            case "84":errEvent = "无线通信故障恢复";online=1;err_code="00";break;
            case "05":errEvent = "传感器故障";break;
            case "85":errEvent = "传感器故障恢复";err_code="00";break;
            case "06":errEvent = "开阀操作失败";break;
            case "86":errEvent = "开阀操作成功";err_code="00";opening = 1;break;
            case "07":errEvent = "关阀操作失败";break;
            case "87":errEvent = "关阀操作成功";err_code="00";opening = 0;break;
            default:return null;
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("errEvent",errEvent);map.put("online",online);map.put("err_code",err_code);map.put("opening",opening);
        return map;
    }
}
