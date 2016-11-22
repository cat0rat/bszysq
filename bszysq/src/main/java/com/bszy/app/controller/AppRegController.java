package com.bszy.app.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.app.form.AppRegForm;
import com.bszy.app.pojo.AppUser;
import com.bszy.app.security.AppUserCurUtil;
import com.bszy.app.security.SmscodeTimer;
import com.bszy.app.service.AppUserService;
import com.mao.captcha.Captcha;
import com.mao.lang.MUtil;
import com.mao.smscode.AliDayuSms;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.ssm.FormValid;

@Controller
@RequestMapping("/app")
public class AppRegController extends BaseController {
	
	@Inject
	private AppUserService service;
	
	// TODO json

	@RequestMapping(value = "/captcha.ico", method = RequestMethod.GET)
	public void captcha(Long gid, HttpServletResponse response, HttpSession session) {
		Captcha captcha = new Captcha();
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			String captchaValue = captcha.getCertPic(80, 30, os);
			AppUserCurUtil.captcha_to_session(session, captchaValue);
		} catch (IOException e) {
		}finally{
			if(os != null)
				try {
					os.close();
				} catch (IOException e) {
				}
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/smscode/{mobile}", method = RequestMethod.GET)
	public AjaxResult smscode_json(@PathVariable String mobile, /*String captcha, */HttpServletRequest request, HttpSession session){
		AjaxResult ar = new AjaxResult();
		
		// 手机号
		if(FormValid.isEmpty(mobile)){ ar.t_fail("1212"); return ar; }
		if(!FormValid.isMobile(mobile)){ ar.t_fail("1213"); return ar; }
		
//		// 图形验证码
//		if(FormValid.isEmpty(captcha)){ ar.t_fail("1205"); return ar; }
//		String captcha_val = AppUserCurUtil.cur_captcha(session);
//		if(FormValid.isEmpty(captcha_val)){ ar.t_fail("1207"); return ar; }
//		if(!captcha_val.equalsIgnoreCase(captcha)){ ar.t_fail("1206"); return ar; }
		
		// 短信验证码
		Long ss = SmscodeTimer.remaining(mobile, 60);
		if(ss != null){ ar.t_fail("1211"); return ar; }	// ar.setData(ss); 
		
		String smscode = MUtil.smscode();
		SmscodeTimer.build(mobile, smscode);
		//AppUserCurUtil.smscode_to_session(session, smscode);
		// ... 调用 发送短信验证码 接口
		String rstr = AliDayuSms.sendSmscode(mobile, smscode);
		if(rstr == null){
			System.out.println("手机号：" + mobile + ", 短信验证码：" + smscode);
			ar.t_succ();
			//ar.t_succ(smscode); 
			ar.setMsg("短信验证码已发送到您的手机上，请注意查收。");
		}else{
			ar.t_fail("1231", rstr);
		}
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public AjaxResult reg_json(@RequestBody AppRegForm form, HttpServletRequest request, HttpSession session){
		AjaxResult ar = new AjaxResult();
		if(form == null){ ar.t_fail("1501"); return ar; }
		
		String mobile = form.getMobile();	// 帐号
		if(FormValid.isEmpty(mobile)){ ar.t_fail("1201"); return ar; }
		if(!FormValid.isMobile(mobile)){ ar.t_fail("1202"); return ar; }
		
		String nname = form.getNname();	// 昵称
		if(!FormValid.lenAllowNull(nname, 2, 16)){ ar.t_fail("2101"); return ar; }
		
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
		
		Long id = service.hasName(mobile);
		if(FormValid.isId(id)){ ar.t_fail("1230"); return ar; }
		
		pwd = DigestUtils.md5Hex(pwd);
		
		AppUser mo = new AppUser();
		mo.init_add();
		mo.setName(mobile);
		mo.setNname(nname);
		mo.setPwd(pwd);
		mo.setRolex(0);
		mo.setAuthx(1);
		mo.setMobile(mobile);
		mo.setSex("0");
		mo.setLstat(0);
		mo.setLcount(0);
		
		boolean rb = service.add(mo);
		ar.t_result(rb);
		return ar;
	}
	
}
