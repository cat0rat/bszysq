package com.bszy.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bszy.admin.mapper.ArticleMapper;
import com.bszy.admin.pojo.Article;
import com.bszy.admin.vo.ArticleSearch;
import com.bszy.admin.vo.CommentSearch;
import com.bszy.app.vo.AppArticleDetail;
import com.bszy.app.vo.AppArticleSimple;
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
	
	@Transactional
	public boolean dings(String ids, Integer recom){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		map.put("ding", recom);
		Long rn = mapper().dings(map);
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
	

	/**
	 * 为app列出主题使用, 优先查询置顶的, 置顶记录不足时, 查询正常 
	 * @param bs
	 */
	public BasePage<AppArticleSimple> list_for_app(ArticleSearch bs){
		/* 存取分析
			1:
			limit = 5
	
			D data = 8  --> tpage = 2
			5 3
			page = 2
			D q 3
			  c 2
			N q 2 and start = 0
			page = 3
			D q 0
			  c 5
			N q 5 and start = 2  --> (page - tpage - 1) * limit + (limit - (ttot % limit)) -- > (page - tpage) * limit - (ttot % limit)
			page = 4
			D q 0
			  c 5
			N q 5 and start = 7
			
			D data = 13  --> tpage = 3
			5 5 3
			page = 3
			D q 3
			  c 2
			N q 2 and start = 0
			page = 4
			D q 0
			  c 5
			N q 5 and start = 2
			page = 5
			D q 0
			  c 5
			N q 5 and start = 7
			
			so normal skip = (page - tpage) * limit - (ttot % limit)
			
			2: used
			limit = 5
	
			D data = 8
			5 3
			page = 2 --> 5 ~ 9
			D q 3 --> 5 ~ 7
			  c 2
			N q 2 and start = 0
			page = 3 --> 10 ~ 14
			D q 0
			  c 5
			N q 5 and start = 2  --> pageStart - ttot = 10 - 8
			page = 4 --> 15 ~ 19
			D q 0
			  c 5
			N q 5 and start = 7  --> pageStart - ttot = 15 - 8
			
			D data = 13  --> tpage = 3
			5 5 3
			page = 5 --> 20 ~ 24
			D q 0
			  c 5
			N q 5 and start = 7	
				
			D data = 10  --> tpage = 2
			5 5
			page = 3 --> 10 ~ 14
			D q 0
			  c 5
			N q 5 and start = 0	
			
		 */
		BasePage<AppArticleSimple> bp = new BasePage<AppArticleSimple>();
		long start = bs.start_i();
		int limit = bs.limit_must();
		List<AppArticleSimple> rows = null;
		
		// 1 查询置顶的
		bs.setDing(1);
		List<AppArticleSimple> rowsDing = mapper().list_simple(bs);
		Long totalDing = mapper().lscount_simple(bs);
		
		// 2 查询正常的记录总数
		bs.setDing(0);
		Long totalNormal = mapper().lscount_simple(bs);
		Long total = totalDing + totalNormal;
		
		// 3 如果 置顶记录不小于限制数, 说明置顶数充足, 返回
		int sizeDing = rowsDing != null ? rowsDing.size() : 0;
		if(sizeDing >= limit){
			rows = rowsDing;
		}else if(totalNormal > 0){
			// 4 置顶记录不足, 且有正常数据
			int limitNormal = limit - (int)(totalDing % limit);
			long skip = start - totalDing;
			if(skip >= 0L){
				bs.setStart(skip);
				bs.setLimit(limit);
			}else{
				bs.setStart(0L);
				bs.setLimit(limitNormal);
			}
			List<AppArticleSimple> rowsNormal = mapper().list_simple(bs);
			if(rowsNormal != null && rowsNormal.size() > 0){
				rows = rowsDing != null ? rowsDing : new ArrayList<AppArticleSimple>();
				rows.addAll(rowsNormal);
			}else{
				rows = rowsDing;
			}
		}
		
		// 设置返回
		bp.t_param(bs.page_i(), limit);
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
