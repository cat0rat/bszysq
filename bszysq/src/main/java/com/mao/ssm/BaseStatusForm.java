package com.mao.ssm;

import java.io.Serializable;

/**
 * 处理状态变化
 * @author jzs 2016年11月29日 上午2:07:57
 */
public class BaseStatusForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;		// ID
	private String ids;		// IDs
	private String status;	// 更新为的状态值
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
