package com.pizza.wechat.request.bean;

import java.util.HashMap;

import net.sf.json.JSONObject;

/**
 * 模板消息中的填充数据
 * @copyright gold-tech
 * @author emmy
 * @date  2015年8月11日 下午3:07:52
 */
public class TemplateFormData implements DataMap{
	private HashMap<String, TemplateDataItem> dataMap = new HashMap<String, TemplateDataItem>(1);
	public void add(String key,TemplateDataItem value){
		dataMap.put(key, value);
	}
	public void remove(String key){
		dataMap.remove(key);
	}
	public void clear(){
		dataMap.clear();
	}
	public TemplateDataItem get(String key){
		return dataMap.get(key);
	}
	public String toJSONString(){
		return JSONObject.fromObject(dataMap).toString();
	}
}
