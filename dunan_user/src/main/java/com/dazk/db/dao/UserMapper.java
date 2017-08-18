/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月27日 上午11:17:24 * 
*/
package com.dazk.db.dao;

import java.util.List;

import com.dazk.common.util.UserUtilMapper;
import com.dazk.db.model.User;
import com.dazk.db.param.UserParam;

public interface UserMapper extends UserUtilMapper<User> {

//	@Select("SELECT * FROM user")
//	@Results({ @Result(property = "loginName", column = "login_name") })
//	List<User> getAll();
//
//	@Select("SELECT * FROM user WHERE id = #{id}")
//	@Results({ @Result(property = "loginName", column = "login_name") })
//	User getOne(Long id);
//
//	@Insert("INSERT INTO user(name,login_name,sex) VALUES(#{name}, #{login_name}, #{sex})")
//	int insert(User user);
//
//	@Update("UPDATE users SET name=#{name}, WHERE id =#{id}")
//	void update(User user);
//
//	@Delete("DELETE FROM user WHERE id =#{id}")
//	void delete(Long id);
//
//	// select u.username,u.role_id,r.name from user as u inner join role as r
//	// where u.role_id=r.id;
//	// select ="com.zwk.dao.UserInfoDAO.getUserInfoById",getUserinfoById方法必须存在
////	@Select("select * from test_user u where u.id = #{id}")
//	//@Results({ @Result(id = true, property = "id", column = "id"), @Result(property = "password", column = "password"),
//		//	@Result(property = "userInfo", column = "info_id", one = @One(select = "com.zwk.dao.UserInfoDAO.getUserInfoById")) })
//	//public User getById(@Param("id") String id);
//	
//	@Select("select username,id,nickname from user u where u.parent_user = #{parent_user}")
//	List<User> getUserByPid();

	List<User> queryUser(UserParam obj);

	int queryUserCount(UserParam obj);

}
