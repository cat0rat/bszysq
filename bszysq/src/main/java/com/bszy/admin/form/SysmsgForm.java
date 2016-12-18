package com.bszy.admin.form;

import com.mao.ssm.BaseForm;

public class SysmsgForm extends BaseForm {
	private static final long serialVersionUID = 1L;
	
	private String name;		// 标题(<200字符)
	private String content;		// 内容(<500字符)
	private String typex;		// 消息类型: 0: 系统消息, 1: 主题或评论
	private String extra;		// 额外内容, 消息类型为1时,为 主题id
	
	private String userid;		// 目标用户id
	private String getuicid;	// 个推ClientID(20~50字符)
	protected String ids;		// 目标用户ids
	
	private String rangx;		// 推送范围: 0: 单用户, 1: 安卓用户, 2: IOS用户, 10: 指定用户, 11: 条件用户, 20: 全部用户
	private String descx;		// 推送描述(<500字符)
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTypex() {
		return typex;
	}
	public void setTypex(String typex) {
		this.typex = typex;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getGetuicid() {
		return getuicid;
	}
	public void setGetuicid(String getuicid) {
		this.getuicid = getuicid;
	}
	public String getRangx() {
		return rangx;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setRangx(String rangx) {
		this.rangx = rangx;
	}
	public String getDescx() {
		return descx;
	}
	public void setDescx(String descx) {
		this.descx = descx;
	}
	
}
