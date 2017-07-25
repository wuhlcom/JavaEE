package com.dazk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.db.dao.*;
import com.dazk.db.model.*;
import com.dazk.service.QueryDeviceService;
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

    private final int houseCodelen = 16;

    private final int buildCodeLen = 10;

    @Override
    public List<HouseValve> queryValve(JSONObject obj) {
        String scope = obj.getString("scope");
        String scopeType = obj.getString("scopeType");
        String scopeStr = obj.getString("scopeStr");
        List<String> codes = new ArrayList<String>();

        int codeNum = getCodes(scope,scopeType,scopeStr,codes);

        if(codeNum == -1){
            return new ArrayList<HouseValve>();
        }
        HouseValve record = new HouseValve();
        record = JSON.parseObject(obj.toJSONString(), HouseValve.class);

        if (record.getPage() != null && record.getListRows() != null) {
            PageHelper.startPage(record.getPage(), record.getListRows());
        }

        Example example = new Example(HouseValve.class);
        //创建查询条件

        Example.Criteria codeCriteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for(int i = 0;i < codes.size();i++){
            codeCriteria.orBetween("house_code",minCode(codes.get(i),houseCodelen),maxCode(codes.get(i),houseCodelen));
        }
        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo(record).andEqualTo("isdel",0);

        example.and(recordCriteria);

        return houseValveMapper.selectByExample(example);
    }

    @Override
    public int queryValveCount(JSONObject obj) {
        String scope = obj.getString("scope");
        String scopeType = obj.getString("scopeType");
        String scopeStr = obj.getString("scopeStr");
        List<String> codes = new ArrayList<String>();

        int codeNum = getCodes(scope,scopeType,scopeStr,codes);

        if(codeNum == -1){
            return 0;
        }
        HouseValve record = new HouseValve();
        record = JSON.parseObject(obj.toJSONString(), HouseValve.class);

        Example example = new Example(HouseValve.class);
        //创建查询条件

        Example.Criteria codeCriteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for(int i = 0;i < codes.size();i++){
            codeCriteria.orBetween("house_code",minCode(codes.get(i),houseCodelen),maxCode(codes.get(i),houseCodelen));
        }
        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo(record).andEqualTo("isdel",0);

        example.and(recordCriteria);

        return houseValveMapper.selectCountByExample(example);
    }

    @Override
    public List<HouseCalorimeter> queryHouseCalorimeter(JSONObject obj) {
        String scope = obj.getString("scope");
        String scopeType = obj.getString("scopeType");
        String scopeStr = obj.getString("scopeStr");
        List<String> codes = new ArrayList<String>();

        int codeNum = getCodes(scope,scopeType,scopeStr,codes);

        if(codeNum == -1){
            return new ArrayList<HouseCalorimeter>();
        }
        HouseCalorimeter record = new HouseCalorimeter();
        record = JSON.parseObject(obj.toJSONString(), HouseCalorimeter.class);

        if (record.getPage() != null && record.getListRows() != null) {
            PageHelper.startPage(record.getPage(), record.getListRows());
        }

        Example example = new Example(HouseCalorimeter.class);
        //创建查询条件

        Example.Criteria codeCriteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for(int i = 0;i < codes.size();i++){
            codeCriteria.orBetween("house_code",minCode(codes.get(i),houseCodelen),maxCode(codes.get(i),houseCodelen));
        }
        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo(record).andEqualTo("isdel",0);

        example.and(recordCriteria);

        return houseCalorimeterMapper.selectByExample(example);
    }

    @Override
    public int queryHouseCalorimeterCount(JSONObject obj) {
        String scope = obj.getString("scope");
        String scopeType = obj.getString("scopeType");
        String scopeStr = obj.getString("scopeStr");
        List<String> codes = new ArrayList<String>();

        int codeNum = getCodes(scope,scopeType,scopeStr,codes);

        if(codeNum == -1){
            return 0;
        }
        HouseCalorimeter record = new HouseCalorimeter();
        record = JSON.parseObject(obj.toJSONString(), HouseCalorimeter.class);

        Example example = new Example(HouseCalorimeter.class);
        //创建查询条件

        Example.Criteria codeCriteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for(int i = 0;i < codes.size();i++){
            codeCriteria.orBetween("house_code",minCode(codes.get(i),houseCodelen),maxCode(codes.get(i),houseCodelen));
        }
        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo(record).andEqualTo("isdel",0);

        example.and(recordCriteria);

        return houseCalorimeterMapper.selectCountByExample(example);
    }

    @Override
    public List<Concentrator> queryConcentrator(JSONObject obj) {
        String scope = obj.getString("scope");
        String scopeType = obj.getString("scopeType");
        String scopeStr = obj.getString("scopeStr");
        List<String> codes = new ArrayList<String>();

        int codeNum = getCodes(scope,scopeType,scopeStr,codes);

        if(codeNum == -1){
            return new ArrayList<Concentrator>();
        }
        Concentrator record = new Concentrator();
        record = JSON.parseObject(obj.toJSONString(), Concentrator.class);

        if (record.getPage() != null && record.getListRows() != null) {
            PageHelper.startPage(record.getPage(), record.getListRows());
        }

        Example example = new Example(Concentrator.class);
        //创建查询条件

        Example.Criteria codeCriteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for(int i = 0;i < codes.size();i++){
            codeCriteria.orBetween("building_unique_code",minCode(codes.get(i),buildCodeLen),maxCode(codes.get(i),buildCodeLen));
        }
        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo(record).andEqualTo("isdel",0);

        example.and(recordCriteria);

        return concentratorMapper.selectByExample(example);
    }

    @Override
    public int queryConcentratorCount(JSONObject obj) {
        String scope = obj.getString("scope");
        String scopeType = obj.getString("scopeType");
        String scopeStr = obj.getString("scopeStr");
        List<String> codes = new ArrayList<String>();

        int codeNum = getCodes(scope,scopeType,scopeStr,codes);

        if(codeNum == -1){
            return 0;
        }
        Concentrator record = new Concentrator();
        record = JSON.parseObject(obj.toJSONString(), Concentrator.class);

        Example example = new Example(Concentrator.class);
        //创建查询条件

        Example.Criteria codeCriteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for(int i = 0;i < codes.size();i++){
            codeCriteria.orBetween("building_unique_code",minCode(codes.get(i),buildCodeLen),maxCode(codes.get(i),buildCodeLen));
        }
        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo(record).andEqualTo("isdel",0);

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

        record.setIsdel(0);

        return gatewayMapper.select(record);
    }

    @Override
    public int queryGatewayCount(JSONObject obj) {
        Gateway record = new Gateway();
        record = JSON.parseObject(obj.toJSONString(), Gateway.class);

        if (record.getPage() != null && record.getListRows() != null) {
            PageHelper.startPage(record.getPage(), record.getListRows());
        }
        record.setIsdel(0);
        return gatewayMapper.selectCount(record);
    }

    @Override
    public List<BuildingValve> queryBuildingValve(JSONObject obj) {
        String scope = obj.getString("scope");
        String scopeType = obj.getString("scopeType");
        String scopeStr = obj.getString("scopeStr");
        List<String> codes = new ArrayList<String>();

        int codeNum = getCodes(scope,scopeType,scopeStr,codes);

        if(codeNum == -1){
            return new ArrayList<BuildingValve>();
        }
        BuildingValve record = new BuildingValve();
        record = JSON.parseObject(obj.toJSONString(), BuildingValve.class);

        if (record.getPage() != null && record.getListRows() != null) {
            PageHelper.startPage(record.getPage(), record.getListRows());
        }

        Example example = new Example(BuildingValve.class);
        //创建查询条件

        Example.Criteria codeCriteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for(int i = 0;i < codes.size();i++){
            codeCriteria.orBetween("building_unique_code",minCode(codes.get(i),buildCodeLen),maxCode(codes.get(i),buildCodeLen));
        }
        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo(record).andEqualTo("isdel",0);

        example.and(recordCriteria);

        return buildingValveMapper.selectByExample(example);
    }

    @Override
    public int queryBuildingValveCount(JSONObject obj) {
        String scope = obj.getString("scope");
        String scopeType = obj.getString("scopeType");
        String scopeStr = obj.getString("scopeStr");
        List<String> codes = new ArrayList<String>();

        int codeNum = getCodes(scope,scopeType,scopeStr,codes);

        if(codeNum == -1){
            return 0;
        }
        BuildingValve record = new BuildingValve();
        record = JSON.parseObject(obj.toJSONString(), BuildingValve.class);

        Example example = new Example(BuildingValve.class);
        //创建查询条件

        Example.Criteria codeCriteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for(int i = 0;i < codes.size();i++){
            codeCriteria.orBetween("building_unique_code",minCode(codes.get(i),buildCodeLen),maxCode(codes.get(i),buildCodeLen));
        }
        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo(record).andEqualTo("isdel",0);

        example.and(recordCriteria);

        return buildingValveMapper.selectCountByExample(example);
    }

    @Override
    public List<BuildingCalorimeter> queryBuildingCalorimeter(JSONObject obj) {
        String scope = obj.getString("scope");
        String scopeType = obj.getString("scopeType");
        String scopeStr = obj.getString("scopeStr");
        List<String> codes = new ArrayList<String>();

        int codeNum = getCodes(scope,scopeType,scopeStr,codes);

        if(codeNum == -1){
            return new ArrayList<BuildingCalorimeter>();
        }
        BuildingCalorimeter record = new BuildingCalorimeter();
        record = JSON.parseObject(obj.toJSONString(), BuildingCalorimeter.class);

        if (record.getPage() != null && record.getListRows() != null) {
            PageHelper.startPage(record.getPage(), record.getListRows());
        }

        Example example = new Example(BuildingCalorimeter.class);
        //创建查询条件

        Example.Criteria codeCriteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for(int i = 0;i < codes.size();i++){
            codeCriteria.orBetween("building_unique_code",minCode(codes.get(i),buildCodeLen),maxCode(codes.get(i),buildCodeLen));
        }
        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo(record).andEqualTo("isdel",0);

        example.and(recordCriteria);

        return buildingCalorimeterMapper.selectByExample(example);
    }

    @Override
    public int queryBuildingCalorimeterCount(JSONObject obj) {
        String scope = obj.getString("scope");
        String scopeType = obj.getString("scopeType");
        String scopeStr = obj.getString("scopeStr");
        List<String> codes = new ArrayList<String>();

        int codeNum = getCodes(scope,scopeType,scopeStr,codes);

        if(codeNum == -1){
            return 0;
        }
        BuildingCalorimeter record = new BuildingCalorimeter();
        record = JSON.parseObject(obj.toJSONString(), BuildingCalorimeter.class);

        Example example = new Example(BuildingCalorimeter.class);
        //创建查询条件

        Example.Criteria codeCriteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for(int i = 0;i < codes.size();i++){
            codeCriteria.orBetween("building_unique_code",minCode(codes.get(i),buildCodeLen),maxCode(codes.get(i),buildCodeLen));
        }
        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo(record).andEqualTo("isdel",0);

        example.and(recordCriteria);

        return buildingCalorimeterMapper.selectCountByExample(example);
    }

    private int getCodes(String scope,String scopeType,String scopeStr,List<String> codes){
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
        if(codes.size() == 0) return -1;
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
