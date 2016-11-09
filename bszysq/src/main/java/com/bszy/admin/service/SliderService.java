package com.bszy.admin.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bszy.admin.mapper.SliderMapper;
import com.bszy.admin.pojo.Slider;
import com.mao.ssm.BaseService;

@Service
public class SliderService extends BaseService<Slider, SliderMapper> {
	
	@Inject
	private SliderMapper mapper;
	public SliderMapper mapper(){return mapper;}
	
}
