package com.dazk.refectest;

import com.alibaba.fastjson.JSON;
import com.dazk.db.model.Concentrator;
import com.dazk.db.param.ConcentratorDetailParam;

/**
 * Created by Administrator on 2017/8/15.
 */
public class Parent2Sbu {
    public static void main(String[] args) {
        Concentrator a = new Concentrator();
        a.setAddress("123");
        ConcentratorDetailParam b = new ConcentratorDetailParam(a,"1","2");
        System.out.println(JSON.toJSON(b));
    }
}
