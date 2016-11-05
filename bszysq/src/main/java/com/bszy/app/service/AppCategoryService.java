package com.bszy.app.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bszy.app.mapper.AppCategoryMapper;
import com.bszy.app.pojo.AppCategory;
import com.mao.ssm.BaseService;

@Service
public class AppCategoryService extends BaseService<AppCategory, AppCategoryMapper> {
	
	@Inject
	private AppCategoryMapper mapper;
	public AppCategoryMapper mapper(){return mapper;}
	

	public List<AppCategory> list_art(Integer num){
		return mapper().list_art(num);
	}
	
}
