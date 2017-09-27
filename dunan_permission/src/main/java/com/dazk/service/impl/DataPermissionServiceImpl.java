/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月28日 下午3:03:26 * 
*/
package com.dazk.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.util.GlobalParamsUtil;
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
@Transactional
public class DataPermissionServiceImpl implements DataPermissionService {
	public final static Logger logger = LoggerFactory.getLogger(DataPermissionServiceImpl.class);
	@Autowired
	private DataPermissionMapper dataPermiMapper;

	@Autowired
	private UserMapper userMapper;
	
	@Resource
	private UserService userService;

	@Resource
	private RolePermissionService rolePermiService;

	public final static Integer CODE_TYPE_COMPANY = 1;
	public final static String COMPANY_ADD = "/company/add";
	public final static String COMPANY_UPDATE = "/company/update";
	public final static String COMPANY_DELETE = "/company/delete";

	public final static Integer CODE_TYPE_STATION = 2;
	public final static String STATION_ADD = "/hotStation/add";
	public final static String STATION_UPDATE = "/hotStation/update";
	public final static String STATION_DELETE = "/hotStation/delete";

	public final static Integer CODE_TYPE_COMMUNITY = 3;
	public final static String COMMUNITY_ADD = "/community/add";
	public final static String COMMUNITY_UPDATE = "/community/update";
	public final static String COMMUNITY_DELETE = "/community/delete";

	// 添加数据权限
	@Override
	public int addDataPermi(JSONObject obj) {
		Long user_id = obj.getLong("user_id");
		String code_value = obj.getString("code_value");

		DataPermission record = new DataPermission();
		record.setUser_id(user_id);
		record.setCode_value(code_value);
		int exist = dataPermiMapper.selectCount(record);

		if (exist >= 1) {
			return -1;
		}

		record = JSON.parseObject(obj.toJSONString(), DataPermission.class);
		return dataPermiMapper.insertSelective(record);
	}

	// data_type '数据权限类型,1 增删改查 0：查询'
	// 有删除,添加，修改任1权限就设置为1
	// 只有查询权限设置为0
	private int getDataType(Long userid, Integer codeType) {	
		Integer dateType = 0;
		JSONObject userParams = new JSONObject();
		userParams.put("id",userid);
		User user = userService.queryUserOne(userParams);	
		//找不到用户
		if (user==null){
			return -1;
		}
		
		Long userRoleId = user.getRole_id();

		JSONObject obj = new JSONObject();
		obj.put("role_id", userRoleId);
		Map menuUris = rolePermiService.queryRoleMenu(obj);
		Set<String> uris = (TreeSet<String>) menuUris.get("uris");	
		if (menuUris==null||uris==null||menuUris.isEmpty()) {
			return dateType;
		}

		if (codeType == CODE_TYPE_COMPANY) {
			if (uris.contains(COMPANY_ADD) || uris.contains(COMPANY_UPDATE) || uris.contains(COMPANY_DELETE)) {
				dateType = 1;
			}
		} else if (codeType == CODE_TYPE_STATION) {
			if (uris.contains(STATION_ADD) || uris.contains(STATION_UPDATE) || uris.contains(STATION_DELETE)) {
				dateType = 1;
			}
		} else if (codeType == CODE_TYPE_COMMUNITY) {
			if (uris.contains(COMMUNITY_ADD) || uris.contains(COMMUNITY_UPDATE) || uris.contains(COMMUNITY_DELETE)) {
				dateType = 1;
			}
		}
		return dateType;
	}

