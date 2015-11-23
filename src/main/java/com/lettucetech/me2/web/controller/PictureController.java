package com.lettucetech.me2.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.common.constant.Me2Constants;
import com.lettucetech.me2.common.pojo.RestfulResult;
import com.lettucetech.me2.pojo.Customer;
import com.lettucetech.me2.pojo.Picture;
import com.lettucetech.me2.service.PictureService;

@Controller
public class PictureController {
	@Autowired
	private PictureService pictureService;
	
	/**
	 * 保存蜜图AB面
	 * @param session
	 * @param pictures
	 * @return
	 */
	@RequestMapping(value = "/pictures", method ={RequestMethod.POST})
	public ModelAndView addPicture(HttpSession session,@RequestBody List<Picture> pictures){
		Customer customer = (Customer) session.getAttribute(Me2Constants.METOOUSER);
		Picture A = pictures.get(0);
		Picture B = pictures.get(1);
		
		A.setCustomerId(customer.getCustomerId());
		A.setCreatTime(new Date());
		pictureService.insertSelective(A);
		if(B!=null){
			B.setCustomerId(customer.getCustomerId());
			B.setCreatTime(new Date());
			B.setParentId(A.getPid());
		}
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(pictures);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		
		return mav;

	}

}
