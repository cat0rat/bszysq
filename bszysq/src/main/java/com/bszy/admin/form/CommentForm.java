package com.bszy.admin.form;

import com.mao.ssm.BaseForm;

public class CommentForm extends BaseForm {
	private static final long serialVersionUID = 1L;
	
	private String artid;		// 文章id
	private String authorid;	// 文章作者id
	private String userid;		// 评论者id
	private String touserid;	// 对谁的评论id
	
	private String content;		// 内容(<1000字符)
	private String imgs;		// 多图(<5000字符，完整网址)
	
	private String liken;		// 点赞数
	private String commn;		// 评论数
	
	public String getArtid() {
		return artid;
	}
	public void setArtid(String artid) {
		this.artid = artid;
	}
	public String getAuthorid() {
		return authorid;
	}
	public void setAuthorid(String authorid) {
		this.authorid = authorid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTouserid() {
		return touserid;
	}
	public void setTouserid(String touserid) {
		this.touserid = touserid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	public String getLiken() {
		return liken;
	}
	public void setLiken(String liken) {
		this.liken = liken;
	}
	public String getCommn() {
		return commn;
	}
	public void setCommn(String commn) {
		this.commn = commn;
	}
	
}
