package com.dazk.service.impl;

import com.dazk.db.dao.HouseValveMapper;
import com.dazk.db.dao.SystemParameterMapper;
import com.dazk.db.model.HouseHolder;
import com.dazk.db.model.SystemParameter;
import com.dazk.service.SystemParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by Administrator on 2017/8/6.
 */
@Service
public class SystemParameterServiceImpl implements SystemParameterService{
    @Autowired
    private SystemParameterMapper systemParameterMapper;

    @Override
    public List<SystemParameter> getByName(String name) {
        SystemParameter record = new SystemParameter();
        record.setName(name);
        record.setIsdel(0);
        return systemParameterMapper.select(record);
    }

    @Override
    public List<SystemParameter> getAll() {
        SystemParameter record = new SystemParameter();
        record.setIsdel(0);
        return systemParameterMapper.select(record);
    }
}
