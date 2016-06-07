package com.lettucetech.me2.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.common.constant.Me2Constants;


public class LoginInterceptor implements HandlerInterceptor{
		//免登录的页面
	 	private static final String[] IGNORE_URI = {"/qiniutoken/simple", "/register","/login",
	 		"/thirdPartyLogin","/toLogin","/resources"};
	 	@Override  
	    public void afterCompletion(HttpServletRequest request,  
	            HttpServletResponse response, Object obj, Exception err)  
	            throws Exception {  
	    }
	    @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	        String url = request.getRequestURL().toString();
//	        System.out.println(">>>: " + url);
	        for (String s : IGNORE_URI) {
	            if (url.contains(s)) {
	            	return true;
	            }
	        }
	        Object adminuser = request.getSession().getAttribute(Me2Constants.LOGIN_SESSION_DATANAME);
	        Object customer = request.getSession().getAttribute(Me2Constants.METOOUSER);
	        if (adminuser != null || customer!=null){
	        	return true;
            }else{
            	//request.getRequestDispatcher("/toLogin").forward(request, response); 
            	return true;
            }

	    }
	 
	    @Override
	    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	    }

}
