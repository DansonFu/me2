package com.lettucetech.me2.web.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.common.constant.Me2Constants;
import com.lettucetech.me2.common.pojo.RestfulResult;
import com.lettucetech.me2.common.utils.QiniuUtil;
import com.qiniu.util.Auth;

@Controller
@RequestMapping("/qiniutoken")
public class QiniuController {
	private static final Logger logger = Logger.getLogger(QiniuController.class);
	
	/**
	 * 7牛简单上传token
	 * @param session
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/simple/{type}",method=RequestMethod.GET)
	public ModelAndView getSimpleToken(HttpSession session,@PathVariable String type) {
		String token=null;
		if("b".equals(type)){
			token = QiniuUtil.uploadToken(Me2Constants.METOOPRIVATE);
		}else{
			token = QiniuUtil.uploadToken(Me2Constants.METOOPULIC);
		}
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(token);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	/**
	 * 7牛覆盖上传token
	 * @param session
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/update/{type}/{key}",method=RequestMethod.GET)
	public ModelAndView getUpdateToken(HttpSession session,@PathVariable String type,@PathVariable String key) {
		String token=null;
		if("b".equals(type)){
			token = QiniuUtil.uploadToken(Me2Constants.METOOPRIVATE,key);
		}else{
			token = QiniuUtil.uploadToken(Me2Constants.METOOPULIC,key);
		}
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(token);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	
	/**
	 * 取得7牛的图片下载地址
	 * @param session
	 * @param key
	 * @return
	 */
	@RequestMapping(value="/download/{key}",method=RequestMethod.GET)
	public ModelAndView getDownloadUrl(HttpSession session,@PathVariable String key) {
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(QiniuUtil.getDownUrl(key));
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}

}
