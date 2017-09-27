package com.dunan.login.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.dunan.login.bean.User;
import com.dunan.login.repository.UserRepository;



@Service
public class UserService {
	@Resource
	private UserRepository userRepository;
	
	public User findByUsername(String name){
		User user = userRepository.findByUsername(name);
		return user;
	}
	
	public User findByUsernameAndDisused(String name, Integer disused){
		User user = userRepository.findByUsernameAndDisused(name, disused);
		return user;
	}
	
	public User findByUsernameAndIsdel(String name, Integer isdel){
		User user = userRepository.findByUsernameAndIsdel(name, isdel);
		return user;
	}
	
	
}
