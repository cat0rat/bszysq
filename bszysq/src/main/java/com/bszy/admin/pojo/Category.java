package com.bszy.admin.pojo;

import com.mao.ssm.BasePojo;

/**
 * 版块
 * @author Mao 2016年10月29日 下午1:19:48
 */
public class Category extends BasePojo {
	private static final long serialVersionUID = 1L;
	
	private String name;	// 版块名称(1~500字符)
	private Integer sortn;	// 排序号(默认0, 倒序)
	private String img;		// 配图(<500字符，完整网址)
	private Long artid;		// 主题id
	private String artname;	// 主题标题
	
	protected Integer addart;		// 禁止用户发布主题, 0: 正常; 1: 禁止
	protected Integer addartauth;	// 认证用户才能发布主题, 0: 无需认证; 1: 需要认证
	protected Integer lookartauth;	// 认证用户才能查看, 0: 无需认证; 1: 需要认证; 2: 无需登录
	
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
	public Integer getAddart() {
		return addart;
	}
	public void setAddart(Integer addart) {
		this.addart = addart;
	}
	public Integer getAddartauth() {
		return addartauth;
	}
	public void setAddartauth(Integer addartauth) {
		this.addartauth = addartauth;
	}
	public Integer getLookartauth() {
		return lookartauth;
	}
	public void setLookartauth(Integer lookartauth) {
		this.lookartauth = lookartauth;
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
