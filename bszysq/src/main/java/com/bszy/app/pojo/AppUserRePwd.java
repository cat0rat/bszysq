package com.bszy.app.pojo;

import com.mao.ssm.BasePojo;

/**
 * 用户修改密码
 * @author Mao 2016年11月13日 下午12:10:40
 */
public class AppUserRePwd extends BasePojo {
	private static final long serialVersionUID = 1L;
	
	private String oldpwd;	// 原密码(6~16位字母数字)
	private String pwd;		// 新密码(6~16位字母数字)
	
	public String getOldpwd() {
		return oldpwd;
	}
	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
