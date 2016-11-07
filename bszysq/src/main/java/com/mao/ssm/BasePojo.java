package com.mao.ssm;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BasePojo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;		// ID
	private Date ctime;		// 创建时间
	private Date utime;		// 更新时间
	private Integer isdel;	// 删除标记, 0: 正常; 1: 已删除
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getIsdel() {
		return isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Date getUtime() {
		return utime;
	}
	public void setUtime(Date utime) {
		this.utime = utime;
	}
	
	// TODO 辅助
	public String getUtimeStr() {
		if(utime != null)
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(utime);
		else
			return null;
	}
	public String getCtimeStr() {
		if(ctime != null)
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ctime);
		else
			return null;
	}
	public String getIsdelStr() {
		return isdel != null && isdel == 0 ? "正常" : "已删除";
	}
	
	public void init_add(){
		ctime = new Date();
		utime = ctime;
		isdel = 0;
	}
	public void init_update(){
		utime = new Date();
	}
	
}
