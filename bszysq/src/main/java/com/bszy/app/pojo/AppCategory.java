package com.bszy.app.pojo;

import com.mao.ssm.BasePojo;

/**
 * 版块
 * @author Mao 2016年10月29日 下午1:19:48
 */
public class AppCategory extends BasePojo {
	private static final long serialVersionUID = 1L;
	
	private String name;		// 版块名称(1~500字符)
	private Integer sortn;		// 排序号(默认0, 倒序)
	private String img;			// 配图(<500字符，完整网址)
	private String artid;		// 文章id
	private String artname;		// 文章标题
	
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
	public String getArtid() {
		return artid;
	}
	public void setArtid(String artid) {
		this.artid = artid;
	}
	public String getArtname() {
		return artname;
	}
	public void setArtname(String artname) {
		this.artname = artname;
	}
	
}
