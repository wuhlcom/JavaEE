/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月20日 下午2:12:28 * 
*/
package com.enviroment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.enviroment.common.errcode.ResultStatusCode;
import com.enviroment.db.dao.HeatDao;
import com.enviroment.db.entity.HeatEntity;
import com.enviroment.service.HeatService;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

@Service
public class HeatServiceImpl implements HeatService {
	public final static Logger logger = LoggerFactory.getLogger(HeatServiceImpl.class);

	@Autowired
	private HeatDao heatDao;

	@Override
	public List<HeatEntity> query(JSONObject jsonObj) {
		Integer type = jsonObj.getInteger("type");
		String dev_address = jsonObj.getString("dev_address");
		List<HeatEntity> powerRs = new ArrayList<>();
		
		HeatEntity record = new HeatEntity();
		record = JSON.parseObject(jsonObj.toJSONString(), HeatEntity.class);
		if (record.getPage() != null && record.getListRows() != null) {
			PageHelper.startPage(record.getPage(), record.getListRows());
		} else if (record.getPage() == null && record.getListRows() != null) {
			PageHelper.startPage(1, record.getListRows());
		} else if (record.getListRows() == null) {
			PageHelper.startPage(1, 0);
		}
		
		Example example = new Example(HeatEntity.class);
		Example.Criteria recordCriteria = example.createCriteria();
		example.setOrderByClause("created_at desc");
	    if (type == 1) {		
			recordCriteria.andLike("dev_address", "%" + dev_address + "%");		
		}
		powerRs = heatDao.selectByExample(example);
		return powerRs;
	}

	@Override
	public int add(JSONObject jsonObj) {	
		logger.debug("begin add data add........");
		int result = 0;
		HeatEntity record = new HeatEntity();
		record = JSON.parseObject(jsonObj.toJSONString(), HeatEntity.class);
		record.setCreated_at(System.currentTimeMillis() / 1000);
		result = heatDao.insertSelective(record);
		return result;
	}
	
	public int count(JSONObject jsonObj) {
		Integer type = jsonObj.getInteger("type");
		String dev_address = jsonObj.getString("dev_address");

		HeatEntity record = new HeatEntity();
		record = JSON.parseObject(jsonObj.toJSONString(), HeatEntity.class);

		Example example = new Example(HeatEntity.class);
		Example.Criteria recordCriteria = example.createCriteria();

		if (type == 0) {
		} else if (type == 1) {
			example.setOrderByClause("id asc");
			recordCriteria.andEqualTo("dev_address", dev_address);
		}
		return heatDao.selectCountByExample(example);
	}

	@Override
	public String result(JSONObject jsonObj) {
		JSONObject resultObj = new JSONObject();
		List<HeatEntity> result = this.query(jsonObj);

		int totalRows = this.count(jsonObj);
		resultObj.put("errcode", ResultStatusCode.SUCCESS.getCode());
		resultObj.put("totalRows", totalRows);
		resultObj.put("result", result);
		return resultObj.toString();
	}


}
