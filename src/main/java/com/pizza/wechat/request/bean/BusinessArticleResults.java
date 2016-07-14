package com.pizza.wechat.request.bean;

public class BusinessArticleResults {
	/**
	 * 素材总数
	 */
	private Integer total_count;
	/**
	 * 本次获取总数
	 */
	private Integer item_count;
	/**
	 * 素材
	 */
	private BusinessArticleItem[] item;
	public Integer getTotal_count() {
		return total_count;
	}
	public void setTotal_count(Integer total_count) {
		this.total_count = total_count;
	}
	public Integer getItem_count() {
		return item_count;
	}
	public void setItem_count(Integer item_count) {
		this.item_count = item_count;
	}
	public BusinessArticleItem[] getItem() {
		return item;
	}
	public void setItem(BusinessArticleItem[] item) {
		this.item = item;
	}
	
}
