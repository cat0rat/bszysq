package com.bszy.admin.security;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.session.Session;

import com.bszy.admin.pojo.Admin;

/**
 * 
 * @author Mao 2016年10月28日 上午1:39:12
 */
public class AdminCurUtil {
	
	public static final String Cur_User = "admin_session";		// 当前用户在session中存储的名字
	public static final String Cur_Shiro_User = "admin_shiro_session";	// 当前用户类型在shiroSession中存储的名字
	public static final String Cur_Shiro_Auth = "admin_shiro_auth";		// 当前用户的权限在shirioSession中的名字
	public static final String Cur_Rolex = "admin_rolex";		// 当前用户角色
	

	/** 当前用户的 shiro session */
	public static Session cur_shiro_session() {
		return SecurityUtils.getSubject().getSession();
	}

	/** 当前用户的ID */
	public static Long cur_uid() {
		return cur_user().getId();
	}

	/** 当前登录用户的信息  */
	public static AdminCurInfo cur_user() {
		Object uo = cur_shiro_session().getAttribute(Cur_Shiro_User);
		return uo != null ? (AdminCurInfo) uo : new AdminCurInfo();
	}

	/** 获取当前用户权限shiro对象 */
	public static AuthorizationInfo cur_shiro_auth() {
		SimpleAuthorizationInfo info = (SimpleAuthorizationInfo)cur_shiro_session().getAttribute(Cur_Shiro_Auth);
		if(info == null){
			info = new SimpleAuthorizationInfo();
			cur_shiro_session().setAttribute(Cur_Shiro_Auth, info);
		}
		return info;
	}
	
	/** 保存用户信息到session */
	public static AdminCurInfo to_session(HttpSession session, Admin admin){
		// 存入HttpSession
		session.setAttribute(Cur_User, admin);
		session.setAttribute(Cur_Rolex, admin.getRolex());
		
		// 将用户信息存入shiro session
		AdminCurInfo ui = to_shiro_user(admin);
		cur_shiro_session().setAttribute(Cur_Shiro_User, ui);
		return ui;
	}
	
	/** 转换成shiro用户对象 */
	public static AdminCurInfo to_shiro_user(Admin mo) {
		AdminCurInfo ui = new AdminCurInfo();
		if (mo != null) {
			ui.setId(mo.getId());
			ui.setName(mo.getName());
			ui.setRolex(mo.getRolex());
			ui.setPwd(mo.getPwd());
		}
		return ui;
	}
	
}
