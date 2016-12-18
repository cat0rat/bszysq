package com.mao.ssm;

import javax.servlet.http.HttpServletRequest;

import com.mao.lang.MUtil;

/**
 * 搜索表单 基类
 * @author Mao 2015年9月3日 下午3:35:37
 */
public class BaseSearch {
	
	protected String orderby;		// 排序, 如: id, 或 id desc,utime desc
	
	protected Long start = 0L;	// 开始位置
	protected Integer page = 1;	// 页码, 1开始。
	protected Integer limit = Limit;	// 每页条数，默认20。
	protected Long lastid;	// 上一次查询最后一条数据的id。
	public static Integer Limit = 20;
	
	protected Long uid;	// 当前用户ID
	
	
	// TODO 业务
	
	/** 从request中获取分页信息, 来自 start、page、limit参数 */
	public boolean build(HttpServletRequest request){
		if(request != null){
			try {
				Long start = MUtil.toLong(request.getParameter("start"));
				Integer page = MUtil.toInteger(request.getParameter("page"));
				Integer limit = MUtil.toInteger(request.getParameter("limit"));
				if(start != null){
					this.start = start;
				}
				if(page != null){
					this.page = page;
				}
				if(limit != null){
					this.limit = limit;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/** 返回 开始位置 */
	public Long start(){
		if(start != null) return start;
		return start_i();
	}
	/** 返回 开始位置 */
	public long start_i(){
		if(page != null && limit != null){
			if(page < 1) page = 1;
			start = (long) ((page - 1) * limit);
		}else{
			start = 0L;
		}
		return start;
	}
	/** 返回 每页显示数 */
	public Integer limit(){
		return limit;
	}
	/** 返回 每页显示数, >0返回limit, 否则返回0 */
	public int limit_i(){
		return limit != null && limit > 0 ? limit : 0;
	}
	/** 返回 页码, >0返回page, 否则返回1 */
	public int page_i(){
		return page != null && page > 0 ? page : 1;
	}
	/** 加页数, 返回页码 */
	public int addPage(int x) {
		if(page == null) page = 1;
		page += x;
		return page;
	}
	/** 检测必须有限制条数, 如果没有, 设置为Limit */
	public int limit_must(){
		return limit_must(Limit);
	}
	/** 检测必须有限制条数, 如果没有, 设置为def */
	public int limit_must(int def){
		if(limit == null || limit < 1) limit = def;
		return limit;
	}

	
	// TODO get set
	
//	public Integer getPage() {
//		return page;
//	}

	public void setPage(Integer page) {
		this.page = page;
	}

//	public Integer getLimit() {
//		return limit;
//	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public void setStart(Long start) {
		this.start = start;
	}
	public Long getLastid() {
		return lastid;
	}
	public void setLastid(Long lastid) {
		this.lastid = lastid;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	
	
}
