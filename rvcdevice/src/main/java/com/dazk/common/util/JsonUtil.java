package com.dazk.common.util;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/10.
 */
public class JsonUtil {
    public static void filterNull(JSONObject jsonObj){
        List<String> keys = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
            if(entry.getValue().equals("")){
                keys.add(entry.getKey());
            }
        }
        for(String key : keys){
            jsonObj.remove(key);
        }
    }
}
