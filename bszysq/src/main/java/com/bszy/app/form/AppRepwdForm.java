package com.bszy.app.form;

import com.mao.ssm.BaseForm;

public class AppRepwdForm extends BaseForm {
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
