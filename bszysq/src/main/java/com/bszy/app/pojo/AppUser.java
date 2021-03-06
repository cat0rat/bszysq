package com.bszy.app.pojo;

import java.util.Date;

import com.mao.ssm.BasePojo;

/**
 * 用户
 * @author Mao 2016年10月29日 下午1:19:39
 */
public class AppUser extends BasePojo {
	private static final long serialVersionUID = 1L;
	
	protected String name;		// 用户名(手机号)
	protected String pwd;		// 密码(6~16位字母数字)
	protected String nname;		// 昵称(2~16位字母数字汉字)
	protected Integer rolex;	// 角色: 0:普通用户, 9: 超级管理员
	
	protected String unionid;	// 微信唯一id(20~50字符)
	protected String openid;	// 微信id(20~50字符)
	
	protected String getuicid;		// 个推ClientID(20~50字符)
	protected String getuialias;	// 个推别名(<50字符)
	protected String getuitag;		// 个推标签(<500字符)
	
	protected Integer phonetype;	// 手机类型: 0: 安卓; 1: 苹果;
	protected String phonename;		// 手机显示名, 如: Android 4.2, iPhone 7等(<50字符)

	protected Integer authx;	// 0: 已认证; 1: 未认证; 2: 认证中
	protected String mobile;	// 手机号
	protected String tname;		// 姓名(2~20位字母数字汉字)
	protected String citycode;	// 城市编码
	protected String city;		// 城市(<50字符)
	protected String address;	// 地址(<500字符)

	protected String sex;		// 1:男; 2:女; 0:未知
	protected String head;		// 头像(<500字符，完整网址)
	protected String email;		// 邮箱(<500字符)
	protected Date birth;		// 生日
	protected Date ltime;		// 最后一次登录时间
	protected String lip;		// 最后一次登录ip
	protected Integer lcount;	// 登录次数
	protected Integer lstat;	// 登录状态: 0: 离线; 1: 在线; 2: 隐身
	
	public String getAuthxStr() {
		if(authx != null)
			if(authx == 0) return "已认证";
			else if(authx == 1) return "未认证";
			else if(authx == 2) return "认证中";
		return null;
	}
	public String getSexStr() {
		if(sex != null)
			if("1".equals(sex)) return "男";
			else if("2".equals(sex)) return "女";
			else if("0".equals(sex)) return "未知";
		return null;
	}
//	public String getBirthStr() {
//		return date_str(birth);
//	}
//	public String getLtimeStr() {
//		return datetime_str(ltime);
//	}
//	public String getLstatStr() {
//		if(lstat != null)
//			if(lstat == 0) return "离线";
//			else if(lstat == 1) return "在线";
//			else if(lstat == 2) return "隐身";
//		return null;
//	}
	
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
	public Integer getAuthx() {
		return authx;
	}
	public void setAuthx(Integer authx) {
		this.authx = authx;
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
	public String getNname() {
		return nname;
	}
	public void setNname(String nname) {
		this.nname = nname;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
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
	public String getSex() {
		return sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public Integer getRolex() {
		return rolex;
	}
	public void setRolex(Integer rolex) {
		this.rolex = rolex;
	}
	public String getGetuicid() {
		return getuicid;
	}
	public void setGetuicid(String getuicid) {
		this.getuicid = getuicid;
	}
	public String getGetuialias() {
		return getuialias;
	}
	public void setGetuialias(String getuialias) {
		this.getuialias = getuialias;
	}
	public String getGetuitag() {
		return getuitag;
	}
	public void setGetuitag(String getuitag) {
		this.getuitag = getuitag;
	}
	public Integer getPhonetype() {
		return phonetype;
	}
	public void setPhonetype(Integer phonetype) {
		this.phonetype = phonetype;
	}
	public String getPhonename() {
		return phonename;
	}
	public void setPhonename(String phonename) {
		this.phonename = phonename;
	}
	
}
