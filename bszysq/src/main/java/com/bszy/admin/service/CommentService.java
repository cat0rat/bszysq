package com.bszy.admin.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bszy.admin.mapper.CommentMapper;
import com.bszy.admin.pojo.Comment;
import com.mao.ssm.BaseService;

@Service
public class CommentService extends BaseService<Comment, CommentMapper> {
	
	@Inject
	private CommentMapper mapper;
	public CommentMapper mapper(){return mapper;}
	
}
