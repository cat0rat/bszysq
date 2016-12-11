package com.bszy.admin.form;

import com.mao.ssm.BaseForm;

public class ArticleForm extends BaseForm {
	private static final long serialVersionUID = 1L;
	
	private String name;		// 标题(1~500字符)
	private String img;			// 配图(<500字符，完整网址)
	private String brief;		// 简介(<500字符)
	private String content;		// 内容(<1万字)
	private String imgs;		// 多图(<5000字符，完整网址)
	private String sortn;		// 排序号(默认0, 倒序)
	private String tagid;		// 标签id
	private String tagname;		// 标签名(关联)
	private String cateid;		// 所属版块id
	private String catename;	// 所属版块名称(关联)
	private String userid;		// 发布者id
	private String usernname;	// 发布者昵称(关联)
	private String recom;		// 0: 正常; 1: 推荐(版块下显示)
	
	private String lookn;		// 浏览量
	private String liken;		// 点赞数
	private String sharen;		// 分享数
	private String commn;		// 评论数
	private String favorn;		// 收藏量
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
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
	public String getSortn() {
		return sortn;
	}
	public void setSortn(String sortn) {
		this.sortn = sortn;
	}
	public String getCateid() {
		return cateid;
	}
	public void setCateid(String cateid) {
		this.cateid = cateid;
	}
	public String getCatename() {
		return catename;
	}
	public void setCatename(String catename) {
		this.catename = catename;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsernname() {
		return usernname;
	}
	public void setUsernname(String usernname) {
		this.usernname = usernname;
	}
	public String getTagid() {
		return tagid;
	}
	public void setTagid(String tagid) {
		this.tagid = tagid;
	}
	public String getTagname() {
		return tagname;
	}
	public void setTagname(String tagname) {
		this.tagname = tagname;
	}
	public String getRecom() {
		return recom;
	}
	public void setRecom(String recom) {
		this.recom = recom;
	}
	public String getLookn() {
		return lookn;
	}
	public void setLookn(String lookn) {
		this.lookn = lookn;
	}
	public String getLiken() {
		return liken;
	}
	public void setLiken(String liken) {
		this.liken = liken;
	}
	public String getSharen() {
		return sharen;
	}
	public void setSharen(String sharen) {
		this.sharen = sharen;
	}
	public String getCommn() {
		return commn;
	}
	public void setCommn(String commn) {
		this.commn = commn;
	}
	public String getFavorn() {
		return favorn;
	}
	public void setFavorn(String favorn) {
		this.favorn = favorn;
	}
	
}
