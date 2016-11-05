package com.bszy.admin.pojo;

import com.mao.ssm.BasePojo;

/**
 * 网站信息, 字典
 * @author Mao 2016年10月29日 下午2:32:26
 */
public class Webinfo extends BasePojo {
	private static final long serialVersionUID = 1L;
	
	private String name;	// 名称(1~500字符)
	private String val;		// 值
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	
}
