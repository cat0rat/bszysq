package com.bszy.admin.pojo;

import com.mao.ssm.BaseSearch;

public class CommentSearch extends BaseSearch {
	
	private Long artid;			// 文章id
	private String artname;		// 文章标题(关联)
	
	private Long authorid;		// 文章作者id
	private String authornname;	// 文章作者昵称(关联)
	
	private Long userid;		// 评论者id
	private String usernname;	// 评论者昵称(关联)
	
	private Long touserid;		// 对谁的评论id
	private String tousernname;	// 对谁的评论昵称(关联)
	
	private String content;		// 内容(<1000字符)
	private Integer isdel;		// 删除标记, 0: 正常; 1: 已删除
	
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
	public Long getAuthorid() {
		return authorid;
	}
	public void setAuthorid(Long authorid) {
		this.authorid = authorid;
	}
	public String getAuthornname() {
		return authornname;
	}
	public void setAuthornname(String authornname) {
		this.authornname = authornname;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUsernname() {
		return usernname;
	}
	public void setUsernname(String usernname) {
		this.usernname = usernname;
	}
	public Long getTouserid() {
		return touserid;
	}
	public void setTouserid(Long touserid) {
		this.touserid = touserid;
	}
	public String getTousernname() {
		return tousernname;
	}
	public void setTousernname(String tousernname) {
		this.tousernname = tousernname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getIsdel() {
		return isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

}
