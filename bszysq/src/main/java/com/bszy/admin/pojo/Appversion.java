package com.bszy.admin.pojo;

import java.util.Date;

import com.mao.ssm.BasePojo;

/**
 * App版本
 * @author jiangzushuai 2016年12月11日 下午4:51:35
 */
public class Appversion extends BasePojo {
	private static final long serialVersionUID = 1L;
	
	private Integer typex;		// app类型, 0: Android; 1: IOS
	private String verx;		// 版本号, 如: 1.2.0 或 12.31.00
	private String vercode;		// 版本代码, 如版本号1.3.12,对应代码为1021200
	private String descx;		// 描述
	private String packagex;	// 包名
	private Date upgradex;		// 升级时间
	private Integer force;		// 是否强制更新, 0: 正常; 1: 强制更新
	private Integer release;	// 是否发布版, 0: 不是, 1: 是
	private String url;			// 下载地址
	
	public Integer getTypex() {
		return typex;
	}
	public void setTypex(Integer typex) {
		this.typex = typex;
	}
	public String getVerx() {
		return verx;
	}
	public void setVerx(String verx) {
		this.verx = verx;
	}
	public String getVercode() {
		return vercode;
	}
	public void setVercode(String vercode) {
		this.vercode = vercode;
	}
	public String getDescx() {
		return descx;
	}
	public void setDescx(String descx) {
		this.descx = descx;
	}
	public String getPackagex() {
		return packagex;
	}
	public void setPackagex(String packagex) {
		this.packagex = packagex;
	}
	public Date getUpgradex() {
		return upgradex;
	}
	public void setUpgradex(Date upgradex) {
		this.upgradex = upgradex;
	}
	public Integer getForce() {
		return force;
	}
	public void setForce(Integer force) {
		this.force = force;
	}
	public Integer getRelease() {
		return release;
	}
	public void setRelease(Integer release) {
		this.release = release;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
