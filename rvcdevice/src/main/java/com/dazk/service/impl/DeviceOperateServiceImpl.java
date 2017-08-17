package com.dazk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.ErrCode;
import com.dazk.common.SystemConfig;
import com.dazk.common.util.HttpRequest;
import com.dazk.common.util.RegexUtil;
import com.dazk.db.dao.DeviceLogExampleMapper;
import com.dazk.db.dao.FparameterMapper;
import com.dazk.db.model.*;
import com.dazk.service.DeviceOperateService;
import com.dazk.service.QueryBuildService;
import com.dazk.service.QueryDeviceService;
import com.dazk.service.UpdateDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/7.
 */
@Service
public class DeviceOperateServiceImpl implements DeviceOperateService {

    @Resource
    private QueryDeviceService queryDeviceService;

    @Autowired
    private DeviceLogExampleMapper deviceLogExampleMapper;
    @Autowired
    private FparameterMapper fparameterMapper;

    @Autowired
    private UpdateDeviceService updateDeviceService;

    @Autowired
    private QueryBuildService queryBuildService;

    @Override
    public int opValve(Integer userid,String house_code, Integer open,Integer islock,JSONObject resultObj) {
        HouseValve condition = new HouseValve();
        condition.setIsdel(0);
        condition.setHouse_code(house_code);
        HouseValve houseValve = new HouseValve();
        HouseHolder houseHolder = new HouseHolder();
        try{
            houseHolder = queryBuildService.geHuseHolderByCode(house_code);
            houseValve = queryDeviceService.getHouseValve(condition);
        }catch (Exception e){
            resultObj.put("errcode", ErrCode.repetition);
            resultObj.put("msg", "存在多个设备或对应住户！");
            return -1;
        }
        if(houseValve == null){
            resultObj.put("errcode", ErrCode.noData);
            resultObj.put("msg", "无可操作设备");
            return -1;
        }

        Concentrator conditionConcentrator = new Concentrator();
        conditionConcentrator.setBuilding_unique_code(houseValve.getHouse_code().substring(0,10));
        conditionConcentrator.setIsdel(0);
        Concentrator concentrator = null;
        try{
            concentrator = queryDeviceService.getConcentrator(conditionConcentrator);
        }catch (Exception e){
            resultObj.put("errcode", ErrCode.repetition);
            resultObj.put("msg", "存在多个设备");
            return -1;
        }
        if(houseValve.getType() == 1 && concentrator == null){
            resultObj.put("errcode", ErrCode.noData);
            resultObj.put("msg", "无对应采集器");
            return -1;
        }

        //设备操作
        JSONObject operate = new JSONObject();
        operate.put("comm_type",houseValve.getType());
        operate.put("comm_address",houseValve.getComm_address());
        operate.put("dtu_code",concentrator.getCode());
        operate.put("opening",open);

        String operateRes = HttpRequest.sendJsonPost(SystemConfig.OPValveUrl, operate.toJSONString());
        JSONObject operateResInfo = JSON.parseObject(operateRes);
        OpenLog opLog = new OpenLog();
        opLog.setAddress(houseValve.getAddress());
        opLog.setCreated_at(System.currentTimeMillis()/1000);
        opLog.setRecord_time(System.currentTimeMillis()/1000);
        opLog.setHouse_code(house_code);
        opLog.setLogstatus(1);
        opLog.setUser_id(userid);
        opLog.setName(houseHolder.getName());
        opLog.setOperate(open);

        //设置阀门状态
        updateDeviceService.setValveState(house_code,open,null,islock);
        if(operateResInfo.getString("errcode").equals("0")){
            if(open.equals("0")){
                opLog.setRecord("关阀成功");
                resultObj.put("errcode", ErrCode.success);
                resultObj.put("msg", "关阀成功");
            }else{
                opLog.setRecord("开阀成功");
                resultObj.put("errcode", ErrCode.success);
                resultObj.put("msg", "开阀成功");
            }
            deviceLogExampleMapper.insertSelective(opLog);
            resultObj.put("errcode", ErrCode.success);
        }else{
            if(open.equals("0")){
                opLog.setRecord("关阀失败");
            }else{
                opLog.setRecord("开阀失败");
            }
            deviceLogExampleMapper.insertSelective(opLog);
            resultObj.put("errcode", ErrCode.opValveErr);
            resultObj.put("msg",operateRes);
        }
        return 1;
    }

