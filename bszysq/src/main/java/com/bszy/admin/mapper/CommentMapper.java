package com.bszy.admin.mapper;

import java.util.List;

import com.bszy.admin.pojo.Comment;
import com.mao.ssm.BaseMapper;
import com.mao.ssm.BaseSearch;

/**
 * 
 * @author Mao 2016年10月26日 上午1:23:12
 */
public interface CommentMapper extends BaseMapper<Comment> {
	// TODO 查询 
	public List<Comment> appsublist(BaseSearch bs);
	
	public List<Comment> applist(BaseSearch bs);
	public Long applscount(BaseSearch bs);
	
	// 与评论相关的id
	public Comment refids(Long id);
}