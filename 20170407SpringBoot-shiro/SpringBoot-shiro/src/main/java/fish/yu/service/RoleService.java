package fish.yu.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import fish.yu.dao.PermissionMapper;
import fish.yu.dao.RoleAndPermissionMapper;
import fish.yu.dao.RoleMapper;
import fish.yu.entity.FrontPage;
import fish.yu.entity.Permission;
import fish.yu.entity.Role;
import fish.yu.entity.RoleAndPermission;
import fish.yu.entity.RoleVO;
import fish.yu.entity.RolesAndPermissions;
import fish.yu.util.StringConstant;

/**
 * 
 * @author yuliang-ds1
 *
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {
	
	@Autowired
	RoleMapper roleMapper;
	
	
	@Autowired
	RoleAndPermissionMapper  roleAndPermissionMapper;
	
	
	@Autowired
	PermissionMapper  permissionMapper;
	
	/**
	 * 通过角色集合查询权限集合
	 * 方法的描述 : 
	 * @param list
	 * @return
	 * @throws RuntimeException
	 *
	 */
    @ShiroMethodSecurity(permission=ShiroAopPermission.QUERY_METHOD)
	public  List<RolesAndPermissions> queryPermissionsByRoleIds(List<String>  idslist)throws RuntimeException{
		return roleMapper.selectPermissionsByRoleIds(idslist);
	}
	
	
	
	/**
	 * 查询所有在线用户
	 * @param frontPage
	 * @return
	 */
    @ShiroMethodSecurity(permission=ShiroAopPermission.QUERY_METHOD)
	public Page<RoleVO> queryAllRoles(FrontPage<RoleVO> frontPage) {
		
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
		
		List<Role> roleList = roleMapper.selectPage(page, Condition.instance());
		List<RoleVO> roleVOList = new ArrayList<RoleVO>();
		if(CollectionUtils.isNotEmpty(roleList)){
			
			//角色id集合
			Set<String>  roleIdSet=new  HashSet<String>();
			//获取角色和资源之间的关联关系
			Map<String,List<String>>  roleIdAndPermsMap=new HashMap<String,List<String>>();
			for (Role role : roleList) {
				roleIdSet.add(role.getId());
			}
			
			if(CollectionUtils.isNotEmpty(roleIdSet)){
				List<String>  roleIdList=new ArrayList<String>();
				roleIdList.addAll(roleIdSet);
				List<RolesAndPermissions> selectPermissionsByRoleIds = roleMapper.selectPermissionsByRoleIds(roleIdList);
				
				//封装展现的权限数据格式  用户名用以区分资源
				if(CollectionUtils.isNotEmpty(selectPermissionsByRoleIds)){
					for (RolesAndPermissions rolesAndPermissions : selectPermissionsByRoleIds) {
						String rid = rolesAndPermissions.getRid();
						String pname = rolesAndPermissions.getPname();
						String purl = rolesAndPermissions.getPurl();
						List<String> pnameList = roleIdAndPermsMap.get(rid);
						if(pnameList==null){
							pnameList=new ArrayList<String>();
						}
						if(StringUtils.isNotEmpty(purl)){
							
							//URL资源权限
							if(purl.indexOf("/")>-1){
								
								StringBuilder sb=new StringBuilder();
								String[] split = purl.split("/");
								if(split!=null&&split.length>=2){
									sb.append(pname);
									sb.append("[");
									sb.append(StringConstant.USER_COLON);
									sb.append(split[1]);
									sb.append("]");
								    pnameList.add(sb.toString());
								}
							
							//角色操作权限	
							}else{
								StringBuilder sb=new StringBuilder();
								sb.append(pname);
								sb.append("[");
								sb.append(StringConstant.GROUP_COLON);
								sb.append(purl);
								sb.append("]");
							    pnameList.add(sb.toString());
							}
						}
						
					    roleIdAndPermsMap.put(rid,pnameList);
					}
				}
				
			}
			
			
			for (Role role : roleList) {
				String rid = role.getId();
				RoleVO  roleVO=new RoleVO(role);
				if(roleIdAndPermsMap!=null){
					List<String> pnameList = roleIdAndPermsMap.get(rid);
					if(CollectionUtils.isNotEmpty(pnameList)){
						StringBuilder sb=new StringBuilder();
						Boolean flag=false;
						for (String pname : pnameList) {
							if(flag){
								sb.append(",");
							}
							sb.append(pname);
							flag=true;
						}
						roleVO.setPerms(sb.toString());
					}
				}
				roleVOList.add(roleVO);
			}
		}
		
		Page<RoleVO> pageList = frontPage.getPagePlus();
		if(CollectionUtils.isNotEmpty(roleVOList)){
			pageList.setRecords(roleVOList);
			pageList.setTotal(roleList.size());
		}
		return pageList;
	}
	
	
	
	/**
	 * 新增-更新-删除 Role信息--角色合角色之间的关系
	 * @param RoleVO
	 */
	@Transactional(isolation=Isolation.READ_COMMITTED)
    @ShiroMethodSecurity(permission=ShiroAopPermission.EDIT_METHOD)
	public  void  editRoleInfo(RoleVO  roleVO, String oper){
		
		
		try {
			
			//删除操作-删除这个角色数据，以及角色和资源之间的关联关系
			if(StringConstant.DATA_DELETE.equals(oper)){
				
				String roleVOId = roleVO.getId();
				if(StringUtils.isNotEmpty(roleVOId)){
					//1.先删除之前可能存在的角色和资源关系
					EntityWrapper<RoleAndPermission> entityMapper=new EntityWrapper<RoleAndPermission>();
					entityMapper.eq("role_id", roleVOId);
					roleAndPermissionMapper.delete(entityMapper);
					
					//2.删除角色的信息
					roleMapper.deleteById(roleVOId);
					
					
				}
			//新增或更新操作- 这个角色 ，以及角色合角色之间的关联关系
			}else if(StringConstant.DATA_ADD.equals(oper)||StringConstant.DATA_EDIT.equals(oper)){
				
				if(StringConstant.DATA_ADD.equals(oper)){
					roleVO.setId(null);
				}
				Role role=new Role();
				BeanUtils.copyProperties(role, roleVO);
				
				String perms = roleVO.getPerms();
				
				//1.新增或修改角色信息
				role.insertOrUpdate();
				
				Map<String,String>  permisionNameAndpermissionIdMap=new HashMap<String,String>();
				Set<String>   permissionNameSet=new HashSet<String>();
				
				//得到通过资源名称，获取角色合角色之间的关联关系  更新数据
				if(StringUtils.isNotEmpty(perms)){
					String[] permsList = perms.split(",");
					
					if(permsList!=null&&permsList.length>0){
						for (String permissionName : permsList) {
					       permissionName= permissionName.replaceAll("\\s*", ""); 
						   if(permissionName.indexOf("[")>-1){
							   int indexOf = permissionName.indexOf("[");
							   String subPermissionName= permissionName.substring(0, indexOf);
							   permissionNameSet.add(subPermissionName);
						   }else{
							   permissionNameSet.add(permissionName);
						   }
							//roleTypeSet.add(roleType);
						}
					}
					
					
			
			       if(CollectionUtils.isNotEmpty(permissionNameSet)){
			    	   
			    	    List<String>  permissionNameList=new ArrayList<String>();
			    	    permissionNameList.addAll(permissionNameSet);
			    	    
			    	    EntityWrapper<Permission> entityPermission=new  EntityWrapper<Permission>(); 
			    	    entityPermission.in("name", permissionNameList);
			            List<Permission> permissionList = permissionMapper.selectList(entityPermission);
						if(CollectionUtils.isNotEmpty(permissionList)){
							for (Permission permission : permissionList) {
								permisionNameAndpermissionIdMap.put(permission.getName(), permission.getId());
							}
						}
						
					}
					
					String roleId = role.getId();
					if(permisionNameAndpermissionIdMap!=null&&roleId!=null){
						
						//2.先删除之前可能存在的角色和资源关系
						EntityWrapper<RoleAndPermission> entityMapper=new EntityWrapper<RoleAndPermission>();
						entityMapper.eq("role_id", roleId);
						roleAndPermissionMapper.delete(entityMapper);
						
						
						//3.重新保存角色合角色之间的关系
						for (String permissionName : permissionNameSet) {
							String permissionId = permisionNameAndpermissionIdMap.get(permissionName);
							
							if(roleId!=null){
								RoleAndPermission newgRoleAndPermission=new RoleAndPermission();
								newgRoleAndPermission.setRoleId(roleId);
								newgRoleAndPermission.setPermissionId(permissionId);
								roleAndPermissionMapper.insert(newgRoleAndPermission);
							}
							
						}
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
