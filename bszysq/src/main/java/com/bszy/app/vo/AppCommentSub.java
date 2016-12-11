package com.bszy.app.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论(下级)
 * @author jzs 2016年11月19日 上午1:03:57
 */
public class AppCommentSub implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long artid;			// 文章id
	private Long authorid;		// 文章作者id
	
	private Long userid;		// 评论者id
	private String usernname;	// 评论者昵称(关联)
	private String userhead;	// 评论者头像(关联)
	
	private Long commid;		// 对哪条评论id的评论
	private Long touserid;		// 对谁的评论id
	private String tousernname;	// 对谁的评论昵称(关联)
	private String touserhead;	// 对谁的评论头像(关联)
	
	private String content;		// 内容(<1000字符)
	
	private Long id;		// ID
	private Date ctime;		// 创建时间
	
	public Long getArtid() {
		return artid;
	}
	public void setArtid(Long artid) {
		this.artid = artid;
	}
	public Long getAuthorid() {
		return authorid;
	}
	public void setAuthorid(Long authorid) {
		this.authorid = authorid;
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
	public String getUserhead() {
		return userhead;
	}
	public void setUserhead(String userhead) {
		this.userhead = userhead;
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
	public String getTouserhead() {
		return touserhead;
	}
	public void setTouserhead(String touserhead) {
		this.touserhead = touserhead;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Long getCommid() {
		return commid;
	}
	public void setCommid(Long commid) {
		this.commid = commid;
	}
	
}
