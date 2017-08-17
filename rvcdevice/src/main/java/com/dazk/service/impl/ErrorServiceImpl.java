package com.dazk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dazk.db.dao.CollectErrorMapper;
import com.dazk.db.dao.CompanyMapper;
import com.dazk.db.dao.ErrorForViewMapper;
import com.dazk.db.dao.HouseValveMapper;
import com.dazk.db.model.CollectError;
import com.dazk.db.model.Company;
import com.dazk.db.model.ErrorForViewParam;
import com.dazk.service.ErrorService;
import com.dazk.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
@Service
public class ErrorServiceImpl implements ErrorService {
    @Autowired
    private CollectErrorMapper collectErrorMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private ErrorForViewMapper errorForViewMapper;

    @Override
    public void insertError(String device_code, String device_type, String event, Long occur_time) {
        String error_type = null;//未知异常
        int status = 0;
        String build_code = null;
        JSONObject jsonObj = null;
        try{
            switch (device_type){
                case "01":
                    build_code = jsonObj.parseObject( (String)redisService.hmGet("BuildingCalorimeter",device_code)).getString("building_unique_code");break;
                case "02":
                    build_code = jsonObj.parseObject( (String)redisService.hmGet("HouseValve",device_code)).getString("house_code");break;
                case "03":
                    build_code = jsonObj.parseObject( (String)redisService.hmGet("BuildingValve",device_code)).getString("building_unique_code");break;
                case "04":
                    //温控器未定义
                    build_code = jsonObj.parseObject( (String)redisService.hmGet("Thermostat",device_code)).getString("house_code");
                case "05":
                    build_code = jsonObj.parseObject( (String)redisService.hmGet("Concentrator",device_code)).getString("building_unique_code");
                case "06":
                    build_code = jsonObj.parseObject( (String)redisService.hmGet("GateWay",device_code)).getString("company_code");break;
                case "07":
                    build_code = jsonObj.parseObject( (String)redisService.hmGet("HouseCalorimeter",device_code)).getString("house_code");break;
                default:build_code = device_code;break;
            }
        }catch (Exception e){
            build_code = device_code;
        }
        switch (event){
            case "01":error_type = "01";status = 0;break;
            case "81":error_type = "01";status = 1;break;
            case "02":error_type = "02";status = 0;break;
            case "82":error_type = "02";status = 1;break;
            case "03":error_type = "03";status = 0;break;
            case "04":error_type = "04";status = 0;break;
            case "84":error_type = "04";status = 1;break;
            case "05":error_type = "05";status = 0;break;
            case "85":error_type = "05";status = 1;break;
        }
        CollectError collectError = new CollectError(device_type, device_code, occur_time, null, error_type, event, status,build_code);
        collectErrorMapper.insertSelective(collectError);
    }

    @Override
    public List<ErrorForViewParam> getDeviceFaultLog(List<String> codes, String device_type, String device_code,String error_type, Long start_time, Long end_time, Integer page, Integer listRows) {
        Integer start = 0;
        if( listRows != null &&  listRows > 0){
            if(page != null && page > 0){
                start = (page-1)*listRows;
            }else{
                start = 0;
            }
        }
        /*switch (device_type){
            case "01":
                    errorForViewMapper.getBuildingCalorimeterFault(codes,device_type,device_code,start_time,end_time,page,listRows);
            case "02":
                errorForViewMapper.getHouseValveFault(codes,device_type,device_code,start_time,end_time,page,listRows);
            case "03":
                errorForViewMapper.getBuildingValveFault(codes,device_type,device_code,start_time,end_time,page,listRows);
            case "04":
                //温控器未定义
            case "05":
                errorForViewMapper.getConcentratoFault(codes,device_type,device_code,start_time,end_time,page,listRows);
            case "06":
                errorForViewMapper.getGateWayFault(codes,device_type,device_code,start_time,end_time,page,listRows);
            case "07":
                errorForViewMapper.getHouseCalorimeterFault(codes,device_type,device_code,start_time,end_time,page,listRows);
        }*/
        return errorForViewMapper.getDeviceFault(codes,device_type,device_code,error_type,start_time,end_time,start,listRows);
    }

    @Override
    public Integer getDeviceFaultLogCount(List<String> codes, String device_type, String device_code,String error_type, Long start_time, Long end_time, Integer page, Integer listRows) {
        Integer start = 0;
        if( listRows != null &&  listRows > 0){
            if(page != null && page > 0){
                start = (page-1)*listRows;
            }else{
                start = 0;
            }
        }
        /*switch (device_type){
            case "01":
                    errorForViewMapper.getBuildingCalorimeterFault(codes,device_type,device_code,start_time,end_time,page,listRows);
            case "02":
                errorForViewMapper.getHouseValveFault(codes,device_type,device_code,start_time,end_time,page,listRows);
            case "03":
                errorForViewMapper.getBuildingValveFault(codes,device_type,device_code,start_time,end_time,page,listRows);
            case "04":
                //温控器未定义
            case "05":
                errorForViewMapper.getConcentratoFault(codes,device_type,device_code,start_time,end_time,page,listRows);
            case "06":
                errorForViewMapper.getGateWayFault(codes,device_type,device_code,start_time,end_time,page,listRows);
            case "07":
                errorForViewMapper.getHouseCalorimeterFault(codes,device_type,device_code,start_time,end_time,page,listRows);
        }*/
        return errorForViewMapper.getDeviceFaultCount(codes,device_type,device_code,error_type,start_time,end_time,start,listRows);
    }
}
