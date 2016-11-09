package com.bszy.admin.pojo;

import com.mao.ssm.BasePojo;

/**
 * 评论
 * @author Mao 2016年10月29日 下午1:18:52
 */
public class Comment extends BasePojo {
	private static final long serialVersionUID = 1L;
	
	private Long artid;			// 文章id
	private String artname;		// 文章标题(关联)
	private Long authorid;		// 文章作者id
	private String authornname;	// 文章作者昵称(关联)
	private String authorhead;	// 文章作者头像(关联)
	
	private Long userid;		// 评论者id
	private String usernname;	// 评论者昵称(关联)
	private String userhead;	// 评论者头像(关联)
	
	private Long touserid;		// 对谁的评论id
	private String tousernname;	// 对谁的评论昵称(关联)
	private String touserhead;	// 对谁的评论头像(关联)
	
	private String context;		// 内容(<1000字符)
	private String imgs;		// 多图(<5000字符，完整网址)
	
	private Long liken;		// 点赞数
	private Long commn;		// 评论数
	
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
	public String getAuthorhead() {
		return authorhead;
	}
	public void setAuthorhead(String authorhead) {
		this.authorhead = authorhead;
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
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
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
	public Long getLiken() {
		return liken;
	}
	public void setLiken(Long liken) {
		this.liken = liken;
	}
	public Long getCommn() {
		return commn;
	}
	public void setCommn(Long commn) {
		this.commn = commn;
	}
	
}
