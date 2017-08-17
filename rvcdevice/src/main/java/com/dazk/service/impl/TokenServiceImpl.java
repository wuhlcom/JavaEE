package com.dazk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.ErrCode;
import com.dazk.common.SystemConfig;
import com.dazk.common.util.HttpRequest;
import com.dazk.common.util.RegexUtil;
import com.dazk.service.TokenService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/8/7.
 */
@Service
public class TokenServiceImpl implements TokenService {
    public int getUserid(String token,JSONObject resultObj){
        if(token == null || token.equals("")){
            resultObj.put("errcode", ErrCode.tokenErr);
            resultObj.put("msg","token异常");
            return -1;
        }

        //根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
        JSONObject tokenObj = new JSONObject();
        tokenObj.put("token",token);
        String loginRes = HttpRequest.sendJsonPost(SystemConfig.isloginUrl, tokenObj.toJSONString());
        JSONObject userInfo = JSON.parseObject(loginRes);
        if(userInfo.getString("status") == null || !userInfo.getString("status").equals("1")){
            resultObj.put("errcode", ErrCode.loginErr);
            resultObj.put("msg","未登录");
            return -1;
        }


        String userid = userInfo.getString("userid");
        if(userid == null || !RegexUtil.isDigits(userid)){
            resultObj.put("errcode", ErrCode.userErr);
            resultObj.put("msg","用户id异常");
            return -1;
        }
        return Integer.parseInt(userid);
    }
}
