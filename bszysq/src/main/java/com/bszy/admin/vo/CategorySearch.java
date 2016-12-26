package com.bszy.admin.vo;

import com.mao.ssm.BaseSearch;

public class CategorySearch extends BaseSearch {
	
	private String name;	// 版块名称(1~500字符)
	protected Integer addart;		// 禁止用户发布主题, 0: 正常; 1: 禁止
	protected Integer addartauth;	// 认证用户才能发布主题, 0: 无需认证; 1: 需要认证
	protected Integer lookartauth;	// 认证用户才能查看, 0: 无需认证; 1: 需要认证
	private Integer sortn;	// 排序号(默认0, 倒序)
	private Integer isdel;	// 删除标记, 0: 正常; 1: 已删除
	
	{
		orderby = "sortn asc, id asc";
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIsdel() {
		return isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	public Integer getSortn() {
		return sortn;
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
	public void setSortn(Integer sortn) {
		this.sortn = sortn;
	}

	
}
