package com.bszy.app.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.app.pojo.AppCategory;
import com.bszy.app.service.AppCategoryService;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;

@Controller
@RequestMapping("/app/category")
public class AppCategoryController extends BaseController {
	
	@Inject
	private AppCategoryService service;
	
	@ResponseBody
	@RequestMapping("/get.json")
	public void get(Long id, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		AppCategory mo = service.get(id);
		ar.t_succ(mo);
	}
	
	@ResponseBody
	@RequestMapping("/list.json")
	public void list(HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		List<AppCategory> mos = service.list();
		ar.t_succ(mos);
	}
	
	@ResponseBody
	@RequestMapping("/list_art.json")
	public void list_art(HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		List<AppCategory> mos = service.list_art(1);
		ar.t_succ(mos);
	}
	
}
