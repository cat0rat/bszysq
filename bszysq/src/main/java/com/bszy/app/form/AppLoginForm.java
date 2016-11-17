package com.bszy.app.form;

import com.mao.ssm.BaseForm;

public class AppLoginForm extends BaseForm {
	private static final long serialVersionUID = 1L;
	
	private String mobile;	// 用户名(手机号)
	private String pwd;		// 密码(6~16位字母数字)
	private String smscode;	// 短信验证码(4位)
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getSmscode() {
		return smscode;
	}
	public void setSmscode(String smscode) {
		this.smscode = smscode;
	}
	
	
	
}
