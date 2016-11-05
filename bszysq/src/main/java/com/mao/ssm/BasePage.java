package com.mao.ssm;

import java.io.Serializable;
import java.util.List;

/**
 * 基本分页类
 * @author Mao 2016年10月30日 下午8:00:13
 */
public class BasePage <T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer page;	// 页码
	private Integer limit;	// 每页条数
	private Long total = 0L;		// 总记录数
	private Integer totalPage = 0;	// 总页数
	private Long lastid = 0L;		// 最后一条数据的id
	private List<T> rows;			// 数据
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public Long getLastid() {
		return lastid;
	}
	public void setLastid(Long lastid) {
		this.lastid = lastid;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
	
	// TODO 辅助方法
	
	public void t_result(Long total, List<T> rows){
		this.total = total == null ? 0 : total;
		this.rows = rows;
		calcTotalPage();
	}
	
	/** 计算总页数 */
	public void calcTotalPage(){
		if(limit != null && limit > 0 && total != null && total > 0){
			totalPage = (int)(total / limit);
			if(total % limit != 0L){
				totalPage++;
			} 
		}else{
			totalPage = 0;
		}
	}
	
	public void t_param(Integer page, Integer limit){
		this.page = page == null ? 1 : page;
		this.limit = limit == null ? 0 : limit;
	}
	public void t_param(int page, int limit){
		this.page = page;
		this.limit = limit;
	}
}
