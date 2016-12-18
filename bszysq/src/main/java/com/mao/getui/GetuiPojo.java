package com.mao.getui;

import java.util.Date;

/**
 * 个推透传消息内容
 * @author jzs 2016年11月24日 上午1:01:54
 */
public class GetuiPojo {
	
	public String title;		// 标题
	public String content;		// 内容
	public long createTime;		// 时间
	public Integer typex;		// 消息类型: 0: 系统消息, 1: 文章或评论
	public String extra;		// 额外内容, 消息类型为0时,为 文章id
	
	public GetuiPojo() {
	}
	public GetuiPojo(String title, String content) {
		super();
		this.title = title;
		this.content = content;
		this.createTime = new Date().getTime();
	}
	public GetuiPojo(String title, String content, Integer typex, String extra) {
		super();
		this.title = title;
		this.content = content;
		this.typex = typex;
		this.extra = extra;
		this.createTime = new Date().getTime();
	}
	public GetuiPojo(String title, String content, int createTime) {
		super();
		this.title = title;
		this.content = content;
		this.createTime = createTime;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public Integer getTypex() {
		return typex;
	}
	public void setTypex(Integer typex) {
		this.typex = typex;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	@Override
	public String toString() {
		return "GetuiPojo [title=" + title + ", content=" + content + ", createTime=" + createTime + ", typex=" + typex
				+ ", extra=" + extra + "]";
	}
	
	
}
