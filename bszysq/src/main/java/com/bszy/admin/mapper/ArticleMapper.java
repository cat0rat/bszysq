package com.bszy.admin.mapper;

import java.util.Map;

import com.bszy.admin.pojo.Article;
import com.mao.ssm.BaseMapper;

/**
 * 
 * @author Mao 2016年10月29日 下午1:43:19
 */
public interface ArticleMapper extends BaseMapper<Article> {
	public Long recoms(Map<String, Object> map);
}