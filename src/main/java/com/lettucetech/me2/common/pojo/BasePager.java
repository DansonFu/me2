package com.lettucetech.me2.common.pojo;

import com.lettucetech.me2.common.utils.WebUtil;

/**
 * 分页请求对象
 * 
 * @author 刘洋
 */
public class BasePager {

	/** 显示页码 */
	private Integer pageIndex;

	/** 显示记录数 */
	private Integer pageSize;

	/** 排序字段 */
	private String sortField;

	/** 排序方式 */
	private String sortOrder;

	/** 取得数据开始条数 */
	private Integer recordStart;

	/** 取得数据结束条数 */
	private Integer recordEnd;

	/**
	 * @return the pageIndex
	 */
	public Integer getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex
	 *            the pageIndex to set
	 */
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the sortField
	 */
	public String getSortField() {
		return WebUtil.toClumn(sortField);

	}

	/**
	 * @param sortField
	 *            the sortField to set
	 */
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	/**
	 * @return the sortOrder
	 */
	public String getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder
	 *            the sortOrder to set
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the recordStart
	 */
	public Integer getRecordStart() {
		if (this.getPageIndex() == null || this.getPageSize() == null) {
			return null;
		}
		recordStart = this.getPageIndex() * this.getPageSize();
		return recordStart;
	}

	/**
	 * @param recordStart
	 *            the recordStart to set
	 */
	public void setRecordStart(Integer recordStart) {
		this.recordStart = recordStart;
	}

	/**
	 * @return the recordEnd
	 */
	public Integer getRecordEnd() {
		if (this.getPageIndex() == null || this.getPageSize() == null) {
			return null;
		}
		recordEnd = (this.getPageIndex() * this.getPageSize()) + this.getPageSize();
		return recordEnd;
	}

	/**
	 * @param recordEnd
	 *            the recordEnd to set
	 */
	public void setRecordEnd(Integer recordEnd) {
		this.recordEnd = recordEnd;
	}
}
