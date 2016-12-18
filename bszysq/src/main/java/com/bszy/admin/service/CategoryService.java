package com.bszy.admin.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bszy.admin.mapper.CategoryMapper;
import com.bszy.admin.pojo.Category;
import com.bszy.admin.vo.CategoryArtAuth;
import com.bszy.app.vo.AppCategoryArt;
import com.mao.ssm.BaseService;

@Service
public class CategoryService extends BaseService<Category, CategoryMapper> {
	
	@Inject
	private CategoryMapper mapper;
	public CategoryMapper mapper(){return mapper;}
	
	public List<AppCategoryArt> list_art(Integer num){
		return mapper.list_art(num);
	}
	
	/** 版块下文章的权限信息 */
	public CategoryArtAuth artauth(Long id){
		return mapper().artauth(id);
	}
}
