package com.bszy.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bszy.admin.mapper.ArticleMapper;
import com.bszy.admin.pojo.Article;
import com.bszy.admin.pojo.CommentSearch;
import com.mao.ssm.BasePage;
import com.mao.ssm.BaseSearch;
import com.mao.ssm.BaseService;

@Service
public class ArticleService extends BaseService<Article, ArticleMapper> {
	
	@Inject
	private ArticleMapper mapper;
	public ArticleMapper mapper(){return mapper;}
	@Inject
	private CommentService commentService;
	
	@Transactional
	public boolean recoms(String ids, Integer recom){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		map.put("recom", recom);
		Long rn = mapper().recoms(map);
		return rn != null && rn > 0;
	}
	
	/**
	 * 基方法 查询
	 * @param bs
	 */
	public BasePage<Article> commlist(BaseSearch bs){
		BasePage<Article> bp = new BasePage<Article>();
		bs.start_i();
		List<Article> rows = mapper.commlist(bs);
		Long total = mapper.commlscount(bs);

		bp.t_param(bs.page_i(), bs.limit_i());
		bp.t_result(total, rows);
		if(rows != null && rows.size() > 0){
			Article mo = rows.get(rows.size() - 1);
			if(mo != null){
				Long lastid = mo.getId();
				bp.setLastid(lastid != null ? lastid : 0L);
			}
		}
		
		return bp;
	}
	
	// 带评论
	public Article get_comms(Long id){
		Article mo = mapper().get(id);
		CommentSearch bs = new CommentSearch();
		bs.setArtid(mo.getId());
		bs.setLimit(5);
		mo.setComms(commentService.list(bs).getRows());
		return mo;
	}
	
}
