package com.dazk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.ErrCode;
import com.dazk.common.util.JsonUtil;
import com.dazk.db.model.HouseValveData;
import com.dazk.service.QueryDeviceStatusService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */
@RestController
@RequestMapping("/device")
public class QueryDeviceStatusController {

    @Resource
    private QueryDeviceStatusService queryDeviceStatusService;

    @RequestMapping(value = "/queryValveStatus", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String queryValveStatus(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);
            JsonUtil.filterNull(parameter);
            //数据校验

            //权限验证
            String token  = request.getParameter("token");
            //根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

            //数据查询，成功后返回.
            List<HouseValveData> result = queryDeviceStatusService.queryValveStatus(parameter);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
            }
            int totalRows = queryDeviceStatusService.queryValveStatusCount(parameter);
            resultObj.put("errcode", ErrCode.success);
            resultObj.put("totalRows",totalRows);
            resultObj.put("result",result);
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }
}
