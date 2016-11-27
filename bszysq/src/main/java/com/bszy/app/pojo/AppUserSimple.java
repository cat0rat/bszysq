package com.bszy.app.pojo;

import java.io.Serializable;
import java.util.Date;

import com.mao.ssm.BasePojo;

/**
 * 用户
 * @author Mao 2016年10月29日 下午1:19:39
 */
public class AppUserSimple implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;		// ID
	private String nname;	// 昵称(2~16位字母数字汉字)
	private Integer authx;	// 0: 已认证; 1: 未认证; 2: 认证中
	private String sex;		// 1:男; 2:女; 0:未知
	private String head;	// 头像(<500字符，完整网址)
	private Date birth;		// 生日
	
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
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
}
