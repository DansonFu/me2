package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.common.pojo.RestfulResult;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Customer;
import com.lettucetech.me2.service.CustomerService;

@Controller
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 修改用户
	 * @param session
	 * @param customer
	 * @return
	 */
	@RequestMapping(value="/customers",method=RequestMethod.PUT)
	public void updateCustomer(HttpSession session,HttpServletResponse response,Customer customer) {
		customer.setUpdateTime(new Date());
		int rows = customerService.updateByPrimaryKeySelective(customer);
		
		RestfulResult result = new RestfulResult();
		if(rows>0){
			result.setSuccess(true);
			result.setMessage("修改成功");
		}else {
			result.setSuccess(false);
			result.setMessage("修改失败");
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
	
	@RequestMapping(value="/customers/{id}",method=RequestMethod.GET)
	public ModelAndView getCustomer(@PathVariable String id) {

		Customer customer = customerService.selectByPrimaryKey(Integer.valueOf(id));
		
		RestfulResult result = new RestfulResult();
		if("0".equals(customer.getDel())){
			result.setSuccess(true);
			result.setObj(customer);
		}else {
			result.setSuccess(false);
			result.setMessage("该用户已被封");
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		
		return mav;
		
	}
	
	@RequestMapping(value="/customers",method=RequestMethod.GET)
	public ModelAndView getCustomers() {
		Criteria example = new Criteria();
		List<Customer> customers = customerService.selectByParams(example);
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(customers);

		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		
		return mav;
		
	}
}
