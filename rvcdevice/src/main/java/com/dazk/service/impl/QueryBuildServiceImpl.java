package com.dazk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.db.dao.*;
import com.dazk.db.model.*;
import com.dazk.service.QueryBuildService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
@Service
public class QueryBuildServiceImpl implements QueryBuildService {
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
    public List<HouseHolder> queryHouseHolder(List<String> codes,String name,Integer page,Integer listRows) {

        if (page != null && listRows != null) {
            PageHelper.startPage(page, listRows);
        }
        Example example = new Example(HouseHolder.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for (String code : codes){
            criteria.orLike("code",code+"%");
        }

        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo("isdel",0);
        if(name != null){
            criteria.andLike("name","%"+name+"%");
        }
        example.and(recordCriteria);
        return houseHolderMapper.selectByExample(example);
    }

    @Override
    public int queryHouseHolderCount(List<String> codes) {
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
        return houseHolderMapper.selectCountByExample(example);
    }

    @Override
    public HouseHolder geHuseHolder(HouseHolder record) {
        return houseHolderMapper.selectOne(record);
    }

    @Override
    public HouseHolder geHuseHolderByCode(String code){
        HouseHolder record = new HouseHolder();
        record.setCode(code);record.setIsdel(0);
        return houseHolderMapper.selectOne(record);
    }

    @Override
    public Building getBuilding(Building record) {
        return buildingMapper.selectOne(record);
    }

    @Override
    public List<Building> getBuildingByCodes(List<String> codes) {
        Example example = new Example(Building.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for (String code : codes){
            criteria.orLike("building_unique_code",code+"%");
        }

        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo("isdel",0);
        example.and(recordCriteria);

        return buildingMapper.selectByExample(example);
    }

    @Override
    public List<Company> getCompanyByCodes(List<String> codes) {
        Example example = new Example(Company.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        for (String code : codes){
            criteria.orLike("code",code+"%");
        }

        Example.Criteria recordCriteria = example.createCriteria();
        recordCriteria.andEqualTo("isdel",0);
        example.and(recordCriteria);

        return companyMapper.selectByExample(example);
    }

    @Override
    public List<Company> getCompanyByCode(String code) {
        Example example = new Example(Company.class);
        //创建查询条件
        Example.Criteria criteria = example.createCriteria();
        //设置查询条件 多个andEqualTo串联表示 and条件查询
        criteria.andEqualTo("code",code).andEqualTo("isdel",0);
        return companyMapper.selectByExample(example);
    }
}
