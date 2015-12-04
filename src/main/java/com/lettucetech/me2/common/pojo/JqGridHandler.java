package com.lettucetech.me2.common.pojo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lettucetech.me2.common.utils.JsonUtil;

public class JqGridHandler {
	private int page = 1; // 表示请求页码的参数名称
	private int rows = 20; // 每页显示记录数
	private String sidx; // 指示查询排序条件
	private String sord = "desc"; // 指示查询排序方式ASC和DESC
	private boolean _search;// 表示是否是搜索请求的参数名称
	private String oper; //
	private String id; // 表示当在编辑数据模块中发送数据时，使用的id的名称

	private String filters;// 多条件查询参数
	private String searchField;// 单字段查询
	private String searchOper;
	private String searchString;

	// 存储总体的search
	JqGridFilterSearch filterSearch = null;

	public JqGridHandler() {

	}
	
	/**
	 * 获取查询条件
	 * @param prefix
	 * @return
	 */
	public Map<String, Object>  getCondition() {
		Map<String, Object> condition = new HashMap<String, Object>();
		conditions();
		if(filterSearch!=null){
			for(JqGridSearchRule rule:filterSearch.getRules()){
				condition.put(rule.getField(), rule.getData());
			}
		}
		
		return condition;
	}
	
	/**
	 * 取得查询条件的sql语句
	 * 
	 * @param prefix
	 *            数据库名
	 * @param isWhere
	 *            是否包含where字符
	 * @return
	 */
	public String getWheres(String prefix, boolean isWhere) {
		conditions();
		String sql = tranToSQL(prefix);
		if (sql.trim().equals("")) {
			return "";
		}
		if (!isWhere) {
			return new StringBuilder(" where ").append(sql).toString();
		}
		return new StringBuilder(" and ").append(sql).toString();
	}

	/**
	 * 取得排序的sql
	 * 
	 * @param prefix
	 *            数据库名
	 * @param isOrder
	 *            是否包含order by字符
	 * @return
	 */
	public String getOrders(String prefix, boolean isOrder) {
		StringBuilder sb = new StringBuilder();
		if (isOrder) {
			if (null != prefix) {
				sb.append(prefix).append(".");
			}

		} else {
			sb.append(" order by ");
			if (null != prefix) {
				sb.append(prefix).append(".");
			}
		}
		return sb.append(doTables(sidx)).append(" ").append(sord).toString();
	}

	// 根据conditions转换成sql格式
	public String tranToSQL(String prefix) {
		StringBuilder sb = new StringBuilder("");

		if (null != filterSearch) {
			List<JqGridSearchRule> rules = filterSearch.getRules();
			int count = 0;
			if (null != rules && (count = rules.size()) > 0) {
				for (JqGridSearchRule rule : rules) {
					if (null != rule.getField() && null != rule.getData()
							&& null != rule.getOp()) {
						if ("eq".equalsIgnoreCase(rule.getOp())) {

							sb.append(rule.getField()).append(" = ")
									.append("'").append(rule.getData())
									.append("'");

						} else if ("nq".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" != ")
									.append("'").append(rule.getData())
									.append("'");
						} else if ("lt".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" < ")
									.append("'").append(rule.getData())
									.append("'");
						} else if ("le".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" <= ")
									.append("'").append(rule.getData())
									.append("'");
						} else if ("gt".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" > ")
									.append("'").append(rule.getData())
									.append("'");
						} else if ("ge".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" >= ")
									.append("'").append(rule.getData())
									.append("'");
						} else if ("bw".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" like ")
									.append("'").append(rule.getData())
									.append("%").append("'");
						} else if ("ew".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" like ")
									.append("'").append("%")
									.append(rule.getData()).append("'");
						} else if ("cn".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" like ")
									.append("'").append("%")
									.append(rule.getData()).append("%")
									.append("'");
						} else {

						}
						count--;
						if (count > 0) {
							if (null != filterSearch.getGroupOp()) {
								if (filterSearch.getGroupOp().equals("and"))
									sb.append(" and ");
								else
									sb.append(" or ");
							}

						}
					}

				}
			}
		}
		return sb.toString();
	}

	// 装载
	private void conditions() {

		// 分拆，全部写入filersearch
//		if (_search) {
			// 先写多选择的，一般有多选择就不会有单选择。
			if (null != filters && filters.length() > 0) {
				// Map m = new HashMap();
				// m.put("rules", JqGridSearchRule.class);
				// filterSearch = (JqGridFilterSearch)
				// JsonUtils.getDTOList(filters,
				// JqGridFilterSearch.class, m);
				filterSearch = (JqGridFilterSearch) JsonUtil.Decode(filters,JqGridFilterSearch.class);
			} else {
				if (null != searchOper && null != searchString
						&& null != searchField) {
					JqGridSearchRule rule = new JqGridSearchRule();
					rule.setData(searchString);
					rule.setOp(searchOper);
					rule.setField(doTables(searchField));
					filterSearch = new JqGridFilterSearch();
					filterSearch.setGroupOp(null);
					List<JqGridSearchRule> rules = new ArrayList<JqGridSearchRule>();
					rules.add(rule);
					filterSearch.setRules(rules);
				}
			}
//		}

	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}


	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean get_search() {
		return _search;
	}

	public void set_search(boolean _search) {
		this._search = _search;
	}

	private String doTables(String str) {
		if (str.startsWith("__")) {
			str = str.substring(2);
			return str.replaceAll("_", ".");
		} else {
			return str;
		}

	}
	
	public JqGridFilterSearch getFilterSearch() {
		return filterSearch;
	}

	public void setFilterSearch(JqGridFilterSearch filterSearch) {
		this.filterSearch = filterSearch;
	}
	
	/**
	 * 
	 * @param o 要获取类型信息的实体类
	 * @param column Java字段和数据库字段对应关系
	 * @return 设置好类型并将Java字段替换为数据库字段的查询条件类
	 */
	public JqGridFilterSearch getFilterSearch(Object o,Map<String,String> column) {
		//查看条件转换json->JqGridFilterSearch
		conditions();
		if(this.getFilterSearch()!=null){
			//获取实体声明属性
			Field[] fields=o.getClass().getDeclaredFields();
			Map<String,String> map=new HashMap<String,String>();
			for(int i=0;i<fields.length;i++){
				String fieldsType = fields[i].getType().toString();
				String fieldsName = fields[i].getName();
				//属性类型为自定义则获取自定义对象属性
				if(fieldsType.startsWith("class com.lettucetech.me2.pojo")){
					fieldsType = fieldsType.substring(6);
					try {
						Class<?> classes=Class.forName(fieldsType);
						Field[] OtherObjFields=classes.getDeclaredFields();
						for(Field field:OtherObjFields){
							map.put(fieldsName+"."+field.getName(), field.getType().toString());
						}
					} catch (Exception e) {
					}
				}else{
					map.put(fields[i].getName(), fieldsType);
				}
			}
			for(int i=0;i<this.getFilterSearch().getRules().size();i++){
				JqGridSearchRule jgsr=this.getFilterSearch().getRules().get(i);
				jgsr.setClassType(map.get(jgsr.getField()));
				if(column.get(jgsr.getField())!=null){
					jgsr.setField(column.get(jgsr.getField()));
				}
			}
		}
		return filterSearch;
	}
}
