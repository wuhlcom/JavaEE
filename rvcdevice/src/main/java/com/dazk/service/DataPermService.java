package com.dazk.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 */
public interface DataPermService {
    List<String> permFilter(Integer userid,List<String> codes,Integer datatype);

    List<String> getAll(Integer userid,Integer datatype);

    int userPerm(List<String> codes, String token, JSONObject parameter, JSONObject resultObj);

    int getUserid(String token,JSONObject resultObj);

    List<String> getCodesScope(String type,JSONObject pramter,JSONObject resultObj);
}
