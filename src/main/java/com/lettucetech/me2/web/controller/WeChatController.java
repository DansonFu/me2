package com.lettucetech.me2.web.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lettucetech.me2.common.pojo.RestfulResult;
import com.lettucetech.me2.common.pojo.WexinUserInfo;
import com.lettucetech.me2.common.utils.CommentUtil;
import com.lettucetech.me2.pojo.Customer;
import com.lettucetech.me2.service.CustomerService;
import com.pizza.wechat.APIException;
import com.pizza.wechat.request.api.AccessTokenApi;



/**
 * 
 * 
 * @author 
 */
@Controller
public class WeChatController {
	
	@Autowired
	private CustomerService customerService;
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(WeChatController.class);
	
//	@RequestMapping("/pair")
//	public @ResponseBody RestfulResult getAccessToken (HttpSession session) {
//		
//		String appid ="wxe5351a7af103b624";
//		String appsecret ="d3243d71444f4556ea4e7a566747d1e2";
//		String token="";
//		AccessTokenApi api=new AccessTokenApi();
//		try {
//			api.getAuthToken(token, appsecret);
//		} catch (APIException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		RestfulResult result =new RestfulResult();
//		result.setObj(api);
//		result.setSuccess(true);
//		result.setMessage("");
//		return result;
//	}

	

	public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
           
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

	@RequestMapping(value ="/getOpenIdandToken")
	public @ResponseBody RestfulResult getOpenIdandToken(HttpSession session) {
		String appid="wx7db28beaa8f18221";
		String appsecret="44c72f00e918c74d5fc60588d49ed923";
		String httpUrl = "https://api.weixin.qq.com/cgi-bin/token";
		String httpArg = "grant_type=client_credential&appid="+appid+"&secret="+appsecret+"";
		String jsonResult = request(httpUrl, httpArg);
		System.out.println(jsonResult);
		
		Gson gson=new Gson();
		Map<String,String> jsonMap = gson.fromJson(jsonResult, new TypeToken<Map<String, String>>(){}.getType());
		
		String str = jsonMap.get("access_token");
		String httpUrl1="https://api.weixin.qq.com/cgi-bin/user/get";
		String httpArg1 ="access_token="+str+"";
		String jsonResult2 = request(httpUrl1, httpArg1);
		System.out.println(jsonResult2);
		RestfulResult result=new RestfulResult();
		result.setSuccess(true);
		result.setObj(jsonResult);
		result.setObj(jsonResult2);
		return result;
	}
//	 public static void main(String args[]) {
//		    // 获取接口访问凭证
//		    String accessToken = CommentUtil.getToken("xxxx", "xxxx").getAccessToken();
//		    /**
//		     * 获取用户信息
//		     */
//		    WexinUserInfo user = getUserInfo(accessToken, "ooK-yuJvd9gEegH6nRIen-gnLrVw");
//		    System.out.println("OpenID：" + user.getOpenId());
//		    System.out.println("关注状态：" + user.getSubscribe());
//		    System.out.println("关注时间：" + user.getSubscribeTime());
//		    System.out.println("昵称：" + user.getNickname());
//		    System.out.println("性别：" + user.getSex());
//		    System.out.println("国家：" + user.getCountry());
//		    System.out.println("省份：" + user.getProvince());
//		    System.out.println("城市：" + user.getCity());
//		    System.out.println("语言：" + user.getLanguage());
//		    System.out.println("头像：" + user.getHeadImgUrl());
//		  }
	 /**
	   * 获取用户信息
	   * 
	   * @param accessToken 接口访问凭证
	   * @param openId 用户标识
	   * @return WeixinUserInfo
	   */
	 @RequestMapping(value="/getuserInfo",method=RequestMethod.GET)
	  public @ResponseBody RestfulResult getUserInfo(String accessToken, String openId) {
	    Customer  weixinUserInfo = null;
	    RestfulResult result=new RestfulResult();
		
		
	    // 拼接请求地址
	    String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
	    requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
	    // 获取用户信息
	    JSONObject jsonObject = CommentUtil.httpsRequest(requestUrl, "GET", null);
	    if (null != jsonObject) {
	      try {
	        weixinUserInfo = new Customer();
	        // 用户的标识
	        weixinUserInfo.setOpenid(jsonObject.getString("openid"));
	        // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
	        weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
	        // 用户关注时间
	        String time=jsonObject.getString("subscribe_time");
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date date =sdf.parse(time);
	        weixinUserInfo.setSubscribeTime(date);
	        // 昵称
	        weixinUserInfo.setNickname(jsonObject.getString("nickname"));
	        // 用户的性别（1是男性，2是女性，0是未知）
	        weixinUserInfo.setSex(jsonObject.getInt("sex"));
	        // 用户所在国家
	        weixinUserInfo.setCountry(jsonObject.getString("country"));
	        // 用户所在省份
	        weixinUserInfo.setProvince(jsonObject.getString("province"));
	        // 用户所在城市
	        weixinUserInfo.setCity(jsonObject.getString("city"));
	        // 用户的语言，简体中文为zh_CN
	        weixinUserInfo.setLanguage(jsonObject.getString("language"));
	        // 用户头像
	        weixinUserInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
	        customerService.insert(weixinUserInfo);
	      } catch (Exception e) {
	        if (0 == weixinUserInfo.getSubscribe()) {
	          log.error("用户{}已取消关注", weixinUserInfo.getOpenid());
	        } else {
	          int errorCode = jsonObject.getInt("errcode");
	          String errorMsg = jsonObject.getString("errmsg");
	          log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
	        }
	      }
	    }
	    result.setSuccess(true);
	    result.setObj(weixinUserInfo);
	    result.setMessage("用户信息");
	    return result;
	  }
}



