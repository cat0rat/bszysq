package com.bszy.admin.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bszy.admin.mapper.ArttagMapper;
import com.bszy.admin.pojo.Arttag;
import com.mao.ssm.BaseService;

@Service
public class ArttagService extends BaseService<Arttag, ArttagMapper> {
	
	@Inject
	private ArttagMapper mapper;
	public ArttagMapper mapper(){return mapper;}
	
}
