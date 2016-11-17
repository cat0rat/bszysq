package com.bszy.admin.mapper;

import java.util.List;

import com.bszy.admin.pojo.Category;
import com.bszy.app.pojo.AppCategoryArt;
import com.mao.ssm.BaseMapper;

/**
 * 
 * @author Mao 2016年10月26日 上午1:23:12
 */
public interface CategoryMapper extends BaseMapper<Category> {
	public List<AppCategoryArt> list_art(Integer num);
}