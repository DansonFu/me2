package com.pizza.wechat.request.api;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pizza.wechat.APIException;
import com.pizza.wechat.APIRuntimeException;
import com.pizza.wechat.request.APIHttpConnect;
import com.pizza.wechat.request.JSONInput;
import com.pizza.wechat.request.URLParam;
import com.pizza.wechat.request.bean.CustomerServiceBean;
import com.pizza.wechat.request.bean.RespArticle;
import com.pizza.wechat.util.Utils;

/**
 * 客服管理
 * 
 * @ClassName : CustomerService
 * @author : emmy.cheng
 * @date : 2015年11月14日 下午4:58:58
 */
public class CustomerServiceApi {
	private static final String ADD = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";
	private static final String UPDATE = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=ACCESS_TOKEN";
	private static final String DELETE = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=ACCESS_TOKEN";
	private static final String QUERY = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN";
	private static final String SEND = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	private static final String UPLOAD_HEAD = "http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=ACCESS_TOKEN&kf_account={0}";

	/**
	 * 新增客服账号每个公众号限制10个
	 * 
	 * @param name
	 * @param account
	 * @param wxNo
	 *            公众号微信号
	 * @param pwd
	 * @param accessToken
	 * @throws APIException
	 */
	public void add(String name, String account, String wxNo, String pwd,
			String accessToken) throws APIException {
		validateParam(name, account, wxNo);
		URLParam url = new URLParam(ADD, accessToken);
		JSONInput map = new JSONInput();
		map.add("kf_account", account + "@" + wxNo);
		map.add("nickname", name);
		map.add("password", pwd);
		APIHttpConnect.post(url, map);
	}

	private void validateParam(String name, String account, String wxNo) {
		if (Utils.isEmpty(account) || Utils.isEmpty(wxNo)
				|| Utils.isEmpty(name)) {
			throw new APIRuntimeException(
					"customer account in param {name,account,wxNo} is not empty!");
		}
		int len = Utils.stringLen(name);
		if (len > 12) {
			throw new APIRuntimeException(
					"customer account in param name maxlength 12, current len["
							+ len + "]!");
		}
	}

	/**
	 * 更新客服账号
	 * 
	 * @param name
	 * @param account
	 * @param wxNo
	 * @param pwd
	 * @param accessToken
	 * @throws APIException
	 */
	public void update(String name, String account, String wxNo, String pwd,
			String accessToken) throws APIException {
		validateParam(name, account, wxNo);
		URLParam url = new URLParam(UPDATE, accessToken);
		JSONInput map = new JSONInput();
		map.add("kf_account", account + "@" + wxNo);
		map.add("nickname", name);
		map.add("password", pwd);
		APIHttpConnect.post(url, map);
	}

	/**
	 * 删除客服账号
	 * 
	 * @param name
	 * @param account
	 * @param wxNo
	 * @param pwd
	 * @param accessToken
	 * @throws APIException
	 */
	public void delete(String name, String account, String wxNo, String pwd,
			String accessToken) throws APIException {
		validateParam(name, account, wxNo);
		URLParam url = new URLParam(DELETE, accessToken);
		JSONInput map = new JSONInput();
		map.add("kf_account", account + "@" + wxNo);
		map.add("nickname", name);
		map.add("password", pwd);
		APIHttpConnect.post(url, map);
	}

	/**
	 * 上传客服头像格式必须为jpg
	 * 
	 * @param file
	 * @param account
	 * @param wxNo
	 * @param accessToken
	 * @throws APIException
	 */
	public void uploadHeadImg(File file, String account, String wxNo,
			String accessToken) throws APIException {
		URLParam url = new URLParam(MessageFormat.format(UPLOAD_HEAD, account
				+ "@" + wxNo), accessToken);
		APIHttpConnect.upload(url, file);
	}

	/**
	 * 获取所有客服账号
	 * 
	 * @param accessToken
	 * @return
	 * @throws APIException
	 */
	public List<CustomerServiceBean> query(String accessToken)
			throws APIException {
		URLParam url = new URLParam(QUERY, accessToken);
		JSONObject result = APIHttpConnect.post(url, null);
		JSONArray array = result.getJSONArray("kf_list");
		List<CustomerServiceBean> beans = new ArrayList<CustomerServiceBean>();
		for (Object object : array) {
			JSONObject object2 = (JSONObject) object;
			String kfAccount = Utils.toString(object2.get("kf_account"));
			String kfNick = Utils.toString(object2.get("kf_nick"));
			Integer kfId = Utils.toInt(object2.get("kf_id"));
			String kfHeadimgurl = Utils.toString(object2.get("kf_headimgurl"));
			beans.add(new CustomerServiceBean(kfAccount, kfNick, kfId,
					kfHeadimgurl));
		}
		return beans;
	}

