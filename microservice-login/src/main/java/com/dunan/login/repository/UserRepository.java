package com.dunan.login.repository;

import org.springframework.data.repository.CrudRepository;

import com.dunan.login.bean.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	public User findByUsername(String name);
	
	public User findByUsernameAndDisused(String name, Integer disused);
	
	public User findByUsernameAndIsdel(String name, Integer isdel);
}
