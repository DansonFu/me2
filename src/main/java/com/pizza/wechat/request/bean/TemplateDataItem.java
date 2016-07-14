package com.pizza.wechat.request.bean;




/**
 * 模板消息中的填充数据
 * @copyright gold-tech
 * @author emmy
 * @date  2015年8月11日 下午3:07:52
 */
public class TemplateDataItem extends DynaBean{
	/** 
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	//需要填充的内容
	private String value;
	//内容背景色
	private String color;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
}
