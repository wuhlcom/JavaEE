package com.dazk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.ErrCode;
import com.dazk.common.util.JsonUtil;
import com.dazk.common.util.StrUtil;
import com.dazk.db.model.*;
import com.dazk.service.DataPermService;
import com.dazk.service.QueryBuildService;
import com.dazk.service.QueryDeviceService;
import com.dazk.service.StealHeatService;
import com.dazk.validator.JsonParamValidator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/13.
 */
@RestController
@RequestMapping("/selectcount")
public class SelectCountController {
    @Resource
    private QueryBuildService queryBuildService;

    @Resource
    private QueryDeviceService queryDeviceService;

    @Resource
    private DataPermService dataPermService;

    @Resource
    private StealHeatService stealHeatService;

    @RequestMapping(value = "/stealheat", method= RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String stealHeat(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody, @RequestHeader(value = "token") String token) {
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);
            JsonUtil.filterNull(parameter);
            List<String> codes = new ArrayList<String>();
            //数据校验
            if(!JsonParamValidator.stealHeatVal(parameter,resultObj)){
                return resultObj.toJSONString();
            }

            //用户校验及权限验证
            int isLiberty  = dataPermService.userPerm(codes,token,parameter,resultObj);

            if(isLiberty < 0){
                return resultObj.toJSONString();
            }
            //数据查询，成功后返回.
            Integer page = JsonParamValidator.str2Digits(parameter.getString("page"));
            Integer listRows = JsonParamValidator.str2Digits(parameter.getString("listRows"));

            Double wit_min = null;
            if(parameter.getString("wit_min") != null){wit_min = Double.parseDouble(parameter.getString("wit_min"));}
            Double wit_max = null;
            if(parameter.getString("wit_max") != null){wit_max = Double.parseDouble(parameter.getString("wit_max"));}
            Double wot_min = null;
            if(parameter.getString("wot_min") != null){wot_min = Double.parseDouble(parameter.getString("wot_min"));}
            Double wot_max = null;
            if(parameter.getString("wot_max") != null){wot_max = Double.parseDouble(parameter.getString("wot_max"));}
            String condition1 = null;
            if(parameter.getString("condition1") != null){condition1 = parameter.getString("condition1");}
            String condition2 = null;
            if(parameter.getString("condition2") != null){condition2 = parameter.getString("wot_max");}
            List<StealHeatData> result = new ArrayList<StealHeatData>();
            //获取所有可盗热列表
            List<StealHeatData> stealHeatDatas = stealHeatService.getAll(wit_min,wit_max,wot_min,wot_max,condition1,condition2);
            for(int i = 0;i < stealHeatDatas.size();i++){
                if(!StrUtil.hasPerIn(stealHeatDatas.get(i).getHouse_code(),codes)){
                    stealHeatDatas.remove(i);
                }
            }
            //查询住户
            List<HouseHolder> houseHolders = queryBuildService.queryHouseHolder(codes,null,null,null);
            Map<String,HouseHolder> houseHoldermap = new HashMap<String,HouseHolder>();
            for(HouseHolder obj: houseHolders){
                houseHoldermap.put(obj.getCode(),obj);
            }
            for(int i = 0;i < stealHeatDatas.size();i++){
                StealHeatData stealHeatData = stealHeatDatas.get(i);
                HouseHolder houseHolder = houseHoldermap.get(stealHeatData.getHouse_code());
                stealHeatData.setId_card(houseHolder.getId_card());
                stealHeatData.setMes_status(houseHolder.getMes_status());
                stealHeatData.setName(houseHolder.getName());
            }
            Integer totalRows = stealHeatDatas.size();

            if(page != null && listRows != null){
                if(page < 1) page = 1;if(listRows < 1) listRows = 10;
                int start = (page-1)*listRows;
                int end = (page-1)*listRows + listRows > totalRows?totalRows:(page-1)*listRows + listRows;
                for(int i = ((page-1)*listRows);i < end;i++){
                    result.add(stealHeatDatas.get(i));
                }
            }

            for(int i = 0;i < result.size();i++){
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
            }
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
