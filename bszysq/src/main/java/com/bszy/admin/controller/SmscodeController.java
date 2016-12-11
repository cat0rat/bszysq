package com.bszy.admin.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.form.SmscodeForm;
import com.bszy.admin.pojo.Smscode;
import com.bszy.admin.service.SmscodeService;
import com.bszy.admin.vo.SmscodeSearch;
import com.mao.lang.MUtil;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.ssm.FormValid;

@Controller
@RequestMapping("/admin/smscode")
public class SmscodeController extends BaseController {
	
	@Inject
	private SmscodeService service;
	
	// TODO page
	@RequestMapping(value = {"/", "/page.do"}, method = RequestMethod.GET)
	public String page(){
		return "admin/smscode";
	}
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String list(){
		return "admin/smscode/list";
	}
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(){
		return "admin/smscode/add";
	}
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Long id, Model model) {
		model.addAttribute("bean", service.get(id));
		return "admin/smscode/edit";
	}
	@RequestMapping(value = "/view.do", method = RequestMethod.GET)
	public String view(Long id, Model model) {
		model.addAttribute("bean", service.get(id));
		return "admin/smscode/view";
	}
	
	// TODO json
	
	@ResponseBody
	@RequestMapping(value = "/get.json", method = RequestMethod.POST)
	public void get_json(Long id, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.get(id));
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete.json", method = RequestMethod.POST)
	public void delete_json(Long id, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_result(service.delete(id));
	}
	
	@ResponseBody
	@RequestMapping(value = "/dels.json", method = RequestMethod.POST)
	public void dels_json(String ids, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(ids == null || !FormValid.isIds(ids)){ ar.t_fail("1501"); return ; }
		ar.t_result(service.dels(ids));
	}
	
	@ResponseBody
	@RequestMapping(value = "/list.json", method = RequestMethod.POST)
	public void list_json(SmscodeSearch bs, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.list(bs));
	}

	@ResponseBody
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	public void add_json(SmscodeForm form, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(form == null){ ar.t_fail("1501"); return ; }
		
		String mobile = form.getMobile();	// 手机号
		if(FormValid.isEmpty(mobile)){ ar.t_fail("12001"); return ; }
		if(!FormValid.isMobile(mobile)){ ar.t_fail("12002"); return ; }
		
		String code = form.getCode();	// 验证码(4字符)
		if(FormValid.isEmpty(mobile)){ ar.t_fail("12003"); return ; }
		if(!FormValid.len(code, 4)){ ar.t_fail("12004"); return ; }
		
		Integer typen = MUtil.toInteger(form.getTypen());	// 类型(0: 注册; 1: 忘记密码)
		if(typen == null || typen < 0){ ar.t_fail("12005"); return ; }
		
		Smscode mo = new Smscode();
		mo.init_add();
		
		mo.setMobile(mobile);
		mo.setCode(code);
		mo.setTypen(typen);
		
		boolean rb = service.add(mo);
		ar.t_result(rb);
	}
	
	@ResponseBody
	@RequestMapping(value = "/update.json", method = RequestMethod.POST)
	public void update_json(SmscodeForm form, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(form == null){ ar.t_fail("1501"); return ; }
		Long id = MUtil.toLong(form.getId());
		if(!MUtil.isId(id)){ ar.t_fail("1501"); return ; }
		
		String mobile = form.getMobile();	// 手机号
		if(FormValid.isEmpty(mobile)){ ar.t_fail("12001"); return ; }
		if(!FormValid.isMobile(mobile)){ ar.t_fail("12002"); return ; }
		
		String code = form.getCode();	// 验证码(4字符)
		if(FormValid.isEmpty(mobile)){ ar.t_fail("12003"); return ; }
		if(!FormValid.len(code, 4)){ ar.t_fail("12004"); return ; }
		
		Integer typen = MUtil.toInteger(form.getTypen());	// 类型(0: 注册; 1: 忘记密码)
		if(typen == null || typen < 0){ ar.t_fail("12005"); return ; }
		
		Smscode mo = new Smscode();
		mo.init_update();
		mo.setId(id);
		
		mo.setMobile(mobile);
		mo.setCode(code);
		mo.setTypen(typen);
		
		boolean rb = service.update(mo);
		ar.t_result(rb);
	}
	
}
