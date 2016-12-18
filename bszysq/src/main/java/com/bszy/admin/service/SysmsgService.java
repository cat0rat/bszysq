package com.bszy.admin.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bszy.admin.mapper.SysmsgMapper;
import com.bszy.admin.pojo.Sysmsg;
import com.mao.ssm.BaseService;

@Service
public class SysmsgService extends BaseService<Sysmsg, SysmsgMapper> {
	
	@Inject
	private SysmsgMapper mapper;
	public SysmsgMapper mapper(){return mapper;}
	
}
