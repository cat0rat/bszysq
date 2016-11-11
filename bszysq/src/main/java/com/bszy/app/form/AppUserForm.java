package com.bszy.app.form;

import com.mao.ssm.BaseForm;

public class AppUserForm extends BaseForm {
	private static final long serialVersionUID = 1L;
	
	private String name;	// 用户名(6~16位字母数字)
	private String pwd;		// 密码(6~16位字母数字)
	private String nname;	// 昵称(6~16位字母数字汉字)
	private String rolex;	// 角色: 0:普通用户, 9: 超级管理员
	
	private String unionid;	// 微信唯一id(20~50字符)
	private String openid;	// 微信id(20~50字符)

	private String authx;	// 0: 已认证; 1: 未认证; 2: 申请认证
	private String mobile;	// 手机号
	private String tname;	// 姓名(2~20位字母数字汉字)
	private String citycode;// 城市编码
	private String city;	// 城市(<50字符)
	private String address;	// 地址(<500字符)

	private String sex;		// 1:男; 2:女; 0:未知
	private String head;	// 头像(<500字符，完整网址)
	private String email;	// 邮箱(<500字符)
	private String birth;		// 生日
	private String ltime;		// 最后一次登录时间
	private String lip;		// 最后一次登录ip
	private String lcount;	// 登录次数
	private String lstat;	// 登录状态: 0: 离线; 1: 在线; 2: 隐身
	
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
	public String getAuthx() {
		return authx;
	}
	public void setAuthx(String authx) {
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
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getLtime() {
		return ltime;
	}
	public void setLtime(String ltime) {
		this.ltime = ltime;
	}
	public String getLip() {
		return lip;
	}
	public void setLip(String lip) {
		this.lip = lip;
	}
	public String getLcount() {
		return lcount;
	}
	public void setLcount(String lcount) {
		this.lcount = lcount;
	}
	public String getLstat() {
		return lstat;
	}
	public void setLstat(String lstat) {
		this.lstat = lstat;
	}
	public String getRolex() {
		return rolex;
	}
	public void setRolex(String rolex) {
		this.rolex = rolex;
	}
	
}
