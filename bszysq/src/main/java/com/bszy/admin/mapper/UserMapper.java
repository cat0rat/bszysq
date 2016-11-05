package com.bszy.admin.mapper;

import java.util.List;

import com.bszy.admin.pojo.User;

/**
 * 
 * @author Mao 2016年10月26日 上午1:23:12
 */
public interface UserMapper {
	public User get(Long id);
	public List<User> list();
}