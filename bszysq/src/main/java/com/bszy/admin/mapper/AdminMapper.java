package com.bszy.admin.mapper;

import java.util.Map;

import com.bszy.admin.pojo.Admin;
import com.mao.ssm.BaseMapper;


/**
 * 
 * @author Mao 2016年10月27日 下午11:42:15
 */
public interface AdminMapper extends BaseMapper<Admin> {
	public Admin login(Map<String, String> params);
}