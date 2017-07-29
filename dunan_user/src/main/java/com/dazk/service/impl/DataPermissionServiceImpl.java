/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月28日 下午3:03:26 * 
*/
package com.dazk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.db.dao.DataPermissionMapper;
import com.dazk.db.model.DataPermission;
import com.dazk.db.model.DataPermission;
import com.dazk.service.DataPermissionService;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

@Service
public class DataPermissionServiceImpl implements DataPermissionService {

	@Autowired
	private DataPermissionMapper dataPermiMapper;

	@Override
	public int addDataPermi(JSONObject obj) {
		DataPermission record = new DataPermission();
		Long id = obj.getLong("id");
		Long user_id = obj.getLong("user_id");
		String company_code = obj.getString("company_code");

		record.setId(id);
		record.setUser_id(user_id);
		record.setCompany_code(company_code);
		int exist = dataPermiMapper.selectCount(record);

		if (exist > 0) {
			return -1;
		}

		record = JSON.parseObject(obj.toJSONString(), DataPermission.class);
		return dataPermiMapper.insertSelective(record);
	}

	@Override
	public int delDataPermi(JSONObject obj) {
		DataPermission record = new DataPermission();
		Long id = obj.getLong("id");
		Long user_id = obj.getLong("user_id");
		String company_code = obj.getString("company_code");
		String hotstation_code = obj.getString("hotstation_code");
		String community_code = obj.getString("community_code");

		record.setId(id);
		record.setUser_id(user_id);
		record.setCompany_code(company_code);

		int exist = dataPermiMapper.selectCount(record);
		if (exist != 0) {
			return 1;
		}

		// 创建example
		Example example = new Example(DataPermission.class);
		// 创建查询条件
		Example.Criteria criteria = example.createCriteria();
		// 设置查询条件 多个andEqualTo串联表示 and条件查询

		try {
			if (hotstation_code != null && community_code == null) {
				criteria.andEqualTo("id", id).andEqualTo("company_code", company_code).andEqualTo("user_id", user_id)
						.andEqualTo("hotstation_code", hotstation_code);
			} else if (hotstation_code == null && community_code != null) {
				criteria.andEqualTo("id", id).andEqualTo("company_code", company_code).andEqualTo("user_id", user_id)
						.andEqualTo("community_code", community_code);
			} else if (hotstation_code != null && community_code != null) {
				criteria.andEqualTo("id", id).andEqualTo("company_code", company_code).andEqualTo("user_id", user_id)
						.andEqualTo("community_code", community_code).andEqualTo("hotstation_code", hotstation_code);
			} else {
				criteria.andEqualTo("id", id).andEqualTo("company_code", company_code).andEqualTo("user_id", user_id);
			}
			return dataPermiMapper.deleteByExample(example);
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * type 0 所有，type 1 id, user_id, company_code
	 */
	@Override
	public List<DataPermission> queryDataPermi(JSONObject obj) {
		Integer type = obj.getInteger("type");
		Long id = obj.getLong("id");
		Long user_id = obj.getLong("user_id");
		String company_code = obj.getString("company_code");

		DataPermission record = new DataPermission();
		record = JSON.parseObject(obj.toJSONString(), DataPermission.class);

		if (record.getPage() != null && record.getListRows() != null) {
			PageHelper.startPage(record.getPage(), record.getListRows());
		} else if (record.getPage() == null && record.getListRows() != null) {
			PageHelper.startPage(1, record.getListRows());
		} else if (record.getListRows() == null) {
			PageHelper.startPage(1, 0);
		}

		Example example = new Example(DataPermission.class);
		// 创建查询条件
		Example.Criteria recordCriteria = example.createCriteria();
		if (type == 0) {
			return dataPermiMapper.selectAll();
		} else if (type == 1) {
			recordCriteria.andEqualTo("id", id).andEqualTo("user_id", user_id).andEqualTo("company_code", company_code);
			example.and(recordCriteria);
			return dataPermiMapper.selectByExample(example);
		} else {
			return null;
		}

	}

	@Override
	public int updateDataPermi(JSONObject obj) {
		DataPermission record = new DataPermission();
		Long id = obj.getLong("id");
		Long user_id = obj.getLong("user_id");
		String company_code = obj.getString("company_code");

		record.setId(id);
		record.setUser_id(user_id);
		record.setCompany_code(company_code);

		int exist = dataPermiMapper.selectCount(record);
		if (exist > 1) {
			return -2;
		}

		if (exist == 0) {
			return 0;
		}

		record = JSON.parseObject(obj.toJSONString(), DataPermission.class);
		try {
			// 创建example
			Example example = new Example(DataPermission.class);
			// 创建查询条件
			Example.Criteria criteria = example.createCriteria();
			// 设置查询条件 多个andEqualTo串联表示 and条件查询
			criteria.andEqualTo("id", id).andEqualTo("company_code", company_code).andEqualTo("user_id", user_id);
			return dataPermiMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}

	}

	@Override
	public int queryDataPermiCount(JSONObject obj) {
		Integer type = obj.getInteger("type");	
		Long id = obj.getLong("id");
		Long user_id = obj.getLong("user_id");
		String company_code = obj.getString("company_code");
		DataPermission record = new DataPermission();
		record = JSON.parseObject(obj.toJSONString(), DataPermission.class);
		Example example = new Example(DataPermission.class);
		// 设置查询条件 多个andEqualTo串联表示 and条件查询
		Example.Criteria recordCriteria = example.createCriteria();
		if (type == 0) {			
			example.and(recordCriteria);
		} else if (type == 1) {
			recordCriteria.andEqualTo("id", id).andEqualTo("company_code", company_code).andEqualTo("user_id", user_id);
			example.and(recordCriteria);			
		}
		return dataPermiMapper.selectCountByExample(example);	
	}

}
