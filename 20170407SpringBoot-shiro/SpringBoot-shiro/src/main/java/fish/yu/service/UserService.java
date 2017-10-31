package fish.yu.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;

import fish.yu.annotation.ShiroAopPermission;
import fish.yu.annotation.ShiroMethodSecurity;
import fish.yu.dao.GroupMapper;
import fish.yu.dao.RoleMapper;
import fish.yu.dao.UserGroupMapper;
import fish.yu.dao.UserMapper;
import fish.yu.dao.UserRoleMapper;
import fish.yu.entity.FrontPage;
import fish.yu.entity.Group;
import fish.yu.entity.GroupAndRoles;
import fish.yu.entity.Role;
import fish.yu.entity.RolesAndPermissions;
import fish.yu.entity.User;
import fish.yu.entity.UserAndGroups;
import fish.yu.entity.UserAndRoles;
import fish.yu.entity.UserGroup;
import fish.yu.entity.UserRole;
import fish.yu.entity.UserVO;
import fish.yu.shiro.MyCredentialsMatcher;
import fish.yu.shiro.MyShiroRealm;
import fish.yu.util.RandomStringUtil;
import fish.yu.util.RedisUtil;
import fish.yu.util.StringConstant;

/**
 * 
 * @author yuliang-ds1
 *
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
	
	private static final Log logger = LogFactory.getLog(UserService.class);
	
	@Autowired
	RedisSessionDAO redisSessionDAO;
	
	@Autowired
	UserMapper  userMapper;
	
	@Autowired
	RoleMapper  roleMapper;
	
	@Autowired
	GroupMapper  groupMapper;
	
	@Autowired
	UserRoleMapper userRoleMapper;
	
	@Autowired
	UserGroupMapper  userGroupMapper;
	
	@Autowired
	MyShiroRealm  myShiroRealm;
	
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	  
	@Autowired
	RedisUtil redisUtil;
	
   /*
    * 通过用户id获得角色集合
    */
	public  UserAndRoles  queryRolesByUserId(String id)throws RuntimeException{
		
		return   userMapper.selectRolesByUserId(id);
		
	}
	
	/**
	 * 通过用户ids集合获取角色集合
	 * @param ids
	 * @return
	 * @throws RuntimeException
	 */
    public  List<UserAndRoles>  queryRolesByUserIds(List<String> ids)throws RuntimeException{
		
		  return userMapper.selectRolesByUserIds(ids);
		
	}
	
	/**
	 * 查询所有用户
	 * @param frontPage
	 * @return
	 */
    @ShiroMethodSecurity(permission=ShiroAopPermission.QUERY_METHOD)
	public Page<UserVO> queryAllUsers(FrontPage<UserVO> frontPage) {
		
    	System.out.println("userMapper==="+userMapper);
		int rows = frontPage.getRows();
		int currentPage = frontPage.getPage();
		String sord = frontPage.getSord();
		boolean ascFlag=false;
		if("asc".equals(sord)){
			ascFlag=true;
		}
		Pagination page =new Pagination();
		page.setSize(rows);
		page.setCurrent(currentPage);
		page.setAsc(ascFlag);
		if(StringUtils.isNotEmpty(frontPage.getSidx())){
			page.setOrderByField(frontPage.getSidx());
		}
		
		//Page  page=new Page(1,5,"id");
		
		List<User> userList = userMapper.selectPage(page, Condition.instance());
		
		List<UserVO> userVOList = new ArrayList<UserVO>();
		Page<UserVO> pageList = frontPage.getPagePlus();
		
		//封装用户信息
		if(CollectionUtils.isNotEmpty(userList)){
			Session session=SecurityUtils.getSubject().getSession();
			for (User user : userList) {
				UserVO  userVO=getCommonUser(user,session);
				userVOList.add(userVO);
				System.out.println("UserService-queryAllUsers-User ："+user);
			}
			
		}
		//封装用户角色--资源 权限数据
		
		if(CollectionUtils.isNotEmpty(userList)){
			Set<String>  userIdSet=new HashSet<String>();
			for (User user : userList) {
				userIdSet.add(user.getId());
			}
			
			if(CollectionUtils.isNotEmpty(userIdSet)){
				
				List<String>  userIdList=new ArrayList<String>();
				userIdList.addAll(userIdSet);
				
				
				//1.用户--role集合-permission集合
				List<UserAndRoles> selectRolesByUserIds = userMapper.selectRolesByUserIds(userIdList);
				Set<String>  roleIdSet=new HashSet<String>();
				//用户对应的角色类型集合
				Map<String,List<String>> uidAndrtypeMap=new HashMap<String,List<String>>();
				//用户对应的角色id集合
				Map<String,List<String>> uidAndridMap=new HashMap<String,List<String>>();
				//角色对应的权限集合
				Map<String,List<String>> rIdAndPurlMap=new HashMap<String,List<String>>();
				
				if(selectRolesByUserIds!=null){
					
					
					
					for (UserAndRoles userAndRoles : selectRolesByUserIds) {
						List<Role> roles = userAndRoles.getRoles();
						String uid = userAndRoles.getId();
						Set<String>  rTypeSet=new HashSet<String>();
						Set<String>  currentIdSet=new HashSet<String>();
						if(CollectionUtils.isNotEmpty(roles)){
							for (Role role : roles) {
								rTypeSet.add(role.getType());
								roleIdSet.add(role.getId());
								currentIdSet.add(role.getId());
							}
						}
						List<String>  rTypeList=new ArrayList<String>();
						List<String>  rIdList=new ArrayList<String>();
						rTypeList.addAll(rTypeSet);
						rIdList.addAll(currentIdSet);
						uidAndrtypeMap.put(uid, rTypeList);
						uidAndridMap.put(uid, rIdList);
						
					}
					
					if(CollectionUtils.isNotEmpty(roleIdSet)){
						List<String>  roleIdList=new ArrayList<String>();
						roleIdList.addAll(roleIdSet);
						List<RolesAndPermissions> selectPermissionsByRoleIds = roleMapper.selectPermissionsByRoleIds(roleIdList);
						if(CollectionUtils.isNotEmpty(selectPermissionsByRoleIds)){
							
							for (RolesAndPermissions rolesAndPermissions : selectPermissionsByRoleIds) {
								String rid = rolesAndPermissions.getRid();
								String purl = rolesAndPermissions.getPurl();
								List<String> list = rIdAndPurlMap.get(rid);
								if(list==null){
									list=new ArrayList<String>();
								}
								list.add(purl);
								rIdAndPurlMap.put(rid, list);
							}
						}
					
					
					}
					
				}
					
			
				
				
				
				//2.用户-用户组集合-角色集合  权限集合
				
				Map<String,Set<String>>  userIdAndGroupNameListMap=new HashMap<String,Set<String>>();
				Map<String,Set<String>>  userIdAndGroupIdListMap=new HashMap<String,Set<String>>();
				
				Map<String,Set<String>>  groupIdAndRoleIdListMap=new HashMap<String,Set<String>>();
				Map<String,Set<String>>  groupIdAndRoleNameListMap=new HashMap<String,Set<String>>();
				
				Map<String,Set<String>>  roleIdAndPUrlListMap=new HashMap<String,Set<String>>();
				//展示部分列表     全部分组ID集合
				Set<String>  groupIdAllSet=new HashSet<String>();
				//展示部分列表     全部角色ID集合
				Set<String>  roleIdAllSet=new HashSet<String>();
				List<UserAndGroups> selectGroupsByUserIds = userMapper.selectGroupsByUserIds(userIdList);
				if(selectGroupsByUserIds!=null){
				
					for (UserAndGroups userAndGroups : selectGroupsByUserIds) {
						String uid = userAndGroups.getId();
						List<Group> groups = userAndGroups.getGroups();
						if(CollectionUtils.isNotEmpty(groups)){
							Set<String>  groupNameSet=new HashSet<String>();
							Set<String>  groupIdSet=new HashSet<String>();
							for (Group group : groups) {
								groupNameSet.add(group.getName());
								groupIdSet.add(group.getId());
							}
							
							userIdAndGroupNameListMap.put(uid, groupNameSet);
							userIdAndGroupIdListMap.put(uid, groupIdSet);
							if(groupIdSet!=null){
								groupIdAllSet.addAll(groupIdSet);
							}
						}
						
					}
					
					//查询所有用户组--对应的角色   查询所有角色对应的 -资源权限
					if(groupIdAllSet!=null){
						List<String>  groupIdAllList=new ArrayList<String>();
						groupIdAllList.addAll(groupIdAllSet);
						List<GroupAndRoles> selectRolesByGroupIds = groupMapper.selectRolesByGroupIds(groupIdAllList);
						//1.查对应所有角色
						if(CollectionUtils.isNotEmpty(selectRolesByGroupIds)){
							
							for (GroupAndRoles groupAndRoles : selectRolesByGroupIds) {
								//roleIdAllSet
								String gid = groupAndRoles.getGid();
								List<Role> roles = groupAndRoles.getRoles();
								if(CollectionUtils.isNotEmpty(roles)){
									Set<String>  roleNameSet=new HashSet<String>();
									Set<String>  _roleIdSet=new HashSet<String>();
									for (Role role : roles) {
										
										String rid = role.getId();
										String rname = role.getName();
										_roleIdSet.add(rid);
										roleNameSet.add(rname);
									}
									groupIdAndRoleIdListMap.put(gid,_roleIdSet);
									groupIdAndRoleNameListMap.put(gid, roleNameSet);
									if(_roleIdSet!=null){
									 roleIdAllSet.addAll(_roleIdSet);
									}
								}
							}
							
						}
						//2.查对应所有资源
						if(roleIdAllSet!=null&&roleIdAllSet.size()>0){
							List<String>  roleIdList=new ArrayList<String>();
							roleIdList.addAll(roleIdAllSet);
							List<RolesAndPermissions> selectPermissionsByRoleIds = roleMapper.selectPermissionsByRoleIds(roleIdList);
							if(CollectionUtils.isNotEmpty(selectPermissionsByRoleIds)){
								for (RolesAndPermissions rolesAndPermissions : selectPermissionsByRoleIds) {
									String rid = rolesAndPermissions.getRid();
									String purl = rolesAndPermissions.getPurl();
									Set<String> set = roleIdAndPUrlListMap.get(rid);
									if(set==null){
										set=new HashSet<String>();
									}
									set.add(purl);
									
									roleIdAndPUrlListMap.put(rid, set);
								}
							}
							
						}
					}
					
					
				}
				
				
				System.out.println("开始填充：userVOList-size:"+userVOList.size());
				System.out.println("开始填充：userIdAndGroupNameListMap:");
				
				for (Entry<String,Set<String>> userAndGroups : userIdAndGroupNameListMap.entrySet()) {
					System.out.print("userIdAndGroupNameListMap key："+userAndGroups.getKey());
					for (String s : userAndGroups.getValue()) {
						System.out.print("value："+s);
					}
					System.out.println();
				}
				
				for (Entry<String,Set<String>> userAndGroups : userIdAndGroupIdListMap.entrySet()) {
					System.out.print(" userIdAndGroupIdListMap key："+userAndGroups.getKey());
					for (String s : userAndGroups.getValue()) {
						System.out.print("value："+s);
					}
					System.out.println();
				}
				

				for (Entry<String,Set<String>> userAndGroups : groupIdAndRoleIdListMap.entrySet()) {
					System.out.print("groupIdAndRoleIdListMap key："+userAndGroups.getKey());
					for (String s : userAndGroups.getValue()) {
						System.out.print("value："+s);
					}
					System.out.println();
				}
				
				for (Entry<String,Set<String>> userAndGroups : groupIdAndRoleNameListMap.entrySet()) {
					System.out.print(" groupIdAndRoleNameListMap key："+userAndGroups.getKey());
					for (String s : userAndGroups.getValue()) {
						System.out.print("value："+s);
					}
					System.out.println();
				}
				
				
				for (Entry<String,Set<String>> userAndGroups : roleIdAndPUrlListMap.entrySet()) {
					System.out.print(" roleIdAndPUrlListMap key："+userAndGroups.getKey());
					for (String s : userAndGroups.getValue()) {
						System.out.print("value："+s);
					}
					System.out.println();
				}
				
				
				
		
				
				
				if(CollectionUtils.isNotEmpty(userVOList)){
					
					for (UserVO userVo : userVOList) {
						String uid = userVo.getId();
						//填充角色集合
						if(uidAndrtypeMap!=null){
							List<String> list = uidAndrtypeMap.get(uid);
							if(list!=null){
								StringBuilder sb=new StringBuilder();
								Boolean flag=false;
								for (String type : list) {
									if(flag){
										sb.append(",");
									}
									sb.append(type);
									
									flag=true;
								}
								userVo.setRoles(sb.toString());
							}
						}
						//填充权限集合
						if(uidAndridMap!=null){
							
							List<String> roleIdList = uidAndridMap.get(uid);
							if(roleIdList!=null&&rIdAndPurlMap!=null){
								Set<String> urlSet=new HashSet<String>();
								
								//获取每个角色对应的资源url
								for (String roleId : roleIdList) {
									List<String> urlList = rIdAndPurlMap.get(roleId);
									if(urlList!=null){
										for (String purl : urlList) {
											if(purl!=null){
												StringBuilder sb=new StringBuilder();
												String[] split = purl.split("/");
												if(split!=null&&split.length>=2){
													sb.append("[");
													sb.append(StringConstant.USER_COLON);
													sb.append(split[1]);
													sb.append("]");
													
													urlSet.add(sb.toString());
												}
											}
										}
									}
								}
								
								//拼接展示资源权限的数据格式
								if(urlSet!=null){
									StringBuilder sb=new StringBuilder();
									Boolean bl=false;
									for (String urlMatch : urlSet) {
										if(bl){
											sb.append(",");
										}
										sb.append(urlMatch);
										bl=true;
									}
									userVo.setPerms(sb.toString());
								}
							}
						}
						
						
						
						
						
						
						//用户-对应用户组名称集合
						
						try{
					    if(userIdAndGroupNameListMap!=null){
							Set<String> groupNameSet = userIdAndGroupNameListMap.get(uid);
							Boolean flag=false;
							StringBuilder sb=new StringBuilder();
							if(CollectionUtils.isNotEmpty(groupNameSet)){
								for (String groupName : groupNameSet) {
									if(flag){
										sb.append(",");
									}
									sb.append(groupName);
									flag=true;
								}
								userVo.setGroups(sb.toString());
							}
							
							
						}
						
						
						if(userIdAndGroupIdListMap!=null){
							Set<String> groupIdSet = userIdAndGroupIdListMap.get(uid);
							if(CollectionUtils.isNotEmpty(groupIdSet)){
								
								//用户所有的资源URlSet集合
								Set<String>  purlAllSet=new HashSet<String>();
								
								StringBuilder sbRoleName=new StringBuilder();
								for (String groupId : groupIdSet) {
									if(groupIdAndRoleIdListMap!=null){
										Set<String> roleIdList = groupIdAndRoleIdListMap.get(groupId);
										if(CollectionUtils.isNotEmpty(roleIdList)){
											for (String roleId : roleIdList) {
												
												if(roleIdAndPUrlListMap!=null){
													Set<String> purlSet = roleIdAndPUrlListMap.get(roleId);
													if(purlSet!=null){
														purlAllSet.addAll(purlSet);
													}
													
												}
											}
										}
									}
									
									if(groupIdAndRoleNameListMap!=null){
										Set<String> roleNameSet = groupIdAndRoleNameListMap.get(groupId);
										if(CollectionUtils.isNotEmpty(roleNameSet)){
											Boolean  flag=false;
											for (String roleName : roleNameSet) {
												if(flag){
													sbRoleName.append(",");
												}
												sbRoleName.append(roleName);
												flag=true;
											}
										}
									}
								}
								userVo.setGroupRoleNames(sbRoleName.toString());
								
								if(CollectionUtils.isNotEmpty(purlAllSet)){
									
									StringBuilder sb=new StringBuilder();
									Boolean flag=false;
									for (String purl : purlAllSet) {
										if(flag){
											sb.append(",");
										}
										if(StringUtils.isNotEmpty(purl)){
											//URL资源权限
											if(purl.indexOf("/")>-1){
												String[] split = purl.split("/");
												if(split!=null&&split.length>=2){
													sb.append("[");
													sb.append(StringConstant.USER_COLON);
													sb.append(split[1]);
													sb.append("]");
												}
											
											//角色操作权限	
											}else{
												sb.append("[");
												sb.append(StringConstant.GROUP_COLON);
												sb.append(purl);
												sb.append("]");
											}
										}
										flag=true;
									}
									userVo.setGroupPerms(sb.toString());
								}
								
								
							}
							
						}
						
						}catch(Exception e){
							e.printStackTrace();
						}
						
					}
					
				}
					
				
			}
			
			
			System.out.println("UserVO -size:"+userVOList.size());
			
			for (UserVO user : userVOList) {
				System.out.println("UserVO :"+user);
			}
			pageList.setRecords(userVOList);
			
			pageList.setTotal(userVOList.size());
		}
		return pageList;
	}
	
	
	/**
	 * 查询所有在线用户
	 * @param frontPage
	 * @return
	 */
    @ShiroMethodSecurity(permission=ShiroAopPermission.QUERY_METHOD)
	public Page<UserVO> getPagePlus(FrontPage<UserVO> frontPage) {
		// 因为我们是用redis实现了shiro的session的Dao,而且是采用了shiro+redis这个插件
		// 所以从spring容器中获取redisSessionDAO
		// 来获取session列表.
		Collection<Session> sessions = redisSessionDAO.getActiveSessions();
		Iterator<Session> it = sessions.iterator();
		List<UserVO> onlineUserList = new ArrayList<UserVO>();
		Page<UserVO> pageList = frontPage.getPagePlus();
		// 遍历session
		while (it.hasNext()) {
			// 这是shiro已经存入session的
			// 现在直接取就是了
			Session session = it.next();
			UserVO onlineUser = getSessionBo(session);
			if(onlineUser!=null){
				onlineUserList.add(onlineUser);
			}
		}
		// 再将List<UserVO>转换成mybatisPlus封装的page对象
		int page = frontPage.getPage() - 1;
		int rows = frontPage.getRows() - 1;
		int startIndex = page * rows;
		int endIndex = (page * rows) + rows;
		int size = onlineUserList.size();
		if (endIndex > size) {
			endIndex = size;
		}
		pageList.setRecords(onlineUserList.subList(startIndex, endIndex));
		pageList.setTotal(size);
		return pageList;
	}
	//从session中获取UserOnline对象
	private UserVO getSessionBo(Session session){
		//获取session登录信息。
		Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
		if(null == obj){
			return null;
		}
		//确保是 SimplePrincipalCollection对象。
		if(obj instanceof SimplePrincipalCollection){
			SimplePrincipalCollection spc = (SimplePrincipalCollection)obj;
			/**
			 * 获取用户登录的，@link SampleRealm.doGetAuthenticationInfo(...)方法中
			 * return new SimpleAuthenticationInfo(user,user.getPswd(), getName());的user 对象。
			 */
			obj = spc.getPrimaryPrincipal();
			if(null != obj && obj instanceof User){
				//存储session + user 综合信息
				UserVO userVo = new UserVO((User)obj);
				//最后一次和系统交互的时间
				userVo.setLastAccess(session.getLastAccessTime());
				//主机的ip地址
				userVo.setHostName(session.getHost());
				//session ID
				userVo.setSessionId(session.getId().toString());
				//session最后一次与系统交互的时间
				userVo.setLastLoginTime(session.getLastAccessTime());
				//回话到期 ttl(ms)
				userVo.setTimeout(session.getTimeout());
				//session创建时间
				userVo.setStartTime(session.getStartTimestamp());
				//是否踢出
				userVo.setSessionStatus(false);
				return userVo;
			}
		}
		return null;
	}
	
	
	public UserVO  getCommonUser(User user,Session session){
		
		//获取session登录信息。
	
		if(null !=user){
			System.out.println("userService-getCommonUser:"+session);
			//存储session + user 综合信息
			UserVO userVo = new UserVO((User)user);
			//最后一次和系统交互的时间
			userVo.setLastAccess(session.getLastAccessTime());
			//主机的ip地址
			userVo.setHostName(session.getHost());
			//session ID
			userVo.setSessionId(session.getId().toString());
			//session最后一次与系统交互的时间
			userVo.setLastLoginTime(session.getLastAccessTime());
			//回话到期 ttl(ms)
			userVo.setTimeout(session.getTimeout());
			//session创建时间
			userVo.setStartTime(session.getStartTimestamp());
			//是否踢出
			userVo.setSessionStatus(false);
			return userVo;
		}
		return null;
	}
	
	
	@Transactional(isolation=Isolation.READ_COMMITTED)
	public  void  editUserInfo(UserVO  userVO, String oper){
		
		try {
			
			ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
		    opsForValue.set("shiro_redis_refresh_"+userVO.getNickName(), "true");
			//redisUtil.set("shiro_redis_refresh:"+userVO.getNickName(),"true");
			System.out.println("shiro_redis_refresh"+userVO.getNickName()+  "   true");
			//删除操作-删除这个角色数据，以及角色和资源之间的关联关系
			if(StringConstant.DATA_DELETE.equals(oper)){
				
				String userVOId =userVO.getId();
				if(StringUtils.isNotEmpty(userVOId)){
					//1.先删除之前可能存在的角色和资源关系
					EntityWrapper<UserRole> userAndRoleMapper=new EntityWrapper<UserRole>();
					userAndRoleMapper.eq("user_id", userVOId);
					userRoleMapper.delete(userAndRoleMapper);
					
					EntityWrapper<UserGroup> userAndGroupyMapper=new EntityWrapper<UserGroup>();
					userAndGroupyMapper.eq("user_id", userVOId);
					userGroupMapper.delete(userAndGroupyMapper);
					
					//2.删除角色的信息
					userMapper.deleteById(userVOId);
					
					
				}
			//新增或更新操作- 这个角色 ，以及角色合角色之间的关联关系
			}else if(StringConstant.DATA_ADD.equals(oper)||StringConstant.DATA_EDIT.equals(oper)){
				
				
				System.out.println("开始新增或更新用户 Start");
				if(StringConstant.DATA_ADD.equals(oper)){
					userVO.setId(null);
				}
				User user=new User();
				BeanUtils.copyProperties(user, userVO);
				
				
				//生成四位随机盐
				String generateSalt= RandomStringUtil.generateSaltWord();
				user.setSalt(generateSalt);
				Object simpleHash = new SimpleHash(MyCredentialsMatcher.HASH_A_NAME, user.getPassword(),  
						ByteSource.Util.bytes(user.getNickName()+user.getSalt()),MyCredentialsMatcher.HASH_A_NAME_NUM); 
				user.setPassword(simpleHash.toString());
				user.setStatus(1);
				//1.新增或修改用户信息
				
				System.out.println("user:"+user);
				user.insertOrUpdate();
				
				
				String roles = userVO.getRoles();
				//根据角色类型获取角色ID
				Map<String,String>  roleTypeAndRoleIdMap=new HashMap<String,String>();
				Set<String>  roleTypeSet=new HashSet<String>();
				
				Map<String,String>  groupTypeAndGroupIdMap=new HashMap<String,String>();
				Set<String>  groupTypeSet=new HashSet<String>();
				
				//1.得到通过角色类型，获取用户和角色关联关系  更新数据
				if(StringUtils.isNotEmpty(roles)){
					String[] rolesList = roles.split(",");
					
					if(rolesList!=null&&rolesList.length>0){
						for (String roleType : rolesList) {
							roleType= roleType.replaceAll("\\s*", ""); 
							roleTypeSet.add(roleType);
						}
					}
					
			       if(CollectionUtils.isNotEmpty(roleTypeSet)){
			    	   
			    	    List<String>  roleTypeList=new ArrayList<String>();
			    	    roleTypeList.addAll(roleTypeSet);
			    	    
			    	    EntityWrapper<Role> entityRole=new  EntityWrapper<Role>(); 
			    	    entityRole.in("type", roleTypeList);
			            List<Role>  roleList = roleMapper.selectList(entityRole);
						if(CollectionUtils.isNotEmpty(roleList)){
							for (Role role : roleList) {
								roleTypeAndRoleIdMap.put(role.getType(), role.getId());
							}
						}
						
					}
					
					String  userId= user.getId();
					if(roleTypeAndRoleIdMap!=null&&userId!=null){
						
						//2.先删除之前可能存在的用户和角色关系
						EntityWrapper<UserRole> entityRoleMapper=new EntityWrapper<UserRole>();
						entityRoleMapper.eq("user_id", userId);
						userRoleMapper.delete(entityRoleMapper);
						
						
						//3.重新保存用户和角色之间的关系
						for (String roleType : roleTypeSet) {
							String roleId = roleTypeAndRoleIdMap.get(roleType);
							if(roleId!=null){
								UserRole newUserRole=new UserRole();
								newUserRole.setRoleId(roleId);
								newUserRole.setUserId(userId);
								userRoleMapper.insert(newUserRole);
							}
						}
					}
				}else{
					//删除关系
					
					if(StringConstant.DATA_EDIT.equals(oper)){
						//2.先删除之前可能存在的用户和角色关系
						EntityWrapper<UserRole> entityRoleMapper=new EntityWrapper<UserRole>();
						entityRoleMapper.eq("user_id", userVO.getId());
						userRoleMapper.delete(entityRoleMapper);
					}
					
				}
				
				
				 String groups = userVO.getGroups();
				//1.得到通过角色类型，获取用户和角色关联关系  更新数据
				if(StringUtils.isNotEmpty(groups)){
					String[] groupsList = groups.split(",");
					
					if(groupsList!=null&&groupsList.length>0){
						for (String groupType : groupsList) {
							groupType= groupType.replaceAll("\\s*", ""); 
							groupTypeSet.add(groupType);
						}
					}
					
			       if(CollectionUtils.isNotEmpty(groupTypeSet)){
			    	   
			    	    List<String>  groupTypeList=new ArrayList<String>();
			    	    groupTypeList.addAll(groupTypeSet);
			    	    
			    	    EntityWrapper<Group> entityGroup=new  EntityWrapper<Group>(); 
			    	    entityGroup.in("type", groupTypeList);
			            List<Group>  groupList = groupMapper.selectList(entityGroup);
						if(CollectionUtils.isNotEmpty(groupList)){
							for (Group group : groupList) {
								groupTypeAndGroupIdMap.put(group.getType(), group.getId());
							}
						}
						
					}
					
					String  userId= user.getId();
					if(groupTypeAndGroupIdMap!=null&&userId!=null){
						
						//2.先删除之前可能存在的用户和用户组关系
						EntityWrapper<UserGroup> entityGroupMapper=new EntityWrapper<UserGroup>();
						entityGroupMapper.eq("user_id", userId);
						userGroupMapper.delete(entityGroupMapper);
						
						
						//3.重新保存用户和用户组之间的关系
						for (String groupType : groupTypeSet) {
							String groupId = groupTypeAndGroupIdMap.get(groupType);
							if(groupId!=null){
								UserGroup newUseGroup=new UserGroup();
								newUseGroup.setGroupId(groupId);
								newUseGroup.setUserId(userId);
								userGroupMapper.insert(newUseGroup);
							}
						}
					}
				}else{
					//删除关系
					if(StringConstant.DATA_EDIT.equals(oper)){
						//
						EntityWrapper<UserGroup> entityGroupMapper=new EntityWrapper<UserGroup>();
						entityGroupMapper.eq("user_id", userVO.getId());
						userGroupMapper.delete(entityGroupMapper);
					}
				}
				
				
			}
			
			
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
