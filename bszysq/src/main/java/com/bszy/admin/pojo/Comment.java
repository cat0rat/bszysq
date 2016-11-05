package com.bszy.admin.pojo;

import com.mao.ssm.BasePojo;

/**
 * 评论
 * @author Mao 2016年10月29日 下午1:18:52
 */
public class Comment extends BasePojo {
	private static final long serialVersionUID = 1L;
	
	private String img;		// 配图(<500字符，完整网址)
	private String context;	// 内容(<1万字)
	private String imgs;	// 多图(<5000字符，完整网址)
	private Integer sortn;	// 排序号(默认0, 倒序)
	private String tagids;	// 标签id(多英文逗号分隔)
	private Long cateid;	// 所属版块id
	private Long userid;	// 创建者id
	private Integer recom;	// 0: 正常; 1: 推荐(版块下显示)
	
	private Long lookn;		// 浏览量
	private Long liken;		// 点赞数
	private Long sharen;	// 分享数
	private Long commn;		// 评论数
	private Long favorn;	// 收藏量
	
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
	public String getTagids() {
		return tagids;
	}
	public void setTagids(String tagids) {
		this.tagids = tagids;
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
	
}
