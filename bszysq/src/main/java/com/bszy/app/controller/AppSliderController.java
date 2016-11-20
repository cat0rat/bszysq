package com.bszy.app.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.pojo.SliderSearch;
import com.bszy.admin.service.SliderService;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;

@Controller
@RequestMapping("/app/slider")
public class AppSliderController extends BaseController {
	
	@Inject
	private SliderService service;
	
	// TODO json
	
	@ResponseBody
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public AjaxResult get_json_var(@PathVariable Long id){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.get(id));
		return ar;
	}

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public AjaxResult list_json_get(){
		return list_json(new SliderSearch());
	}
	
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public AjaxResult list_json(@RequestBody SliderSearch bs){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.list_simple(bs).getRows());
		return ar;
	}
	
}
