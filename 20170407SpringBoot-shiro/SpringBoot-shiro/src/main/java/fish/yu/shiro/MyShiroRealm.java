package fish.yu.shiro;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.toolkit.CollectionUtils;

import fish.yu.entity.Role;
import fish.yu.entity.RolesAndPermissions;
import fish.yu.entity.User;
import fish.yu.entity.UserAndRoles;
import fish.yu.service.PermissionService;
import fish.yu.service.RolePermissionService;
import fish.yu.service.RoleService;
import fish.yu.service.UserRoleService;
import fish.yu.service.UserService;
import fish.yu.util.StringConstant;
/**
 * shiro的核心类  获取用户数据封装成subject  包含用户认证数据的封装 +用户鉴权所需 权限数据的封装
 * @author yuliang-ds1
 *
 */
public class MyShiroRealm extends AuthorizingRealm{
	
	
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PermissionService permissionService;
	
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	
	
	/**
	 * 从数据库中加载用户的权限  包含用户角色权限 用户模块权限 以及用户分组权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection priCollenction) {
		// TODO Auto-generated method stub
		
		System.out.println("权限认证方法：MyShiroRealm.doGetAuthorizationInfo()");
		User user = (User)SecurityUtils.getSubject().getPrincipal();
		String userId = user.getId();
		System.out.println("查询出来的userId:"+userId);
		SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
		
		
		
		try{
			//从数据库中查询用户角色   根据用户ID查询角色（role），放入到Authorization里。
			List<Role> roleList=null;//用户拥有角色集合
			List<RolesAndPermissions> roleAndPermissionNameList=null;//用户拥有的权限集合
		    if(StringUtils.hasLength(userId)){
		    	//根据用户id查询用户--角色关联关系，即用户拥有的角色
		    	UserAndRoles queryRolesByUserId = userService.queryRolesByUserId(userId);
		    	if(queryRolesByUserId!=null){
		    		
		    		for (Role role : queryRolesByUserId.getRoles()) {
						System.out.println("用户拥有的角色："+role+"  角色id:"+role.getId());
					}
		    		
		    		
		    	}
		    	//根据用户拥有角色id集合，查询所有拥有的资源权限名称
		    	if(queryRolesByUserId!=null){
		    		roleList = queryRolesByUserId.getRoles();
		    		if(CollectionUtils.isNotEmpty(roleList)){
		    			Set<String>  roleIdSet=new HashSet<String>();
		    			for (Role role : roleList) {
		    				roleIdSet.add(role.getId());
						}
		    			List<String>  roleIdList=new  ArrayList<String>();
		    			roleIdList.addAll(roleIdSet);
		    			if(CollectionUtils.isNotEmpty(roleIdList)){
		    				System.out.println("根据用户角色id集合查询用户权限-开始");
		    				Long  l1=System.currentTimeMillis();
		    				roleAndPermissionNameList = roleService.queryPermissionsByRoleIds(roleIdList);
		    				Long  l3=System.currentTimeMillis();
		    				System.out.println("根据用户角色id集合查询用户权限-结束:"+(l3-l1));
	
		    				if(roleAndPermissionNameList!=null){
			    				System.out.println("权限集合size:"+roleAndPermissionNameList.size());
		    			    }
		    			}
		    		}
		    	}
		    	
		    	
		    }
		   
		    
		    //封装用户拥有的角色
		    if(CollectionUtils.isNotEmpty(roleList)){
		    	//用户类型集合
		    	Set<String> roleTypeSet = new HashSet<String>();
		    	for (Role role : roleList) {
		    		if(role!=null&&role.getType()!=null){
		    			roleTypeSet.add(role.getType());
					}
		    	}
		    	//将用户拥有的角色类型，赋给授权信息
		    	info.setRoles(roleTypeSet);
		    }
		    
		 
		    //封装用户拥有的资源权限数据
			Set<String> permissionSet = new HashSet<String>();
			if(CollectionUtils.isNotEmpty(roleAndPermissionNameList)){
				//截取url中第一个字符串作为权限数据
				for (RolesAndPermissions roleAndPermission : roleAndPermissionNameList) {
					StringBuilder sb=new StringBuilder();
					String purl = roleAndPermission.getPurl();
					if(StringUtils.hasLength(purl)){
						String[] split = purl.split("/");
						if(split!=null&&split.length>=2){
							sb.append(StringConstant.USER_COLON);
							sb.append(split[1]);
							permissionSet.add(sb.toString());
						}
						
					}
				}
				if(CollectionUtils.isNotEmpty(permissionSet)){
					for (String p : permissionSet) {
						System.out.println("用户拥有的权限："+p);
					}
					
				}
				info.setStringPermissions(permissionSet);
		        return info;
				
			}
			
			
			info.setStringPermissions(permissionSet);
		}catch(Exception  e){
			e.printStackTrace();
			throw new UnauthorizedException(e.getMessage());
		}
        return info;
	}
	
	
	
	
	/**
	 * 用户认证操作
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationtoken) throws AuthenticationException {
		// TODO Auto-generated method stub
        System.out.println("身份认证方法封装数据开始：MyShiroRealm.doGetAuthenticationInfo()");
		
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationtoken;
		String name = token.getUsername();
		String password = String.valueOf(token.getPassword());
		//访问一次，计数一次
		ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
		opsForValue.increment(StringConstant.SHIRO_LOGIN_COUNT+name, 1);
		//计数大于5时，设置用户被锁定一小时
		if(Integer.parseInt(opsForValue.get(StringConstant.SHIRO_LOGIN_COUNT+name))>=5){
			opsForValue.set(StringConstant.SHIRO_IS_LOCK+name, "LOCK");
			stringRedisTemplate.expire(StringConstant.SHIRO_IS_LOCK+name, 1, TimeUnit.HOURS);
		}
		if ("LOCK".equals(opsForValue.get(StringConstant.SHIRO_IS_LOCK+name))){
			throw new DisabledAccountException("由于密码输入错误次数大于5次，帐号已经禁止登录！");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nickname", name);
		//密码进行加密处理  明文为  password+name
		//String paw = password+name;
		//String pawDES = MyDES.encryptBasedDes(paw);
		//System.out.println("加密后的密码："+pawDES);
		//map.put("pswd", password);
		User user = null;
		// 从数据库获取对应用户名密码的用户
		List<User> userList = userService.selectByMap(map);
		if(userList.size()!=0){
			user = userList.get(0);
		} 
		if (null == user) {
			throw new AccountException("帐号或密码不正确！");
		}else if(user.getStatus()==0){
			/**
			 * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
			 */
			throw new DisabledAccountException("此帐号已经设置为禁止登录！");
		}else{
			//登录成功
			//更新登录时间 last login time
			user.setLastLoginTime(new Date());
			userService.updateById(user);
			//清空登录计数
			opsForValue.set(StringConstant.SHIRO_LOGIN_COUNT+name, "0");
		}
		System.out.println("MyShiroRealm-doGetAuthenticationInfo-User:"+user);
		return new SimpleAuthenticationInfo(user, user.getPassword(),ByteSource.Util.bytes(user.getNickName()+user.getSalt()), getName());
	}
	
	/*
	 * 清楚缓存中  当前用户的权限信息
	 * 
	 */
	public void clearCachedAuthorizationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject()
				.getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
	
	
	
	/**
	 * 清楚PrincipalCollection的用户数据
	 */
	public void clearCachedAuthorizationInfo(
			PrincipalCollection principalCollection) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
	
	
	public static void main(String[] args) {
		
		String url="/user/add";
		String[] split = url.split("/");
		//for (String string : split) {
			System.out.println(split[1]);
		//}
		
		
	}
	
	

}
