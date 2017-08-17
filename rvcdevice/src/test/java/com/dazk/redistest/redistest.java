package com.dazk.redistest;

import com.dazk.db.model.HouseValve;
import com.dazk.db.model.HouseValveData;
import com.dazk.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/8/2.
 */
@RestController
public class redistest {
    @Autowired
    private static ValueOperations<String, Object> valueOperations;

    @Autowired
    private static HashOperations<String, String, Object> hashOperations;

    @Autowired
    private static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private static RedisService redisService;

    public static void main(String[] args) {
        HouseValve valve = new HouseValve();
        valve.setHouse_code("1011");
        redisService.hmSet("HouseValve","011",new HouseValveData(valve));


    }
}
