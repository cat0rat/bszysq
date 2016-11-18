package com.bszy.app.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 主题(简单)
 * @author jzs 2016年11月19日 上午12:40:27
 */
public class AppArticleSimple implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;		// 标题(1~500字符)
	private String img;			// 配图(<500字符，完整网址)
	private String content;		// 内容(<1万字)
	private String tagid;		// 标签id
	private String tagname;		// 标签名(关联)
	private Long userid;		// 发布者id
	private String usernname;	// 发布者昵称(关联)
	private String userhead;	// 发布者头像(关联)
	
	private Long id;		// ID
	private Date ctime;		// 创建时间
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTagid() {
		return tagid;
	}
	public void setTagid(String tagid) {
		this.tagid = tagid;
	}
	public String getTagname() {
		return tagname;
	}
	public void setTagname(String tagname) {
		this.tagname = tagname;
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
	public String getUserhead() {
		return userhead;
	}
	public void setUserhead(String userhead) {
		this.userhead = userhead;
	}
	
}
