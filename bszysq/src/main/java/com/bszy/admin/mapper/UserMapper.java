package com.bszy.admin.mapper;

import com.bszy.admin.pojo.User;
import com.mao.ssm.BaseMapper;
import com.mao.ssm.BaseStatusForm;

/**
 * 
 * @author Mao 2016年10月26日 上午1:23:12
 */
public interface UserMapper extends BaseMapper<User> {
	public Long option_authx(BaseStatusForm mo);
	public Long option_authxs(BaseStatusForm mo);
	
	public Long option_isdel(BaseStatusForm mo);
	public Long option_isdels(BaseStatusForm mo);
	
	public Long repwd(User mo);
}