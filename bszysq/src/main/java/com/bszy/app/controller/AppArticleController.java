package com.bszy.app.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.app.pojo.AppArticle;
import com.bszy.app.pojo.AppArticleSearch;
import com.bszy.app.service.AppArticleService;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BasePage;

@Controller
@RequestMapping("/app/article")
public class AppArticleController {
	
	@Inject
	private AppArticleService service;
	
	@ResponseBody
	@RequestMapping("/get.json")
	public AppArticle get(Long id){
		AppArticle mo = service.get(id);
		return mo;
	}
	
	@ResponseBody
	@RequestMapping("/list.json")
	public AjaxResult list(AppArticleSearch bs, HttpServletRequest request){
		AjaxResult ar = new AjaxResult();
		BasePage<AppArticle> bp = service.list(bs);
		ar.t_succ(bp);
		return ar;
	}
	
}
