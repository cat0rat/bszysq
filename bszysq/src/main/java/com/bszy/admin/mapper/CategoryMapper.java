package com.bszy.admin.mapper;

import java.util.List;

import com.bszy.admin.pojo.Category;
import com.bszy.admin.vo.CategoryArtAuth;
import com.bszy.app.vo.AppCategoryArt;
import com.mao.ssm.BaseMapper;

/**
 * 
 * @author Mao 2016年10月26日 上午1:23:12
 */
public interface CategoryMapper extends BaseMapper<Category> {
	public List<AppCategoryArt> list_art(Integer num);
	
	//public Long check_category_access(Long cateid, Long uid);	// 检测版块是否可以访问
	/** 版块下文章的权限信息 */
	public CategoryArtAuth artauth(Long id);
}