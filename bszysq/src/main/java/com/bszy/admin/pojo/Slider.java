package com.bszy.admin.pojo;


import com.mao.ssm.BasePojo;

/**
 * 轮播
 * @author Mao 2016年11月9日 下午7:30:13
 */
public class Slider extends BasePojo {
	private static final long serialVersionUID = 1L;

	private String name;	// 标题(<100字符)
	private String img;		// 配图(<500字符，完整网址)
	private Integer sortn;	// 排序号(默认0, 倒序)
	private Integer pos;	// 位置(0:首页)
	private String brief;	// 简介(<500字符)
	private String link;	// 跳转地址(<500字符，完整网址)
	
	private Integer typex;		// 跳转类型, 0: 外链; 1: 内部主题
	private Long artid;			// 主题id
	private Long cateid;		// 版块id
	private String artname;		// 主题标题
	private String catename;	// 版块标题
	
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
	public Integer getTypex() {
		return typex;
	}
	public void setTypex(Integer typex) {
		this.typex = typex;
	}
	public Long getArtid() {
		return artid;
	}
	public void setArtid(Long artid) {
		this.artid = artid;
	}
	public Long getCateid() {
		return cateid;
	}
	public void setCateid(Long cateid) {
		this.cateid = cateid;
	}
	public String getArtname() {
		return artname;
	}
	public void setArtname(String artname) {
		this.artname = artname;
	}
	public String getCatename() {
		return catename;
	}
	public void setCatename(String catename) {
		this.catename = catename;
	}
	public Integer getSortn() {
		return sortn;
	}
	public void setSortn(Integer sortn) {
		this.sortn = sortn;
	}
	public Integer getPos() {
		return pos;
	}
	public void setPos(Integer pos) {
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