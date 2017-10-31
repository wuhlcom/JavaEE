package fish.yu.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.toolkit.StringUtils;

import fish.yu.annotation.ShiroMethodSecurity;
import fish.yu.dao.GroupMapper;
import fish.yu.dao.RoleMapper;
import fish.yu.dao.UserMapper;
import fish.yu.entity.Group;
import fish.yu.entity.GroupAndRoles;
import fish.yu.entity.Role;
import fish.yu.entity.RolesAndPermissions;
import fish.yu.entity.User;
import fish.yu.entity.UserAndGroups;
import fish.yu.shiro.MyShiroRealm;
import fish.yu.shiro.filter.KickoutSessionControlFilter;
import fish.yu.util.StringConstant;


/**
 * 对增删改查的方法，进行按用户分组过滤
 * @author yuliang-ds1
 *
 */
@Aspect
@Component
public class MethodTypeInterceptor  {
	
	  private static final Logger logger = LoggerFactory.getLogger(MethodTypeInterceptor.class);
	
	  @Autowired
	  UserMapper  userMapper;
	  
	  
	  @Autowired
	  RoleMapper  roleMapper;
	  
	  
	  @Autowired
	  GroupMapper groupMapper;
	  
	  @Autowired
	  MyShiroRealm myShiroRealm;
	  
	  
	  
	  @Around("execution(public * fish.yu.service..*.*(..))")
	  public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		  
		  
		System.out.println("MethodTypeInterceptor-AOP切入开始分组权限判断:");
		// 被代理的对象
		Object target = pjp.getTarget();
		
		MethodSignature signature =(MethodSignature)pjp.getSignature();
		
		//被代理的方法对象
		Method method = signature.getMethod();
		
		//是否加了注释
		boolean present = method.isAnnotationPresent(ShiroMethodSecurity.class);
		boolean presentRequiresRoles = method.isAnnotationPresent(RequiresRoles.class);
		System.out.println("MethodTypeInterceptor-AOP_presentRequiresRoles切入开始分组权限判断:"+presentRequiresRoles);
		String className = target.getClass().getSimpleName();
		String methodName = method.getName();
		System.out.println("MethodTypeInterceptor-AOP_presentRequiresRoles切入开始分组权限判断:"+methodName);
		Object retVal=null;
		//加了权限注解
		if(present){
			//用户-分组-角色--权限集合
		    Set<String>  permisionOperateSet=new HashSet<String>();
			
			ShiroMethodSecurity security = method.getAnnotation(ShiroMethodSecurity.class);
			String permission = security.permission();
			boolean own =false;
			//do sth 做权限判断
			Object principal = SecurityUtils.getSubject().getPrincipal();
			User  user=(User)principal;
			if(user!=null){
				 String id = user.getId();
				 if(StringUtils.isNotEmpty(id)){
					 //得到用户拥有的分组集合
					 UserAndGroups selectGroupsByUserId = userMapper.selectGroupsByUserId(id);
					 
					 if(selectGroupsByUserId!=null){
						 
						 List<Group> groups = selectGroupsByUserId.getGroups();
						 Set<String>   groupIdSet=new HashSet<String>();
						 if(CollectionUtils.isNotEmpty(groups)){
							 for (Group group : groups) {
								 groupIdSet.add(group.getId());
							}
						 }
						 
						 //用分组集合查询角色集合
						 Set<String>  roleIdSet=new HashSet<String>();
						 Set<String>  roleTypeSet=new HashSet<String>();
						 if(CollectionUtils.isNotEmpty(groupIdSet)){
							 List<String>   gourpIdList=new ArrayList<String>();
							 gourpIdList.addAll(groupIdSet);
							 if(gourpIdList!=null&&gourpIdList.size()>0){
								 List<GroupAndRoles> selectRolesByGroupIds = groupMapper.selectRolesByGroupIds(gourpIdList);
								 if(CollectionUtils.isNotEmpty(selectRolesByGroupIds)){
									 for (GroupAndRoles groupAndRoles : selectRolesByGroupIds) {
										 List<Role> roles = groupAndRoles.getRoles();
										 if(CollectionUtils.isNotEmpty(roles)){
											for (Role role : roles) {
												roleIdSet.add(role.getId());
												roleTypeSet.add(role.getType());
											}
										 }
									}
									 
								 }
								 
							 }
							 
						 }
						 
						 //通过角色集合拿到用户可以操作增删改查的权限集合
						 
					
						 if(CollectionUtils.isNotEmpty(roleIdSet)){
							 List<String>  roleIdList=new ArrayList<String>();
							 roleIdList.addAll(roleIdSet);
							 List<RolesAndPermissions> selectPermissionsByRoleIds = roleMapper.selectPermissionsByRoleIds(roleIdList);
							 
							 if(CollectionUtils.isNotEmpty(selectPermissionsByRoleIds)){
								 
								 for (RolesAndPermissions rolesAndPermissions : selectPermissionsByRoleIds) {
									 String purl = rolesAndPermissions.getPurl();
									 StringBuilder sb=new StringBuilder();
									 sb.append("[");
									 sb.append(StringConstant.GROUP_COLON);
									 sb.append(purl);
									 sb.append("]");
									 permisionOperateSet.add(sb.toString());
								}
								 
							 }
						 }
					 }
				 }
			}
			
			
			//用户分组权限包含这个部分的权限
			logger.debug("MethodTypeInterceptor-AOP切入分组权限判断-className："+className+" methodName:"+methodName+"  需要的权限："+permission);
			logger.debug("MethodTypeInterceptor-AOP切入分组权限判断-className："+className+" methodName:"+methodName+"  用户的权限：");
			for (String p : permisionOperateSet) {
				logger.debug(p);
			}
			if(CollectionUtils.isNotEmpty(permisionOperateSet)&&permisionOperateSet.contains(permission)){
				own=true;
			}
			//放行
			if(own){
				logger.debug("MethodTypeInterceptor-AOP切入分组权限判断通过!");
				return  pjp.proceed();
			}else{
				logger.debug("MethodTypeInterceptor-AOP切入分组权限判断失败抛出异常!");
				throw new AccessDeniedException("AOP-用户ID-分组-角色-方法操作权限标识集合-异常--用户没有权限！");
			}
		}else{
			logger.debug("MethodTypeInterceptor-AOP切入分组权限判断通过!-未加注解权限限制!");
			return  pjp.proceed();
		}
	  }
	  
	  
	  
	  @Around("execution(public * fish.yu.controller..*.*(..))")
	  public Object doBasicRefreshCacahe(ProceedingJoinPoint pjp) throws Throwable {
		  
		  
		 try{ 
		  
			Subject subject = KickoutSessionControlFilter.threadSubject.get();
			// 被代理的对象
		    if(subject!=null){
		    	 User user = (User)subject.getPrincipal();
		    	 if(user!=null){
		    		 ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
			 	     String refershFlag = opsForValue.get("shiro_redis_refresh_"+user.getNickName());
			 	     if(refershFlag!=null&&"true".equals(refershFlag)){
			 	    	System.out.println("MethodTypeInterceptor-AOP切入刷新缓存:"+user.getNickName());
			 	    	 //清空用户身份信息 清除授权信息
						myShiroRealm.clearCachedAuthorizationInfo(subject.getPrincipals());
			 	     }
			 	    opsForValue.set("shiro_redis_refresh_"+user.getNickName(),"false");
		    	 }
		    }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	  
	     return  pjp.proceed();
	  }
	  @Autowired
      StringRedisTemplate stringRedisTemplate;
	  
}
