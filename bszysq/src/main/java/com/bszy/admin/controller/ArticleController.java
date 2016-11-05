package com.bszy.admin.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.pojo.Article;
import com.bszy.admin.service.ArticleService;

@Controller
@RequestMapping("/admin/article")
public class ArticleController {
	
	@Inject
	private ArticleService service;
	
	@ResponseBody
	@RequestMapping("/get.json")
	public Article get(Long id){
		Article mo = service.get(id);
		return mo;
	}
	
}
