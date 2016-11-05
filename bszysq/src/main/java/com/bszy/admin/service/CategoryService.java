package com.bszy.admin.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bszy.admin.mapper.CategoryMapper;
import com.bszy.admin.pojo.Category;
import com.mao.ssm.BaseService;

@Service
public class CategoryService extends BaseService<Category, CategoryMapper> {
	
	@Inject
	private CategoryMapper mapper;
	public CategoryMapper mapper(){return mapper;}
	
}
