package com.dazk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.ErrCode;
import com.dazk.service.UpdateDeviceService;
import com.dazk.validator.JsonParamValidator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/7/21.
 */
@RestController
@RequestMapping("/device")
public class UpdateDeviceController {
    @Resource
    private UpdateDeviceService updateDeviceService;

    @RequestMapping(value = "/updateValve", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String updateValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);
            //数据校验
            if(!JsonParamValidator.valveVal(parameter)){
                //非法数据，返回错误码
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "参数错误");
                return resultObj.toJSONString();
            }

            //权限验证
            String token  = request.getParameter("token");
            //根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

            //数据入库，成功后返回.
            int res = updateDeviceService.updateValve(parameter);
            if(res == 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }else if(res == -1){
                resultObj.put("errcode", ErrCode.routineErr);
                resultObj.put("msg", "更新时程序出错");
                return resultObj.toJSONString();
            }else if(res == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "更新数据不存在");
                return resultObj.toJSONString();
            }else if(res > 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }
            resultObj.put("errcode", ErrCode.unknowErr);
            resultObj.put("msg", "未知错误");
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/updateHouseCalorimeter", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String updateHouseCalorimeter(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);
            //数据校验
            if(!JsonParamValidator.houseCalorimeterVal(parameter)){
                //非法数据，返回错误码
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "参数错误");
                return resultObj.toJSONString();
            }

            //权限验证
            String token  = request.getParameter("token");
            //根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

            //数据入库，成功后返回.
            int res = updateDeviceService.updateHouseCalorimeter(parameter);
            if(res == 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }else if(res == -1){
                resultObj.put("errcode", ErrCode.routineErr);
                resultObj.put("msg", "更新时程序出错");
                return resultObj.toJSONString();
            }else if(res == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "更新数据不存在");
                return resultObj.toJSONString();
            }else if(res > 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }
            resultObj.put("errcode", ErrCode.unknowErr);
            resultObj.put("msg", "未知错误");
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/updateConcentrator", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String updateConcentrator(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);
            //数据校验
            if(!JsonParamValidator.concentratorVal(parameter)){
                //非法数据，返回错误码
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "参数错误");
                return resultObj.toJSONString();
            }

            //权限验证
            String token  = request.getParameter("token");
            //根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

            //数据入库，成功后返回.
            int res = updateDeviceService.updateConcentrator(parameter);
            if(res == 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }else if(res == -1){
                resultObj.put("errcode", ErrCode.routineErr);
                resultObj.put("msg", "更新时程序出错");
                return resultObj.toJSONString();
            }else if(res == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "更新数据不存在");
                return resultObj.toJSONString();
            }else if(res > 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }
            resultObj.put("errcode", ErrCode.unknowErr);
            resultObj.put("msg", "未知错误");
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/updateGateway", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String updateGateway(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);
            //数据校验
            if(!JsonParamValidator.gatewayVal(parameter)){
                //非法数据，返回错误码
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "参数错误");
                return resultObj.toJSONString();
            }

            //权限验证
            String token  = request.getParameter("token");
            //根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

            //数据入库，成功后返回.
            int res = updateDeviceService.updateGateway(parameter);
            if(res == 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }else if(res == -1){
                resultObj.put("errcode", ErrCode.routineErr);
                resultObj.put("msg", "更新时程序出错");
                return resultObj.toJSONString();
            }else if(res == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "更新数据不存在");
                return resultObj.toJSONString();
            }else if(res > 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }
            resultObj.put("errcode", ErrCode.unknowErr);
            resultObj.put("msg", "未知错误");
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/updateBuildingValve", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String updateBuildingValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);
            //数据校验
            if(!JsonParamValidator.buildingValveVal(parameter)){
                //非法数据，返回错误码
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "参数错误");
                return resultObj.toJSONString();
            }

            //权限验证
            String token  = request.getParameter("token");
            //根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

            //数据入库，成功后返回.
            int res = updateDeviceService.updateBuildingValve(parameter);
            if(res == 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }else if(res == -1){
                resultObj.put("errcode", ErrCode.routineErr);
                resultObj.put("msg", "更新时程序出错");
                return resultObj.toJSONString();
            }else if(res == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "更新数据不存在");
                return resultObj.toJSONString();
            }else if(res > 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }
            resultObj.put("errcode", ErrCode.unknowErr);
            resultObj.put("msg", "未知错误");
            return resultObj.toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.toString());
            return resultObj.toJSONString();
        }
    }

    @RequestMapping(value = "/updateBuildingCalorimeter", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String updateBuildingCalorimeter(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);
            //数据校验
            if(!JsonParamValidator.buildingCalorimeterVal(parameter)){
                //非法数据，返回错误码
                resultObj.put("errcode", ErrCode.parameErr);
                resultObj.put("msg", "参数错误");
                return resultObj.toJSONString();
            }

            //权限验证
            String token  = request.getParameter("token");
            //根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

            //数据入库，成功后返回.
            int res = updateDeviceService.updateBuildingCalorimeter(parameter);
            if(res == 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }else if(res == -1){
                resultObj.put("errcode", ErrCode.routineErr);
                resultObj.put("msg", "更新时程序出错");
                return resultObj.toJSONString();
            }else if(res == 0){
                resultObj.put("errcode", ErrCode.noData);
                resultObj.put("msg", "更新数据不存在");
                return resultObj.toJSONString();
            }else if(res > 1){
                resultObj.put("errcode", ErrCode.success);
                return resultObj.toJSONString();
            }
            resultObj.put("errcode", ErrCode.unknowErr);
            resultObj.put("msg", "未知错误");
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
