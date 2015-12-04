package com.lettucetech.me2.common.pojo;

import java.util.List;

public class JqGridFilterSearch {
    private String groupOp; //多字段查询时分组类型，主要是AND或者OR   

    private List<JqGridSearchRule> rules; //多字段查询时候，查询条件的集合 

	public String getGroupOp() {
		return groupOp;
	}

	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}

	public List<JqGridSearchRule> getRules() {
		return rules;
	}

	public void setRules(List<JqGridSearchRule> rules) {
		this.rules = rules;
	}
    
}
