package com.bszy.admin.form;

import com.mao.ssm.BaseForm;

public class SmscodeForm extends BaseForm {
	private static final long serialVersionUID = 1L;
	
	private String mobile;	// 手机号
	private String code;	// 验证码(4字符)
	private String typen;	// 类型(0: 注册; 1: 忘记密码)
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTypen() {
		return typen;
	}
	public void setTypen(String typen) {
		this.typen = typen;
	}
	
}
