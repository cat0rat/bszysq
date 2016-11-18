package com.bszy.admin.mapper;

import java.util.List;
import java.util.Map;

import com.bszy.admin.pojo.Article;
import com.bszy.app.pojo.AppArticleDetail;
import com.bszy.app.pojo.AppArticleSimple;
import com.mao.ssm.BaseMapper;
import com.mao.ssm.BaseSearch;

/**
 * 
 * @author Mao 2016年10月29日 下午1:43:19
 */
public interface ArticleMapper extends BaseMapper<Article> {
	public AppArticleDetail detail(Long id);
	public Long recoms(Map<String, Object> map);
	
	public List<Article> commlist(BaseSearch bs);
	public Long commlscount(BaseSearch bs);
	
	public List<AppArticleSimple> list_simple(BaseSearch bs);
	public Long lscount_simple(BaseSearch bs);
}