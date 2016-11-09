package com.bszy.admin.pojo;

import com.mao.ssm.BasePojo;

/**
 * 主题
 * @author Mao 2016年10月29日 下午1:19:55
 */
public class Article extends BasePojo {
	private static final long serialVersionUID = 1L;
	
	private String name;		// 标题(1~500字符)
	private String img;			// 配图(<500字符，完整网址)
	private String brief;		// 简介(<500字符)
	private String context;		// 内容(<1万字)
	private String imgs;		// 多图(<5000字符，完整网址)
	
	private Integer sortn;		// 排序号(默认0, 倒序)
	private String tagid;		// 标签id
	private String tagname;		// 标签名(关联)
	private Long cateid;		// 所属版块id
	private String catename;	// 所属版块名称(关联)
	private Long userid;		// 发布者id
	private String usernname;	// 发布者昵称(关联)
	private Integer recom;		// 0: 正常; 1: 推荐(版块下显示)
	
	private Long lookn;		// 浏览量
	private Long liken;		// 点赞数
	private Long sharen;	// 分享数
	private Long commn;		// 评论数
	private Long favorn;	// 收藏量
	
	public String getRecomStr() {
		if(recom != null)
			if(0 == recom) return "正常";
			else if(1 == recom) return "推荐";
		return "未知";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSortn() {
		return sortn;
	}
	public void setSortn(Integer sortn) {
		this.sortn = sortn;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Integer getRecom() {
		return recom;
	}
	public void setRecom(Integer recom) {
		this.recom = recom;
	}
	public Long getLookn() {
		return lookn;
	}
	public void setLookn(Long lookn) {
		this.lookn = lookn;
	}
	public Long getLiken() {
		return liken;
	}
	public void setLiken(Long liken) {
		this.liken = liken;
	}
	public Long getSharen() {
		return sharen;
	}
	public void setSharen(Long sharen) {
		this.sharen = sharen;
	}
	public Long getCommn() {
		return commn;
	}
	public void setCommn(Long commn) {
		this.commn = commn;
	}
	public Long getFavorn() {
		return favorn;
	}
	public void setFavorn(Long favorn) {
		this.favorn = favorn;
	}
	public Long getCateid() {
		return cateid;
	}
	public void setCateid(Long cateid) {
		this.cateid = cateid;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getCatename() {
		return catename;
	}
	public void setCatename(String catename) {
		this.catename = catename;
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
	
}
