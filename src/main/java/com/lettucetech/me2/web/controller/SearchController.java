package com.lettucetech.me2.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.common.constant.Me2Constants;
import com.lettucetech.me2.common.pojo.RestfulResult;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Customer;
import com.lettucetech.me2.pojo.Picture;
import com.lettucetech.me2.pojo.Task;
import com.lettucetech.me2.service.CustomerService;
import com.lettucetech.me2.service.PictureService;
import com.lettucetech.me2.service.TaskService;
/**
 * 搜索页面的方法
 * @author 付大海
 *
 */
@Controller
public class SearchController {

	private static final Logger logger = Logger.getLogger(SearchController.class);
	@Autowired
	private CustomerService customerService;
	@Autowired
	private PictureService pictureService;
	@Autowired
	private TaskService taskService;
	
	/**
	 * 搜索用户的详细信息和发布的多有密图信息
	 * @param session
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value="/search/{customerId}",method=RequestMethod.GET)
	public ModelAndView Searchbody(HttpSession session,@PathVariable String customerId){
		Customer customer=customerService.selectByPrimaryKey(Integer.valueOf(customerId));
		
		Criteria example=new Criteria();
		example.put("customerId",customerId);
		List<Picture> list=pictureService.selectByParams(example);
		
		RestfulResult result=new RestfulResult();
		result.setSuccess(true);
		result.setObj(list);
		result.setObj(customer);
		ModelAndView mav=new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	
	/**
	 * 根据学校名称搜索小红人信息,按照热度排序
	 * @param session
	 * @param schoolname
	 * @param sort 排序字段:hits 热度
	 * @param offset
	 * @param length
	 * @return
	 */
	@RequestMapping(value = "/search/{schoolname}", method ={RequestMethod.GET})
	public ModelAndView getPicturesBySchool(HttpSession session,@PathVariable String schoolname,String offset,String length){
		
		Criteria example = new Criteria();
		example.put("school",schoolname);
		
		example.setMysqlOffset(Integer.parseInt(offset));
		example.setMysqlLength(Integer.parseInt(length));
		example.setOrderByClause("score");
		example.setSord("asc");
		List<Customer> customers=customerService.selectByParams(example);
		
		
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result .setObj(customers);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	
	/**
	 * 按照图片的标签类型搜索此用户发过的此类型的图片
	 * @param session
	 * @param customerId 此用户的id
	 * @param pictype 投票令,悬赏令
	 * @return
	 */
	@RequestMapping(value = "/search/{customerId}/{pictype}", method ={RequestMethod.GET})
	public ModelAndView getPicturesBypictype(HttpSession session,@PathVariable String customerId,@PathVariable String pictype){
		
		Criteria example = new Criteria();
		example.put("customerId",customerId);
		example.put("pictype", pictype);
		
		
		List<Picture> picture=pictureService.selectByParams(example);
		
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(picture);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	
	@RequestMapping(value = "/task/{customerId}", method ={RequestMethod.GET})
	public ModelAndView getMyTask(HttpSession session,@PathVariable String customerId){
		
		Criteria example = new Criteria();
		example.put("customerId",customerId);
	
		
		
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	
	/**
	 * 获取任务列表
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/tasks", method ={RequestMethod.GET})
	public ModelAndView getTask(HttpSession session){
		
		Criteria example = new Criteria();
		List<Task> tasks=taskService.selectByParams(example);
	
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(tasks);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
}
