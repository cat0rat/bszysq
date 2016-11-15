package com.bszy.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.app.pojo.AppUser;
import com.bszy.app.service.AppUserService;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.ssm.FormValid;

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
		if(FormValid.isEmpty(name)){ ar.t_fail("1201"); return ; }
		if(!FormValid.isMobile(name)){ ar.t_fail("1202"); return ; }
		
		if(FormValid.isEmpty(pwd)){ ar.t_fail("1203"); return ; }
		if(!FormValid.len(pwd, 6, 16)){ ar.t_fail("1204"); return ; }
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		params.put("pwd", DigestUtils.md5Hex(pwd));
		AppUser mo = service.login(params);
		if(mo != null){
			if(mo.getIsdel() != 0){
				ar.t_fail("1028");  return ;	// 被冻结
			}else{
				ar.t_succ_not_null(mo, "1101"); // 成功
			}
		}else{
			ar.t_fail("1004");	// 帐号或密码错误
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
		ar.t_succ();
	}
	
}
