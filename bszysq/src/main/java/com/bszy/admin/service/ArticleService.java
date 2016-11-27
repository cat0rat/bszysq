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
import com.bszy.app.pojo.AppArticleDetail;
import com.bszy.app.pojo.AppArticleSimple;
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
	
	public AppArticleDetail detail(Long id){
		return mapper().detail(id);
	}
	
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
	public BasePage<AppArticleSimple> commlist(BaseSearch bs){
		BasePage<AppArticleSimple> bp = new BasePage<AppArticleSimple>();
		bs.start_i();
		List<AppArticleSimple> rows = mapper.commlist(bs);
		Long total = mapper.commlscount(bs);

		bp.t_param(bs.page_i(), bs.limit_i());
		bp.t_result(total, rows);
		if(rows != null && rows.size() > 0){
			AppArticleSimple mo = rows.get(rows.size() - 1);
			if(mo != null){
				Long lastid = mo.getId();
				bp.setLastid(lastid != null ? lastid : 0L);
			}
		}
		
		return bp;
	}
	
	// 带评论
	public AppArticleDetail get_comms(Long id){
		AppArticleDetail mo = mapper().detail(id);
		CommentSearch bs = new CommentSearch();
		bs.setArtid(mo.getId());
		mo.setComms(commentService.listref(bs).getRows());
		return mo;
	}
	

	/**
	 * 基方法 查询
	 * @param bs
	 */
	public BasePage<AppArticleSimple> list_simple(BaseSearch bs){
		BasePage<AppArticleSimple> bp = new BasePage<AppArticleSimple>();
		bs.start_i();
		List<AppArticleSimple> rows = mapper().list_simple(bs);
		Long total = mapper().lscount_simple(bs);

		bp.t_param(bs.page_i(), bs.limit_i());
		bp.t_result(total, rows);
		if(rows != null && rows.size() > 0){
			AppArticleSimple mo = rows.get(rows.size() - 1);
			if(mo != null){
				Long lastid = mo.getId();
				bp.setLastid(lastid != null ? lastid : 0L);
			}
		}
		
		return bp;
	}
	
}
