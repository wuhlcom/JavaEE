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
import com.dazk.db.dao.UserMapper;
import com.dazk.db.model.DataPermission;
import com.dazk.db.model.User;
import com.dazk.db.model.DataPermission;
import com.dazk.service.DataPermissionService;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

@Service
public class DataPermissionServiceImpl implements DataPermissionService {

	@Autowired
	private DataPermissionMapper dataPermiMapper;

	@Autowired
	private UserMapper userMapper;

	@Override
	public int addDataPermi(JSONObject obj) {
		Long user_id = obj.getLong("user_id");
		String codeValue = obj.getString("codeValue");
		User user = new User();
		user.setId(user_id);
		int exist = userMapper.selectCount(user);
		// 用户不存在则返回错误
		if (exist == 0) {
			return 0;
		}

		if (exist > 1) {
			return -2;
		}

		DataPermission record = new DataPermission();
		record.setUser_id(user_id);
		record.setCodeValue(codeValue);
		exist = dataPermiMapper.selectCount(record);

		if (exist > 0) {
			return -1;
		}

		record = JSON.parseObject(obj.toJSONString(), DataPermission.class);
		return dataPermiMapper.insertSelective(record);
	}

	/**
	 * type 0按id删除 type 1按user_id删除 type 按id+user_id删除
	 */
	@Override
	public int delDataPermi(JSONObject obj) {		
		DataPermission record = new DataPermission();

		Integer type = obj.getInteger("type");
		Long id = obj.getLong("id");		
		Long user_id = obj.getLong("user_id");	
		if (type == 0) {
			record.setId(id);
		} else if (type == 1) {
			record.setUser_id(user_id);
		} 

		int exist = dataPermiMapper.selectCount(record);
		if (exist == 0) {
			return 0;
		}

		// 创建example
		Example example = new Example(DataPermission.class);
		// 创建查询条件
		Example.Criteria criteria = example.createCriteria();
		// 设置查询条件 多个andEqualTo串联表示 and条件查询

		try {
			if (type == 0) {
				criteria.andEqualTo("id", id);
			} else if (type == 1) {
				criteria.andEqualTo("user_id", user_id);
			} 		
			System.out.println(example);
			return dataPermiMapper.deleteByExample(example);
		} catch (Exception e) {
			return -1;
		}
	}

	
	@Override
	public int updateDataPermi(JSONObject obj) {
		DataPermission record = new DataPermission();
		Long id = obj.getLong("id");
		Long user_id = obj.getLong("user_id");		

		record.setId(id);
		record.setUser_id(user_id);
		
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
			criteria.andEqualTo("id", id).andEqualTo("user_id", user_id);
			return dataPermiMapper.updateByExampleSelective(record, example);
		} catch (Exception e) {
			return -1;
		}

	}
	
	/**
	 * type 0 所有， type 1 user_id type 2 id+user_id
	 */
	@Override
	public List<DataPermission> queryDataPermi(JSONObject obj) {
		Integer type = obj.getInteger("type");
		Long id = obj.getLong("id");
		Long user_id = obj.getLong("user_id");


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
			recordCriteria.andEqualTo("user_id", user_id);
			example.and(recordCriteria);
			return dataPermiMapper.selectByExample(example);
		} else if (type == 2) {
			recordCriteria.andEqualTo("id", id).andEqualTo("user_id", user_id);
			example.and(recordCriteria);
			return dataPermiMapper.selectByExample(example);
		} else {
			return null;
		}

	}

	@Override
	public int queryDataPermiCount(JSONObject obj) {
		Integer type = obj.getInteger("type");
		Long id = obj.getLong("id");
		Long user_id = obj.getLong("user_id");
	
		DataPermission record = new DataPermission();
		record = JSON.parseObject(obj.toJSONString(), DataPermission.class);
		Example example = new Example(DataPermission.class);
		// 设置查询条件 多个andEqualTo串联表示 and条件查询
		Example.Criteria recordCriteria = example.createCriteria();
		if (type == 0) {
			example.and(recordCriteria);
		} else if (type == 1) {
			recordCriteria.andEqualTo("id", id).andEqualTo("user_id", user_id);
			example.and(recordCriteria);
		}
		return dataPermiMapper.selectCountByExample(example);
	}

}
