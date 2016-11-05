package com.bszy.admin.service;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bszy.admin.mapper.AdminMapper;
import com.bszy.admin.pojo.Admin;
import com.mao.ssm.BaseService;

@Service
public class AdminService extends BaseService<Admin, AdminMapper> {
	
	@Inject
	private AdminMapper mapper;
	public AdminMapper mapper(){return mapper;}
	
	public Admin login(Map<String, String> params){
		return mapper.login(params);
	}
}
