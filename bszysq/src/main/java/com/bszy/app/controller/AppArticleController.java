package com.bszy.app.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.pojo.Article;
import com.bszy.admin.pojo.ArticleSearch;
import com.bszy.admin.service.ArticleService;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.ssm.BasePage;

@Controller
@RequestMapping("/app/article")
public class AppArticleController extends BaseController {
	
	@Inject
	private ArticleService service;
	
	@ResponseBody
	@RequestMapping("/get.json")
	public Article get(Long id, HttpServletRequest request){
		Article mo = service.get(id);
		return mo;
	}
	
	@ResponseBody
	@RequestMapping("/list.json")
	public AjaxResult list(ArticleSearch bs, HttpServletRequest request){
		AjaxResult ar = new AjaxResult();
		BasePage<Article> bp = service.list(bs);
		ar.t_succ(bp);
		return ar;
	}
	
}
