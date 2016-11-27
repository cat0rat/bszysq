package com.bszy.app.controller;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.app.form.AppRegForm;
import com.bszy.app.form.AppRepwdForm;
import com.bszy.app.form.AppUserForm;
import com.bszy.app.pojo.AppUser;
import com.bszy.app.pojo.AppUserRePwd;
import com.bszy.app.security.SmscodeTimer;
import com.bszy.app.service.AppUserService;
import com.mao.lang.MUtil;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.ssm.FormValid;

@Controller
@RequestMapping("/app")
public class AppUserController extends BaseController {
	
	@Inject
	private AppUserService service;
	
	@ResponseBody
	@RequestMapping(value = "/user/simple/{id}", method = RequestMethod.GET)
	public AjaxResult simple_json_var(@PathVariable Long id){
		AjaxResult ar = new AjaxResult();
		if(!FormValid.isId(id)){ ar.t_fail("1501"); return ar; }
		ar.t_succ_not_null(service.simple(id));
		return ar;
	}
	
	// 忘记密码
	@ResponseBody
	@RequestMapping(value = "/user/findpwd", method = RequestMethod.POST)
	public AjaxResult reg_json(@RequestBody AppRegForm form, HttpSession session){
		AjaxResult ar = new AjaxResult();
		if(form == null){ ar.t_fail("1501"); return ar; }
		
		String mobile = form.getMobile();	// 帐号
		if(FormValid.isEmpty(mobile)){ ar.t_fail("1201"); return ar; }
		if(!FormValid.isMobile(mobile)){ ar.t_fail("1202"); return ar; }
		
		String pwd = form.getPwd();	// 密码
		if(FormValid.isEmpty(pwd)){ ar.t_fail("1203"); return ar; }
		if(!FormValid.len(pwd, 6, 16)){ ar.t_fail("1204"); return ar; }
		
//		String captcha = form.getCaptcha();	// 图形验证码
//		if(FormValid.isEmpty(captcha)){ ar.t_fail("1205"); return ar; }
//		String captcha_val = AppUserCurUtil.cur_captcha(session);
//		if(FormValid.isEmpty(captcha_val)){ ar.t_fail("1207"); return ar; }
//		if(!captcha_val.equalsIgnoreCase(captcha)){ ar.t_fail("1206"); return ar; }
		
		String smscode = form.getSmscode();	// 短信验证码
		if(FormValid.isEmpty(smscode)){ ar.t_fail("1208"); return ar; }
		String smscode_val = SmscodeTimer.smscode(mobile);
		if(FormValid.isEmpty(smscode_val)){ ar.t_fail("1210"); return ar; }
		if(!smscode_val.equalsIgnoreCase(smscode)){ ar.t_fail("1209"); return ar; }
		
		pwd = DigestUtils.md5Hex(pwd);
		
		AppUser mo = new AppUser();
		mo.init_update();
		mo.setName(mobile);
		mo.setPwd(pwd);
		
		boolean rb = service.findpwd(mo);
		ar.t_result(rb);
		return ar;
	}
	
	// TODO 需登录
	
	// 我的信息
	@ResponseBody
	@RequestMapping(value = "/uc/user/mine/{uid}", method = RequestMethod.GET)
	public AjaxResult mine_json_var(@PathVariable String uid){
		AjaxResult ar = new AjaxResult();
		Long id = MUtil.toLong(uid);	// 检查当前用户ID(登录)
		if(!FormValid.isId(id)){ ar.t_fail("1001"); return ar; }
		ar.t_succ_not_null(service.mine(id));
		return ar;
	}
	
	// 我的简单信息
	@RequestMapping(value = "/uc/user/simple/{uid}", method = RequestMethod.GET)
	public AjaxResult uc_simple_json_var(@PathVariable String uid){
		AjaxResult ar = new AjaxResult();
		Long id = MUtil.toLong(uid);	// 检查当前用户ID(登录)
		if(!FormValid.isId(id)){ ar.t_fail("1001"); return ar; }
		AppUser mo = service.simple(id);
		ar.t_succ(mo);
		return ar;
	}
	
	// 用户信息
	@ResponseBody
	@RequestMapping(value = "/uc/user/get/{uid}", method = RequestMethod.GET)
	public AjaxResult get_json_var(@PathVariable String uid){
		AjaxResult ar = new AjaxResult();
		Long id = MUtil.toLong(uid);	// 检查当前用户ID(登录)
		if(!FormValid.isId(id)){ ar.t_fail("1001"); return ar; }
		AppUser mo = service.get(id);
		ar.t_succ(mo);
		return ar;
	}
	
	// 认证
	@ResponseBody
	@RequestMapping(value = "/uc/user/auth", method = RequestMethod.POST)
	public AjaxResult uc_auth_json(@RequestBody AppUserForm form){
		AjaxResult ar = new AjaxResult();
		if(form == null){ ar.t_fail("1501"); return ar; }
		
		Long uid = MUtil.toLong(form.getUid());	// 检查当前用户ID(登录)
		if(!FormValid.isId(uid)){ ar.t_fail("1001"); return ar; }
		
		String tname = form.getTname();	// 姓名
		if(FormValid.isEmpty(tname)){ ar.t_fail("1270"); return ar; }
		if(!FormValid.len(tname, 2, 16)){ ar.t_fail("1271"); return ar; }
		
		String mobile = form.getMobile();	// 手机号
		if(FormValid.isEmpty(mobile)){ ar.t_fail("1212"); return ar; }
		if(!FormValid.isMobile(mobile)){ ar.t_fail("1213"); return ar; }
		
		String address = form.getAddress();	// 地址
		if(!FormValid.lenAllowNull(address, 2, 50)){ ar.t_fail("1272"); return ar; }
		
		AppUser mo = new AppUser();
		mo.init_update();
		mo.setId(uid);
		mo.setTname(tname);
		mo.setMobile(mobile);
		mo.setAddress(address);
		mo.setAuthx(2);
		
		boolean rb = service.update(mo);
		ar.t_result(rb);
		return ar;
	}
	
