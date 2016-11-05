package com.bszy.admin.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bszy.admin.mapper.UserMapper;
import com.bszy.admin.pojo.User;

@Service
public class UserService {
	
	@Inject
	private UserMapper mapper;
	
	public User get(Long id){
		return mapper.get(id);
	}
	
	public List<User> list(){
		return mapper.list();
	}
}
