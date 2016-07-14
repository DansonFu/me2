package com.pizza.wechat.request.bean;

public class GroupBean{
	private String name;
	private Integer id;
	private Integer count;
	public GroupBean() {
	}
	public GroupBean(String name, Integer id, Integer count) {
		this.name = name;
		this.id = id;
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
