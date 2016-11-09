package com.bszy.admin.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.form.CategoryForm;
import com.bszy.admin.pojo.Category;
import com.bszy.admin.pojo.CategorySearch;
import com.bszy.admin.service.CategoryService;
import com.mao.lang.MUtil;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.ssm.FormValid;

@Controller
@RequestMapping("/admin/category")
public class CategoryController extends BaseController {
	
	@Inject
	private CategoryService service;
	
	// TODO page
	@RequestMapping(value = {"/", "/page.do"}, method = RequestMethod.GET)
	public String page(){
		return "admin/category";
	}
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String list(){
		return "admin/category/list";
	}
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(){
		return "admin/category/add";
	}
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Long id, Model model) {
		model.addAttribute("bean", service.get(id));
		return "admin/category/edit";
	}
	@RequestMapping(value = "/view.do", method = RequestMethod.GET)
	public String view(Long id, Model model) {
		model.addAttribute("bean", service.get(id));
		return "admin/category/view";
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
	public void list_json(CategorySearch bs, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.list(bs));
	}

	@ResponseBody
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	public void add_json(CategoryForm form, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(form == null){ ar.t_fail("1501"); return ; }
		
		String name = form.getName();	// 名称
		if(FormValid.isEmpty(name)){ ar.t_fail("6001"); return ; }
		if(!FormValid.range(name, 1, 20)){ ar.t_fail("6002"); return ; }
		
		Category mo = new Category();
		mo.init_add();
		mo.setName(name);
		mo.setImg(form.getImg());
		mo.setSortn(MUtil.toInt(form.getSortn(), 0));
		boolean rb = service.add(mo);
		ar.t_result(rb);
	}
	
	@ResponseBody
	@RequestMapping(value = "/update.json", method = RequestMethod.POST)
	public void update_json(CategoryForm form, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(form == null){ ar.t_fail("1501"); return ; }
		Long id = MUtil.toLong(form.getId());
		if(!MUtil.isId(id)){ ar.t_fail("1501"); return ; }
		
		String name = form.getName();
		if(FormValid.isEmpty(name)){ ar.t_fail("6001"); return ; }
		if(!FormValid.range(name, 1, 20)){ ar.t_fail("6002"); return ; }
		
		Category mo = new Category();
		mo.init_update();
		mo.setId(id);
		mo.setName(name);
		mo.setImg(form.getImg());
		mo.setSortn(MUtil.toInteger(form.getSortn()));
		mo.setIsdel(MUtil.toInteger(form.getIsdel()));
		boolean rb = service.update(mo);
		ar.t_result(rb);
	}
	
}
