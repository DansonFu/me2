package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.wechat.vo.MPAccount;
import com.lettucetech.me2.common.constant.Me2Constants;
import com.lettucetech.me2.common.pojo.RestfulResult;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.common.utils.MD5;
import com.lettucetech.me2.pojo.Advertis;
import com.lettucetech.me2.pojo.Attention;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Customer;
import com.lettucetech.me2.pojo.CustomerPrivilege;
import com.lettucetech.me2.pojo.Message;
import com.lettucetech.me2.pojo.Picture;
import com.lettucetech.me2.pojo.Privilege;
import com.lettucetech.me2.service.AdvertisService;
import com.lettucetech.me2.service.AttentionService;
import com.lettucetech.me2.service.CustomerPrivilegeService;
import com.lettucetech.me2.service.CustomerService;
import com.lettucetech.me2.service.MessageService;
import com.lettucetech.me2.service.PictureService;
import com.lettucetech.me2.service.PrivilegeService;
import com.squareup.okhttp.Request;

/**
 * 网站首页controller
 * 
 * @author 
 */
@Controller
public class WeChatController {
	
	private static final Logger logger = Logger.getLogger(WeChatController.class);
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private PictureService pictureService;
	@Autowired
	private AttentionService attentionService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private AdvertisService advertisService;
	@Autowired
	private CustomerPrivilegeService customerPrivilegeService;
	@Autowired
	private PrivilegeService privilegeService;
	
	
	/**
	 * 验证昵称唯一性验证
	 * @param session
	 * @param username
	 * @return
	 */
	@RequestMapping(value="/register/username/{username}",method=RequestMethod.GET)
	public ModelAndView verifyUsername(HttpSession session,@PathVariable String username) {
		Criteria example = new Criteria();
		example.put("username", username);
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
		
		RestfulResult result = new RestfulResult();
		if (customer.getCity()==null || customer.getSex()==null || customer.getSchool()==null || customer.getYearEnterSchool()==null) {
			result.setSuccess(false);
			customer.setIsinfoComplete(1);
			result.setObj(customer);
			result.setMessage("用户信息不全");
		}else{
		
		Criteria example = new Criteria();
		
//		example.put("phone", customer.getPhone());
//		List<Customer> list1 = customerService.selectByParams(example);
		example.clear();
		example.put("username", customer.getUsername());
	
		List<Customer> list2 = customerService.selectByParams(example);
		
		
//		if(list1.size()>0){
//			result.setSuccess(false);
//			result.setMessage("该号码已存在");
//		}else 
		if(list2.size()>0){
			Customer cust=customerService.selectByPrimaryKey(customer.getCustomerId());
			
			cust.setCreatTime(new Date());
			cust.setCity(customer.getCity());
			cust.setSex(customer.getSex());
			cust.setHeadimgurl(customer.getHeadimgurl());
			cust.setSource(customer.getSource());
			cust.setApppushtoken(customer.getApppushtoken());
			cust.setSourceid(customer.getSourceid());
			cust.setUsername(customer.getUsername());
			cust.setSchool(customer.getSchool());
			customer.setIsNameDuplicated(1);
			cust.setIsinfoComplete(1);
			cust.setYearEnterSchool(customer.getYearEnterSchool());
				if(customerService.updateByPrimaryKeySelective(cust)>0){
					result.setSuccess(true);
					result.setMessage("注册成功");
					result.setObj(cust);
					session.setAttribute(Me2Constants.METOOUSER, cust);
				}
		
		}else{
			Customer cust=customerService.selectByPrimaryKey(customer.getCustomerId());
				
			cust.setCreatTime(new Date());
			cust.setCity(customer.getCity());
			cust.setSex(customer.getSex());
			cust.setHeadimgurl(customer.getHeadimgurl());
			cust.setSource(customer.getSource());
			cust.setApppushtoken(customer.getApppushtoken());
			cust.setSourceid(customer.getSourceid());
			cust.setUsername(customer.getUsername());
			cust.setSchool(customer.getSchool());
			cust.setYearEnterSchool(customer.getYearEnterSchool());
			cust.setIsinfoComplete(1);
				if(customerService.updateByPrimaryKeySelective(cust)>0){
					result.setSuccess(true);
					result.setMessage("注册成功");
					result.setObj(cust);
					session.setAttribute(Me2Constants.METOOUSER, cust);
				}
			
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
 * 第三方平台用户登录
 * @param session
 * @param uid
 * @param source
 * @return
 */
	@RequestMapping(value="/thirdPartyLogin/{access_token}/{source}",method=RequestMethod.GET)
	public @ResponseBody RestfulResult thirdPartyLogin(HttpServletRequest request,HttpSession session,@PathVariable String access_token, @PathVariable String source,
			String weixinId,String weixinName,String weixinheadimg) {
		Criteria example = new Criteria();
		example.put("sourceid", access_token);
		example.put("source", source);
		List<Customer> list = customerService.selectByParams(example);
		RestfulResult result = new RestfulResult();

		
			if (list.size()>0) {
				result.setMessage("该账户已存在");
				result.setObj(list);
				result.setSuccess(true);
			}else {
				
				Customer customer = new Customer();
				customer.setSource(source);
				customer.setSourceid(access_token);
				customer.setCreatTime(new Date());
				
				if(customerService.insertSelect(customer)==1){
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
		return result;
	}
	
	/**
	 * 三方登录之后查询用户关注的所有人发表的帖子数
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value="/attentions/{customerId}/login",method=RequestMethod.GET)
	public @ResponseBody RestfulResult AttentionBysomebody(HttpSession session, @PathVariable String customerId,String offset,String length){
		//Customer customer = customerService.selectByPrimaryKey(Integer.valueOf(customerId));
		RestfulResult result = new RestfulResult();
		Criteria example = new Criteria();
		example.put("customerId",customerId );
		
		List<Attention> list=attentionService.selectByParams(example);
		List list2=new ArrayList();
		if (list.size()>0) {
			for (Attention attention : list) {
				
				if (attention.getAttentionType()==1) {
					
					example.clear();
					example.put("customerId",attention.getAttentionCustomerId());
					example.setOrderByClause("creat_time");
					example.setSord("desc");
					example.put("front", "a");
					example.setMysqlOffset(Integer.valueOf(offset));
					example.setMysqlLength(Integer.valueOf(length));
					List<Picture> pictures  =pictureService.selectByParams(example);
					list2.add(pictures);
				}
			}
			result .setObj(list2);
			
			result.setSuccess(true);
			result.setMessage("被关注人的所有消息");
		}else {
			
			result.setSuccess(true);
			result.setMessage("未关注任何人");
		}
		
		return result;
	}





}
