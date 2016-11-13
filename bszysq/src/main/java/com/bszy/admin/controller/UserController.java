package com.bszy.admin.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.form.UserForm;
import com.bszy.admin.pojo.User;
import com.bszy.admin.pojo.UserSearch;
import com.bszy.admin.service.UserService;
import com.mao.lang.DateUtil;
import com.mao.lang.MUtil;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.ssm.FormValid;

@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {
	
	@Inject
	private UserService service;
	
	// TODO page

	@RequestMapping(value = {"/", "/page.do"}, method = RequestMethod.GET)
	public String page(){
		return "admin/user";
	}
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String list(){
		return "admin/user/list";
	}
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(){
		return "admin/user/add";
	}
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Long id, Model model) {
		model.addAttribute("bean", service.get(id));
		return "admin/user/edit";
	}
	@RequestMapping(value = "/view.do", method = RequestMethod.GET)
	public String view(Long id, Model model) {
		model.addAttribute("bean", service.get(id));
		return "admin/user/view";
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
	public void list_json(UserSearch bs, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.list(bs));
	}

	@ResponseBody
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	public void add_json(UserForm form, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(form == null){ ar.t_fail("1501"); return ; }
		
		String name = form.getName();	// 用户名
		if(FormValid.isEmpty(name)){ ar.t_fail("2102"); return ; }
		if(!FormValid.len(name, 3, 16)){ ar.t_fail("2103"); return ; }
		
		String nname = form.getNname();	// 昵称
		if(!FormValid.lenAllowNull(nname, 2, 16)){ ar.t_fail("2101"); return ; }
		
		String pwd = form.getPwd();	// 密码
		if(FormValid.isEmpty(pwd)){ ar.t_fail("2104"); return ; }
		if(!FormValid.len(pwd, 6, 16)){ ar.t_fail("2105"); return ; }
		pwd = DigestUtils.md5Hex(pwd);
		
		User mo = new User();
		mo.init_add();
		mo.setName(name);
		mo.setPwd(pwd);
		mo.setNname(nname);
		
		mo.setUnionid(form.getUnionid());
		mo.setOpenid(form.getOpenid());
		
		mo.setAuthx(1);
		mo.setMobile(form.getMobile());
		mo.setTname(form.getTname());
		mo.setCitycode(form.getCitycode());
		mo.setCity(form.getCity());
		mo.setAddress(form.getAddress());
		
		mo.setSex(form.getSex());
		mo.setHead(form.getHead());
		mo.setEmail(form.getEmail());
		mo.setBirth(DateUtil.toDate(form.getBirth(), null, false));
		mo.setLcount(0);
		mo.setLstat(0);
		
		boolean rb = service.add(mo);
		ar.t_result(rb);
	}
	
	@ResponseBody
	@RequestMapping(value = "/update.json", method = RequestMethod.POST)
	public void update_json(UserForm form, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(form == null){ ar.t_fail("1501"); return ; }
		Long id = MUtil.toLong(form.getId());
		if(!MUtil.isId(id)){ ar.t_fail("1501"); return ; }
		
		String nname = form.getNname();
		if(!FormValid.lenAllowNull(nname, 2, 16)){ ar.t_fail("2101"); return ; }
		
		User mo = new User();
		mo.init_update();
		mo.setId(id);
		mo.setNname(nname);
		
		mo.setUnionid(form.getUnionid());
		mo.setOpenid(form.getOpenid());
		
		mo.setAuthx(MUtil.toInteger(form.getAuthx()));
		mo.setMobile(form.getMobile());
		mo.setTname(form.getTname());
		mo.setCitycode(form.getCitycode());
		mo.setCity(form.getCity());
		mo.setAddress(form.getAddress());
		
		mo.setSex(form.getSex());
		mo.setHead(form.getHead());
		mo.setEmail(form.getEmail());
		mo.setBirth(DateUtil.toDate(form.getBirth(), null, false));
		
		boolean rb = service.update(mo);
		ar.t_result(rb);
	}
	
}
