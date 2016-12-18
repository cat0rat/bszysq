package com.bszy.admin.vo;

import java.io.Serializable;

public class CategoryArtAuth implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Long id;
	protected Integer addart;		// 禁止用户发布主题, 0: 正常; 1: 禁止
	protected Integer addartauth;	// 认证用户才能发布主题, 0: 无需认证; 1: 需要认证
	protected Integer lookartauth;	// 认证用户才能查看, 0: 无需认证; 1: 需要认证; 2: 无需登录
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getAddart() {
		return addart;
	}
	public void setAddart(Integer addart) {
		this.addart = addart;
	}
	public Integer getAddartauth() {
		return addartauth;
	}
	public void setAddartauth(Integer addartauth) {
		this.addartauth = addartauth;
	}
	public Integer getLookartauth() {
		return lookartauth;
	}
	public void setLookartauth(Integer lookartauth) {
		this.lookartauth = lookartauth;
	}

}
