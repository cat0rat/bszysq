package com.bszy.admin.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bszy.admin.mapper.UserMapper;
import com.bszy.admin.pojo.User;
import com.mao.ssm.BaseService;
import com.mao.ssm.BaseStatusForm;

@Service
public class UserService extends BaseService<User, UserMapper> {
	@Inject
	private UserMapper mapper;
	public UserMapper mapper(){return mapper;}
	
	@Transactional
	public boolean authx(BaseStatusForm mo){
		Long rn = mapper().authx(mo);
		return rn != null && rn == 1;
	}
	@Transactional
	public boolean authxs(BaseStatusForm mo){
		Long rn = mapper().authxs(mo);
		return rn != null && rn > 0;
	}
	
}
