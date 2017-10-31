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
import com.enviroment.db.dao.WaterDao;
import com.enviroment.db.entity.WaterEntity;
import com.enviroment.service.WaterService;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

@Service
public class WaterServiceImpl implements WaterService {
	public final static Logger logger = LoggerFactory.getLogger(WaterServiceImpl.class);

	@Autowired
	private WaterDao waterDao;

	@Override
	public List<WaterEntity> query(JSONObject jsonObj) {
		Integer type = jsonObj.getInteger("type");
		String dev_address = jsonObj.getString("dev_address");
		List<WaterEntity> waterRs = new ArrayList<>();
		
		WaterEntity record = new WaterEntity();
		record = JSON.parseObject(jsonObj.toJSONString(), WaterEntity.class);
		if (record.getPage() != null && record.getListRows() != null) {
			PageHelper.startPage(record.getPage(), record.getListRows());
		} else if (record.getPage() == null && record.getListRows() != null) {
			PageHelper.startPage(1, record.getListRows());
		} else if (record.getListRows() == null) {
			PageHelper.startPage(1, 0);
		}
		
		Example example = new Example(WaterEntity.class);
		Example.Criteria recordCriteria = example.createCriteria();
		example.setOrderByClause("created_at desc");
		 if (type == 1) {		
			recordCriteria.andLike("dev_address", "%" + dev_address + "%");		
		}
		waterRs = waterDao.selectByExample(example);
		return waterRs;
	}

	@Override
	public int add(JSONObject jsonObj) {		
		logger.debug("begin add data add........");
		int result = 0;
		WaterEntity record = new WaterEntity();
		record = JSON.parseObject(jsonObj.toJSONString(), WaterEntity.class);	
		record.setCreated_at(System.currentTimeMillis() / 1000);
		result = waterDao.insertSelective(record);
		return result;
	}
	
	@Override
	public int count(JSONObject jsonObj) {
		Integer type = jsonObj.getInteger("type");
		String dev_address = jsonObj.getString("dev_address");

		WaterEntity record = new WaterEntity();
		record = JSON.parseObject(jsonObj.toJSONString(), WaterEntity.class);

		Example example = new Example(WaterEntity.class);
		Example.Criteria recordCriteria = example.createCriteria();

		if (type == 0) {
		} else if (type == 1) {
			example.setOrderByClause("id asc");
			recordCriteria.andEqualTo("dev_address", dev_address);
		}
		return waterDao.selectCountByExample(example);
	}

	@Override
	public String result(JSONObject jsonObj) {
		JSONObject resultObj = new JSONObject();
		List<WaterEntity> result = this.query(jsonObj);	
		int totalRows = this.count(jsonObj);
		resultObj.put("errcode", ResultStatusCode.SUCCESS.getCode());
		resultObj.put("totalRows", totalRows);
		resultObj.put("result", result);
		return resultObj.toString();
	}


}
