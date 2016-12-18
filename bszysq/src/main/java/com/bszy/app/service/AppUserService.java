package com.bszy.app.service;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bszy.app.mapper.AppUserMapper;
import com.bszy.app.pojo.AppUser;
import com.bszy.app.vo.AppUserMine;
import com.bszy.app.vo.AppSimpleUser;
import com.bszy.app.vo.AppUserRePwd;
import com.mao.ssm.BaseService;

@Service
public class AppUserService extends BaseService<AppUser, AppUserMapper> {
	@Inject
	private AppUserMapper mapper;
	public AppUserMapper mapper(){return mapper;}
	
	public AppUserMine login(Map<String, String> params){
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
	
	public AppUserMine mine(Long id){
		return mapper.mine(id);
	}
	
	public AppSimpleUser simple(Long id){
		return mapper.simple(id);
	}
	
	// 修改密码
	public boolean repwd(AppUserRePwd params){
		Long rn = mapper.repwd(params);
		return rn != null && rn == 1;
	}
	// 找回密码
	public boolean findpwd(AppUser params){
		Long rn = mapper.findpwd(params);
		return rn != null && rn == 1;
	}
	// 绑定个推ClientID
	public boolean bindGetuiCid(AppUser params){
		Long rn = mapper.bindGetuiCid(params);
		return rn != null && rn == 1;
	}
}
