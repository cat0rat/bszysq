package com.bszy.app.security;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.bszy.app.pojo.AppUser;

/**
 * 
 * @author Mao 2016年10月28日 上午1:39:12
 */
public class AppUserCurUtil {
	
	public static final String Cur_User = "uc_session";		// 当前用户在session中存储的名字
	public static final String Cur_Shiro_User = "uc_shiro_session";	// 当前用户类型在shiroSession中存储的名字
	public static final String Cur_Shiro_Auth = "uc_shiro_auth";		// 当前用户的权限在shirioSession中的名字
	public static final String Cur_Rolex = "uc_rolex";		// 当前用户角色
	

	/** 当前用户的 shiro session */
	public static Session cur_shiro_session() {
		return SecurityUtils.getSubject().getSession();
	}

	/** 当前用户的ID */
	public static Long cur_uid() {
		return cur_user().getId();
	}

	/** 当前登录用户的信息  */
	public static AppUserCurInfo cur_user() {
		Object uo = cur_shiro_session().getAttribute(Cur_Shiro_User);
		return uo != null ? (AppUserCurInfo) uo : new AppUserCurInfo();
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
	public static AppUserCurInfo to_session(HttpSession session, AppUser appUser){
		// 存入HttpSession
		session.setAttribute(Cur_User, appUser);
		session.setAttribute(Cur_Rolex, appUser.getRolex());
		
		// 将用户信息存入shiro session
		AppUserCurInfo ui = to_shiro_user(appUser);
		cur_shiro_session().setAttribute(Cur_Shiro_User, ui);
		return ui;
	}
	
	/** 转换成shiro用户对象 */
	public static AppUserCurInfo to_shiro_user(AppUser mo) {
		AppUserCurInfo ui = new AppUserCurInfo();
		if (mo != null) {
			ui.setId(mo.getId());
			ui.setName(mo.getName());
			ui.setRolex(mo.getRolex());
			ui.setPwd(mo.getPwd());
		}
		return ui;
	}
	
	/** 登出 */
	public static void logout() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
		}
	}
	
}
