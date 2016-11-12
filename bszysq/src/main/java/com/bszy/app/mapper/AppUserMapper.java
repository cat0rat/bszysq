package com.bszy.app.mapper;

import java.util.Map;

import com.bszy.app.pojo.AppUser;
import com.mao.ssm.BaseMapper;

/**
 * 
 * @author Mao 2016年10月26日 上午1:23:12
 */
public interface AppUserMapper extends BaseMapper<AppUser> {
	public AppUser login(Map<String, String> params);
	
	public AppUser mine(Long id);
}