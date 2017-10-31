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
import fish.yu.entity.FrontPage;
import fish.yu.entity.Permission;
import fish.yu.entity.PermissionVO;
import fish.yu.entity.Role;
import fish.yu.entity.RoleAndPermission;
import fish.yu.util.StringConstant;



/**
 * 
 * @author yuliang-ds1
 *
 */
@Service
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {
	
	
	@Autowired
	PermissionMapper permissionMapper;
	
	
	@Autowired
	RoleAndPermissionMapper  roleAndPermissionMapper;
	
	/**
	 * 查询所有在线用户
	 * @param frontPage
	 * @return
	 */
	@ShiroMethodSecurity(permission=ShiroAopPermission.QUERY_METHOD)
	public Page<PermissionVO> queryAllPermissions(FrontPage<PermissionVO> frontPage) {
		
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
		
		List<Permission> permissionList = permissionMapper.selectPage(page, Condition.instance());
		List<PermissionVO>  permissionVOList=new ArrayList<PermissionVO>();
		
		if(CollectionUtils.isNotEmpty(permissionList)){
			for (Permission p : permissionList) {
				PermissionVO  pvo=new PermissionVO(p);
				String url = p.getUrl();
				if(url!=null){
					if(url.indexOf("/")>-1){
						StringBuilder sb=new StringBuilder();
						String[] split = url.split("/");
						if(split!=null&&split.length>=2){
							sb.append("[");
							sb.append(StringConstant.USER_COLON);
							sb.append(split[1]);
							sb.append("]");
						}
						pvo.setPermissionIdentify(sb.toString());
						
					}else{
						StringBuilder sb=new StringBuilder();
						sb.append("[");
						sb.append(StringConstant.GROUP_COLON);
						sb.append(url);
						sb.append("]");
						pvo.setPermissionIdentify(sb.toString());
					}
					
				}
				
				permissionVOList.add(pvo);
			}
		}
		Page<PermissionVO> pageList = frontPage.getPagePlus();
		if(CollectionUtils.isNotEmpty(permissionVOList)){
		
			pageList.setRecords(permissionVOList);
			pageList.setTotal(permissionVOList.size());
			
		}
		return pageList;
	}
	
	
	
	/**
	 * 新增-更新-删除 Role信息--角色合角色之间的关系
	 * @param RoleVO
	 */
	@Transactional(isolation=Isolation.READ_COMMITTED)
    @ShiroMethodSecurity(permission=ShiroAopPermission.EDIT_METHOD)
	public  void  editPermissionInfo(Permission  permission, String oper){
		
		
		try {
			
			//删除操作-删除这个角色数据，以及角色和资源之间的关联关系
			if(StringConstant.DATA_DELETE.equals(oper)){
				
				String permissionId = permission.getId();
				if(StringUtils.isNotEmpty(permissionId)){
					//先判断数据之间的引用关系
					EntityWrapper<RoleAndPermission> entityMapper=new EntityWrapper<RoleAndPermission>();
					entityMapper.eq("permission_id", permissionId);
					List<RoleAndPermission> selectList = roleAndPermissionMapper.selectList(entityMapper);
					if(selectList==null||selectList.size()==0){
						//如果没有被引用删除 如果被引用不能删除 
						permissionMapper.deleteById(permissionId);
					}
					
					
				}
			//新增或更新操作- 这个角色 ，以及角色合角色之间的关联关系
			}else if(StringConstant.DATA_ADD.equals(oper)||StringConstant.DATA_EDIT.equals(oper)){
				
				if(StringConstant.DATA_ADD.equals(oper)){
					permission.setId(null);
				}
				
				permission.insertOrUpdate();
				
			}
			
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
