package com.pizza.wechat.request.api;

import net.sf.json.JSONObject;

import com.pizza.wechat.APIException;
import com.pizza.wechat.APIRuntimeException;
import com.pizza.wechat.request.APIHttpConnect;
import com.pizza.wechat.request.JSONInput;
import com.pizza.wechat.request.URLParam;
import com.pizza.wechat.request.bean.TemplateForm;
import com.pizza.wechat.util.Utils;

/**
 * 模板消息注意记录微信推送过来的消息状态
 * 
 * @ClassName : TemplateMessage
 * @author : emmy.cheng
 * @date : 2015年10月24日 上午1:05:18
 */
public class TemplateMessageApi {
	private static final String SET_INDUSTRY = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
	private static final String SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	private static final String ADD = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";

	/**
	 * 发送模板消息 并返回消息msgid
	 * 
	 * @param form
	 *            模板消息数据
	 * @param accessToken
	 * 
	 * @return
	 * @throws APIException
	 */
	public Long send(TemplateForm form, String accessToken) throws APIException {
		if (Utils.isEmpty(form.getTemplate_id())
				|| Utils.isEmpty(form.getTouser()) || form.getData() == null) {
			throw new APIRuntimeException(
					"send in param form:{template_id,touser,data} is not empty!");
		}
		URLParam url = new URLParam(SEND, accessToken);
		JSONObject result = APIHttpConnect.post(url, form);
		return result.getLong("msgid");
	}

	/**
	 * 获取模板/添加模板,数量限制在15个
	 * 
	 * @param shortId
	 * @param accessToken
	 * @return
	 * @throws APIException
	 */
	public String getLongId(String shortId, String accessToken)
			throws APIException {
		if (Utils.isEmpty(shortId)) {
			throw new APIRuntimeException(
					"getLongId in param {shortId} is not empty!");
		}
		URLParam url = new URLParam(ADD, accessToken);
		JSONInput data = new JSONInput();
		data.add("template_id_short", shortId);
		JSONObject result = APIHttpConnect.post(url, data);
		return result.getString("template_id");
	}

	/**
	 * 设置公众号行业(一个月只能调用一次此接口)
	 * 
	 * @param trade1
	 * @param trade2
	 * @param accessToken
	 * @throws APIException
	 */
	public void setTrade(int trade1, int trade2, String accessToken)
			throws APIException {
		URLParam url = new URLParam(SET_INDUSTRY, accessToken);
		JSONInput data = new JSONInput();
		data.add("industry_id1", trade1);
		data.add("industry_id2", trade2);
		APIHttpConnect.post(url, data);
	}
}
