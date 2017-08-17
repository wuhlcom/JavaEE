package com.dazk.service;

import com.alibaba.fastjson.JSONObject;
import com.dazk.db.model.Building;
import com.dazk.db.model.Company;
import com.dazk.db.model.HouseHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
public interface QueryBuildService {
    public List<HouseHolder> queryHouseHolder(List<String> codes,String name,Integer page,Integer listRows);

    public int queryHouseHolderCount(List<String> codes);

    public HouseHolder geHuseHolder(HouseHolder record);

    public HouseHolder geHuseHolderByCode(String code);

    public Building getBuilding(Building record);

    public List<Building> getBuildingByCodes(List<String> codes);

    public List<Company> getCompanyByCodes(List<String> codes);

    public List<Company> getCompanyByCode(String codes);
}
