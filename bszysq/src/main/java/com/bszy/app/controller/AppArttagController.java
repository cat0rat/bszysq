package com.bszy.app.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.pojo.ArttagSearch;
import com.bszy.admin.service.ArttagService;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;

@Controller
@RequestMapping("/app/arttag")
public class AppArttagController extends BaseController {
	
	@Inject
	private ArttagService service;
	
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
	public void list_json(ArttagSearch bs, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.list(bs).getRows());
	}
	
	@ResponseBody
	@RequestMapping(value = "/list_idval.json")
	public void list_idval_json(ArttagSearch bs, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.list_idval(bs).getRows());
	}

}
