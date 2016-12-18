package com.bszy.app.mapper;

import java.util.Map;

import com.bszy.app.pojo.AppUser;
import com.bszy.app.vo.AppUserAuth;
import com.bszy.app.vo.AppUserGetui;
import com.bszy.app.vo.AppUserMine;
import com.bszy.app.vo.AppUserRePwd;
import com.bszy.app.vo.AppUserSimple;
import com.mao.ssm.BaseMapper;

/**
 * 
 * @author Mao 2016年10月26日 上午1:23:12
 */
public interface AppUserMapper extends BaseMapper<AppUser> {
	public AppUserMine login(Map<String, String> params);
	
	public AppUserMine mine(Long id);
	public AppUserSimple simple(Long id);
	public Long hasName(String name);
	public Long repwd(AppUserRePwd params);
	public Long findpwd(AppUser params);
	
	// 个推
	public Long bindGetuiCid(AppUser params);
	public AppUserGetui getuiSimple(Long id);	// 获取 个推简单信息
	
	// 用户认证状态
	public AppUserAuth user_auth(Long id);
	public AppUserAuth mobile_auth(String mobile);
	
}