package com.bszy.admin.vo;

import com.mao.ssm.BaseSearch;

public class VersionxSearch extends BaseSearch {
	
	private Integer typex;		// app类型, 0: Android; 1: IOS
	private String packagex;	// 包名

	public Integer getTypex() {
		return typex;
	}
	public void setTypex(Integer typex) {
		this.typex = typex;
	}
	public String getPackagex() {
		return packagex;
	}
	public void setPackagex(String packagex) {
		this.packagex = packagex;
	}

}
