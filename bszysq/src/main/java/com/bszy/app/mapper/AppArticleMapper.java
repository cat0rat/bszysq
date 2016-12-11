package com.bszy.app.mapper;

import java.util.List;

import com.bszy.app.pojo.AppArticle;
import com.bszy.app.vo.AppArticleSearch;
import com.mao.ssm.BaseMapper;

/**
 * 
 * @author Mao 2016年10月29日 下午5:56:19
 */
public interface AppArticleMapper extends BaseMapper<AppArticle> {
	public List<AppArticle> list(AppArticleSearch bs);
	public Long lscount(AppArticleSearch bs);
}