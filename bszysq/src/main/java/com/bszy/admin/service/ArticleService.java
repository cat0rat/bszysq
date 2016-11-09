package com.bszy.admin.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bszy.admin.mapper.ArticleMapper;
import com.bszy.admin.pojo.Article;
import com.mao.ssm.BaseService;

@Service
public class ArticleService extends BaseService<Article, ArticleMapper> {
	
	@Inject
	private ArticleMapper mapper;
	public ArticleMapper mapper(){return mapper;}
	
	@Transactional
	public boolean recoms(String ids, Integer recom){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		map.put("recom", recom);
		Long rn = mapper().recoms(map);
		return rn != null && rn > 0;
	}
	
}
