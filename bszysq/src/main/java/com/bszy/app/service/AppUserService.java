package com.bszy.app.service;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bszy.app.mapper.AppUserMapper;
import com.bszy.app.pojo.AppUser;
import com.mao.ssm.BaseService;

@Service
public class AppUserService extends BaseService<AppUser, AppUserMapper> {
	@Inject
	private AppUserMapper mapper;
	public AppUserMapper mapper(){return mapper;}
	
	public AppUser login(Map<String, String> params){
		return mapper.login(params);
	}
}
