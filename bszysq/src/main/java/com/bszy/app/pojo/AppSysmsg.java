package com.bszy.app.pojo;


import com.mao.ssm.BasePojo;

/**
 * 系统消息
 * @author jzs 2016年11月27日 下午4:11:05
 */
public class AppSysmsg extends BasePojo {
	private static final long serialVersionUID = 1L;

	public String name;			// 标题(<200字符)
	public String content;		// 内容(<500字符)
	public Integer typex;		// 消息类型: 0: 主题或评论, 100: 系统消息
	public String extra;		// 额外内容, 消息类型为0时,为 主题id
	
	public Long userid;			// 目标用户id
	public String getuicid;		// 个推ClientID(20~50字符)
	
	public String getContent() {
		return content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

}