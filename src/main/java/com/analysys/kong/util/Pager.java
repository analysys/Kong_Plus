package com.analysys.kong.util;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 * @param <T>
 */
public class Pager<T> implements Serializable {
	private static final long serialVersionUID = -5911711666648944453L;
	public static final Integer DEFAULT_PAGE_SIZE = 10;
	private List<T> pageData;
	private int totalPage;
	private int totalCount;
	private int currentPage = 1;
	private int pageSize = 10;
	private int firstPage;
	private int prePage;
	private int nextPage;
	private int lastPage;
	private boolean isFirst;
	private boolean isLast;
	private int displayPageNums = 5;

	/**
	 * 分页处理
	 * @param current_page 当前页
	 * @param page_size 每页的数量
	 * @param total_count 总记录数
	 */
	public void process(int current_page, int page_size, int total_count) {
		this.pageSize = page_size;
		this.totalCount = total_count;
		this.totalPage = (this.totalCount + page_size - 1) / page_size;
		this.currentPage = (current_page < 1 || current_page > totalPage) ? 1 : current_page;
		this.firstPage = 1;
		this.lastPage = totalPage;
		this.prePage = this.currentPage > 1 ? this.currentPage - 1 : 1;
		this.nextPage = this.currentPage < totalPage ? this.currentPage + 1 : totalPage;
		this.isFirst = this.currentPage <= 1;
		this.isLast = this.currentPage >= totalPage;
	}

	/**
	 * 验证pageIndex是否有效
	 * @param pageIndex
	 * @return
	 */
	public static Integer handPageIndex(Integer pageIndex) {
		return (pageIndex == null || pageIndex < 0) ? 1 : pageIndex;
	}

	/**
	 * 处理无效页大小
	 * @param pageSize
	 * @return
	 */
	public static Integer handPageSize(Integer pageSize) {
		return (pageSize == null || pageSize == 0) ? DEFAULT_PAGE_SIZE : pageSize;
	}

	/**
	 * 获取显示页号
	 * @return
	 */
	public List<Integer> getDisplayPages() {
		List<Integer> display = new LinkedList<Integer>();
		int index = (currentPage % displayPageNums) != 0 ? currentPage - currentPage % displayPageNums + 1 : (currentPage > 0 ? currentPage - displayPageNums + 1 : 1);
		index = index > totalPage ? 1 : index;
		for (int i = 1; index <= totalPage && i <= displayPageNums; i++, index++) {
			display.add(index);
		}
		return display;
	}
	
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		if(totalCount > 0)
			setTotalPage((totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize + 1));
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}

	public List<T> getData() {
		return pageData;
	}

	public List<T> getPageData() {
		return pageData;
	}

	public int getDisplayPageNums() {
		return displayPageNums;
	}

	public void setDisplayPageNums(int displayPageNums) {
		this.displayPageNums = displayPageNums;
	}
}