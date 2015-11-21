package com.lettucetech.me2.common.pojo;

import java.util.List;

/**
 * MiniUi Grid返回对象
 * 
 * @author 刘洋
 */
public class BaseGridReturn {

	/**
	 * 总共条数
	 */
	private int total;
	/**
	 * 所有数据
	 */
	private List<?> data;

	public BaseGridReturn() {
	}

	public BaseGridReturn(int total, List<?> data) {
		this.total = total;
		this.data = data;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

}
