package com.bszy.app.vo;

import java.io.Serializable;

/**
 * 版块(带一条主题)
 * @author jzs 2016年11月18日 上午1:46:37
 */
public class AppCategoryArt implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;			// ID
	private String name;		// 版块名称(1~500字符)
	private String img;			// 配图(<500字符，完整网址)
	private Long artid;			// 主题id
	private String artname;		// 主题标题
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Long getArtid() {
		return artid;
	}
	public void setArtid(Long artid) {
		this.artid = artid;
	}
	public String getArtname() {
		return artname;
	}
	public void setArtname(String artname) {
		this.artname = artname;
	}
	
}
