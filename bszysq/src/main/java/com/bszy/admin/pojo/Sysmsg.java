package com.bszy.admin.pojo;


import com.mao.ssm.BasePojo;

/**
 * 系统消息
 * @author jzs 2016年11月27日 下午4:11:05
 */
public class Sysmsg extends BasePojo {
	private static final long serialVersionUID = 1L;
	
	public static Integer Typex_Sys = 0;
	public static Integer Typex_Art = 1;

	private String name;		// 标题(<200字符)
	private String content;		// 内容(<500字符)
	private Integer typex;		// 消息类型: 0: 系统消息, 1: 主题或评论
	private String extra;		// 额外内容, 消息类型为1时,为 主题id
	
	private Long userid;			// 目标用户id
	private String getuicid;		// 个推ClientID(20~50字符)
	
	private Integer rangx;		// 推送范围: 0: 单用户, 1: 安卓用户, 2: IOS用户, 10: 指定用户, 11: 条件用户, 20: 全部用户
	private String descx;		// 推送描述(<500字符)
	
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
	public Integer getRangx() {
		return rangx;
	}
	public void setRangx(Integer rangx) {
		this.rangx = rangx;
	}
	public String getDescx() {
		return descx;
	}
	public void setDescx(String descx) {
		this.descx = descx;
	}

}