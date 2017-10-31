package fish.yu.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import fish.yu.entity.Group;
import fish.yu.entity.GroupAndRoles;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @since 2017-02-10
 */
public interface GroupMapper extends BaseMapper<Group> {
	
	
	
	/**
	 * 通过id 查询角色集合
	 * @param id
	 * @return
	 * @throws RuntimeException
	 */
	public  GroupAndRoles selectRolesByGroupId(String  id)throws RuntimeException;
	

	/**
	 * 通过ids集合  查询角色集合
	 * 方法的描述 : 通过角色集合and资源集合
	 * @param 
	 * @return
	 * @throws RuntimeException
	 *
	 */
	public  List<GroupAndRoles> selectRolesByGroupIds(List  list)throws RuntimeException;
	

}