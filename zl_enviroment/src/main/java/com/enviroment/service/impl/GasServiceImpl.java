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
import com.enviroment.db.dao.GasDao;
import com.enviroment.db.entity.GasEntity;
import com.enviroment.service.GasService;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

@Service
public class GasServiceImpl implements GasService {
	public final static Logger logger = LoggerFactory.getLogger(GasServiceImpl.class);

	@Autowired
	private GasDao gasDao;

	@Override
	public List<GasEntity> query(JSONObject jsonObj) {
		Integer type = jsonObj.getInteger("type");
		String dev_address = jsonObj.getString("dev_address");
		List<GasEntity> result = new ArrayList<>();
		
		GasEntity record = new GasEntity();
		record = JSON.parseObject(jsonObj.toJSONString(), GasEntity.class);
		if (record.getPage() != null && record.getListRows() != null) {
			PageHelper.startPage(record.getPage(), record.getListRows());
		} else if (record.getPage() == null && record.getListRows() != null) {
			PageHelper.startPage(1, record.getListRows());
		} else if (record.getListRows() == null) {
			PageHelper.startPage(1, 0);
		}
		
		Example example = new Example(GasEntity.class);
		Example.Criteria recordCriteria = example.createCriteria();
		example.setOrderByClause("created_at desc");	
		if (type == 1) {		
			recordCriteria.andLike("dev_address", "%" + dev_address + "%");		
		}
		result = gasDao.selectByExample(example);
		return result;
	}

	@Override
	public int add(JSONObject jsonObj) {		
		logger.debug("begin add  data add........");
		int result = 0;
		GasEntity record = new GasEntity();
		record = JSON.parseObject(jsonObj.toJSONString(), GasEntity.class);	
		record.setCreated_at(System.currentTimeMillis() / 1000);
		result = gasDao.insertSelective(record);
		return result;
	}
	
	public int count(JSONObject jsonObj) {
		Integer type = jsonObj.getInteger("type");
		String dev_address = jsonObj.getString("dev_address");

		GasEntity record = new GasEntity();
		record = JSON.parseObject(jsonObj.toJSONString(), GasEntity.class);

		Example example = new Example(GasEntity.class);
		Example.Criteria recordCriteria = example.createCriteria();

		if (type == 0) {
		} else if (type == 1) {
			example.setOrderByClause("id asc");
			recordCriteria.andEqualTo("dev_address", dev_address);
		}
		return gasDao.selectCountByExample(example);
	}

	@Override
	public String result(JSONObject jsonObj) {
		JSONObject resultObj = new JSONObject();
		List<GasEntity> result = this.query(jsonObj);	
		int totalRows = this.count(jsonObj);
		resultObj.put("errcode", ResultStatusCode.SUCCESS.getCode());
		resultObj.put("totalRows", totalRows);
		resultObj.put("result", result);
		return resultObj.toString();
	}


}
