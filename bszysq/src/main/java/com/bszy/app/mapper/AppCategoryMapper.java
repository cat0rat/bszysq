package com.bszy.app.mapper;

import java.util.List;

import com.bszy.app.pojo.AppCategory;
import com.mao.ssm.BaseMapper;

/**
 * 
 * @author Mao 2016年10月26日 上午1:23:12
 */
public interface AppCategoryMapper extends BaseMapper<AppCategory> {
	public List<AppCategory> list_art(Integer num);
}