package com.bszy.admin.form;

import com.mao.ssm.BaseForm;

public class SliderForm extends BaseForm {
	private static final long serialVersionUID = 1L;
	
	private String name;	// 版块名称(1~20字符)
	private String img;		// 配图(<500字符，完整网址)
	private String sortn;	// 排序号(默认0, 倒序)
	private String pos;		// 位置
	private String brief;	// 简介
	private String link;	// 跳转地址
	
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
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
}
