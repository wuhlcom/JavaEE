package com.zhilu.device.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.zhilu.device.bean.TblIotDevice;

public interface TblIotDeviceRepository
		extends JpaRepository<TblIotDevice, String>, JpaSpecificationExecutor<TblIotDevice> {
	/**
	 * spring data jpa 会自动注入实现（根据方法命名规范） 自定义简单查询
	 * 
	 * 自定义的简单查询就是根据方法名来自动生成SQL，
	 * 主要的语法是findXXBy,readAXXBy,queryXXBy,countXXBy,getXXBy后面跟属性名称：
	 * User findByUserName(String userName); 也使用一些加一些关键字And、 Or	
	 * User findByUserNameOrEmail(String username, String email); 修改、删除、统计也是类似语法
	 * Long deleteById(Long id);
	 * Long countByUserName(String userName) 
	 * 基本上SQL体系中的关键词都可以使用，例如：LIKE、	 * IgnoreCase、 OrderBy。
	 * List<User> findByEmailLike(String email);
	 * User findByUserNameIgnoreCase(String userName);	 
	 * List<User> findByUserNameOrderByEmailDesc(String email);
	 */
	List<TblIotDevice> findTblIotDeviceById(String id);
	
	List<TblIotDevice> findByMac(String mac);	
	List<TblIotDevice> findTblIotDeviceByMac(String mac);	

	List<TblIotDevice> findTblIotDeviceByName(String name);

	List<TblIotDevice> findTblIotDeviceByUseridAndId(String userid, String id);

	// 使用原生sql
	@Query(value = "select * from TblIotDevice limit ?1", nativeQuery = true)
	List<TblIotDevice> findAllTblIotDevicesByCount(int count);
	

	@Transactional	
	@Modifying
	@Query("delete from TblIotDevice where id = :id and userid = :userid")
	void deleteByUseridAndId(String id,String userid);

	
		
//	@Query("select * from TblIotDevice")
//	List<TblIotDevice> findAll();
	
	// ------------- 使用 @Query 注解
	// // 没有参数的查询
	// @Query("select p from Person p where p.id = (select max(p2.id) from
	// Person p2)")
	// TblIotDevice getMaxIdPerson();

	// 使用数字占位符来接收实参,实参与这里设置的顺序必须一致
	// @Query("select p from Person p where lastName=?1 and email=?2")
	// TblIotDevice readPersonByLastNameAndEmail(String lastName,String email);
	// 使用符号占位符,实参可以不按顺序
	// @Query("select p from Person p where email=:email and lastName=:name")
	// TblIotDevice readPersonByLastNameAndEmailThroughName(@Param("name")
	// String lastName,@Param("email") String email);
	//
	// 使用 like,未使用%号,实参要加%才能模糊匹配
	// @Query("select p from Person p where lastName like ?1")
	// TblIotDevice readPersonByLike(String likeName);
	//
	// @Query like 注解支持使用百分号,实参不用加%
	// @Query("select p from Person p where lastName like %?1%")
	// TblIotDevice readPersonByLike2(String likeName);
	//
	// @Query 注解支持使用百分号
	// @Query("select p from Person p where lastName like %:lastName%")
	// TblIotDevice readPersonByLike3(@Param("lastName")String name);
	//
	// // 使用原生的 SQL
	// @Query(value="select * from jpa_person p1 where p1.last_name like
	// %:lastName%",nativeQuery=true)
	// TblIotDevice getPersonUsingOriginSQL(@Param("lastName")String lastName);
}
