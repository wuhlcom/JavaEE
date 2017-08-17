package com.dazk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.dazk.db.dao.HouseValveMapper;
import com.dazk.db.model.*;
import com.dazk.service.DataPermService;
import com.dazk.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private RedisService redisService;

    @Autowired
    private HouseValveMapper houseValveMapper;

    @Resource
    private DataPermService dataPermService;

    @RequestMapping(value = "/redis", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public void statepush(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        try{
            StringBuffer sb = new StringBuffer();
            redisService.set("str", "str");
            sb.append("str=").append(redisService.get("str").toString()).append(",");
            redisService.hmSet("hmset","key","val");
            sb.append("hmset=").append(redisService.hmGet("hmset","key")).append(",");
            redisService.lPush("list","val");
            sb.append("list=").append(redisService.lRange("list",0,1).toString()).append(",");
            redisService.add("set","val");
            sb.append("set=").append(redisService.setMembers("set").toString()).append(",");
            redisService.zAdd("zset","val1",1);
            redisService.zAdd("zset","val2",2);
            sb.append("zset=").append(redisService.rangeByScore("zset",1,2)).append(",");
            System.out.println(sb.toString());


            HouseValve valve = new HouseValve();
            valve.setHouse_code("100011");
            //redisService.set("1011",valve);
            //HouseValve aa = (HouseValve)redisService.get("1011");


            HouseValve houseValveRecord = new HouseValve();
            houseValveRecord.setIsdel(0);
            List<HouseValve> houseValveList = houseValveMapper.select(houseValveRecord);

            for (HouseValve houseValve : houseValveList){
                redisService.hmSet("aa","100","111");

            }
            for (HouseValve houseValve : houseValveList){
                String aa = (String)redisService.hmGet("aa","100");
                System.out.println(JSON.toJSONString(aa));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/perm", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public void perm(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        List<String> codes = new ArrayList<String>();
        codes.add("001");
    }

}
