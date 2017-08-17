package com.dazk.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2017/8/7.
 */
public interface TokenService {
    public int getUserid(String token,JSONObject resultObj);
}
