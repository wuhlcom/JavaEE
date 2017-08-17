package com.dazk.jsontest;

import com.alibaba.fastjson.JSON;
import com.dazk.db.param.BatchStatusListParam;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/8/12.
 */
public class Json2lcass {

    public static void main(String[] args) {
        Gson gs = new Gson();
        // TODO Auto-generated method stub
        String str = "{\"data\":[{\"key\":\"87654321\",\"value\":{\"comm_type\":1,\"dtu_code\":\"1234567\",\"comm_address\":\"87654321\",\"collect_time\":1500000000,\"opening\":99}},{\"key\":\"4786e6ed00450032\",\"value\":{\"comm_type\":1,\"dtu_code\":\"1234567\",\"comm_address\":\"87654322\",\"collect_time\":1500000000,\"opening\":10}},{\"key\":\"87654323\",\"value\":{}}]}";
        //BatchStatusListParam batchStatusListParam = JSON.parseObject(str, BatchStatusListParam.class) ;
        BatchStatusListParam batchStatusListParam = gs.fromJson(str, BatchStatusListParam.class);//把JSON字符串转为对象

        System.out.println(JSON.toJSON(batchStatusListParam));
    }
}