	/**
	 * 发送文本消息 所哟回复消息必须是48小时内的 而且是被动的
	 * 
	 * @param content
	 * @param openId
	 * @param accessToken
	 * @throws APIException
	 */
	public void replyText(String content, String openId, String accessToken)
			throws APIException {
		if (Utils.isEmpty(content) || Utils.isEmpty(openId)) {
			throw new APIRuntimeException(
					"reply text in param {content,openId} is not empty!");
		}
		URLParam url = new URLParam(SEND, accessToken);
		JSONInput map = new JSONInput();
		map.add("touser", openId);
		map.add("msgtype", "text");
		JSONInput text = new JSONInput();
		text.add("content", content);
		map.add("text", text.toJSONString());
		APIHttpConnect.post(url, map);
	}

	/**
	 * 发送图片消息
	 * 
	 * @param mediaId
	 * @param openId
	 * @param accessToken
	 * @throws APIException
	 */
	public void replyImage(String mediaId, String openId, String accessToken)
			throws APIException {
		if (Utils.isEmpty(mediaId) || Utils.isEmpty(openId)) {
			throw new APIRuntimeException(
					"reply image in param {mediaId,openId} is not empty!");
		}
		URLParam url = new URLParam(SEND, accessToken);
		JSONInput map = new JSONInput();
		map.add("touser", openId);
		map.add("msgtype", "image");
		JSONInput image = new JSONInput();
		image.add("media_id", mediaId);
		map.add("image", image.toJSONString());
		APIHttpConnect.post(url, map);
	}

	/**
	 * 发送语音消息
	 * 
	 * @param mediaId
	 * @param openId
	 * @param accessToken
	 * @throws APIException
	 */
	public void replyVoice(String mediaId, String openId, String accessToken)
			throws APIException {
		if (Utils.isEmpty(mediaId) || Utils.isEmpty(openId)) {
			throw new APIRuntimeException(
					"reply voice in param {mediaId,openId} is not empty!");
		}
		URLParam url = new URLParam(SEND, accessToken);
		JSONInput map = new JSONInput();
		map.add("touser", openId);
		map.add("msgtype", "voice");
		JSONInput voice = new JSONInput();
		voice.add("media_id", mediaId);
		map.add("voice", voice.toJSONString());
		APIHttpConnect.post(url, map);
	}

	/**
	 * 发送视频消息
	 * 
	 * @param mediaId
	 * @param openId
	 * @param title
	 * @param desc
	 * @param thumbId
	 * @param accessToken
	 * @throws APIException
	 */
	public void replyVoide(String mediaId, String openId, String title,
			String desc, String thumbId, String accessToken)
			throws APIException {
		if (Utils.isEmpty(mediaId) || Utils.isEmpty(openId)
				|| Utils.isEmpty(thumbId)) {
			throw new APIRuntimeException(
					"reply video in param {mediaId,openId,thumbId} is not empty!");
		}
		URLParam url = new URLParam(SEND, accessToken);
		JSONInput map = new JSONInput();
		map.add("touser", openId);
		map.add("msgtype", "video");
		JSONInput video = new JSONInput();
		video.add("media_id", mediaId);
		video.add("thumb_media_id", thumbId);
		video.add("title", title);
		video.add("description", desc);
		map.add("video", video.toJSONString());
		APIHttpConnect.post(url, map);
	}

	/**
	 * 发送音乐消息
	 * 
	 * @param musicUrl
	 * @param hqMusicurl
	 * @param openId
	 * @param title
	 * @param desc
	 * @param thumbId
	 * @param accessToken
	 * @throws APIException
	 */
	public void replyMusic(String musicUrl, String hqMusicurl, String openId,
			String title, String desc, String thumbId, String accessToken)
			throws APIException {
		if (Utils.isEmpty(musicUrl) || Utils.isEmpty(thumbId)
				|| Utils.isEmpty(openId) || Utils.isEmpty(hqMusicurl)) {
			throw new APIRuntimeException(
					"reply music in param {musicUrl,openId,hqMusicurl,thumbId} is not empty!");
		}
		URLParam url = new URLParam(SEND, accessToken);
		JSONInput map = new JSONInput();
		map.add("touser", openId);
		map.add("msgtype", "music");
		JSONInput music = new JSONInput();
		music.add("musicurl", musicUrl);
		music.add("hqmusicurl", hqMusicurl);
		music.add("thumb_media_id", thumbId);
		music.add("title", title);
		music.add("description", desc);
		map.add("music", music.toJSONString());
		APIHttpConnect.post(url, map);
	}

	/**
	 * 发送图文消息
	 * 
	 * @param articles
	 * @param openId
	 * @param accessToken
	 * @throws APIException
	 */
	public void replyNews(List<RespArticle> articles, String openId,
			String accessToken) throws APIException {
		if (Utils.isEmpty(openId)) {
			throw new APIRuntimeException(
					"reply news in param {openId} is not empty!");
		}
		URLParam url = new URLParam(SEND, accessToken);
		JSONInput map = new JSONInput();
		map.add("touser", openId);
		map.add("msgtype", "news");
		JSONInput news = new JSONInput();
		news.add("articles", JSONArray.fromObject(articles).toString());
		map.add("news", news.toJSONString());
		APIHttpConnect.post(url, map);
	}
}
