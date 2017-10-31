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
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
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
import fish.yu.dao.GroupAndRoleMapper;
import fish.yu.dao.GroupMapper;
import fish.yu.dao.RoleMapper;
import fish.yu.entity.FrontPage;
import fish.yu.entity.Group;
import fish.yu.entity.GroupAndRole;
import fish.yu.entity.GroupAndRoles;
import fish.yu.entity.GroupVO;
import fish.yu.entity.Role;
import fish.yu.util.StringConstant;

/**
 * 
 * @author yuliang-ds1
 *
 */
@Service
public class GroupService extends ServiceImpl<GroupMapper, Group> {
	
	private static final Log logger = LogFactory.getLog(GroupService.class);
	
	@Autowired
	GroupMapper  groupMapper;
	
	
	@Autowired
	RoleMapper  roleMapper;
	
	
	@Autowired
	GroupAndRoleMapper  groupAndRoleMapper;
	
	
	
	/**
	 * 根据用户组ids查询角色集合
	 * @param list
	 * @return
	 * @throws RuntimeException
	 */
	public  List<GroupAndRoles> selectRolesByGroupIds(List  list)throws RuntimeException{
		
		return groupMapper.selectRolesByGroupIds(list);
		
	}
	

	
	/**
	 * 查询所有用户组信息
	 * @param frontPage
	 * @return
	 */
    @ShiroMethodSecurity(permission=ShiroAopPermission.QUERY_METHOD)
	public Page<GroupVO> queryAllGroups(FrontPage<GroupVO> frontPage) {
		
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
		
		List<Group> groupList = groupMapper.selectPage(page, Condition.instance());
		List<GroupVO>  groupVOList=new ArrayList<GroupVO>();
		if(CollectionUtils.isNotEmpty(groupList)){
			for (Group group : groupList) {
				GroupVO  groupVO=new GroupVO(group);
				String roleId = group.getId();
				if(roleId!=null){
					GroupAndRoles selectRolesByGroupId = groupMapper.selectRolesByGroupId(roleId);
					if(selectRolesByGroupId!=null){
						List<Role> roles = selectRolesByGroupId.getRoles();
						if(CollectionUtils.isNotEmpty(roles)){
							Boolean flag=false;
							StringBuilder sb=new StringBuilder();
							for (Role role : roles) {
								if(flag){
									sb.append(",");
								}
								sb.append(role.getType());
								flag=true;
							}
							groupVO.setRoles(sb.toString());
						}
						
					}
				}
				groupVOList.add(groupVO);
			}
		}
		
		
		Page<GroupVO> pageList = frontPage.getPagePlus();
		if(CollectionUtils.isNotEmpty(groupList)){
		
			pageList.setRecords(groupVOList);
			pageList.setTotal(groupList.size());
			
		}
		return pageList;
	}
	
	/**
	 * 新增-更新-删除 用户组信息--用户组合角色之间的关系
	 * @param groupVO
	 */
	@Transactional(isolation=Isolation.READ_COMMITTED)
    @ShiroMethodSecurity(permission=ShiroAopPermission.EDIT_METHOD)
	public  void  editGroupInfo(GroupVO  groupVO, String oper){
		
		
		try {
			
			//删除操作-删除这个用户组数据，以及用户组合角色之间的关联关系
			if(StringConstant.DATA_DELETE.equals(oper)){
				
				String groupVOId = groupVO.getId();
				if(StringUtils.isNotEmpty(groupVOId)){
					//1.先删除之前可能存在的用户组和角色关系
					EntityWrapper<GroupAndRole> entityMapper=new EntityWrapper<GroupAndRole>();
					entityMapper.eq("group_id", groupVOId);
					groupAndRoleMapper.delete(entityMapper);
					
					//2.删除用户组的信息
					groupMapper.deleteById(groupVOId);
					
					
				}
			//新增或更新操作- 这个用户组 ，以及用户组合角色之间的关联关系
			}else if(StringConstant.DATA_ADD.equals(oper)||StringConstant.DATA_EDIT.equals(oper)){
				
				if(StringConstant.DATA_ADD.equals(oper)){
					groupVO.setId(null);
				}
				Group group=new Group();
				BeanUtils.copyProperties(group, groupVO);
				String roles = groupVO.getRoles();
				
				//1.新增或修改用户组信息
				group.insertOrUpdate();
				
				Map<String,String>  roleTypeAndRoleIdMap=new HashMap<String,String>();
				
				//得到通过角色type，获取用户组合角色之间的关联关系  更新数据
				if(StringUtils.isNotEmpty(roles)){
					String[] roleList = roles.split(",");
					Set<String>   roleTypeSet=new HashSet<String>();
					if(roleList!=null&&roleList.length>0){
						for (String roleType : roleList) {
							roleTypeSet.add(roleType);
						}
					}
					
					
			
					if(CollectionUtils.isNotEmpty(roleTypeSet)){
						
						List<Role> roleAllList = roleMapper.selectList(Condition.instance());
						if(CollectionUtils.isNotEmpty(roleAllList)){
							for (Role role : roleAllList) {
								roleTypeAndRoleIdMap.put(role.getType(), role.getId());
							}
						}
						
					}
					
					String groupId = group.getId();
					if(roleTypeAndRoleIdMap!=null&&groupId!=null){
						
						//2.先删除之前可能存在的用户组和角色关系
						EntityWrapper<GroupAndRole> entityMapper=new EntityWrapper<GroupAndRole>();
						entityMapper.eq("group_id", groupId);
						groupAndRoleMapper.delete(entityMapper);
						
						
						//3.重新保存用户组合角色之间的关系
						for (String roleType : roleTypeSet) {
							String roleId = roleTypeAndRoleIdMap.get(roleType);
							
							if(roleId!=null){
								GroupAndRole newgGroupAndRole=new GroupAndRole();
								newgGroupAndRole.setGroupId(groupId);
								newgGroupAndRole.setRoleId(roleId);;
								groupAndRoleMapper.insert(newgGroupAndRole);
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
