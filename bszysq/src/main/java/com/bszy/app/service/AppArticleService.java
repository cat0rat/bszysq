package com.bszy.app.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bszy.app.mapper.AppArticleMapper;
import com.bszy.app.pojo.AppArticle;
import com.bszy.app.pojo.AppArticleSearch;
import com.mao.ssm.BasePage;
import com.mao.ssm.BaseService;

@Service
public class AppArticleService extends BaseService<AppArticle, AppArticleMapper> {
	
	@Inject
	private AppArticleMapper mapper;
	public AppArticleMapper mapper(){return mapper;}
	
	public BasePage<AppArticle> list(AppArticleSearch bs){
		BasePage<AppArticle> bp = new BasePage<AppArticle>();
		bs.start_i();
		List<AppArticle> rows = mapper.list(bs);
		Long total = mapper.lscount(bs);

		bp.t_param(bs.page_i(), bs.limit_i());
		bp.t_result(total, rows);
		if(rows != null && rows.size() > 0){
			AppArticle mo = rows.get(rows.size() - 1);
			if(mo != null){
				Long lastid = mo.getId();
				bp.setLastid(lastid != null ? lastid : 0L);
			}
		}
		
		return bp;
	}
	
}
