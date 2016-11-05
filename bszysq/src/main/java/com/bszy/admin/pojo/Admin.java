package com.bszy.admin.pojo;

import java.util.Date;

import com.mao.ssm.BasePojo;

/**
 * 管理员
 * @author Mao 2016年10月29日 下午1:19:14
 */
public class Admin extends BasePojo {
	private static final long serialVersionUID = 1L;
	
	private String name;	// 用户名(6~16位字母数字)
	private String pwd;		// 密码(6~16位字母数字)
	private Integer rolex;	// 角色, 1:超级管理员
	
	private String mobile;	// 手机号
	private String head;	// 头像(<500字符，完整网址)
	private String email;	// 邮箱(<500字符)
	private Date ltime;		// 最后一次登录时间
	private String lip;		// 最后一次登录ip
	private Integer lcount;	// 登录次数
	private Integer lstat;	// 登录状态: 0: 离线; 1: 在线; 2: 隐身
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getLtime() {
		return ltime;
	}
	public void setLtime(Date ltime) {
		this.ltime = ltime;
	}
	public String getLip() {
		return lip;
	}
	public void setLip(String lip) {
		this.lip = lip;
	}
	public Integer getLcount() {
		return lcount;
	}
	public void setLcount(Integer lcount) {
		this.lcount = lcount;
	}
	public Integer getLstat() {
		return lstat;
	}
	public void setLstat(Integer lstat) {
		this.lstat = lstat;
	}
	public Integer getRolex() {
		return rolex;
	}
	public void setRolex(Integer rolex) {
		this.rolex = rolex;
	}
	
}
