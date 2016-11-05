package com.bszy.admin.pojo;

import com.mao.ssm.BasePojo;

/**
 * 标签
 * @author Mao 2016年10月29日 下午1:20:02
 */
public class Arttag extends BasePojo {
	private static final long serialVersionUID = 1L;
	
	private String name;	// 标签名称(1~20字符)
	private Integer sortn;	// 排序号(默认0, 倒序)
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSortn() {
		return sortn;
	}
	public void setSortn(Integer sortn) {
		this.sortn = sortn;
	}
	
}