	// 批量添加数据权限
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public int addDataPermiBatch(JSONObject obj) {
		int rs = 0;
		List<DataPermission> dataList = new ArrayList<>();
		Long user_id = obj.getLong("userid");
		JSONArray datas = obj.getJSONArray("data");		
				
		JSONObject delParams = new JSONObject();
		delParams.put("type", 1);
		delParams.put("user_id", user_id);

		//查询用户的parent_user
		Example example = new Example(User.class);
		// 创建查询条件
		Example.Criteria recordCriteria = example.createCriteria();
		recordCriteria.andEqualTo("isdel", 0).andEqualTo("id", user_id);
		example.and(recordCriteria);
		List<User> user = userMapper.selectByExample(example);
		Long pid = user.get(0).getParent_user();
		
		//根据用户id删除旧的数据权限
		rs = this.delDataPermi(delParams);
		if (rs == -1) {
			throw new RuntimeErrorException(null, "删除数据权限异常!");
		}

		//pid==0表示超级管理员
		if (pid == 0) {			
			DataPermission record = new DataPermission();
			record.setUser_id(user_id);
			record.setCode_value("");
			record.setData_type(1);
			record.setCode_type(1);
			return dataPermiMapper.insert(record);
		}
		
		//非超级管理员用户如果权限data为空清空权限
		if (datas.isEmpty()) {
			return 1;
		}

		for (Object data : datas) {
			JSONObject jsonTemp = (JSONObject) data;
			Integer code_type = jsonTemp.getInteger("code_type");
			String code_value = jsonTemp.getString("code_type");
			Integer data_type = getDataType(user_id, code_type);
			if (data_type==-1) {
				throw new RuntimeErrorException(null, "添加数据权限时找不到用户!");
			}

			jsonTemp.put("user_id", user_id);
			jsonTemp.put("data_type", data_type);

			DataPermission record = new DataPermission();
			record.setUser_id(user_id);
			record.setCode_value(code_value);

			int exist = dataPermiMapper.selectCount(record);
			if (exist >= 1) {
				continue;
			}
			record = JSON.parseObject(jsonTemp.toJSONString(), DataPermission.class);
			dataList.add(record);
		}
		if (dataList.isEmpty()) {
			throw new RuntimeErrorException(null, "添加数据权限时有异常!");
		}

		rs = dataPermiMapper.insertList(dataList);
		if (rs == 0) {
			throw new RuntimeErrorException(null, "添加数据权限失败!");
		}
		return rs;
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
		
		Example example = new Example(DataPermission.class);
		Example.Criteria recordCriteria = example.createCriteria();			
		if (type == 0) {
			record.setId(id);
			recordCriteria.andEqualTo("id", id);		
		} else if (type == 1) {
			record.setUser_id(user_id);
			recordCriteria.andEqualTo("user_id", user_id);			
		}		
				
		int exist = dataPermiMapper.selectCountByExample(example);
		if (exist == 0) {
			return 0;
		}

		example.clear();
		
		Example example1 = new Example(DataPermission.class);
		Example.Criteria criteria = example1.createCriteria();
		// 设置查询条件 多个andEqualTo串联表示 and条件查询

		try {
			if (type == 0) {
				criteria.andEqualTo("id", id);
			} else if (type == 1) {
				criteria.andEqualTo("user_id", user_id);
			}
				
			return dataPermiMapper.deleteByExample(example1);
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
		List<DataPermission> rs = new ArrayList<>();

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
			rs = dataPermiMapper.selectByExample(example);
		} else if (type == 2) {
			recordCriteria.andEqualTo("id", id);	
			rs = dataPermiMapper.selectByExample(example);
		}
		return rs;
	}

