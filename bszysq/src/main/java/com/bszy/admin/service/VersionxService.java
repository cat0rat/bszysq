package com.bszy.admin.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bszy.admin.mapper.VersionxMapper;
import com.bszy.admin.pojo.Versionx;
import com.bszy.admin.vo.VersionxSearch;
import com.mao.ssm.BaseService;


@Service
public class VersionxService extends BaseService<Versionx, VersionxMapper> {
	
	@Inject
	private VersionxMapper mapper;
	public VersionxMapper mapper(){return mapper;}
	
	public List for_packagex(VersionxSearch bs) {
		return mapper().for_packagex(bs);
	}
}