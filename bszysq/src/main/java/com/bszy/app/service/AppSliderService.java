package com.bszy.app.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bszy.app.mapper.AppSliderMapper;
import com.bszy.app.pojo.AppSlider;
import com.mao.ssm.BaseService;

@Service
public class AppSliderService extends BaseService<AppSlider, AppSliderMapper> {
	
	@Inject
	private AppSliderMapper mapper;
	public AppSliderMapper mapper(){return mapper;}
	
}
