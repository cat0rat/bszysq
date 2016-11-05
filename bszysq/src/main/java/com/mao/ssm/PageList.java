package com.mao.ssm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PageList<T> {
	public static int defaultPageSize = 10;

	private List<T> page = null; // 记录列表

	private int pageCount = 0; // 总页数

	private int count = 0; // 总记录数目

	private int pageIndex = 0; // 第几页

	private int pageSize = 0; // 分页大小

	private int pageFirstIndex; // 本页第一条

	private int pageLastIndex; // 本页最后一条

	private String info_key_pageIndex = "pageIndex";

	private String info_gap = "  ";

	public static final PageList EMPTY = new PageList(null, 0, 0, 0,
			defaultPageSize);

	/**
	 * 
	 * @param page
	 *            List
	 * @param count
	 *            int
	 * @param pageIndex
	 *            int:当前页第的页号 1<=pageIndex<=pageCount
	 * @param pageSize
	 *            int
	 */
	public PageList(List<T> page, int count, int pageIndex, int pageSize) {
		// 修正page的值
		List<T> pageFixed = page == null ? new ArrayList<T>() : page;
		// 修正count的值
		if (count < 0) {
			throw new RuntimeException("无效的count值！！！");
		}
		int countFixed = count;
		// 修正pageSize的值
		if (pageSize < 0) {
			throw new RuntimeException("无效的pageSize值！！！");
		}
		int pageSizeFixed = pageSize;
		// 总是计算得到pageCount,此时认为count和pageSize是有效的
		int pageCountFixed = count / pageSize;
		if (count % pageSize != 0) {
			pageCountFixed++;
		}
		/**
		 * 修正pageIndex的值,此时认为pageCount的值是有效的
		 */
		int pageIndexFixed = pageIndex < 0 ? 1 : pageIndex;
		// 总是用pageCount的值修正pageIndex的值
		pageIndexFixed = pageIndex > pageCountFixed ? pageCountFixed
				: pageIndex;
		// 设置结果
		this.page = pageFixed;
		this.pageCount = pageCountFixed;
		this.count = countFixed;
		this.pageIndex = pageIndexFixed;
		this.pageSize = pageSizeFixed;
	}

	/**
	 * @param page
	 *            List
	 * @param pageCount
	 *            int
	 * @param count
	 *            int
	 * @param pageIndex
	 *            int
	 * @param pageSize
	 *            int
	 */
	public PageList(List<T> page, int pageCount, int count, int pageIndex,
			int pageSize) {
		if (page == null) {
			page = new ArrayList<T>();
		}
		this.page = page;
		this.pageCount = pageCount;
		this.count = count;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	 public static PageList emptyList(int pageSize) {
		    return new PageList(null, 0, 0, 0, pageSize);
		  }

		  public static PageList wrapList(List fullList, int pageIndex, int pageSize) {
		    List dataList = (fullList == null) ? new ArrayList() : fullList;
		    int cnt = dataList.size();
		    if (cnt == 0) {
		      return new PageList(new ArrayList(), 0, 0, 0, pageSize);
		    }

		    if (pageSize <= 0) {
		      pageSize = cnt;
		    }
		    int pageCnt = cnt / pageSize;
		    if ((cnt % pageSize) != 0) {
		      pageCnt++;
		    }
		    int pageInd = (pageIndex < 1) ? 1 : pageIndex;
		    pageInd = (pageIndex > pageCnt) ? pageCnt : pageIndex;
		    int begin = (pageInd - 1) * pageSize;
		    if (begin < 0) {
		      begin = 0;
		      // 1<=cnt=fullList.size()
		      // 0<=maxIndex<=cnt-1
		      // 0<=begin<=maxIndex=cnt-1
		      // 0<=end<=maxIndex=cnt-1
		    }
		    int end = begin + pageSize - 1;
		    int maxIndex = cnt - 1;
		    if (end > maxIndex) {
		      end = maxIndex;

		    }
		    List list = new ArrayList(pageSize);
		    int skip = -1;
		    Iterator iter = dataList.iterator();
		    while (iter.hasNext()) {
		      skip++;
		      Object obj = iter.next();
		      if (skip < begin) {
		        continue;
		      }

		      if (skip > end) {
		        break;
		      }

		      list.add(obj);

		    }
		    // 用实际的参数建立PageList
		    return new PageList(list, pageCnt, cnt, pageInd, pageSize);
		  }
		  
	public static int getDefaultPageSize() {
		return defaultPageSize;
	}

	public static void setDefaultPageSize(int defaultPageSize) {
		PageList.defaultPageSize = defaultPageSize;
	}

	public int getPageFirstIndex() {
		return pageFirstIndex;
	}

	public void setPageFirstIndex(int pageFirstIndex) {
		this.pageFirstIndex = pageFirstIndex;
	}

	public int getPageLastIndex() {
		return pageLastIndex;
	}

	public void setPageLastIndex(int pageLastIndex) {
		this.pageLastIndex = pageLastIndex;
	}

	public String getInfo_key_pageIndex() {
		return info_key_pageIndex;
	}

	public void setInfo_key_pageIndex(String info_key_pageIndex) {
		this.info_key_pageIndex = info_key_pageIndex;
	}

	public String getInfo_gap() {
		return info_gap;
	}

	public void setInfo_gap(String info_gap) {
		this.info_gap = info_gap;
	}

	public static PageList getEmpty() {
		return EMPTY;
	}

	public void setPage(List<T> page) {
		this.page = page;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getPage() {
		return page;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getCount() {
		return count;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public int getPrevPageIndex() {
		int idx = pageIndex - 1;
		if (idx < 1) {
			idx = 1;
		}
		return idx;
	}

	public int getNextPageIndex() {
		int idx = pageIndex + 1;
		if (idx > pageCount) {
			idx = pageCount;
		}
		return idx;
	}

	public boolean isFirstPage() {
		return pageIndex <= 1;
	}

	public boolean isMiddlePage() {
		return ((pageIndex > 1) && (pageIndex < pageCount));
	}

	public boolean isLastPage() {
		return ((pageIndex >= pageCount) && (pageCount != 0));
	}

	public boolean isNextPageAvailable() {
		return (!isLastPage());
	}

	public boolean isPreviousPageAvailable() {
		return (!isFirstPage());
	}

	public boolean isPageExisted() {
		if (getCount() == 0) {
			return false;
		}
		return true;
	}

	public String getDescInfo() {
		StringBuffer sb = new StringBuffer();
		// 搜索结果：共20页
		sb.append("搜索结果：共").append(this.getPageCount()).append("页");
		return sb.toString();
	}

	public String getPositionInfo() {
		StringBuffer sb = new StringBuffer();
		// 1/20
		sb.append(info_gap).append(this.getPageIndex()).append("/")
				.append(this.getPageCount());
		return sb.toString();
	}

	public String getNavInfo(String getPageUrl) {
		StringBuffer sb = new StringBuffer();
		// 第一页
		if (!this.isFirstPage()) {
			sb.append("<a title='第一页' href=").append(getPageUrl).append("&")
					.append(info_key_pageIndex).append("=1").append(">|<</a> ");
		} else {
			sb.append("|< ");
		}
		// 上一页
		if (this.isPreviousPageAvailable()) {
			sb.append(info_gap).append("<a title='上一页' href=")
					.append(getPageUrl).append("&").append(info_key_pageIndex)
					.append("=").append(this.getPrevPageIndex())
					.append("><<</a> ");
		} else {
			sb.append(info_gap).append("<< ");
		}
		// 下一页
		if (this.isNextPageAvailable()) {
			sb.append(info_gap).append("<a title='下一页' href=")
					.append(getPageUrl).append("&").append(info_key_pageIndex)
					.append("=").append(this.getNextPageIndex())
					.append(">>></a> ");
		} else {
			sb.append(info_gap).append(">> ");
		}
		// 最后一页
		if (!this.isLastPage()) {
			sb.append("<a title='最后一页' href=").append(getPageUrl).append("&")
					.append(info_key_pageIndex).append("=")
					.append(this.getPageCount()).append(">>|</a>");
		} else {
			sb.append(">|");
		}
		return sb.toString();
	}

	public String getInfo(String getPageUrl) {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getDescInfo()).append(this.info_gap)
				.append(this.getPositionInfo()).append(this.info_gap)
				.append(this.getNavInfo(getPageUrl));
		return sb.toString();

	}

}
