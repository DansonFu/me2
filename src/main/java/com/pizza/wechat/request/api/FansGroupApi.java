package com.pizza.wechat.request.api;

import java.util.ArrayList;
import java.util.List;

import com.pizza.wechat.APIException;
import com.pizza.wechat.APIRuntimeException;
import com.pizza.wechat.request.APIHttpConnect;
import com.pizza.wechat.request.JSONInput;
import com.pizza.wechat.request.URLParam;
import com.pizza.wechat.request.bean.GroupBean;
import com.pizza.wechat.util.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 粉丝分组管理
 * 
 * @ClassName : FansGroup
 * @author : emmy.cheng
 * @date : 2015年11月14日 下午4:57:12
 */
public class FansGroupApi {
	private static final String QUERY = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
	private static final String CREATE = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
	private static final String UPDATE = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
	private static final String DELETE = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=ACCESS_TOKEN";
	private static final String GETBYID = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";
	private static final String UPDATE_USER = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
	private static final String BATCH_USER = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=ACCESS_TOKEN";

	/**
	 * 查询所有分组
	 * 
	 * @param accessToken
	 * @return
	 * @throws APIException
	 */
	@SuppressWarnings("unchecked")
	public List<GroupBean> query(String accessToken) throws APIException {
		URLParam url = new URLParam(QUERY, accessToken);
		JSONObject result = APIHttpConnect.post(url, null);
		JSONArray groups = result.getJSONArray("groups");
		List<GroupBean> results = new ArrayList<GroupBean>();
		results.addAll(JSONArray.toCollection(groups, GroupBean.class));
		return results;
	}

	/**
	 * 创建分组
	 * 
	 * @param accessToken
	 * @param name
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int create(String accessToken, String name) throws APIException {
		validateName(name);
		URLParam url = new URLParam(CREATE, accessToken);
		JSONInput out = new JSONInput();
		JSONInput groupName = new JSONInput();
		groupName.add("name", name);
		out.add("group", groupName.toJSONString());
		JSONObject result = APIHttpConnect.post(url, out);
		return result.getJSONObject("group").getInt("id");
	}

	private void validateName(String name) {
		if (Utils.isEmpty(name)) {
			throw new APIRuntimeException("group in param {name} is not empty!");
		}
		int len = name.length();
		if (len > 30) {
			throw new APIRuntimeException(
					"group name maxlength 30, current len[" + len + "]!");
		}
	}

	/**
	 * 更新分组
	 * 
	 * @param accessToken
	 * @param name
	 * @param id
	 * @throws Exception
	 *             void
	 */
	public void update(String accessToken, String name, int id)
			throws APIException {
		validateName(name);
		JSONInput out = new JSONInput();
		JSONInput groupName = new JSONInput();
		groupName.add("name", name);
		groupName.add("id", id);
		out.add("group", groupName.toJSONString());
		URLParam url = new URLParam(UPDATE, accessToken);
		APIHttpConnect.post(url, out);
	}

	/**
	 * 删除分组
	 * 
	 * @param epWxId
	 * @param id
	 * @throws Exception
	 *             void
	 */
	public void delete(String accessToken, int id) throws APIException {
		JSONInput groupName = new JSONInput();
		JSONInput out = new JSONInput();
		groupName.add("id", id);
		out.add("group", groupName.toJSONString());
		URLParam url = new URLParam(DELETE, accessToken);
		APIHttpConnect.post(url, out);
	}

	/**
	 * 获取用户所在分组主键
	 * 
	 * @param accessToken
	 * @param openid
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int getGroupIdByOpenId(String accessToken, String openid)
			throws APIException {
		if (Utils.isEmpty(openid)) {
			throw new APIRuntimeException(
					"getGroupIdByOpenId in param {openid} is not empty!");
		}
		JSONInput out = new JSONInput();
		out.add("openid", openid);
		URLParam url = new URLParam(GETBYID, accessToken);
		JSONObject result = APIHttpConnect.post(url, out);
		return result.getInt("groupid");
	}

	/**
	 * 更新用户所在分组
	 * 
	 * @param accessToken
	 * @param toGroupId
	 * @param openid
	 * @throws Exception
	 *             void
	 */
	public void toGroup(String accessToken, int toGroupId, String openid)
			throws APIException {
		if (Utils.isEmpty(openid)) {
			throw new APIRuntimeException(
					"toGroup in param {openid} is not empty!");
		}
		URLParam url = new URLParam(UPDATE_USER, accessToken);
		JSONInput out = new JSONInput();
		out.add("openid", openid);
		out.add("to_groupid", toGroupId);
		APIHttpConnect.post(url, out);
	}

	/**
	 * 批量更新用户 到指定分组
	 * 
	 * @param accessToken
	 * @param toGroupId
	 * @param openids
	 * @throws Exception
	 *             void
	 */
	public void batchGroup(String accessToken, int toGroupId, String[] openids)
			throws APIException {
		if (openids == null || openids.length < 1) {
			throw new APIRuntimeException(
					"batchGroup in param {openids} is not empty!");
		}
		URLParam url = new URLParam(BATCH_USER, accessToken);
		JSONInput out = new JSONInput();
		out.add("openid_list", openids);
		out.add("to_groupid", toGroupId);
		APIHttpConnect.post(url, out);
	}
}
