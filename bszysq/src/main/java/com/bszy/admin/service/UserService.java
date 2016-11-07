package com.bszy.admin.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bszy.admin.mapper.UserMapper;
import com.bszy.admin.pojo.User;
import com.mao.ssm.BaseService;

@Service
public class UserService extends BaseService<User, UserMapper> {
	@Inject
	private UserMapper mapper;
	public UserMapper mapper(){return mapper;}
	
}
