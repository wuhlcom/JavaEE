package fish.yu.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import fish.yu.entity.Role;
import fish.yu.entity.RolesAndPermissions;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @since 2017-02-10
 */
public interface RoleMapper extends BaseMapper<Role> {
	
	
	/**
	 * 
	 * 方法的描述 : 通过角色集合and资源集合
	 * @param id
	 * @return
	 * @throws RuntimeException
	 *
	 */
	public  List<RolesAndPermissions> selectPermissionsByRoleIds(List  list)throws RuntimeException;
	

}