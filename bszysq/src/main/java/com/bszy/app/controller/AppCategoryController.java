package com.bszy.app.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.pojo.ArttagSearch;
import com.bszy.admin.service.CategoryService;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;

@Controller
@RequestMapping("/app/category")
public class AppCategoryController extends BaseController {
	
	@Inject
	private CategoryService service;
	
	@ResponseBody
	@RequestMapping("/get/{id}.json")
	public void get_json_var(@PathVariable Long id, HttpServletRequest request){
		get_json(id, request);
	}
	@ResponseBody
	@RequestMapping("/get.json")
	public void get_json(Long id, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.get(id));
	}
	
	@ResponseBody
	@RequestMapping("/list.json")
	public void list(HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.list());
	}
	
	@ResponseBody
	@RequestMapping("/list_art.json")
	public void list_art(HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.list_art(1));
	}
	

	@ResponseBody
	@RequestMapping(value = "/list_idval.json")
	public void list_idval_json(ArttagSearch bs, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.list_idval(bs));
	}
	
}
