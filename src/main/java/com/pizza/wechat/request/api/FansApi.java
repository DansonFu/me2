package com.pizza.wechat.request.api;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pizza.wechat.APIException;
import com.pizza.wechat.APIRuntimeException;
import com.pizza.wechat.request.APIHttpConnect;
import com.pizza.wechat.request.JSONInput;
import com.pizza.wechat.request.URLParam;
import com.pizza.wechat.request.bean.FansBean;
import com.pizza.wechat.util.Utils;

/**
 * 粉丝管理
 * 
 * @ClassName : Fans
 * @author : emmy.cheng
 * @date : 2015年11月14日 下午4:57:26
 */
public class FansApi {
	private static final String FANS_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid={0}&lang=zh_CN";
	private static final String BATCH_INFO = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
	private static final String UPDATE_REMARK = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN";
	private static final String USER_INFO = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid={0}&lang=zh_CN";

	/**
	 * 获取粉丝基本信息
	 * 
	 * @param openId
	 * @param accessToken
	 * @return
	 * @throws APIException
	 * @throws ParseException
	 */
	public FansBean getFansByOpenId(String openId, String accessToken)
			throws APIException, ParseException {
		if (Utils.isEmpty(openId)) {
			throw new APIRuntimeException(
					"getFansByOpenId in param {openId} is not empty!");
		}
		URLParam url = new URLParam(MessageFormat.format(FANS_INFO, openId),
				accessToken);
		JSONObject result = APIHttpConnect.post(url, null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = sdf
				.format(new Date(result.getInt("subscribe_time") * 1000L));
		String unionId = Utils.toString(result.get("unionid"));
		String language = Utils.toString(result.get("language"));
		Integer sex = Utils.toInt(result.get("sex"));
		String country = Utils.toString(result.get("country"));
		String province = Utils.toString(result.get("province"));
		String city = Utils.toString(result.get("city"));
		String headImgUrl = Utils.toString(result.get("headimgurl"));
		String nickName = Utils.toString(result.get("nickname"));
		String remark = Utils.toString(result.get("remark"));
		Integer subscribe = Utils.toInt(result.get("subscribe"));
		Integer groupId = Utils.toInt(result.get("groupid"));
		return new FansBean(sdf.parse(d), unionId, language, sex, country,
				province, city, headImgUrl, openId, subscribe, remark, groupId,
				nickName);
	}

	/**
	 * 通过网页2.0授权时获取用户基本信息
	 * 
	 * @param openId
	 * @param accessToken
	 * @return
	 * @throws APIException
	 * @throws ParseException
	 */
	public FansBean getUserInfoByOpenId(String openId, String accessToken)
			throws APIException, ParseException {
		if (Utils.isEmpty(openId)) {
			throw new APIRuntimeException(
					"getUserInfoByOpenId in param {openId} is not empty!");
		}
		URLParam url = new URLParam(MessageFormat.format(USER_INFO, openId),
				accessToken);
		JSONObject result = APIHttpConnect.post(url, null);
		String unionId = Utils.toString(result.get("unionid"));
		Integer sex = Utils.toInt(result.get("sex"));
		String country = Utils.toString(result.get("country"));
		String province = Utils.toString(result.get("province"));
		String city = Utils.toString(result.get("city"));
		String headImgUrl = Utils.toString(result.get("headimgurl"));
		String nickName = Utils.toString(result.get("nickname"));
		return new FansBean(null, unionId, null, sex, country, province, city,
				headImgUrl, openId, null, null, null, nickName);
	}

	/**
	 * 批量获取粉丝基本信息 最多100条
	 * 
	 * @param openIds
	 * @param accessToken
	 * @return
	 * @throws APIException
	 * @throws ParseException
	 */
	public List<FansBean> query(String[] openIds, String accessToken)
			throws APIException, ParseException {
		URLParam url = new URLParam(BATCH_INFO, accessToken);
		JSONArray array = new JSONArray();
		for (String string : openIds) {
			JSONObject input = new JSONObject();
			input.put("openid", string);
			input.put("lang", "zh_CN");
			array.add(input);
		}
		JSONInput input = new JSONInput();
		input.add("user_list", array.toString());
		JSONObject result = APIHttpConnect.post(url, input);
		JSONArray jsonArray = result.getJSONArray("user_info_list");
		List<FansBean> fans = new ArrayList<FansBean>();
		for (Object object : jsonArray) {
			JSONObject data = (JSONObject) object;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String d = sdf.format(new Date(
					data.getInt("subscribe_time") * 1000L));
			String unionId = Utils.toString(data.get("unionid"));
			String language = Utils.toString(data.get("language"));
			Integer sex = Utils.toInt(data.get("sex"));
			String country = Utils.toString(data.get("country"));
			String province = Utils.toString(data.get("province"));
			String city = Utils.toString(data.get("city"));
			String headImgUrl = Utils.toString(data.get("headimgurl"));
			String nickName = Utils.toString(data.get("nickname"));
			String remark = Utils.toString(data.get("remark"));
			String openId = Utils.toString(data.get("openid"));
			Integer subscribe = Utils.toInt(data.get("subscribe"));
			Integer groupId = Utils.toInt(data.get("groupid"));
			fans.add(new FansBean(sdf.parse(d), unionId, language, sex,
					country, province, city, headImgUrl, openId, subscribe,
					remark, groupId, nickName));
		}
		return fans;
	}

	/**
	 * 更新粉丝备注信息
	 * 
	 * @param openId
	 * @param remark
	 *            长度不能超过30
	 * @param accessToken
	 * @throws APIException
	 */
	public void updateRemark(String openId, String remark, String accessToken)
			throws APIException {
		if (Utils.isEmpty(openId) || Utils.isEmpty(remark)) {
			throw new APIRuntimeException(
					"updateRemark in param {openId,remark} is not empty!");
		}
		if (remark.length() > 30) {
			throw new APIRuntimeException(
					"updateRemark remark maxlength 30, current len["
							+ remark.length() + "]!");
		}
		URLParam url = new URLParam(UPDATE_REMARK, accessToken);
		JSONInput out = new JSONInput();
		out.add("openid", openId);
		out.add("remark", remark);
		APIHttpConnect.post(url, out);
	}
}
