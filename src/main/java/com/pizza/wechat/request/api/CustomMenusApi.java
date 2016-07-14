package com.pizza.wechat.request.api;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pizza.wechat.APIException;
import com.pizza.wechat.request.APIHttpConnect;
import com.pizza.wechat.request.JSONInput;
import com.pizza.wechat.request.URLParam;
import com.pizza.wechat.request.bean.Button;
/**
 * 自定义菜单管理
 * @ClassName : CustomMenus
 * @author : emmy.cheng
 * @date : 2015年11月14日 下午4:58:27
 */
public class CustomMenusApi {
	// 获取自定义菜单
	private static final String GET = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	// 创建自定义菜单
	private static final String CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 删除自定义菜单
	private static final String DELETE = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	/**
	 * 删除自定义菜单
	 * 
	 * @param accessToken
	 * @throws Exception
	 * @return boolean
	 */
	public void delete(String accessToken) throws APIException {
		URLParam url = new URLParam(DELETE, accessToken);
		APIHttpConnect.post(url, null);
	}

	/**
	 * 获取自定义菜单
	 * 
	 * @return Menu
	 */
	public JSONObject get(String accessToken) throws APIException {
		URLParam url = new URLParam(GET, accessToken);
		return APIHttpConnect.post(url, null);
	}

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @throws APIException
	 */
	public void create(List<Button> buttons, String accessToken)
			throws APIException {
		URLParam url = new URLParam(CREATE, accessToken);
		JSONInput input = new JSONInput();
		JSONArray array = JSONArray.fromObject(buttons);
		input.add("button", array.toString());
		APIHttpConnect.post(url, input);
	}
}
