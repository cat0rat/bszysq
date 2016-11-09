package com.bszy.admin.pojo;

import com.mao.ssm.BasePojo;

/**
 * 短信验证码
 * @author Mao 2016年11月10日 上午12:28:00
 */
public class Smscode extends BasePojo {
	private static final long serialVersionUID = 1L;
	
	private String mobile;	// 手机号
	private String code;	// 验证码(4字符)
	private Integer typen;	// 类型(0: 注册; 1: 忘记密码)
	
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
	public Integer getTypen() {
		return typen;
	}
	public void setTypen(Integer typen) {
		this.typen = typen;
	}
	
}
