/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月28日 下午3:03:26 * 
*/
package com.dazk.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.util.RegexUtil;
import com.dazk.db.dao.DataPermissionMapper;
import com.dazk.db.dao.UserMapper;
import com.dazk.db.model.DataPermission;
import com.dazk.db.model.Menu;
import com.dazk.service.DataPermissionService;
import com.dazk.service.RolePermissionService;
import com.dazk.service.UserService;
import com.github.pagehelper.PageHelper;
import com.dazk.db.model.User;
import tk.mybatis.mapper.entity.Example;

@Service
public class DataPermissionServiceImpl implements DataPermissionService {

	@Autowired
	private DataPermissionMapper dataPermiMapper;

	@Autowired
	private UserMapper userMapper;

	@Resource
	private RolePermissionService rolePermiService;

	public final static Integer COMPANY_CODE = 1;
	public final static String COMPANY_ADD = "/company/add";
	public final static String COMPANY_UPDATE = "/company/update";
	public final static String COMPANY_DELETE = "/company/delete";

	public final static Integer STATION_CODE = 2;
	public final static String STATION_ADD = "/hotStation/add";
	public final static String STATION_UPDATE = "/hotStation/update";
	public final static String STATION_DELETE = "/hotStation/delete";

	public final static Integer COMMUNITY_CODE = 3;
	public final static String COMMUNITY_ADD = "/community/add";
	public final static String COMMUNITY_UPDATE = "/community/update";
	public final static String COMMUNITY_DELETE = "/community/delete";

	@Override
	public int addDataPermi(JSONObject obj) {
		Long user_id = obj.getLong("user_id");
		String code_value = obj.getString("code_value");

		DataPermission record = new DataPermission();
		record.setUser_id(user_id);
		record.setCode_value(code_value);
		int exist = dataPermiMapper.selectCount(record);

		if (exist > 0) {
			return -1;
		}

		record = JSON.parseObject(obj.toJSONString(), DataPermission.class);
		return dataPermiMapper.insertSelective(record);
	}

	private int getDataType(Long userid, Integer codeType) {
		Integer dateType = 0;
		Example example = new Example(User.class);
		// 创建查询条件
		Example.Criteria recordCriteria = example.createCriteria();
		recordCriteria.andEqualTo("isdel", 0).andEqualTo("id", userid);
		example.and(recordCriteria);
		List<User> user = userMapper.selectByExample(example);
		Long userRoleId = user.get(0).getRole_id();

		JSONObject obj = new JSONObject();
		obj.put("role_id", userRoleId);
		List<Menu> menus = rolePermiService.queryRoleMenus(obj);
		if (menus.size() == 0) {
			return dateType;
		}

		List<String> uris = new ArrayList<>();
		for (Menu menu : menus) {
			String uri = menu.getUri();
			uris.add(uri);
		}

		if (codeType == COMPANY_CODE) {
			if (uris.contains(COMPANY_ADD) || uris.contains(COMPANY_UPDATE) || uris.contains(COMPANY_DELETE)) {
				dateType = 1;
			}
		} else if (codeType == STATION_CODE) {
			if (uris.contains(STATION_ADD) || uris.contains(STATION_UPDATE) || uris.contains(STATION_DELETE)) {
				dateType = 1;
			}
		} else if (codeType == COMMUNITY_CODE) {
			if (uris.contains(COMMUNITY_ADD) || uris.contains(COMMUNITY_UPDATE) || uris.contains(COMMUNITY_DELETE)) {
				dateType = 1;
			}
		}
		return dateType;
	}

	// 批量添加数据权限
	@Override
	public int addDataPermiBatch(JSONObject obj) {
		List<DataPermission> dataList = new ArrayList<>();
		Long user_id = obj.getLong("userid");
		JSONArray datas = obj.getJSONArray("data");

		for (Object data : datas) {
			JSONObject jsonTemp = (JSONObject) data;
			Integer code_type = jsonTemp.getInteger("code_type");
			Integer data_type = getDataType(user_id, code_type);

			jsonTemp.put("user_id", user_id);
			jsonTemp.put("data_type", data_type);
			DataPermission record = new DataPermission();

			record = JSON.parseObject(jsonTemp.toJSONString(), DataPermission.class);

			// int exist = dataPermiMapper.selectCount(record);
			// if (exist != 0) {
			// return -1;
			// }
			dataList.add(record);
		}
		return dataPermiMapper.insertList(dataList);
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
	 * type 0 所有， type 1 user_id type 2 user_id
	 */
	@Override
	public List<DataPermission> queryData(JSONObject obj) {
		Integer type = obj.getInteger("type");
		Long id = obj.getLong("id");
		Long user_id = obj.getLong("user_id");
		List<DataPermission> rs=new ArrayList<>();
		
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
			rs = dataPermiMapper.selectAll();
		} else if (type == 1) {
			recordCriteria.andEqualTo("user_id", user_id);
			example.and(recordCriteria);
			rs = dataPermiMapper.selectByExample(example);
		} else if (type == 2) {
			recordCriteria.andEqualTo("id", id);
			example.and(recordCriteria);
			rs = dataPermiMapper.selectByExample(example);
		} 
		return rs;
	}

	@Override
	public List<String> queryDataPermi(JSONObject obj) {
		Long user_id = obj.getLong("user_id");
		List<String> dataLs = new ArrayList<>();
		Example example = new Example(DataPermission.class);	
		Example.Criteria recordCriteria = example.createCriteria();
		recordCriteria.andEqualTo("user_id", user_id);
		example.and(recordCriteria);
		List<DataPermission> datas = dataPermiMapper.selectByExample(example);
		for (DataPermission data : datas) {
			String code = data.getCode_value();
			if (RegexUtil.isNotNull(code)) {
				dataLs.add(code);
			}
		}	
		// if (dataLs.size()==0) {
		// return null;
		// }
		return dataLs;	
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
			recordCriteria.andEqualTo("user_id", user_id);
			example.and(recordCriteria);
		} else if (type == 2) {
			recordCriteria.andEqualTo("id", id).andEqualTo("user_id", user_id);
			example.and(recordCriteria);
		}
		return dataPermiMapper.selectCountByExample(example);
	}

}
