package com.lettucetech.me2.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Paster;
import com.lettucetech.me2.service.PasterService;

@Controller
public class PasterController {
	@Autowired
	private PasterService pasterService;
	
	/**
	 * 查询贴纸主题
	 */
	@RequestMapping(value = "/pasters", method ={RequestMethod.GET})
	public ModelAndView pasters(HttpSession session){
		Criteria example = new Criteria();
		example.put("del", "0");
		example.put("cover", "1");
		List<Paster> pasters = pasterService.selectByParams(example);
		
		ModelAndView mav = new ModelAndView();
//		mav.setViewName("/admin/metoo");
		mav.addObject("pasters", pasters);
		return mav;

	}
	
	/**
	 * 查询贴纸列表
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/pasters/{id}/children", method ={RequestMethod.GET})
	public ModelAndView getChildren(HttpSession session,@PathVariable String id){
		Criteria example = new Criteria();
		example.put("del", "0");
		example.put("cover", "0");
		example.put("parentId", id);
		List<Paster> pasters = pasterService.selectByParams(example);
		
		ModelAndView mav = new ModelAndView();
//		mav.setViewName("/admin/metoo");
		mav.addObject("pasters", pasters);
		return mav;

	}
}
