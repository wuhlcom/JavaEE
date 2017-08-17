package com.dazk.service;

import com.dazk.db.model.SystemParameter;

import java.util.List;

/**
 * Created by Administrator on 2017/8/6.
 */
public interface SystemParameterService {
    List<SystemParameter> getByName(String name);

    List<SystemParameter> getAll();
}
