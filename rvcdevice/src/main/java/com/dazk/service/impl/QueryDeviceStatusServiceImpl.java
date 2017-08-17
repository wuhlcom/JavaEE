package com.dazk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.db.dao.*;
import com.dazk.db.model.*;
import com.dazk.service.QueryDeviceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */
@Service
public class QueryDeviceStatusServiceImpl implements QueryDeviceStatusService{
    @Autowired
    private DeviceStatusMapper deviceStatusMapper;

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

    @Override
    public List<HouseValveData> queryValveStatus(JSONObject obj) {
        String scope = obj.getString("scope");
        String scopeType = obj.getString("scopeType");
        String scopeStr = obj.getString("scopeStr");
        List<String> codes = new ArrayList<String>();

        int codeNum = getCodes(scope,scopeType,scopeStr,codes);
        if(codeNum == -1){
            return new ArrayList<HouseValveData>();
        }
        for(int i = 0;i < codes.size();i++){
            codes.set(i,codes.get(i)+"%");
        }
        return deviceStatusMapper.findLatestHouseValveStatus(codes);
    }

    @Override
    public int queryValveStatusCount(JSONObject obj) {
        String scope = obj.getString("scope");
        String scopeType = obj.getString("scopeType");
        String scopeStr = obj.getString("scopeStr");
        List<String> codes = new ArrayList<String>();

        int codeNum = getCodes(scope,scopeType,scopeStr,codes);

        if(codeNum == -1){
            return 0;
        }
        for(int i = 0;i < codes.size();i++){
            codes.set(i,codes.get(i)+"%");
        }
        return deviceStatusMapper.findLatestHouseValveStatusCount(codes);
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
}
