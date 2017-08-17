package com.dazk.common.util;

import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.base.select.SelectCountMapper;
import tk.mybatis.mapper.common.base.select.SelectMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * Created by Administrator on 2017/7/23.
 */
public interface BuildMapper<T> extends Mapper<T>, MySqlMapper<T> ,SelectMapper<T> ,SelectCountMapper<T> ,ExampleMapper<T>,InsertListMapper<T> {
}
