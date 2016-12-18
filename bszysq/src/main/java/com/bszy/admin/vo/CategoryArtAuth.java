package com.bszy.admin.vo;

import java.io.Serializable;

public class CategoryArtAuth implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Long id;
	protected Integer addart;
	protected Integer addartauth;
	protected Integer lookartauth;
	
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
