package com.bszy.app.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.app.pojo.AppUser;
import com.bszy.app.security.AppUserCurUtil;
import com.bszy.app.service.AppUserService;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;

@Controller
@RequestMapping("/app/uc/user")
public class AppUserController extends BaseController {
	
	@Inject
	private AppUserService service;
	
	@ResponseBody
	@RequestMapping(value = "/mine.json", method = RequestMethod.POST)
	public void mine_json(HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		Long id = AppUserCurUtil.cur_uid();
		AppUser mo = service.mine(id);
		ar.t_succ(mo);
	}
	

	@ResponseBody
	@RequestMapping(value = "/get.json", method = RequestMethod.POST)
	public void get_json(HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		Long id = AppUserCurUtil.cur_uid();
		AppUser mo = service.get(id);
		ar.t_succ(mo);
	}
	
}
