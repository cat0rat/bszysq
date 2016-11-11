package com.bszy.app.pojo;

import java.util.Date;

import com.mao.ssm.BaseSearch;

public class AppUserSearch extends BaseSearch {
	
	private String name;	// 用户名(6~16位字母数字)
	private String nname;	// 昵称(6~16位字母数字汉字)
	
	private String unionid;	// 微信唯一id(20~50字符)
	private String openid;	// 微信id(20~50字符)

	private Integer authx;	// 0: 已认证; 1: 未认证; 2: 申请认证
	private String mobile;	// 手机号
	private String tname;	// 姓名(2~20位字母数字汉字)
	private String city;	// 城市(<50字符)

	private String sex;		// 1:男; 2:女; 0:未知
	private String email;	// 邮箱(<500字符)
	private Date birth;		// 生日
	private Integer lstat;	// 登录状态: 0: 离线; 1: 在线; 2: 隐身
	private Integer isdel;

	public Integer getIsdel() {
		return isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNname() {
		return nname;
	}
	public void setNname(String nname) {
		this.nname = nname;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Integer getAuthx() {
		return authx;
	}
	public void setAuthx(Integer authx) {
		this.authx = authx;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public Integer getLstat() {
		return lstat;
	}
	public void setLstat(Integer lstat) {
		this.lstat = lstat;
	}
	
}
