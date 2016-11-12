package com.bszy.app.pojo;

import com.mao.ssm.BaseSearch;

public class AppSliderSearch extends BaseSearch {
	
	private String name;
	private Integer sortn;	// 排序号(默认0, 倒序)
	private Integer pos;	// 位置
	private Integer isdel;

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
	public Integer getPos() {
		return pos;
	}
	public void setPos(Integer pos) {
		this.pos = pos;
	}

	
}
