package com.bszy.admin.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bszy.admin.mapper.SmscodeMapper;
import com.bszy.admin.pojo.Smscode;
import com.mao.ssm.BaseService;

@Service
public class SmscodeService extends BaseService<Smscode, SmscodeMapper> {
	
	@Inject
	private SmscodeMapper mapper;
	public SmscodeMapper mapper(){return mapper;}
	
}
