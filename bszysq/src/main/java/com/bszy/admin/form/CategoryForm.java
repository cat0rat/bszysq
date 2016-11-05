package com.bszy.admin.form;

import com.mao.ssm.BaseForm;

public class CategoryForm extends BaseForm {
	private static final long serialVersionUID = 1L;
	
	private String name;	// 版块名称(1~20字符)
	private String sortn;	// 排序号(默认0, 倒序)
	private String img;		// 配图(<500字符，完整网址)
	
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
}