    @Override
    public int setBuildingValve(Integer userid, Map<String, String> paramters, JSONObject resultObj) {
        String building_unique_code = paramters.get("building_unique_code");
        String comm_address = paramters.get("comm_address");

        BuildingValve condition = new BuildingValve();
        condition.setIsdel(0);
        condition.setBuilding_unique_code(building_unique_code);
        condition.setComm_address(comm_address);
        BuildingValve buildingValve = null;
        try{
            buildingValve = queryDeviceService.getBuildingValve(condition);
        }catch (Exception e){
            resultObj.put("errcode", ErrCode.repetition);
            resultObj.put("msg", "存在多个设备");
            return -1;
        }
        if(buildingValve == null){
            resultObj.put("errcode", ErrCode.noData);
            resultObj.put("msg", "无可操作设备");
            return -1;
        }

        Concentrator conditionConcentrator = new Concentrator();
        conditionConcentrator.setBuilding_unique_code(buildingValve.getBuilding_unique_code().substring(0,10));
        conditionConcentrator.setIsdel(0);
        Concentrator concentrator = null;
        try{
            concentrator = queryDeviceService.getConcentrator(conditionConcentrator);
        }catch (Exception e){
            resultObj.put("errcode", ErrCode.repetition);
            resultObj.put("msg", "存在多个设备");
            return -1;
        }
        if(buildingValve.getType() == 1 && concentrator == null){
            resultObj.put("errcode", ErrCode.noData);
            resultObj.put("msg", "无对应采集器");
            return -1;
        }


        //设备操作
        JSONObject operate = new JSONObject();
        operate.put("comm_type",buildingValve.getType());
        operate.put("comm_address",buildingValve.getComm_address());
        operate.put("dtu_code",concentrator.getCode());
        for(int i = 1;i < 24;i++){
            operate.put("opening"+i,paramters.get("opening"+i));
        }

        String operateRes = HttpRequest.sendJsonPost(SystemConfig.SetBuildingValveUrl, operate.toJSONString());
        JSONObject operateResInfo = JSON.parseObject(operateRes);
        Fparameter fparameter = new Fparameter();
        fparameter.initOpen(paramters);

        Example example = new Example(Fparameter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("building_valve_id",buildingValve.getId());

        if(operateResInfo.getString("errcode").equals("0")){
            fparameterMapper.updateByExampleSelective(fparameter,criteria);
            resultObj.put("errcode", ErrCode.success);
        }else{
            fparameterMapper.updateByExampleSelective(fparameter,criteria);
            resultObj.put("errcode", ErrCode.opValveErr);
            resultObj.put("msg",operateRes);
        }
        return 1;
    }

    @Override
    public int opBuildingValve(Integer userid, Map<String, String> paramters, JSONObject resultObj) {
        String building_unique_code = paramters.get("building_unique_code");
        String comm_address = paramters.get("comm_address");

        BuildingValve condition = new BuildingValve();
        condition.setIsdel(0);
        condition.setBuilding_unique_code(building_unique_code);
        condition.setComm_address(comm_address);
        BuildingValve buildingValve = null;
        try{
            buildingValve = queryDeviceService.getBuildingValve(condition);
        }catch (Exception e){
            resultObj.put("errcode", ErrCode.repetition);
            resultObj.put("msg", "存在多个设备");
            return -1;
        }
        if(buildingValve == null){
            resultObj.put("errcode", ErrCode.noData);
            resultObj.put("msg", "无可操作设备");
            return -1;
        }

        Concentrator conditionConcentrator = new Concentrator();
        conditionConcentrator.setBuilding_unique_code(buildingValve.getBuilding_unique_code().substring(0,10));
        conditionConcentrator.setIsdel(0);
        Concentrator concentrator = null;
        try{
            concentrator = queryDeviceService.getConcentrator(conditionConcentrator);
        }catch (Exception e){
            resultObj.put("errcode", ErrCode.repetition);
            resultObj.put("msg", "存在多个设备");
            return -1;
        }
        if(buildingValve.getType() == 1 && concentrator == null){
            resultObj.put("errcode", ErrCode.noData);
            resultObj.put("msg", "无对应采集器");
            return -1;
        }


        //设备操作
        JSONObject operate = new JSONObject();
        operate.put("comm_type",buildingValve.getType());
        operate.put("comm_address",buildingValve.getComm_address());
        operate.put("dtu_code",concentrator.getCode());
        operate.put("opening",paramters.get("open"));

        String operateRes = HttpRequest.sendJsonPost(SystemConfig.opBuildingValveUrl, operate.toJSONString());
        JSONObject operateResInfo = JSON.parseObject(operateRes);

        if(operateResInfo.getString("errcode").equals("0")){
            resultObj.put("errcode", ErrCode.success);
        }else{
            resultObj.put("errcode", ErrCode.opValveErr);
            resultObj.put("msg",operateRes);
        }
        return 1;
    }

    @Override
    public int readBuildingValveSet(Integer userid, Map<String, String> paramters, JSONObject resultObj) {
        String building_unique_code = paramters.get("building_unique_code");
        String comm_address = paramters.get("comm_address");

        BuildingValve condition = new BuildingValve();
        condition.setIsdel(0);
        condition.setBuilding_unique_code(building_unique_code);
        condition.setComm_address(comm_address);
        BuildingValve buildingValve = null;
        try{
            buildingValve = queryDeviceService.getBuildingValve(condition);
        }catch (Exception e){
            resultObj.put("errcode", ErrCode.repetition);
            resultObj.put("msg", "存在多个设备");
            return -1;
        }
        if(buildingValve == null){
            resultObj.put("errcode", ErrCode.noData);
            resultObj.put("msg", "无可操作设备");
            return -1;
        }

        Concentrator conditionConcentrator = new Concentrator();
        conditionConcentrator.setBuilding_unique_code(buildingValve.getBuilding_unique_code().substring(0,10));
        conditionConcentrator.setIsdel(0);
        Concentrator concentrator = null;
        try{
            concentrator = queryDeviceService.getConcentrator(conditionConcentrator);
        }catch (Exception e){
            resultObj.put("errcode", ErrCode.repetition);
            resultObj.put("msg", "存在多个设备");
            return -1;
        }
        if(buildingValve.getType() == 1 && concentrator == null){
            resultObj.put("errcode", ErrCode.noData);
            resultObj.put("msg", "无对应采集器");
            return -1;
        }

        //设备操作
        JSONObject operate = new JSONObject();
        operate.put("comm_type",buildingValve.getType());
        operate.put("comm_address",buildingValve.getComm_address());
        operate.put("dtu_code",concentrator.getCode());

        String operateRes = HttpRequest.sendJsonPost(SystemConfig.ReadBuildingValveSetUrl, operate.toJSONString());
        JSONObject operateResInfo = JSON.parseObject(operateRes);

        if(operateResInfo.getString("errcode").equals("0")){
            resultObj.put("errcode", ErrCode.success);
        }else{
            for(int i = 1;i <= 24;i++){
                resultObj.put("opening"+i,operateResInfo.get("opening"+i));
            }
            resultObj.put("errcode", ErrCode.opValveErr);
            resultObj.put("msg",operateRes);
        }
        return 1;
    }
}
