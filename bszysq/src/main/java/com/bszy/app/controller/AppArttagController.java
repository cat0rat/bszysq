package com.bszy.app.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public AjaxResult get_json_var(@PathVariable Long id){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.get(id));
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public AjaxResult list_json(@RequestBody ArttagSearch bs){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.list(bs).getRows());
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
