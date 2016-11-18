package com.bszy.app.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 主题 详情
 * @author Mao 2016年10月29日 下午1:19:55
 */
public class AppArticleDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;		// 标题(1~500字符)
	private String img;			// 配图(<500字符，完整网址)
	private String content;		// 内容(<1万字)
	private String imgs;		// 多图(<5000字符，完整网址)
	
	private Long tagid;			// 标签id
	private String tagname;		// 标签名(关联)
	private Long cateid;		// 所属版块id
	private String catename;	// 所属版块名称(关联)
	private Long userid;		// 发布者id
	private String usernname;	// 发布者昵称(关联)
	private String userhead;	// 发布者头像(关联)
	
	private Long id;		// ID
	private Date ctime;		// 创建时间
	
	private List<AppCommentSimple> comms;	// 前5条评论
	
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
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
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
	public String getUserhead() {
		return userhead;
	}
	public void setUserhead(String userhead) {
		this.userhead = userhead;
	}
	public List<AppCommentSimple> getComms() {
		return comms;
	}
	public void setComms(List<AppCommentSimple> comms) {
		this.comms = comms;
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
