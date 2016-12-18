package com.bszy.admin.pojo;

import java.util.Date;

import com.mao.ssm.BasePojo;

/**
 * 版块
 * @author Mao 2016年10月29日 下午1:19:48
 */
public class Versionx extends BasePojo {
	private static final long serialVersionUID = 1L;
	
	private Integer typex;
	private String verx;
	private String vercode;
	private String descx;
	private String packagex;
	private Date upgradex;
	private Integer force;
	private Integer release;
	private String url;
	
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
