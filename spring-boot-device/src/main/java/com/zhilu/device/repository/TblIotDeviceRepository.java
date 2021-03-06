package com.zhilu.device.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zhilu.device.bean.TblIotDevice;

public interface TblIotDeviceRepository extends JpaRepository<TblIotDevice, String> {
	/**
	 * spring data jpa 会自动注入实现（根据方法命名规范）
	 * 
	 * @return
	 */
	TblIotDevice findTblIotDeviceById(String id);

	List<TblIotDevice> findTblIotDeviceByName(String name);

	// 使用原生sql
	@Query(value = "select * from TblIotDevice limit ?1", nativeQuery = true)
	List<TblIotDevice> findAllTblIotDevicesByCount(int count);

	// Page<TblIotDevice> findAll(Pageable pageable);
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
