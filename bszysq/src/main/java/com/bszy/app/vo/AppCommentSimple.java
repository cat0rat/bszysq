package com.bszy.app.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评论(一级)
 * @author jzs 2016年11月19日 上午1:03:38
 */
public class AppCommentSimple implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long artid;			// 文章id
	private Long authorid;		// 文章作者id
	
	private Long userid;		// 评论者id
	private String usernname;	// 评论者昵称(关联)
	private String userhead;	// 评论者头像(关联)
	
	private String content;		// 内容(<1000字符)
	private List<AppCommentSub> subcomms;	// 下级评论
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<AppCommentSub> getSubcomms() {
		return subcomms;
	}
	public void setSubcomms(List<AppCommentSub> subcomms) {
		this.subcomms = subcomms;
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
	
}
