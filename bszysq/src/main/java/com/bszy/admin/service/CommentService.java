package com.bszy.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.bszy.admin.mapper.CommentMapper;
import com.bszy.admin.pojo.Comment;
import com.bszy.admin.pojo.CommentSearch;
import com.bszy.app.pojo.AppCommentSimple;
import com.bszy.app.pojo.AppCommentSub;
import com.mao.ssm.BasePage;
import com.mao.ssm.BaseService;

@Service
public class CommentService extends BaseService<Comment, CommentMapper> {
	
	@Inject
	private CommentMapper mapper;
	public CommentMapper mapper(){return mapper;}
	
	// 与评论相关的id
	public Comment refids(Long id){
		return mapper.refids(id);
	}
	
	/** 查询评论 */
	public BasePage<AppCommentSimple> list(CommentSearch bs){
		BasePage<AppCommentSimple> bp = new BasePage<AppCommentSimple>();
		bs.start_i();
		List<AppCommentSimple> rows = mapper().applist(bs);
		Long total = mapper().applscount(bs);

		bp.t_param(bs.page_i(), bs.limit_i());
		bp.t_result(total, rows);
		if(rows != null && rows.size() > 0){
			AppCommentSimple mo = rows.get(rows.size() - 1);
			if(mo != null){
				Long lastid = mo.getId();
				bp.setLastid(lastid != null ? lastid : 0L);
			}
			// 查询下级评论
			Set<Long> commidss = new HashSet<Long>();
			Map<Long, List<AppCommentSub>> idscms = new HashMap<Long, List<AppCommentSub>>();
			for(AppCommentSimple cmm : rows){
				commidss.add(cmm.getId());
				cmm.setSubcomms(new ArrayList<AppCommentSub>());
				idscms.put(cmm.getId(), cmm.getSubcomms());
			}
			Long[] commids = commidss.toArray(new Long[0]);
			String idsstr = StringUtils.join(commids, ',');
			CommentSearch sbs = new CommentSearch();
			sbs.setArtid(bs.getArtid());
			sbs.setLimit(null);
			sbs.setCommids(idsstr);
			List<AppCommentSub> scms = mapper().appsublist(sbs);
			for(AppCommentSub c : scms){
				idscms.get(c.getCommid()).add(c);
			}
		}
		
		return bp;
	}
}
