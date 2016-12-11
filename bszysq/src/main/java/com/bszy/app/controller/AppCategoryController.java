package com.bszy.app.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.service.CategoryService;
import com.bszy.admin.vo.ArttagSearch;
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
	public AjaxResult list(){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.list());
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/list_art", method = RequestMethod.GET)
	public AjaxResult list_art(){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.list_art(1));
		return ar;
	}
	

	@ResponseBody
	@RequestMapping(value = {"/list_idval", "/list_idname"}, method = RequestMethod.GET)
	public AjaxResult list_idval_json(){
		AjaxResult ar = new AjaxResult();
		ArttagSearch bs = new ArttagSearch();
		ar.t_succ_not_null(service.list_idname(bs).getRows());
		return ar;
	}
	
}
