package com.bszy.app.pojo;

import java.io.Serializable;

/**
 * 用户
 * @author Mao 2016年10月29日 下午1:19:39
 */
public class AppUserGetui implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;			// ID
	private String nname;		// 昵称(2~16位字母数字汉字)
	private String getuicid;	// 个推ClientID(20~50字符)
	private String getuialias;	// 个推别名(<50字符)
	private String getuitag;	// 个推标签(<500字符)
	private Integer phonetype;	// 手机类型: 0: 安卓; 1: 苹果;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNname() {
		return nname;
	}
	public void setNname(String nname) {
		this.nname = nname;
	}
	public String getGetuicid() {
		return getuicid;
	}
	public void setGetuicid(String getuicid) {
		this.getuicid = getuicid;
	}
	public String getGetuialias() {
		return getuialias;
	}
	public void setGetuialias(String getuialias) {
		this.getuialias = getuialias;
	}
	public String getGetuitag() {
		return getuitag;
	}
	public Integer getPhonetype() {
		return phonetype;
	}
	public void setPhonetype(Integer phonetype) {
		this.phonetype = phonetype;
	}
	public void setGetuitag(String getuitag) {
		this.getuitag = getuitag;
	}
	
}
