package com.bszy.app.pojo;

import com.mao.ssm.BaseSearch;

public class AppArticleSearch extends BaseSearch {
	
	private String name;
	private Long lastid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLastid() {
		return lastid;
	}

	public void setLastid(Long lastid) {
		this.lastid = lastid;
	}
	
}
