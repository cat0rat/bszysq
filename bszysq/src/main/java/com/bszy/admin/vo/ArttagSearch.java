package com.bszy.admin.vo;

import com.mao.ssm.BaseSearch;

public class ArttagSearch extends BaseSearch {
	
	private String name;
	private Integer sortn;	// 排序号(默认0, 倒序)
	private Integer isdel;
	{
		limit = null;
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
	public void setSortn(Integer sortn) {
		this.sortn = sortn;
	}
	
}
