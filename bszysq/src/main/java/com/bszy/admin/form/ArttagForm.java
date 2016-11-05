package com.bszy.admin.form;

import com.mao.ssm.BaseForm;

public class ArttagForm extends BaseForm {
	private static final long serialVersionUID = 1L;
	
	private String name;	// 标签名称(1~20字符)
	private String sortn;	// 排序号(默认0, 倒序)
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortn() {
		return sortn;
	}
	public void setSortn(String sortn) {
		this.sortn = sortn;
	}
	
	
}
