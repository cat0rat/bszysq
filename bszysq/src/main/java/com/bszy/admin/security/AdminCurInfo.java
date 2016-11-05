package com.bszy.admin.security;

/**
 * 
 * @author Mao 2016年10月28日 上午1:39:18
 */
public class AdminCurInfo {

	private Long id;
	private String name;	// 用户名
	private Integer rolex;	// 角色
	private String pwd;		// 密码
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getRolex() {
		return rolex;
	}
	public void setRolex(Integer rolex) {
		this.rolex = rolex;
	}

}

