package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lettucetech.me2.common.pojo.RestfulResult;
import com.lettucetech.me2.common.utils.SignUtil;
import com.pizza.wechat.APIException;
import com.pizza.wechat.request.api.AccessTokenApi;

/**
 * 
 * 
 * @author 
 */
@Controller
public class WeChatController {
	
	private static final Logger logger = Logger.getLogger(WeChatController.class);
	
	@RequestMapping("/pair")
	public @ResponseBody RestfulResult getAccessToken (HttpSession session) {
		
		String appid ="wxe5351a7af103b624";
		String appsecret ="d3243d71444f4556ea4e7a566747d1e2";
		String token="";
		AccessTokenApi api=new AccessTokenApi();
		try {
			api.getAuthToken(token, appsecret);
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RestfulResult result =new RestfulResult();
		result.setObj(api);
		result.setSuccess(true);
		result.setMessage("");
		return result;
	}
}
	
