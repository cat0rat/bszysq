package com.bszy.admin.pojo;

import com.mao.ssm.BasePojo;

/**
 * 地区代码
 * @author Mao 2016年10月29日 下午1:19:26
 */
public class Area extends BasePojo {
	private static final long serialVersionUID = 1L;
	
	private Integer code;	// 代码(6位数值)
	private String name;	// 名称(<50字符)
	private Integer layer;	// 层级(1,2,3)
	private Integer pcode;	// 上级代码
	private Integer sortn;	// 排序号(默认0, 倒序)
	
	private String segment;	// 区号(4位数字)
	private String fletter;	// 首字母(大写)
	private Double lon;		// 经度(保留6位小数)
	private Double lat;		// 纬度(保留6位小数)
	private String ext;		// 上级代码[,上上级代码](<50字符, 英文逗号分隔)
	private String sname;	// 短名称(<20字符)
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public Integer getLayer() {
		return layer;
	}
	public void setLayer(Integer layer) {
		this.layer = layer;
	}
	public Integer getPcode() {
		return pcode;
	}
	public void setPcode(Integer pcode) {
		this.pcode = pcode;
	}
	public String getSegment() {
		return segment;
	}
	public void setSegment(String segment) {
		this.segment = segment;
	}
	public Integer getSortn() {
		return sortn;
	}
	public void setSortn(Integer sortn) {
		this.sortn = sortn;
	}
	public String getFletter() {
		return fletter;
	}
	public void setFletter(String fletter) {
		this.fletter = fletter;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	
}
