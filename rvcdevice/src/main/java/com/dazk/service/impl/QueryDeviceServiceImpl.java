package com.dazk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.db.dao.*;
import com.dazk.db.model.*;
import com.dazk.db.param.HouseHolderValveParam;
import com.dazk.service.QueryDeviceService;
import com.dazk.service.RedisService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/22.
 */
@Service
public class QueryDeviceServiceImpl implements QueryDeviceService {
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
    private HotstationMapper hotstationMapper;

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private HouseHolderMapper houseHolderMapper;

    @Autowired
    private HouseHolderValveMapper houseHolderValveMapper;

    @Autowired
    private RedisService redisService;

    private final int houseCodelen = 16;

    private final int buildCodeLen = 10;

    @Override
    public List<HouseValve> queryValve(List<String> codes,Integer page,Integer listRows,HouseValve condition) {
        if (page != null && listRows != null) {
            PageHelper.startPage(page, listRows);
        }
        Example example = new Example(HouseValve.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        if(codes != null){
            for (String code : codes){
                criteria.orLike("house_code",code+"%");
            }
        }

        Example.Criteria recordCriteria = example.createCriteria();
        if(condition != null){
            if(condition.getComm_address() != null){
                recordCriteria.andLike("comm_address","%"+condition.getComm_address()+"%");
                condition.setComm_address(null);
            }
            recordCriteria.andEqualTo(condition).andEqualTo("isdel",0);
        }else{
            recordCriteria.andEqualTo("isdel",0);
        }
        example.and(recordCriteria);

        return houseValveMapper.selectByExample(example);
    }

    @Override
    public int queryValveCount(List<String> codes,HouseValve condition) {
        Example example = new Example(HouseValve.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for (String code : codes){
            criteria.orLike("house_code",code+"%");
        }

        Example.Criteria recordCriteria = example.createCriteria();
        if(condition != null){
            if(condition.getComm_address() != null){
                recordCriteria.andLike("comm_address","%"+condition.getComm_address()+"%");
                condition.setComm_address(null);
            }
            recordCriteria.andEqualTo(condition).andEqualTo("isdel",0);
        }else{
            recordCriteria.andEqualTo("isdel",0);
        }
        example.and(recordCriteria);

        return houseValveMapper.selectCountByExample(example);
    }

    @Override
    public List<HouseCalorimeter> queryHouseCalorimeter(List<String> codes,Integer page,Integer listRows,HouseCalorimeter condition) {
        if (page != null && listRows != null) {
            PageHelper.startPage(page, listRows);
        }
        Example example = new Example(HouseCalorimeter.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for (String code : codes){
            criteria.orLike("house_code",code+"%");
        }

        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo(condition).andEqualTo("isdel",0);
        example.and(recordCriteria);

        return houseCalorimeterMapper.selectByExample(example);
    }

    @Override
    public int queryHouseCalorimeterCount(List<String> codes,HouseCalorimeter condition) {
        Example example = new Example(HouseCalorimeter.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for (String code : codes){
            criteria.orLike("house_code",code+"%");
        }

        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo(condition).andEqualTo("isdel",0);
        example.and(recordCriteria);

        return houseCalorimeterMapper.selectCountByExample(example);
    }

    @Override
    public List<Concentrator> queryConcentrator(List<String> codes,Integer page,Integer listRows,Concentrator condition) {
        if (page != null && listRows != null) {
            PageHelper.startPage(page, listRows);
        }
        Example example = new Example(Concentrator.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        if(codes != null){
            for (String code : codes){
                criteria.orLike("building_unique_code",code+"%");
            }
        }

        Example.Criteria recordCriteria = example.createCriteria();
        if(condition.getName() != null){
            recordCriteria.andLike("name","%"+condition.getName()+"%");
            condition.setName(null);
        }
        recordCriteria.andEqualTo(condition).andEqualTo("isdel",0);
        example.and(recordCriteria);



        return concentratorMapper.selectByExample(example);
    }

    @Override
    public int queryConcentratorCount(List<String> codes,Concentrator condition) {
        Example example = new Example(Concentrator.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for (String code : codes){
            criteria.orLike("building_unique_code",code+"%");
        }

        Example.Criteria recordCriteria = example.createCriteria();
        if(condition.getName() != null){
            recordCriteria.andLike("name","%"+condition.getName()+"%");
            condition.setName(null);
        }
        recordCriteria.andEqualTo(condition).andEqualTo("isdel",0);
        example.and(recordCriteria);

        return concentratorMapper.selectCountByExample(example);
    }

    @Override
    public List<Gateway> queryGateway(JSONObject obj) {
        Gateway record = new Gateway();
        record = JSON.parseObject(obj.toJSONString(), Gateway.class);
        if (record.getPage() != null && record.getListRows() != null) {
            PageHelper.startPage(record.getPage(), record.getListRows());
        }

        Example example = new Example(Gateway.class);
        Example.Criteria criteria = example.createCriteria();
        if(record.getName() != null){
            criteria.andLike("name","%"+record.getName()+"%");
            record.setName(null);
        }

        criteria.andEqualTo(record).andEqualTo("isdel",0);
        return gatewayMapper.selectByExample(example);
    }

    @Override
    public int queryGatewayCount(JSONObject obj) {
        Gateway record = new Gateway();
        record = JSON.parseObject(obj.toJSONString(), Gateway.class);

        Example example = new Example(Gateway.class);
        Example.Criteria criteria = example.createCriteria();
        if(record.getName() != null){
            criteria.andLike("name","%"+record.getName()+"%");
            record.setName(null);
        }

        criteria.andEqualTo(record).andEqualTo("isdel",0);
        return gatewayMapper.selectCountByExample(example);
    }

    @Override
    public List<BuildingValve> queryBuildingValve(List<String> codes,Integer page,Integer listRows,BuildingValve condition) {
        if (page != null && listRows != null) {
            PageHelper.startPage(page, listRows);
        }
        Example example = new Example(BuildingValve.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for (String code : codes){
            criteria.orLike("building_unique_code",code+"%");
        }

        Example.Criteria recordCriteria = example.createCriteria();
        if(condition != null){
            if(condition.getName() != null){
                recordCriteria.andLike("name","%"+condition.getName()+"%");
                condition.setName(null);
            }
            recordCriteria.andEqualTo(condition).andEqualTo("isdel",0);
        }else{
            recordCriteria.andEqualTo("isdel",0);
        }
        example.and(recordCriteria);

        return buildingValveMapper.selectByExample(example);
    }

    @Override
    public int queryBuildingValveCount(List<String> codes,BuildingValve condition) {
        Example example = new Example(BuildingValve.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for (String code : codes){
            criteria.orLike("building_unique_code",code+"%");
        }

        Example.Criteria recordCriteria = example.createCriteria();
        if(condition != null){
            if(condition.getName() != null){
                recordCriteria.andLike("name","%"+condition.getName()+"%");
                condition.setName(null);
            }
            recordCriteria.andEqualTo(condition).andEqualTo("isdel",0);
        }else{
            recordCriteria.andEqualTo("isdel",0);
        }
        example.and(recordCriteria);

        return buildingValveMapper.selectCountByExample(example);
    }

    @Override
    public List<BuildingCalorimeter> queryBuildingCalorimeter(List<String> codes,Integer page,Integer listRows,BuildingCalorimeter condition) {
        if (page != null && listRows != null) {
            PageHelper.startPage(page, listRows);
        }
        Example example = new Example(BuildingCalorimeter.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for (String code : codes){
            criteria.orLike("building_unique_code",code+"%");
        }

        Example.Criteria recordCriteria = example.createCriteria();
        if(condition.getName() != null){
            recordCriteria.andLike("name","%"+condition.getName()+"%");
            condition.setName(null);
        }
        recordCriteria.andEqualTo(condition).andEqualTo("isdel",0);
        example.and(recordCriteria);

        return buildingCalorimeterMapper.selectByExample(example);
    }

    @Override
    public int queryBuildingCalorimeterCount(List<String> codes,BuildingCalorimeter condition) {
        Example example = new Example(BuildingCalorimeter.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for (String code : codes){
            criteria.orLike("building_unique_code",code+"%");
        }

        Example.Criteria recordCriteria = example.createCriteria();
        if(condition.getName() != null){
            recordCriteria.andLike("name","%"+condition.getName()+"%");
            condition.setName(null);
        }
        recordCriteria.andEqualTo(condition).andEqualTo("isdel",0);
        example.and(recordCriteria);

        return buildingCalorimeterMapper.selectCountByExample(example);
    }

    @Override
    public BuildingValve getBuildingValve(BuildingValve record){
        record.setIsdel(0);
        return buildingValveMapper.selectOne(record);
    }

    @Override
    public HouseValve getHouseValve(HouseValve record) {
        record.setIsdel(0);return houseValveMapper.selectOne(record);
    }
    @Override
    public Concentrator getConcentrator(Concentrator record){
        record.setIsdel(0);return concentratorMapper.selectOne(record);
    }

    @Override
    public HouseCalorimeter getHouseCalorimeter(HouseCalorimeter record){
        record.setIsdel(0);return houseCalorimeterMapper.selectOne(record);
    }

    @Override
    public Gateway getGateway(Gateway record){
        record.setIsdel(0);return gatewayMapper.selectOne(record);
    }

    @Override
    public BuildingCalorimeter getBuildingCalorimeter(BuildingCalorimeter record){
        record.setIsdel(0);return buildingCalorimeterMapper.selectOne(record);
    }

    @Override
    public String getDeviceUnicode(String device_type,String device_code){
        try{
            JSONObject jsonObj = null;
            switch (device_type){
                case "01":
                    return jsonObj.parseObject( (String)redisService.hmGet("BuildingCalorimeter",device_code)).getString("building_unique_code");
                case "02":
                    return jsonObj.parseObject( (String)redisService.hmGet("HouseValve",device_code)).getString("house_code");
                case "03":
                    return jsonObj.parseObject( (String)redisService.hmGet("BuildingValve",device_code)).getString("building_unique_code");
                case "04":
                    //温控器未定义
                    return "  ";//;jsonObj.parseObject( (String)redisService.hmGet("HouseValve",device_code)).getString("house_code");
                case "05":
                    return jsonObj.parseObject( (String)redisService.hmGet("Concentrato",device_code)).getString("building_unique_code");
                case "06":
                    return jsonObj.parseObject( (String)redisService.hmGet("GateWay",device_code)).getString("company_code");
                case "07":
                    return jsonObj.parseObject( (String)redisService.hmGet("HouseCalorimeter",device_code)).getString("house_code");
            }
            return "";
        }catch (Exception e){
            return "error";
        }

    }

    @Override
    public List<HouseHolderValveParam> queryHouseHolderValve(List<String> codes, Integer error, Integer opening, Integer mes_status, String id_card,String err_code, Integer page, Integer listRows) {
        Integer start = 0;
        if( listRows != null &&  listRows > 0){
            if(page != null && page > 0){
                start = (page-1)*listRows;
            }else{
                start = 0;
            }
        }
        return houseHolderValveMapper.queryHouseHolderValve( codes,  error,  opening,  mes_status,  id_card,err_code,  start,  listRows);
    }

    @Override
    public Integer queryHouseHolderValveCount(List<String> codes, Integer error, Integer opening, Integer mes_status, String id_card,String err_code, Integer page, Integer listRows) {
        Integer start = 0;
        if( listRows != null &&  listRows > 0){
            if(page != null && page > 0){
                start = (page-1)*listRows;
            }else{
                start = 0;
            }
        }
        return houseHolderValveMapper.queryHouseHolderValveCount( codes,  error,  opening,  mes_status,  id_card, err_code, start,  listRows);
    }

    @Override
    public List<HouseHolder> getHouseholderByCodes(List<String> codes){
        Example example = new Example(HouseHolder.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for (String code : codes){
            criteria.orLike("code",code+"%");
        }

        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo("isdel",0);
        example.and(recordCriteria);

        return houseHolderMapper.selectByExample(example);
    }

    @Override
    public List<HouseValve> getHouseValveByCodes(List<String> codes){
        Example example = new Example(HouseValve.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for (String code : codes){
            criteria.orLike("house_code",code+"%");
        }

        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo("isdel",0);
        example.and(recordCriteria);

        return houseValveMapper.selectByExample(example);
    }

    @Override
    public List<HouseCalorimeter> getHouseCalorimeterByCodes(List<String> codes){
        Example example = new Example(HouseCalorimeter.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for (String code : codes){
            criteria.orLike("house_code",code+"%");
        }

        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo("isdel",0);
        example.and(recordCriteria);

        return houseCalorimeterMapper.selectByExample(example);
    }

    @Override
    public List<BuildingValve> getBuildingValveByCodes(List<String> codes) {
        Example example = new Example(BuildingValve.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for (String code : codes){
            criteria.orLike("building_unique_code",code+"%");
        }

        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo("isdel",0);
        example.and(recordCriteria);

        return buildingValveMapper.selectByExample(example);
    }

    @Override
    public List<BuildingCalorimeter> getBuildingCalorimeterByCodes(List<String> codes) {
        Example example = new Example(BuildingCalorimeter.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for (String code : codes){
            criteria.orLike("building_unique_code",code+"%");
        }

        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo("isdel",0);
        example.and(recordCriteria);

        return buildingCalorimeterMapper.selectByExample(example);
    }

    @Override
    public List<Concentrator> getConcentratorByCodes(List<String> codes) {
        Example example = new Example(Concentrator.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for (String code : codes){
            criteria.orLike("building_unique_code",code+"%");
        }

        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo("isdel",0);
        example.and(recordCriteria);

        return concentratorMapper.selectByExample(example);
    }

    @Override
    public List<Gateway> getGatewayByCodes(List<String> codes){
        Example example = new Example(Gateway.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for (String code : codes){
            criteria.orLike("company_code",code+"%");
        }

        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo("isdel",0);
        example.and(recordCriteria);

        return gatewayMapper.selectByExample(example);
    }

    public int getCodes(String scope,String scopeType,String scopeStr,List<String> codes){
        switch (scope) {
            case "0":
                return 0;
            case "1":
                if(scopeType !=null){
                    if(scopeType.equals("0")){
                        codes.add(scopeStr);
                    }else if(scopeType.equals("1")){
                        //创建example
                        Example example = new Example(Company.class);
                        //创建查询条件
                        Example.Criteria criteria = example.createCriteria();
                        //设置查询条件 多个andEqualTo串联表示 and条件查询
                        criteria.andLike("name","%"+scopeStr+"%");
                        List<Company> objects = companyMapper.selectByExample(example);
                        for(Company object : objects){
                            codes.add(object.getCode());
                        }
                    }
                }
                break;
            case "2":
                if(scopeType !=null){
                    if(scopeType.equals("0")){
                        codes.add(scopeStr);
                    }else if(scopeType.equals("1")){
                        //创建example
                        Example example = new Example(HotStation.class);
                        //创建查询条件
                        Example.Criteria criteria = example.createCriteria();
                        //设置查询条件 多个andEqualTo串联表示 and条件查询
                        criteria.andLike("name","%"+scopeStr+"%");
                        List<HotStation> objects = hotstationMapper.selectByExample(example);
                        for(HotStation object : objects){
                            codes.add(object.getCode());
                        }
                    }
                }
                break;
            case "3":
                if(scopeType !=null){
                    if(scopeType.equals("0")){
                        codes.add(scopeStr);
                    }else if(scopeType.equals("1")){
                        //创建example
                        Example example = new Example(Company.class);
                        //创建查询条件
                        Example.Criteria criteria = example.createCriteria();
                        //设置查询条件 多个andEqualTo串联表示 and条件查询
                        criteria.andLike("name",scopeStr);
                        List<Community> objects = communityMapper.selectByExample(example);
                        for(Community object : objects){
                            codes.add(object.getCode());
                        }
                    }
                }
                break;
            case "4":
                if(scopeType !=null){
                    if(scopeType.equals("0")){
                        codes.add(scopeStr);
                    }else if(scopeType.equals("1")){
                        //创建example
                        Example example = new Example(Building.class);
                        //创建查询条件
                        Example.Criteria criteria = example.createCriteria();
                        //设置查询条件 多个andEqualTo串联表示 and条件查询
                        criteria.andLike("name",scopeStr);
                        List<Building> objects = buildingMapper.selectByExample(example);
                        for(Building object : objects){
                            codes.add(object.getUnique_code());
                        }
                    }
                }
                break;
            case "5":
                if(scopeType !=null){
                    if(scopeType.equals("0")){
                        codes.add(scopeStr);
                    }else if(scopeType.equals("1")){
                        //创建example
                        Example example = new Example(HouseHolder.class);
                        //创建查询条件
                        Example.Criteria criteria = example.createCriteria();
                        //设置查询条件 多个andEqualTo串联表示 and条件查询
                        criteria.andLike("name","%"+scopeStr+"%");
                        List<HouseHolder> objects = houseHolderMapper.selectByExample(example);
                        for(HouseHolder object : objects){
                            codes.add(object.getCode());
                        }
                    }
                }
                break;
            default: return -1;
        }
        if(codes.size() == 0) return -2;
        return codes.size();
    }

    private String minCode(String code,int totalLen){
        if(code == null ){
            code = new String();
            for(int i = 0;i < totalLen;i++){
                code += "0";
            }
            return code;
        }
        int len = totalLen - code.length();
        for(int i = 0;i < len;i++){
            code += "0";
        }
        return code;
    }

    private String maxCode(String code,int totalLen){
        if(code == null ){
            code = new String();
            for(int i = 0;i < totalLen;i++){
                code += "9";
            }
            return code;
        }
        int len = totalLen - code.length();
        for(int i = 0;i < len;i++){
            code += "9";
        }
        return code;
    }
}
