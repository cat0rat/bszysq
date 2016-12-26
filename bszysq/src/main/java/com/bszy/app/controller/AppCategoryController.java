package com.bszy.app.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.service.CategoryService;
import com.bszy.admin.vo.ArttagSearch;
import com.bszy.admin.vo.CategorySearch;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;

@Controller
@RequestMapping("/app/category")
public class AppCategoryController extends BaseController {
	
	@Inject
	private CategoryService service;
	
	@ResponseBody
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public AjaxResult get_json_var(@PathVariable Long id){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.get(id));
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public AjaxResult list(CategorySearch bs){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.list(bs));
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/list_art", method = RequestMethod.GET)
	public AjaxResult list_art(){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.list_art(1));
		return ar;
	}
	
	
	/** 只列出可以发表主题的版块 */
	@ResponseBody
	@RequestMapping(value = "/list_idname", method = RequestMethod.GET)
	public AjaxResult list_idname_json(){
		AjaxResult ar = new AjaxResult();
		CategorySearch bs = new CategorySearch();
		bs.setAddart(0);
		ar.t_succ_not_null(service.list_idname(bs).getRows());
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/list_idname_all", method = RequestMethod.GET)
	public AjaxResult list_idname_all_json(){
		AjaxResult ar = new AjaxResult();
		CategorySearch bs = new CategorySearch();
		ar.t_succ_not_null(service.list_idname(bs).getRows());
		return ar;
	}
	
}
