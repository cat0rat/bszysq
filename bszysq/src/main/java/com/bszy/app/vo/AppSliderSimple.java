package com.bszy.app.vo;


import java.io.Serializable;

/**
 * 轮播
 * @author Mao 2016年11月9日 下午7:30:13
 */
public class AppSliderSimple implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;		// ID
	private String name;	// 标题(<100字符)
	private String img;		// 配图(<500字符，完整网址)
	private Integer pos;	// 位置(0:首页)
	private String link;	// 跳转地址(<500字符，完整网址)

	private Integer typex;		// 跳转类型, 0: 外链; 1: 内部主题
	private Long artid;			// 主题id
	private Long cateid;		// 版块id
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Integer getPos() {
		return pos;
	}
	public void setPos(Integer pos) {
		this.pos = pos;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

}