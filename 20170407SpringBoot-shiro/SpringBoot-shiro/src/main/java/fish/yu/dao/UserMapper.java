package fish.yu.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import fish.yu.entity.User;
import fish.yu.entity.UserAndGroups;
import fish.yu.entity.UserAndRoles;

/**
 * 
 * 
 * @author yuliang-ds1
 *
 */
public interface UserMapper extends BaseMapper<User> {
	
	/**
	 * 
	 * 方法的描述 : 通过用户Id查询角色集合
	 * @param id
	 * @return
	 * @throws RuntimeException
	 *
	 */
	public  UserAndRoles  selectRolesByUserId(String id)throws RuntimeException;
	
	
	/**
	 * 
	 * 方法的描述 : 通过用户Ids查询角色集合
	 * @param id
	 * @return
	 * @throws RuntimeException
	 *
	 */
	public  List<UserAndRoles>  selectRolesByUserIds(List<String> ids)throws RuntimeException;
	
	
	
	
	/**
	 * 
	 * 方法的描述 : 通过用户Id查询分组集合
	 * @param id
	 * @return
	 * @throws RuntimeException
	 *
	 */
	public  UserAndGroups  selectGroupsByUserId(String id)throws RuntimeException;
	
	
	/**
	 * 
	 * 方法的描述 : 通过用户Ids查询分组集合
	 * @param id
	 * @return
	 * @throws RuntimeException
	 *
	 */
	public  List<UserAndGroups>  selectGroupsByUserIds(List<String> ids)throws RuntimeException;
	
	

}