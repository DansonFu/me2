package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
		Criteria example = new Criteria();
//		example.put("phone", customer.getPhone());
//		List<Customer> list1 = customerService.selectByParams(example);
//		example.clear();
		example.put("username", customer.getUsername());
		List<Customer> list2 = customerService.selectByParams(example);
		
		RestfulResult result = new RestfulResult();
//		if(list1.size()>0){
//			result.setSuccess(false);
//			result.setMessage("该号码已存在");
//		}else 
		if(list2.size()>0){
			result.setSuccess(false);
			result.setMessage("该呢称已存在");
		}else{
			customer.setCreatTime(new Date());
			//MD5加密
			//customer.setPassword(MD5.getMD5(customer.getPassword()));
			
			customer.setCity(customer.getCity());
			
			customer.setSex(customer.getSex());
			customer.setHeadimgurl(customer.getHeadimgurl());
			customer.setSource(customer.getSource());
			customer.setApppushtoken(customer.getApppushtoken());
			customer.setSourceid(customer.getSourceid());
			customer.setUsername(customer.getUsername());
			customer.setSchool(customer.getSchool());
			
			if(customerService.insertSelective(customer)>0){
				result.setSuccess(true);
				result.setMessage("注册成功");
				result.setObj(customer);
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
//		example.put("id", id);
//		List<Customer> list = customerService.selectByPhoneOrUsername(example);
		example.put("phone", id);
		List<Customer> list = customerService.selectByParams(example);
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
	public @ResponseBody RestfulResult thirdPartyLogin(HttpSession session,@PathVariable String uid,@PathVariable String source) {
		Criteria example = new Criteria();
		example.put("sourceid", uid);
		example.put("source", source);
		List<Customer> list = customerService.selectByParams(example);
		RestfulResult result = new RestfulResult();
		for (Customer customer : list) {
			if(uid.equals(customer.getSourceid())){
				//将仅有的一条数据取出
				Customer cust=customerService.selectByParams4Rand(example);
				example.clear();
				example.put("customerId",cust);
				//当前用户所关注的所有人
				List<Attention> attentions=attentionService.selectByParams(example);
				List p=new ArrayList();
				for (Attention attention : attentions) {
					
					example.clear();
					example.put("customerId",attention.getAttentionCustomerId() );
					List<Picture> pic=pictureService.selectByParams(example);
					p.add(pic);
				}
				result.setSuccess(true);
				result.setObj(p);
				result.setObj(attentions);
				result.setObj(cust);
			}else{
				result.setSuccess(true);
				result.setMessage("该用户未注册");
			}
		}
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
		return result;
	}


/**

	 * 关注指定用户
	 * @param session
	 * @param customerId
	 * @param attentionCustomerId
	 * @return
	 */
	@RequestMapping(value="/attentions",method=RequestMethod.POST)
	public @ResponseBody RestfulResult Attentionsomebody(HttpSession session, Integer customerId, Integer attentionCustomerId){
		Attention attention=new Attention();
		attention.setCustomerId(Integer.valueOf(customerId));
		attention.setAttentionCustomerId(Integer.valueOf(attentionCustomerId));
		attention.setAttentionType(1);
		attentionService.insertSelective(attention);
		
		
		Message record=new Message();
		record.setContent("关注成功");
		record.setCreateTime(new Date());
		record.setCustomerId(Integer.valueOf(attentionCustomerId));
		record.setProcessed("1");
		record.setProposer(Integer.valueOf(customerId));
		record.setType("6");
		messageService.insertSelective(record);
		
		Criteria example = new Criteria();
		
		example.put("attentionCustomerId",attentionCustomerId);
		Customer customer=customerService.selectByPrimaryKey(Integer.valueOf(attentionCustomerId));
		customer.setScore(customer.getScore()+1);
		
		customerService.updateByPrimaryKeySelective(customer);
		String content="";
		String messagetype="";
		switch (customer.getScore()) {
		case 100:
			customer.setGrade(1);
			customer.setGeneralAccount(customer.getGeneralAccount()+10);
			content="恭喜你升到1级";
			messagetype="8";
			break;
		case 200:
			customer.setGrade(2);
			customer.setGeneralAccount(customer.getGeneralAccount()+10);
			content="恭喜你升到2级";
			messagetype="8";
			break;
		case 500:
			customer.setGrade(3);
			customer.setGeneralAccount(customer.getGeneralAccount()+10);
			content="恭喜你升到3级";
			messagetype="8";
			break;
		case 1000:
			customer.setGrade(4);
			customer.setGeneralAccount(customer.getGeneralAccount()+10);
			content="恭喜你升到4级";
			messagetype="8";
			break;
		case 2000:
			customer.setGrade(5);
			customer.setGeneralAccount(customer.getGeneralAccount()+10);
			content="恭喜你升到5级";
			messagetype="8";
			break;
		case 3000:
			customer.setGrade(6);
			customer.setGeneralAccount(customer.getGeneralAccount()+20);
			content="恭喜你升到6级";
			messagetype="8";
			break;
		case 5000:
			customer.setGrade(7);
			customer.setGeneralAccount(customer.getGeneralAccount()+20);
			content="恭喜你升到7级";
			messagetype="8";
			
			break;
		case 8000:
			customer.setGrade(8);
			customer.setGeneralAccount(customer.getGeneralAccount()+20);
			content="恭喜你升到8级";
			messagetype="8";
			break;
		case 15000:
			customer.setGrade(9);
			customer.setGeneralAccount(customer.getGeneralAccount()+20);
			content="恭喜你升到9级";
			messagetype="8";
			break;
		case 20000:
			customer.setGrade(10);
			customer.setGeneralAccount(customer.getGeneralAccount()+20);
			content="恭喜你升到10级";
			messagetype="8";
			break;
		case 25000:
			customer.setGrade(11);
			customer.setGeneralAccount(customer.getGeneralAccount()+50);
			content="恭喜你升到11级";
			messagetype="8";
			break;
		case 30000:
			customer.setGrade(12);
			customer.setGeneralAccount(customer.getGeneralAccount()+50);
			content="恭喜你升到12级";
			messagetype="8";
			break;
		case 35000:
			customer.setGrade(13);
			customer.setGeneralAccount(customer.getGeneralAccount()+50);
			content="恭喜你升到13级";
			messagetype="8";
			break;
		case 40000:
			customer.setGrade(14);
			customer.setGeneralAccount(customer.getGeneralAccount()+50);
			content="恭喜你升到14级";
			messagetype="8";
			break;
		case 50000:
			customer.setGrade(15);
			customer.setGeneralAccount(customer.getGeneralAccount()+50);
			content="恭喜你升到15级";
			messagetype="8";
			break;
		case 60000:
			customer.setGrade(16);
			customer.setGeneralAccount(customer.getGeneralAccount()+100);
			content="恭喜你升到16级";
			messagetype="8";
			break;
		case 80000:
			customer.setGrade(17);
			customer.setGeneralAccount(customer.getGeneralAccount()+100);
			content="恭喜你升到17级";
			messagetype="8";
			break;
		case 100000:
			customer.setGrade(18);
			customer.setGeneralAccount(customer.getGeneralAccount()+100);
			content="恭喜你升到18级";
			messagetype="8";
			break;
		case 120000:
			customer.setGrade(19);
			customer.setGeneralAccount(customer.getGeneralAccount()+100);
			content="恭喜你升到19级";
			messagetype="8";
			break;
		case 200000:
			customer.setGrade(20);
			customer.setGeneralAccount(customer.getGeneralAccount()+100);
			content="恭喜你升到20级";
			messagetype="8";
			break;
			
		default:
			customer.setGrade(0);
			content="未升级";
			messagetype="8";
			break;
		}		
		customerService.updateByPrimaryKeySelective(customer);
		//存到用户消息表中
		
				Message record1 = new Message();
				record1.setContent(content);
				record1.setCreateTime(new Date());
				record1.setCustomerId(Integer.valueOf(attentionCustomerId));
				
				record1.setType(messagetype);
				record1.setProcessed("1");
				record1.setProposer(Integer.valueOf(customerId));
				messageService.insertSelective(record1);
		if (customer.getGrade()==5) {
			content="恭喜你,开启了使用悬赏令的特权";
			messagetype="11";
			
			CustomerPrivilege cp=new CustomerPrivilege();
			cp.setCustomerId(attentionCustomerId);
			cp.setPrivilegeid(7);
			customerPrivilegeService.insertSelective(cp);
			
			Message record2=new Message();
			record2.setContent(content);
			record2.setCreateTime(new Date());
			record2.setCustomerId(attentionCustomerId);
			
			record2.setType(messagetype);
			record2.setProcessed("1");
			record2.setProposer(Integer.valueOf(customerId));
			messageService.insertSelective(record2);
		}
		
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		
		result.setObj(customer);
		
		
		ModelAndView mav=new ModelAndView();
		mav.addObject(result);
		return result;
	}
	
	/**
	 * 判断被关注总数,以此分发奖励
	 * @param attentionCustomerId
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value = "/attentions/{customerId}", method ={RequestMethod.GET})
	public @ResponseBody RestfulResult checkattention(HttpSession session,@PathVariable Integer customerId){
		RestfulResult result=new RestfulResult();
		Criteria example=new Criteria();
		example.put("attentionCustomerId",customerId);
		List<Attention> attentions=attentionService.selectByParams(example);
		Customer customer=customerService.selectByPrimaryKey(customerId);
		String content="";
		String messagetype="";
		if (attentions.size()==1) {
			customer.setGeneralAccount(customer.getGeneralAccount()+10);
		}else if (attentions.size()==10) {
			
			customer.setGeneralAccount(customer.getGeneralAccount()+20);
		}else if (attentions.size()==50) {
			
			customer.setGeneralAccount(customer.getGeneralAccount()+20);
		}else if (attentions.size()==100) {
			
			customer.setGeneralAccount(customer.getGeneralAccount()+20);
		}else if (attentions.size()==500) {
			
			customer.setGeneralAccount(customer.getGeneralAccount()+50);
			content="开启密码解密特权";
			messagetype="16";
			CustomerPrivilege cp=new CustomerPrivilege();
			cp.setCustomerId(customerId);;
			cp.setPrivilegeid(6);
			customerPrivilegeService.insertSelective(cp);
		}else if (attentions.size()==2000) {
			
			customer.setGeneralAccount(customer.getGeneralAccount()+100);
		}else if (attentions.size()==5000) {
		
			customer.setGeneralAccount(customer.getGeneralAccount()+150);
		}else if (attentions.size()==10000) {
			
			customer.setGeneralAccount(customer.getGeneralAccount()+500);
		}else if (attentions.size()==0) {
			result.setMessage("未被任何人关注");
		}
		
		Message record=new Message();
		record.setContent(content);
		record.setCreateTime(new Date());
		record.setCustomer(customer);
		record.setCustomerId(customerId);
		record.setProcessed("1");
		record.setType(messagetype);
		
		
		result.setSuccess(true);
		result.setObj(customer);
		
		
		ModelAndView mav=new ModelAndView();
		mav.addObject(result);
		return result;
	}
	
	
	/**
	 * 判断是否已关注
	 * @param pid
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value = "/attention/{customerId}/{attentionCustomerId}", method ={RequestMethod.GET})
	public @ResponseBody RestfulResult checkagree(HttpSession session,@PathVariable Integer attentionCustomerId,@PathVariable Integer customerId){
		RestfulResult result = new RestfulResult();
		result.setSuccess(false);
		
		Criteria example = new Criteria();
		example.put("attentionCustomerId", attentionCustomerId);
		example.put("customerId", customerId);
		example.put("attentionType", 1);
		
		if(attentionService.selectByParams(example).size()>0){
			result.setSuccess(true);
			result.setMessage("已关注");
		}else{
			result.setSuccess(true);
			result.setMessage("未关注");
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return result;
	}
	
	/**
	 * 取消关注指定用户
	 * @param session
	 * @param customerId
	 * @param attentionCustomerId
	 * @return
	 */
	@RequestMapping(value="/attention/del",method=RequestMethod.DELETE)
	public @ResponseBody RestfulResult AttentionDelsomebody(HttpSession session, String customerId, String attentionCustomerId){
		Criteria example = new Criteria();
		example.put("customerId",customerId);
		example.put("attentionCustomerId",attentionCustomerId);
		
		attentionService.deleteByParams(example);
			
			Message record=new Message();
			record.setContent("已取消关注");
			record.setCreateTime(new Date());
			record.setCustomerId(Integer.valueOf(attentionCustomerId));
			record.setProcessed("1");
			record.setProposer(Integer.valueOf(customerId));
			record.setType("7");
			messageService.insertSelective(record);
		
			
		
			//关注会增加热度,用户热度=所有帖子的热度+被关注数
			example.clear();
			example.put("attentionCustomerId",attentionCustomerId);
			Customer customer=customerService.selectByPrimaryKey(Integer.valueOf(attentionCustomerId));
			customer.setScore(customer.getScore()-1);
			
			customerService.updateByPrimaryKeySelective(customer);
			
			RestfulResult result = new RestfulResult();
			result.setSuccess(true);
			result.setObj(customer.getScore());
		result.setObj(customer.getGeneralAccount());
		result.setObj(customer.getGrade());
		result.setMessage("已取消关注");
		ModelAndView mav=new ModelAndView();
		mav.addObject(result);
		
		return result;
	}
	
	
	
	/**
	 * 判断是否已取消关注
	 * @param pid
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value = "/attention/{customerId}/{attentionCustomerId}/del", method ={RequestMethod.GET})
	public @ResponseBody RestfulResult checkdisagree(HttpSession session,@PathVariable Integer customerId,@PathVariable Integer attentionCustomerId){
		RestfulResult result = new RestfulResult();
		result.setSuccess(false);
		
		Criteria example = new Criteria();
		example.put("attentionCustomerId", attentionCustomerId);
		example.put("customerId", customerId);
		example.put("type", 0);
		
		if(attentionService.selectByParams(example).size()>0){
			result.setSuccess(true);
			result.setMessage("未取消关注");
		}else{
			result.setSuccess(false);
			result.setMessage("已取消关注");
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return result;
	}
	
	/**
	 * 获取广告信息
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/advertis",method=RequestMethod.GET)
	public @ResponseBody RestfulResult advertis (HttpSession session){
		Criteria example = new Criteria();
		List<Advertis> advertis=advertisService.selectByParams(example);
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(advertis);
		ModelAndView mav=new ModelAndView();
		mav.addObject(result);
		return result;
	}
	
	
	/**
	 * 推荐小红人,根据积分的大小排序
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/recommend/customer",method=RequestMethod.GET)
	public @ResponseBody RestfulResult recommendcustomer (HttpSession session,String offset,String length){
		Criteria example = new Criteria();
		example.setOrderByClause("score");
		example.setSord("asc");
		example.setMysqlOffset(Integer.parseInt(offset));
		example.setMysqlLength(Integer.parseInt(length));
		List<Customer> customers=customerService.selectByParams(example);
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(customers);
		ModelAndView mav=new ModelAndView();
		mav.addObject(result);
		return result;
	}
	
	
	
	
	/**
	 * 判断某用户是否有某种特权
	 * @return
	 */
	@RequestMapping(value="/privilege/{customerId}",method=RequestMethod.GET)
	public @ResponseBody RestfulResult attention(@PathVariable Integer customerId){
		Criteria example=new Criteria();
		example.put("customerId",customerId);
		List<CustomerPrivilege> list=customerPrivilegeService.selectByParams(example);
		List list2=new ArrayList();
		for (CustomerPrivilege cp: list) {
			Privilege privilege=privilegeService.selectByPrimaryKey(cp.getId());
			list2.add(privilege);
			
		}
		
		Customer customer=customerService.selectByPrimaryKey(customerId);
		RestfulResult result=new RestfulResult();
		result.setSuccess(true);
		result.setObj(list2);
		//result.setObj(customer);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return result;
	}







}
