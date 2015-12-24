package com.lettucetech.me2.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.common.pojo.RestfulResult;
import com.lettucetech.me2.pojo.Collect;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.service.CollectService;

@Controller
public class CollectController {
	@Autowired
	private CollectService collectService;
	
	/**
	 * 收藏蜜图
	 * @param session
	 * @param collect
	 * @return
	 */
	@RequestMapping(value = "/collects", method ={RequestMethod.POST})
	public ModelAndView addCollect(HttpSession session,Collect collect){	
		RestfulResult result = new RestfulResult();
		result.setSuccess(false);
		
		Criteria example = new Criteria();
		example.put("customerId", collect.getCustomerId());
		example.put("pid", collect.getPid());
		if(collectService.countByParams(example)>0){
			result.setSuccess(true);
		} else{
			collect.setCreateTime(new Date());
			int num = collectService.insertSelective(collect);
			if(num ==1){
				result.setSuccess(true);
			}
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		
		return mav;
	}
	/**
	 * 查询收藏列表
	 * @param session
	 * @param customerId 用户id
	 * @return
	 */
	@RequestMapping(value = "/customers/{customerId}/collects", method ={RequestMethod.GET})
	public ModelAndView getCollect(HttpSession session,@PathVariable String customerId){	
		Criteria example = new Criteria();
		example.put("customerId", customerId);
		example.setOrderByClause("create_time");
		example.setSord("desc");
		List<Collect> collects = collectService.selectByParams(example);
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(collects);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		
		return mav;
	}
	/**
	 * 取消收藏
	 * @param session
	 * @param id 
	 * @return
	 */
	@RequestMapping(value = "/collects/{id}", method ={RequestMethod.DELETE})
	public ModelAndView delCollect(HttpSession session,@PathVariable String id){	
		int num = collectService.deleteByPrimaryKey(Integer.parseInt(id));
		
		RestfulResult result = new RestfulResult();
		if(num ==1){
			result.setSuccess(true);
		}else{
			result.setSuccess(false);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		
		return mav;
	}
	
}
