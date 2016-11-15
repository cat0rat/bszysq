package com.mao.ssm;

import java.io.Serializable;

public class BaseForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;		// ID
	private String isdel;	// 删除标记, 0: 正常; 1: 已删除
	private String uid;		// 当前用户ID
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
}
