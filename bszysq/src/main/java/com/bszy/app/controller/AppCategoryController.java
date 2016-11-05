package com.bszy.app.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.app.pojo.AppCategory;
import com.bszy.app.service.AppCategoryService;
import com.mao.ssm.AjaxResult;

@Controller
@RequestMapping("/app/category")
public class AppCategoryController {
	
	@Inject
	private AppCategoryService service;
	
	@ResponseBody
	@RequestMapping("/get.json")
	public AjaxResult get(Long id){
		AjaxResult ar = new AjaxResult();
		AppCategory mo = service.get(id);
		ar.t_succ(mo);
		return ar;
	}
	
	@ResponseBody
	@RequestMapping("/list.json")
	public AjaxResult list(){
		AjaxResult ar = new AjaxResult();
		List<AppCategory> mos = service.list();
		ar.t_succ(mos);
		return ar;
	}
	
	@ResponseBody
	@RequestMapping("/list_art.json")
	public AjaxResult list_art(){
		AjaxResult ar = new AjaxResult();
		List<AppCategory> mos = service.list_art(1);
		ar.t_succ(mos);
		return ar;
	}
	
}
