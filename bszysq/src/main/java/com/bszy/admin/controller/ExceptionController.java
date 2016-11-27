package com.bszy.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mao.ssm.AjaxResult;

@Controller
@RequestMapping("/exception")
public class ExceptionController {
	
	// TODO 页面
	
	@RequestMapping(value = "/page/404", method = RequestMethod.GET)
	public String page_404() {
		return "exception/page/404";
	}
	
	@RequestMapping(value = "/page/500", method = RequestMethod.GET)
	public String page_500() {
		return "exception/page/500";
	}
	
	// TODO ajax
	
	@ResponseBody
	@RequestMapping(value = "/ajax/404", method = RequestMethod.GET)
	public AjaxResult ajax_404(){
		AjaxResult ar = new AjaxResult();
		ar.setCode("404");
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/500", method = RequestMethod.GET)
	public AjaxResult ajax_500(){
		AjaxResult ar = new AjaxResult();
		ar.setCode("500");
		return ar;
	}
	
	
}
