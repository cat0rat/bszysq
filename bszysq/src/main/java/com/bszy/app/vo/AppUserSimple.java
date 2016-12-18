package com.bszy.app.vo;

import java.io.Serializable;

/**
 * 用户(简单信息)
 * @author jiangzushuai 2016年12月10日 下午10:36:00
 */
public class AppUserSimple implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected Long id;			// ID
	protected String nname;		// 昵称(2~16位字母数字汉字)
	protected String head;		// 头像(<500字符，完整网址)
	protected Integer authx;	// 0: 已认证; 1: 未认证; 2: 认证中
	protected String sex;		// 1:男; 2:女; 0:未知
	protected Integer isdel;	// 删除标记, 0: 正常; 1: 封号
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNname() {
		return nname;
	}
	public void setNname(String nname) {
		this.nname = nname;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public Integer getAuthx() {
		return authx;
	}
	public void setAuthx(Integer authx) {
		this.authx = authx;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getIsdel() {
		return isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	@Override
	public String toString() {
		return "AppSimpleUser [id=" + id + ", nname=" + nname + ", head=" + head + ", authx=" + authx + ", sex=" + sex + ", isdel=" + isdel + "]";
	}
	
}
