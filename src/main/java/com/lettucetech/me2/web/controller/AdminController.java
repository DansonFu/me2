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
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Customer;
import com.lettucetech.me2.pojo.Picture;
import com.lettucetech.me2.service.CustomerService;
import com.lettucetech.me2.service.PictureService;

@Controller
public class AdminController {
	@Autowired
	private PictureService pictureService;
	@Autowired
	private CustomerService customerService;
	/**
	 * 保存蜜图AB面
	 * @param session
	 * @param pictures
	 * @return
	 */
	@RequestMapping(value = "/metoo", method ={RequestMethod.GET})
	public ModelAndView metoo(HttpSession session){
		Criteria example = new Criteria();
		List<Customer> customers = customerService.selectByParams(example);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/metoo");
		mav.addObject("customers", customers);
		return mav;

	}

}
