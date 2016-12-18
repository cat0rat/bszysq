package com.bszy.app.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * @author Mao 2016年10月29日 下午1:19:39
 */
public class AppUserMine implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected Long id;			// ID
	protected String name;		// 用户名(手机号)
	protected String nname;		// 昵称(2~16位字母数字汉字)
	
	protected String unionid;	// 微信唯一id(20~50字符)
	protected String openid;	// 微信id(20~50字符)
	protected String getuicid;	// 个推ClientID(20~50字符)
	
	protected Integer authx;	// 0: 已认证; 1: 未认证; 2: 认证中
	protected String mobile;	// 手机号
	protected String tname;		// 姓名(2~20位字母数字汉字)
	protected String address;	// 地址(<500字符)

	protected String sex;		// 1:男; 2:女; 0:未知
	protected String head;		// 头像(<500字符，完整网址)

	protected Date ctime;		// 创建时间
	protected Date utime;		// 更新时间
	protected Integer isdel;	// 删除标记, 0: 正常; 1: 封号
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getGetuicid() {
		return getuicid;
	}
	public void setGetuicid(String getuicid) {
		this.getuicid = getuicid;
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
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Date getUtime() {
		return utime;
	}
	public void setUtime(Date utime) {
		this.utime = utime;
	}
	public Integer getIsdel() {
		return isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	
}
