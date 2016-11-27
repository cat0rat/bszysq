package com.bszy.app.mapper;

import java.util.Map;

import com.bszy.app.pojo.AppUser;
import com.bszy.app.pojo.AppUserGetui;
import com.bszy.app.pojo.AppUserRePwd;
import com.mao.ssm.BaseMapper;

/**
 * 
 * @author Mao 2016年10月26日 上午1:23:12
 */
public interface AppUserMapper extends BaseMapper<AppUser> {
	public AppUser login(Map<String, String> params);
	
	public AppUser mine(Long id);
	public AppUser simple(Long id);
	public Long hasName(String name);
	public Long repwd(AppUserRePwd params);
	public Long findpwd(AppUser params);
	
	// 个推
	public Long bindGetuiCid(AppUser params);
	public AppUserGetui getuiSimple(Long id);	// 获取 个推简单信息
	
}