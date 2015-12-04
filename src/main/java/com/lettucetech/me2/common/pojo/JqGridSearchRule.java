package com.lettucetech.me2.common.pojo;

public class JqGridSearchRule {
	private String field; // 查询字段
	private String op; // 查询操作
	private String data; // 选择的查询值
	private String classType;//查询字段的类型

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}
}
