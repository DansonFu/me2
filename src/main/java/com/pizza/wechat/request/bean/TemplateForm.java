package com.pizza.wechat.request.bean;

import net.sf.json.JSONObject;


/**
 * 模板消息
 * @copyright gold-tech
 * @author emmy
 * @date  2015年8月11日 下午3:08:13
 */
public class TemplateForm implements DataMap{
	//接受用户openid
	private String touser;
	//模板消息id
	private String template_id;
	//模板消息url 不填写ios点击为空页面 安卓不可点击
	private String url;
	//模板消息顶部颜色
	private String topcolor;
	//模板消息需要填充的数据
	private TemplateFormData data;
	
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTopcolor() {
		return topcolor;
	}
	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}
	public TemplateFormData getData() {
		return data;
	}
	public void setData(TemplateFormData data) {
		this.data = data;
	}
	public String toJSONString(){
		JSONObject data = JSONObject.fromObject(this);
		data.put("data", this.data.toString());
		return data.toString();
	}
}
