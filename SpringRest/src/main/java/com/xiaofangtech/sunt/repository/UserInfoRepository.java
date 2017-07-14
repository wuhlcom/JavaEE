package com.xiaofangtech.sunt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.xiaofangtech.sunt.bean.UserInfo;

public interface UserInfoRepository extends CrudRepository<UserInfo, Integer>{
	UserInfo findUserInfoById(int id);
	List<UserInfo> findUserInfoByRole(String role);
	
	@Query(value = "select * from t_user limit ?1", nativeQuery =true)
	List<UserInfo> findAllUsersByCount(int count);
}
