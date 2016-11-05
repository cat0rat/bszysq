package com.bszy.admin.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.pojo.Admin;
import com.bszy.admin.security.AdminCurUtil;
import com.bszy.admin.service.AdminService;
import com.mao.lang.MUtil;
import com.mao.ssm.AjaxResult;

@Controller
@RequestMapping("/admin")
public class LoginController {
	@Inject
	private AdminService service;
	
	// TODO page

	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect(request.getContextPath() + "/login.html");
	}

	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect(request.getContextPath() + "/login.html");
	}
	
	// TODO json
	
	@ResponseBody
	@RequestMapping(value = "/login.json")
	public AjaxResult login_json(String name, String pwd, HttpServletRequest request){
		AjaxResult ar = new AjaxResult();
		if(name == null || name.length() == 0){ 
			ar.t_fail("1101");
			return ar;
		}
		UsernamePasswordToken token = new UsernamePasswordToken(name, pwd, false);
		try {
			SecurityUtils.getSubject().login(token);
			// 当前登录用户ID
			Long uid = MUtil.toLong(SecurityUtils.getSubject().getPrincipal(), 0L);
			// 获取当前登录用户
			Admin mo = service.get(uid);
			// 存入 session
			AdminCurUtil.to_session(request.getSession(), mo);
			ar.t_succ();
		} catch (Exception e) {
			ar.t_fail("1004");
		}
		return ar;
	}
	
}
