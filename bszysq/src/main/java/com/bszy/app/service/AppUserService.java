package com.bszy.app.service;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bszy.app.mapper.AppUserMapper;
import com.bszy.app.pojo.AppUser;
import com.bszy.app.pojo.AppUserRePwd;
import com.mao.ssm.BaseService;

@Service
public class AppUserService extends BaseService<AppUser, AppUserMapper> {
	@Inject
	private AppUserMapper mapper;
	public AppUserMapper mapper(){return mapper;}
	
	public AppUser login(Map<String, String> params){
		return mapper.login(params);
	}
	
	public Long hasName(String name){
		return mapper.hasName(name);
	}
	
	@Transactional
	public boolean add(AppUser mo){
		Long rn = mapper.add(mo);
		return rn != null && rn == 1;
	}
	
	public AppUser mine(Long id){
		return mapper.mine(id);
	}
	
	public AppUser simple(Long id){
		return mapper.simple(id);
	}
	
	// 修改密码
	public boolean repwd(AppUserRePwd params){
		Long rn = mapper.repwd(params);
		return rn != null && rn == 1;
	}
}
