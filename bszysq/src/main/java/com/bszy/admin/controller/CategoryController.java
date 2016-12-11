package com.bszy.admin.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.form.CategoryForm;
import com.bszy.admin.pojo.Category;
import com.bszy.admin.service.CategoryService;
import com.bszy.admin.vo.CategorySearch;
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
	
	// TODO json
	
	@ResponseBody
	@RequestMapping(value = "/get.json", method = RequestMethod.POST)
	public AjaxResult get_json(Long id){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.get(id));
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete.json", method = RequestMethod.POST)
	public AjaxResult delete_json(Long id){
		AjaxResult ar = new AjaxResult();
		ar.t_result(service.delete(id));
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/dels.json", method = RequestMethod.POST)
	public AjaxResult dels_json(String ids){
		AjaxResult ar = new AjaxResult();
		if(ids == null || !FormValid.isIds(ids)){ ar.t_fail("1501"); return ar; }
		ar.t_result(service.dels(ids));
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/list.json", method = RequestMethod.POST)
	public AjaxResult list_json(CategorySearch bs){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.list(bs));
		return ar;
	}

	@ResponseBody
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	public AjaxResult add_json(CategoryForm form){
		AjaxResult ar = new AjaxResult();
		if(form == null){ ar.t_fail("1501"); return ar; }
		
		String name = form.getName();	// 名称
		if(FormValid.isEmpty(name)){ ar.t_fail("6001"); return ar; }
		if(!FormValid.len(name, 1, 20)){ ar.t_fail("6002"); return ar; }
		
		Category mo = new Category();
		mo.init_add();
		mo.setName(name);
		mo.setImg(form.getImg());
		mo.setSortn(MUtil.toInt(form.getSortn(), 0));
		boolean rb = service.add(mo);
		ar.t_result(rb);
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/update.json", method = RequestMethod.POST)
	public AjaxResult update_json(CategoryForm form){
		AjaxResult ar = new AjaxResult();
		if(form == null){ ar.t_fail("1501"); return ar; }
		Long id = MUtil.toLong(form.getId());
		if(!MUtil.isId(id)){ ar.t_fail("1501"); return ar; }
		
		String name = form.getName();
		if(FormValid.isEmpty(name)){ ar.t_fail("6001"); return ar; }
		if(!FormValid.len(name, 1, 20)){ ar.t_fail("6002"); return ar; }
		
		Category mo = new Category();
		mo.init_update();
		mo.setId(id);
		mo.setName(name);
		mo.setImg(form.getImg());
		mo.setSortn(MUtil.toInteger(form.getSortn()));
		mo.setIsdel(MUtil.toInteger(form.getIsdel()));
		boolean rb = service.update(mo);
		ar.t_result(rb);
		return ar;
	}
	
}