	@Transactional
	@Override
	public Set<String> queryDataPermi(JSONObject obj) {
		String token =obj.getString("token");			
		Long user_id = obj.getLong("user_id");
		
		Set<String> dataLs = new TreeSet<>();
		
		Example example = new Example(DataPermission.class);
		Example.Criteria recordCriteria = example.createCriteria();
		recordCriteria.andEqualTo("user_id", user_id);		
		List<DataPermission> datas = dataPermiMapper.selectByExample(example);
		
		for (DataPermission data : datas) {
			String code = data.getCode_value();
			code=code.trim();//空格也认为是超级管理员
			dataLs.add(code);
		}
	
		// 调试：用来清理超级管理员下的无用数据
		// 如果是超级管理员返回所有公司code
		if (dataLs.contains("")) {	
			//删除所有权限
			JSONObject delParams = new JSONObject();
			delParams.put("type", 1);
			delParams.put("user_id", user_id);
			int rs = this.delDataPermi(delParams);
			
			//插入一条空的权限
			DataPermission record = new DataPermission();
			record.setUser_id(user_id);
			record.setCode_value("");
			record.setData_type(1);
			record.setCode_type(1);
			dataPermiMapper.insert(record);
			
			//如果是超级管理员返回所有公司code
			JSONObject jsonParams = new JSONObject();
			dataLs = getCompanyCodes(token,jsonParams);	
			return dataLs;
		}
		
		if (!dataLs.isEmpty()) {
			dataLs = removeParentData(dataLs);
		}
		return dataLs;
	}
	
	//获取所有公司的code
	public Set<String>  getCompanyCodes(String token,JSONObject jsonParams){			
		RestTemplate restTemplate = new RestTemplate();
		Set<String> codes = new TreeSet<>();
		
		List<HttpMessageConverter<?>> partConverters = new ArrayList<HttpMessageConverter<?>>();
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setWriteAcceptCharset(false);
		partConverters.add(stringConverter);
		restTemplate.setMessageConverters(partConverters);
		
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("token", token);
		
		HttpEntity<String> formEntity = new HttpEntity<String>(jsonParams.toString(), headers);
		
		String result = null;
    	try {
    		result = restTemplate.postForObject(GlobalParamsUtil.COMPANY_URL, formEntity, String.class);    		
		} catch (Exception e1) {
			e1.printStackTrace();
		}	     	
    	if (result!=null) {
    		JSONObject rs = JSONObject.parseObject(result);    	
        	JSONArray companies =rs.getJSONArray("result");
        	for (Object company : companies) {
    			JSONObject jsonTemp = (JSONObject) company;		
    			String code_value = jsonTemp.getString("code");		
    			codes.add(code_value);
    		}
		}

		return codes;
	}

	public Set<String> removeParentData(Set<String> data) {

		logger.debug("old data:"+data);
		List<String> ls01 = new ArrayList<>();
		List<String> ls02 = new ArrayList<>();
		List<String> ls03 = new ArrayList<>();
		for (String code_value : data) {
			if (code_value.length() == 3) {
				ls01.add(code_value);
			} else if (code_value.length() == 6) {
				ls02.add(code_value);
			} else if (code_value.length() == 8) {
				ls03.add(code_value);
			}
		}

		for (String ls1 : ls01) {
			for (String ls2 : ls02) {			
				boolean rs = strStartWith(ls2, ls1);			
				if (rs) {
					data.remove(ls1);
				}

			}
		}

		for (String ls2 : ls02) {
			for (String ls3 : ls03) {	
				boolean rs = strStartWith(ls3, ls2);
				if (rs) {
					data.remove(ls2);
				}
			}
		}

		logger.debug("new data:"+data);
		return data;
	}

	private boolean strStartWith(String content, String reg) {
		boolean rs = content.startsWith(reg);
		return rs;
	}

	@Override
	public int queryDataCount(JSONObject obj) {
		Integer type = obj.getInteger("type");
		Long id = obj.getLong("id");
		Long user_id = obj.getLong("user_id");

		DataPermission record = new DataPermission();
		record = JSON.parseObject(obj.toJSONString(), DataPermission.class);
		Example example = new Example(DataPermission.class);
		// 设置查询条件 多个andEqualTo串联表示 and条件查询
		Example.Criteria recordCriteria = example.createCriteria();
		if (type == 0) {	
		} else if (type == 1) {
			recordCriteria.andEqualTo("user_id", user_id);		
		} else if (type == 2) {
			recordCriteria.andEqualTo("id", id).andEqualTo("user_id", user_id);			
		}
		return dataPermiMapper.selectCountByExample(example);
	}

}