	// 更新个人信息
//	@ResponseBody
//	@RequestMapping(value = "/uc/user/update/nname", method = RequestMethod.POST)
//	public AjaxResult uc_update_nname_json(@RequestBody AppUserForm form){
//		return uc_update_json("nname", form);
//	}
	@ResponseBody
	@RequestMapping(value = "/uc/user/update/{field}", method = RequestMethod.POST)
	public AjaxResult uc_update_json(@PathVariable String field, @RequestBody AppUserForm form){
		AjaxResult ar = new AjaxResult();
		
		Long uid = MUtil.toLong(form.getUid());	// 检查当前用户ID(登录)
		if(!FormValid.isId(uid)){ ar.t_fail("1001"); return ar; }

		if(FormValid.isEmpty(field)){ ar.t_fail("1501"); return ar; }
		
		AppUser mo = new AppUser();
		switch (field) {
		case "head":
			String head = form.getHead();	// 头像
			if(FormValid.isEmpty(head)){ ar.t_fail("1273"); return ar; }
			if(!FormValid.len(head, 2, 500)){ ar.t_fail("1501"); return ar; }
			mo.setHead(head);
			break;
		case "tname":
			String tname = form.getTname();	// 姓名
			if(FormValid.isEmpty(tname)){ ar.t_fail("1270"); return ar; }
			if(!FormValid.len(tname, 2, 16)){ ar.t_fail("1271"); return ar; }
			mo.setTname(tname);
			break;
		case "nname":
			String nname = form.getNname();	// 昵称
			if(FormValid.isEmpty(nname)){ ar.t_fail("1274"); return ar; }
			if(!FormValid.len(nname, 2, 16)){ ar.t_fail("1275"); return ar; }
			mo.setNname(nname);
			break;
		case "mobile":
			String mobile = form.getMobile();	// 手机号
			if(FormValid.isEmpty(mobile)){ ar.t_fail("1212"); return ar; }
			if(!FormValid.isMobile(mobile)){ ar.t_fail("1213"); return ar; }
			mo.setMobile(mobile);
			break;
		case "address":
			String address = form.getAddress();	// 地址
			if(FormValid.isEmpty(address)){ ar.t_fail("1502"); return ar; }
			if(!FormValid.lenAllowNull(address, 2, 50)){ ar.t_fail("1272"); return ar; }
			mo.setAddress(address);
			break;
		default:
			ar.t_fail("1501"); return ar;
		}
		mo.init_update();
		mo.setId(uid);
		
		boolean rb = service.update(mo);
		ar.t_result(rb);
		return ar;
	}

	// 修改密码
	@ResponseBody
	@RequestMapping(value = "/uc/user/repwd", method = RequestMethod.POST)
	public AjaxResult repwd_json(@RequestBody AppRepwdForm form){
		AjaxResult ar = new AjaxResult();
		
		Long id = MUtil.toLong(form.getUid());	// 检查当前用户ID(登录)
		if(!FormValid.isId(id)){ ar.t_fail("1001"); return ar; }
		
		// 原密码
		String oldpwd = form.getOldpwd();
		if(FormValid.isEmpty(oldpwd)){ ar.t_fail("1280"); return ar; }
		if(!FormValid.len(oldpwd, 6, 16)){ ar.t_fail("1281"); return ar; }
		
		// 新密码
		String pwd = form.getPwd();
		if(FormValid.isEmpty(pwd)){ ar.t_fail("1203"); return ar; }
		if(!FormValid.len(pwd, 6, 16)){ ar.t_fail("1204"); return ar; }
		
		oldpwd = DigestUtils.md5Hex(oldpwd);
		pwd = DigestUtils.md5Hex(pwd);
		AppUserRePwd params = new AppUserRePwd();
		params.setId(id);
		params.setOldpwd(oldpwd);
		params.setPwd(pwd);
		params.setUtime(new Date());
		boolean rb = service.repwd(params);
		ar.t_result(rb, "1282");
		return ar;
	}

	// 绑定个推ClientID
	@ResponseBody
	@RequestMapping(value = "/uc/user/bindGetuiCid", method = RequestMethod.POST)
	public AjaxResult bindGetuiCid_json(@RequestBody AppUserForm form){
		AjaxResult ar = new AjaxResult();
		
		Long id = MUtil.toLong(form.getUid());	// 检查当前用户ID(登录)
		if(!FormValid.isId(id)){ ar.t_fail("1001"); return ar; }
		
		// 个推ClientID
		String getuicid = form.getGetuicid();
		if(FormValid.isEmpty(getuicid)){ ar.t_fail("2201"); return ar; }
		if(!FormValid.len(getuicid, 20, 50)){ ar.t_fail("2202"); return ar; }
		
		String phonename = form.getPhonename();	// 手机显示名
		if(!FormValid.lenAllowNull(phonename, 0, 50)){ ar.t_fail("2210"); return ar; }
		
		AppUser params = new AppUser();
		params.setId(id);
		params.setGetuicid(getuicid);
		params.setPhonetype(MUtil.toInt(form.getPhonetype(), 0));
		params.setPhonename(phonename);
		boolean rb = service.bindGetuiCid(params);
		ar.t_result(rb, "1282");
		return ar;
	}
	
}
