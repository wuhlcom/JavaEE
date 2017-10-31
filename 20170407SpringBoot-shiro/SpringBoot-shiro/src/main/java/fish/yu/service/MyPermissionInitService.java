package fish.yu.service;


import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import fish.yu.dao.MyPermissionInitMapper;
import fish.yu.entity.MyPermissionInit;

/**
 * 
 * @author yuliang-ds1
 *
 */
@Service
public class MyPermissionInitService extends ServiceImpl<MyPermissionInitMapper, MyPermissionInit>{
	
	@Autowired
	MyPermissionInitMapper permissionInitMapper;
	
	public List<MyPermissionInit> selectAll() {
		System.out.println("Service PERMISSION_INIT");
		
		return permissionInitMapper.selectAll();
		
		//return null;
	}
}
