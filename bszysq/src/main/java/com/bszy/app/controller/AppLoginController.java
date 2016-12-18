package com.bszy.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.app.form.AppLoginForm;
import com.bszy.app.service.AppUserService;
import com.bszy.app.vo.AppUserMine;
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
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public AjaxResult login_json(@RequestBody AppLoginForm form, HttpServletRequest request){
		AjaxResult ar = new AjaxResult();
		String mobile = form.getMobile(); 
		if(FormValid.isEmpty(mobile)){ ar.t_fail("1201"); return ar; }
		if(!FormValid.isMobile(mobile)){ ar.t_fail("1202"); return ar; }
		
		String pwd = form.getPwd();
		if(FormValid.isEmpty(pwd)){ ar.t_fail("1203"); return ar; }
		if(!FormValid.len(pwd, 6, 16)){ ar.t_fail("1204"); return ar; }
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", mobile);
		params.put("pwd", DigestUtils.md5Hex(pwd));
		AppUserMine mo = service.login(params);
		if(mo != null){
			if(mo.getIsdel() != 0){
				ar.t_fail("1028");  return ar;	// 被冻结
			}else{
				ar.t_succ_not_null(mo, "1101"); // 成功
			}
		}else{
			ar.t_fail("1004");	// 帐号或密码错误
		}
		return ar;
	}
	
	// 未登录
	@ResponseBody
	@RequestMapping(value = "/unlogin", method = RequestMethod.GET)
	public AjaxResult unlogin_json(HttpServletRequest request){
		AjaxResult ar = new AjaxResult();
		ar.t_fail("1001");
		return ar;
	}
	
	// 登录超时
	@ResponseBody
	@RequestMapping(value = "/unauthor", method = RequestMethod.GET)
	public AjaxResult unauthor_json(HttpServletRequest request){
		AjaxResult ar = new AjaxResult();
		ar.t_fail("1005");
		return ar;
	}
	
	// TODO 已登录
	
	@ResponseBody
	@RequestMapping(value = "/uc/logout", method = RequestMethod.GET)
	public AjaxResult logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult ar = new AjaxResult();
		ar.t_succ();
		return ar;
	}
	
}
