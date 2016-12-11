package com.bszy.admin.vo;

import com.mao.ssm.BaseSearch;

public class ArticleSearch extends BaseSearch {
	
	private String name;		// 标题(1~500字符)
	private Long tagid;			// 标签id
	private String tagname;		// 标签名
	private Long cateid;		// 所属版块id
	private String catename;	// 所属版块名称
	private Long userid;		// 发布者id
	private String usernname;	// 发布者昵称(关联)
	private Integer recom;		// 0: 正常; 1: 推荐(版块下显示)
	private Integer isdel;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIsdel() {
		return isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	public Long getTagid() {
		return tagid;
	}
	public void setTagid(Long tagid) {
		this.tagid = tagid;
	}
	public String getTagname() {
		return tagname;
	}
	public void setTagname(String tagname) {
		this.tagname = tagname;
	}
	public Long getCateid() {
		return cateid;
	}
	public void setCateid(Long cateid) {
		this.cateid = cateid;
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
	public Integer getRecom() {
		return recom;
	}
	public void setRecom(Integer recom) {
		this.recom = recom;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}

	
}
