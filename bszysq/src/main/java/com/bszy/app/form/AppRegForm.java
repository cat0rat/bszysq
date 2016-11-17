package com.bszy.app.form;

import com.mao.ssm.BaseForm;

public class AppRegForm extends BaseForm {
	private static final long serialVersionUID = 1L;
	
	private String mobile;	// 用户名(手机号)
	private String nname;	// 昵称(2~16位字母数字汉字)
	private String pwd;		// 密码(6~16位字母数字)
	private String captcha;	// 图形验证码(4位)
	private String smscode;	// 短信验证码(4位)
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getNname() {
		return nname;
	}
	public void setNname(String nname) {
		this.nname = nname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public String getSmscode() {
		return smscode;
	}
	public void setSmscode(String smscode) {
		this.smscode = smscode;
	}
	
	
	
}
