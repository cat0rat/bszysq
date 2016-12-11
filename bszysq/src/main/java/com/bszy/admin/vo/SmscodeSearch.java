package com.bszy.admin.vo;

import com.mao.ssm.BaseSearch;

public class SmscodeSearch extends BaseSearch {
	
	private String mobile;	// 手机号
	private Integer typen;	// 类型(0: 注册; 1: 忘记密码)
	private Integer isdel;	// 删除标记, 0: 正常; 1: 已删除

	public Integer getIsdel() {
		return isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getTypen() {
		return typen;
	}
	public void setTypen(Integer typen) {
		this.typen = typen;
	}
	
}
