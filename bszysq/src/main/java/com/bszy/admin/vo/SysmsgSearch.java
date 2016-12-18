package com.bszy.admin.vo;

import java.util.Date;

import com.mao.ssm.BaseSearch;

public class SysmsgSearch extends BaseSearch {
	
	private String name;		// 标题(<200字符)
	private String content;		// 内容(<500字符)
	private Integer typex;		// 消息类型: 0: 系统消息, 1: 主题或评论
	private String extra;		// 额外内容, 消息类型为1时,为 主题id
	
	private Long userid;		// 目标用户id
	private String getuicid;	// 个推ClientID(20~50字符)
	
	private Integer rang;		// 推送范围: 0: 单用户, 1: 安卓用户, 2: IOS用户, 10: 指定用户, 11: 条件用户, 20: 全部用户
	private String descx;		// 推送描述(<500字符)
	
	private Date stime;		// 开始时间
	private Date etime;		// 结束时间
	protected Integer isdel;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStime() {
		return stime;
	}
	public Integer getIsdel() {
		return isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	public void setStime(Date stime) {
		this.stime = stime;
	}
	public Date getEtime() {
		return etime;
	}
	public void setEtime(Date etime) {
		this.etime = etime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getGetuicid() {
		return getuicid;
	}
	public void setGetuicid(String getuicid) {
		this.getuicid = getuicid;
	}
	public Integer getRang() {
		return rang;
	}
	public void setRang(Integer rang) {
		this.rang = rang;
	}
	public String getDescx() {
		return descx;
	}
	public void setDescx(String descx) {
		this.descx = descx;
	}
	
}
