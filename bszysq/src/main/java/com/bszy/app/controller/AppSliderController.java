package com.bszy.app.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.pojo.SliderSearch;
import com.bszy.app.service.AppSliderService;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;

@Controller
@RequestMapping("/app/slider")
public class AppSliderController extends BaseController {
	
	@Inject
	private AppSliderService service;
	
	// TODO json
	@ResponseBody
	@RequestMapping(value = "/get/{id}.json")
	public void get_json_var(@PathVariable Long id, HttpServletRequest request){
		get_json(id, request);
	}
	@ResponseBody
	@RequestMapping(value = "/get.json")
	public void get_json(Long id, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.get(id));
	}
	
	@ResponseBody
	@RequestMapping(value = "/list.json")
	public void list_json(SliderSearch bs, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.list(bs));
	}
	
}
