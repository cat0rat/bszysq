package com.bszy.app.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.app.pojo.AppUser;
import com.bszy.app.security.AppUserCurUtil;
import com.bszy.app.service.AppUserService;
import com.mao.lang.MUtil;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;

@Controller
@RequestMapping("/app")
public class AppLoginController extends BaseController {
	@Inject
	private AppUserService service;
	
	// TODO json
	
	@ResponseBody
	@RequestMapping(value = "/login.json", method = RequestMethod.POST)
	public void login_json(String name, String pwd, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(name == null || name.length() == 0){ ar.t_fail("1101"); return ; }
		UsernamePasswordToken token = new UsernamePasswordToken(name, pwd, false);
		try {
			SecurityUtils.getSubject().login(token);
			Long uid = MUtil.toLong(SecurityUtils.getSubject().getPrincipal(), 0L);	// 当前登录用户ID
			AppUser mo = service.get(uid);	// 获取当前登录用户
			AppUserCurUtil.to_session(request.getSession(), mo);	// 存入 session
			ar.t_succ_not_null(mo, "1101");
		} catch (Exception e) {
			ar.t_fail("1004");
		}
	}
	
	// 未登录
	@ResponseBody
	@RequestMapping(value = "/unlogin.json")
	public void unlogin_json(HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_fail("1001");
	}
	
	// 登录超时
	@ResponseBody
	@RequestMapping(value = "/unauthor.json")
	public void unauthor_json(HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_fail("1005");
	}
	
	// TODO 已登录
	
	@ResponseBody
	@RequestMapping(value = "/uc/logout.json")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult ar = ajaxResult(request);
		AppUserCurUtil.logout();
		ar.t_succ();
	}
	
}
