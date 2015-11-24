package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
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
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.common.utils.MD5;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Customer;
import com.lettucetech.me2.service.CustomerService;

/**
 * 网站首页controller
 * 
 * @author 
 */
@Controller
public class IndexController {
	
	private static final Logger logger = Logger.getLogger(IndexController.class);
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 手机号唯一性验证
	 * @param session
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="/register/verifyphone/{phone}",method=RequestMethod.GET)
	public ModelAndView verifyphone(HttpSession session,@PathVariable String phone) {
		Criteria example = new Criteria();
		example.put("phone", phone);
		List<Customer> list1 = customerService.selectByParams(example);
		
		RestfulResult result = new RestfulResult();
		if(list1.size()>0){
			result.setSuccess(false);
		}else{
			result.setSuccess(true);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	
	/**
	 * 用户注册
	 * @param session
	 * @param customer
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public void register(HttpSession session,HttpServletResponse response,  Customer customer) {
		Criteria example = new Criteria();
		example.put("phone", customer.getPhone());
		List<Customer> list1 = customerService.selectByParams(example);
		example.clear();
		example.put("username", customer.getUsername());
		List<Customer> list2 = customerService.selectByParams(example);
		
		RestfulResult result = new RestfulResult();
		if(list1.size()>0){
			result.setSuccess(false);
			result.setMessage("该号码已存在");
		}else if(list2.size()>0){
			result.setSuccess(false);
			result.setMessage("该呢称已存在");
		}else{
			customer.setCreatTime(new Date());
			//MD5加密
			customer.setPassword(MD5.getMD5(customer.getPassword()));
			if(customerService.insertSelective(customer)>0){
				result.setSuccess(true);
				result.setMessage("注册成功");
				session.setAttribute(Me2Constants.METOOUSER, customer);
			}
		}
		String jsonArray = JsonUtil.Encode(result);
		
		try {
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 用户登录
	 * @param session
	 * @param id
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/login/{id}/{password}",method=RequestMethod.GET)
	public ModelAndView login(HttpSession session,@PathVariable String id,@PathVariable String password) {
		Criteria example = new Criteria();
		example.put("id", id);
		List<Customer> list = customerService.selectByPhoneOrUsername(example);
		RestfulResult result = new RestfulResult();
		if(list.size()==0){
			result.setSuccess(false);
			result.setMessage("该帐号未注册");
		}else if(!"0".equals(list.get(0).getDel())){
			result.setSuccess(false);
			result.setMessage("该用户已被封");
		}else{
			if(MD5.getMD5(password).equals(list.get(0).getPassword())){
				result.setSuccess(true);
				result.setMessage("登录成功");
				result.setObj(list.get(0));
				session.setAttribute(Me2Constants.METOOUSER, list.get(0));
			}else{
				result.setSuccess(false);
				result.setMessage("密码错误");
			}
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		
		return mav;
	}
/**
 * 第三方平台用户登录
 * @param session
 * @param uid
 * @param source
 * @return
 */
	@RequestMapping(value="/thirdPartyLogin/{uid}/{source}",method=RequestMethod.GET)
	public ModelAndView thirdPartyLogin(HttpSession session,@PathVariable String uid,@PathVariable String source) {
		Criteria example = new Criteria();
		example.put("sourceid", uid);
		example.put("source", source);
		List<Customer> list = customerService.selectByParams(example);
		
		RestfulResult result = new RestfulResult();
		if(list.size()>0){
			if(!"0".equals(list.get(0).getDel())){
				result.setSuccess(false);
				result.setMessage("该用户已被封");
			}else{
				result.setSuccess(true);
				result.setObj(list.get(0));
				session.setAttribute(Me2Constants.METOOUSER, list.get(0));
			}
			
		}else{
			Customer customer = new Customer();
			customer.setSource(source);
			customer.setSourceid(uid);
			customer.setCreatTime(new Date());
			if(customerService.insertSelective(customer)==1){
				result.setSuccess(true);
				result.setObj(customer);
				session.setAttribute(Me2Constants.METOOUSER, customer);
			}else{
				result.setSuccess(false);
				result.setMessage("注册失败请联系管理员");
			}
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	/**
	 * 进入首页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		logger.debug("IndexController - index");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/index");
		return mav;
	}
	

}
