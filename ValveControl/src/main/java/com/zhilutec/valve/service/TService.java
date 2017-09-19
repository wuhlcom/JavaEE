package com.zhilutec.valve.service;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhilutec.valve.bean.models.TblHouseHolderData;
import com.zhilutec.valve.bean.params.HouseHolderDataParams;
import com.zhilutec.valve.bean.params.HouseHolderDataParamSpec;
import com.zhilutec.valve.repository.HouseHolderDataRepo;
import com.zhilutec.valve.util.error.ErrorCode;

import com.zhilutec.valve.util.error.GlobalErrorException;
import com.zhilutec.valve.util.validator.RegexUtil;
import com.zhilutec.valve.util.validator.StealingCondition;

@Service
public class TService {

	public final static Logger logger = LoggerFactory.getLogger(HouseHolderDataService.class);

	@Autowired
	private HouseHolderDataRepo houseHolderDataRepo;

	
	// 盗热数据查询
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TblHouseHolderData> heating(String requestBody) throws GlobalErrorException {	
		HouseHolderDataParams params = new HouseHolderDataParams();
		params = JSON.parseObject(requestBody,HouseHolderDataParams.class);
		List rs = new ArrayList();	
   
		Specification<TblHouseHolderData> timeRange = HouseHolderDataParamSpec.timeRange(params);
		Specification<TblHouseHolderData> conditions = HouseHolderDataParamSpec.conditions(params,0);
		try {
			rs = houseHolderDataRepo.findAll(conditions);
		} catch (Exception e) {
			logger.error("failed to get records by condition", e);
			throw new GlobalErrorException(ErrorCode.DB_ERROR);
		}

		return rs;
	}

}
