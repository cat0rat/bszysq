package com.bszy.admin.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bszy.admin.mapper.ArticleMapper;
import com.bszy.admin.pojo.Article;
import com.mao.ssm.BaseService;

@Service
public class ArticleService extends BaseService<Article, ArticleMapper> {
	
	@Inject
	private ArticleMapper mapper;
	public ArticleMapper mapper(){return mapper;}
	
}
