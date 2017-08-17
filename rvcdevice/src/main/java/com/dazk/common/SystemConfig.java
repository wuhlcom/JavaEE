package com.dazk.common;

import com.dazk.db.model.SystemParameter;
import com.dazk.service.DeviceLogService;
import com.dazk.service.SystemParameterService;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/2.
 */
public class SystemConfig implements CommandLineRunner {
    public static List<String> hmKey = Arrays.asList("BuildingCalorimeter","HouseValve", "BuildingValve","Thermostat", "Concentrator","Gateway","HouseCalorimeter");

    public static String isloginUrl = "http://118.31.102.18:9990/entry/islogin";
    //住户批量状态数据url
    public static String HouseBatchUrl = "http://118.31.102.18:9990/entry/islogin";
    //楼栋调节阀批量状态数据url
    public static String BuildValveBatchUrl = "http://118.31.102.18:9990/entry/islogin";
    //楼栋热表批量状态数据url
    public static String BuildCalorimeterBatchUrl = "http://118.31.102.18:9990/entry/islogin";
    //楼栋热表批量状态数据url
    public static String OPValveUrl = "http://118.31.102.18:9990/entry/islogin";
    //配置楼栋调节阀
    public static String SetBuildingValveUrl = "http://118.31.102.18:9990/entry/islogin";
    //设置楼栋调节阀（即时）
    public static String opBuildingValveUrl = "http://118.31.102.18:9990/entry/islogin";

    //读楼栋调节阀配置
    public static String ReadBuildingValveSetUrl = "http://118.31.102.18:9990/entry/islogin";

    public static String StealHeatListUrl = "http://118.31.102.18:9990/entry/islogin";

    public static String StealHeatCodeListUrl = "http://118.31.102.18:9990/entry/islogin";

    public static int dataTypeQuery = 0;

    public static int dataTypeUpdate = 1;

    public static Map<String,String> params = new HashMap<String,String>();

    @Resource
    private SystemParameterService systemParameterService;

    @Override
    public void run(String... args) throws Exception {
        List<SystemParameter> systemParameter = systemParameterService.getAll();
        for(SystemParameter param : systemParameter){
            params.put(param.getName(),param.getValue());
        }
    }
}
