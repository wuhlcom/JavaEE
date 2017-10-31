/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月13日 上午11:41:22 * 
*/ 
package com.prison.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prison.entity.UserEntity;

@Repository  
	public interface UserDao extends CrudRepository<UserEntity,Long>{  	  
	    public UserEntity findByUsernameAndPassword(String username,String password);  	  
	}  

