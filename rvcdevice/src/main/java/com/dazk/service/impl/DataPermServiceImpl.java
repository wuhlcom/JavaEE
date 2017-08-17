package com.dazk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.ErrCode;
import com.dazk.common.SystemConfig;
import com.dazk.common.util.HttpRequest;
import com.dazk.common.util.RegexUtil;
import com.dazk.db.dao.CompanyMapper;
import com.dazk.db.dao.DatePermissionMapper;
import com.dazk.service.AddDeviceService;
import com.dazk.service.DataPermService;
import com.dazk.service.QueryBuildService;
import com.dazk.service.QueryDeviceService;
import com.dazk.validator.JsonParamValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 */
@Service
public class DataPermServiceImpl implements DataPermService {
    @Autowired
    private DatePermissionMapper datePermissionMapper;

    @Resource
    private DataPermService dataPermService;

    @Resource
    private QueryDeviceService queryDeviceService;


    @Override
    public List<String> permFilter(Integer userid,List<String> codes,Integer datatype){
        //所有前缀为codes的code升序排列后的数组
        List<String> preCodes = datePermissionMapper.getPerm(userid,codes,datatype);
        //去掉比后面短的code。即获取所有叶子节点。
        List<String> resultCodes = new ArrayList<String>();
        if(preCodes.size() <= 1) return preCodes;
        for(int i = 1;i < preCodes.size();i++){
            if(preCodes.get(i-1).length() >= preCodes.get(i).length()){
                resultCodes.add(preCodes.get(i-1));
            }
        }
        return resultCodes;
    }

    @Override
    public List<String> getAll(Integer userid,Integer datatype){
        //所有前缀为codes的code升序排列后的数组
        List<String> preCodes = datePermissionMapper.getPerm(userid,null,datatype);
        //去掉比后面短的code。即获取所有叶子节点。
        List<String> resultCodes = new ArrayList<String>();
        if(preCodes.size() <= 1) return preCodes;
        for(int i = 1;i < preCodes.size();i++){
            if(preCodes.get(i-1).length() >= preCodes.get(i).length()){
                resultCodes.add(preCodes.get(i-1));
            }
        }
        return resultCodes;
    }



    @Override
    public int userPerm(List<String> codes,String token,JSONObject parameter,JSONObject resultObj){
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
        //通过权限过滤得到小区code列表  codes,还需根据前缀进行过滤
        String scope = null;
        String scopeStr = null;
        String company = parameter.getString("company");
        String hotstation = parameter.getString("hotstation");
        String community = parameter.getString("community");
        String building = parameter.getString("building");
        String householder = parameter.getString("householder");
        String scopeType = parameter.getString("scopeType");
        if(scopeType != null){
            if(company != null && (JsonParamValidator.isCompanyCode(company) && scopeType.equals("0") || scopeType.equals("1"))){
                scope = "1";scopeStr = company;
            }
            if(hotstation != null && (JsonParamValidator.isHotstationCode(hotstation) && scopeType.equals("0") || scopeType.equals("1"))){
                scope = "2";scopeStr = hotstation;
            }
            if(community != null && (JsonParamValidator.isCommunityCode(community) && scopeType.equals("0") || scopeType.equals("1"))){
                scope = "3";scopeStr = community;
            }
            if(building != null && (JsonParamValidator.isBuildingUniqueCode(building) && scopeType.equals("0") || scopeType.equals("1"))){
                scope = "4";scopeStr = building;
            }
            if(householder != null && (JsonParamValidator.isHouseCode(householder) && scopeType.equals("0") || scopeType.equals("1"))){
                scope = "5";scopeStr = householder;
            }
        }
        if(scopeStr == null){
            scope = "0";
        }

        int codeNum = queryDeviceService.getCodes(scope,scopeType,scopeStr,codes);
        if(codeNum == -1){
            resultObj.put("errcode", ErrCode.parameErr);
            resultObj.put("msg","scope参数错误");
            return -1;
        }else if(codeNum == -2){
            resultObj.put("errcode", ErrCode.parameErr);
            resultObj.put("msg","没有相应数据");
            return 0;
        }
        //权限过滤后的codes
        codes = dataPermService.permFilter(Integer.parseInt(userid),codes,SystemConfig.dataTypeQuery);
        return 0;
    }

    @Override
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

    @Override
    public List<String> getCodesScope(String type,JSONObject pramter,JSONObject resultObj){
        String code = pramter.getString(type);
        List<String> codes = new ArrayList<String>();
        if(code == null || code.equals("") || code.length() < 8){
            resultObj.put("errcode", ErrCode.parameErr);
            resultObj.put("msg","编号异常");
            return codes;
        }
        codes.add(code.substring(0,3));
        codes.add(code.substring(0,4));
        codes.add(code.substring(0,8));
        return codes;
    }
}
