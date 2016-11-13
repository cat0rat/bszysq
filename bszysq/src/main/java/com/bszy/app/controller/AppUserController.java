package com.bszy.app.controller;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.app.pojo.AppUser;
import com.bszy.app.pojo.AppUserRePwd;
import com.bszy.app.security.AppUserCurUtil;
import com.bszy.app.service.AppUserService;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.ssm.FormValid;

@Controller
@RequestMapping("/app")
public class AppUserController extends BaseController {
	
	@Inject
	private AppUserService service;
	
	@ResponseBody
	@RequestMapping(value = "/user/simple.json", method = RequestMethod.POST)
	public void simple_json(Long id, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(FormValid.isId(id)){ ar.t_fail("1501"); return ; }
		ar.t_succ_not_null(service.simple(id));
	}
	
	// TODO 需登录
	
	// 我的信息
	@ResponseBody
	@RequestMapping(value = "/uc/user/mine.json", method = RequestMethod.POST)
	public void mine_json(HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		Long id = AppUserCurUtil.cur_uid();
		ar.t_succ_not_null(service.mine(id));
	}
	
	// 我的简单信息
	@ResponseBody
	@RequestMapping(value = "/uc/user/simple.json", method = RequestMethod.POST)
	public void uc_simple_json(HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		Long id = AppUserCurUtil.cur_uid();
		AppUser mo = service.simple(id);
		ar.t_succ(mo);
	}
	
	// 用户信息
	@ResponseBody
	@RequestMapping(value = "/uc/user/get.json", method = RequestMethod.POST)
	public void get_json(HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		Long id = AppUserCurUtil.cur_uid();
		AppUser mo = service.get(id);
		ar.t_succ(mo);
	}
	
	// 认证
	@ResponseBody
	@RequestMapping(value = "/uc/user/auth.json", method = RequestMethod.POST)
	public void uc_simple_json(AppUser form, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(form == null){ ar.t_fail("1501"); return ; }
		
		String tname = form.getTname();	// 姓名
		if(FormValid.isEmpty(tname)){ ar.t_fail("1270"); return ; }
		if(!FormValid.len(tname, 2, 16)){ ar.t_fail("1271"); return ; }
		
		String mobile = form.getMobile();	// 手机号
		if(FormValid.isEmpty(mobile)){ ar.t_fail("1212"); return ; }
		if(!FormValid.isMobile(mobile)){ ar.t_fail("1213"); return ; }
		
		String address = form.getAddress();	// 地址
		if(!FormValid.lenAllowNull(address, 2, 50)){ ar.t_fail("1272"); return ; }
		
		Long id = AppUserCurUtil.cur_uid();
		AppUser mo = new AppUser();
		mo.init_update();
		mo.setId(id);
		mo.setTname(tname);
		mo.setMobile(mobile);
		mo.setAddress(address);
		mo.setAuthx(2);
		
		boolean rb = service.update(mo);
		ar.t_result(rb);
	}

	// 修改密码
	@ResponseBody
	@RequestMapping(value = "/uc/user/repwd.json", method = RequestMethod.POST)
	public void repwd_json(String oldpwd, String pwd, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		
		// 原密码
		if(FormValid.isEmpty(oldpwd)){ ar.t_fail("1280"); return ; }
		if(!FormValid.len(oldpwd, 6, 16)){ ar.t_fail("1281"); return ; }
		
		// 新密码
		if(FormValid.isEmpty(pwd)){ ar.t_fail("1203"); return ; }
		if(!FormValid.len(pwd, 6, 16)){ ar.t_fail("1204"); return ; }
		
		Long id = AppUserCurUtil.cur_uid();
		oldpwd = DigestUtils.md5Hex(oldpwd);
		pwd = DigestUtils.md5Hex(pwd);
		AppUserRePwd params = new AppUserRePwd();
		params.setId(id);
		params.setOldpwd(oldpwd);
		params.setPwd(pwd);
		params.setUtime(new Date());
		boolean rb = service.repwd(params);
		ar.t_result(rb, "1282");
	}
	
}
