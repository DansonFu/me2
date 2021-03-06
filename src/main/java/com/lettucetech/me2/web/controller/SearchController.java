
package com.lettucetech.me2.web.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSON;

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
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Customer;
import com.lettucetech.me2.pojo.CustomerPrivilege;
import com.lettucetech.me2.pojo.Gameprop;
import com.lettucetech.me2.pojo.Message;
import com.lettucetech.me2.pojo.Mytask;
import com.lettucetech.me2.pojo.Picture1;
import com.lettucetech.me2.pojo.Present;
import com.lettucetech.me2.pojo.Prop;
import com.lettucetech.me2.pojo.Task;
import com.lettucetech.me2.pojo.Vote;
import com.lettucetech.me2.service.CustomerPrivilegeService;
import com.lettucetech.me2.service.CustomerService;
import com.lettucetech.me2.service.GamepropService;
import com.lettucetech.me2.service.GradeService;
import com.lettucetech.me2.service.MessageService;
import com.lettucetech.me2.service.MytaskService;
import com.lettucetech.me2.service.Picture1Service;
import com.lettucetech.me2.service.PresentService;
import com.lettucetech.me2.service.PropService;
import com.lettucetech.me2.service.TaskService;
import com.lettucetech.me2.service.VoteService;
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
	private Picture1Service pictureService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private MytaskService mytaskService;
	@Autowired
	private PropService propService;
	@Autowired
	private GamepropService gamepropService;
	@Autowired
	private PresentService presentService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private GradeService gradeService;
	@Autowired
	private CustomerPrivilegeService customerPrivilegeService;
	@Autowired
	private VoteService voteService;
	
	
	
	/**
	 * 搜索用户的详细信息和发布的所有密图信息
	 * @param session
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value="/searchCustomerid/{customerId}",method=RequestMethod.GET)
	public @ResponseBody RestfulResult Searchbody(HttpSession session,@PathVariable String customerId){
		Customer customer=customerService.selectByPrimaryKey(Integer.valueOf(customerId));
		
		Criteria example=new Criteria();
		example.put("customerId",customerId);
		List<Picture1> list=pictureService.selectByParams(example);
		
		RestfulResult result=new RestfulResult();
		result.setSuccess(true);
		result.setObj(list);
		result.setObj(customer);
		ModelAndView mav=new ModelAndView();
		mav.addObject(result);
		return result;
	}
//	/**
//	 * 精确搜索用户信息
//	 * @param session
//	 * @param username
//	 * @return
//	 */
//	@RequestMapping(value="/search/{username}",method=RequestMethod.GET)
//	public ModelAndView SearchPeople(HttpSession session,@PathVariable String username){
//		
//		Criteria example=new Criteria();
//		example.put("username",username);
//		Customer customer=customerService.selectByParamsPeopleSearch(example);
//		
//		
//		RestfulResult result=new RestfulResult();
//		result.setSuccess(true);
//		
//		result.setObj(customer);
//		ModelAndView mav=new ModelAndView();
//		mav.addObject(result);
//		return mav;
//	}
	
	/**
	 * 模糊和精确搜索用户信息
	 * @param session
	 * @param username
	 * @return
	 */
	@RequestMapping(value="/searchUsername/{username}",method=RequestMethod.GET)
	public @ResponseBody RestfulResult Search4at(HttpSession session,@PathVariable String username){
		List list=new ArrayList();
		Criteria example=new Criteria();
		example.put("username",username);
		Customer customer=customerService.selectByParamsPeopleSearch(example);
		List<Customer> customers =customerService.selectByParams4at(example);
		list.add(customer);
		list.add(customers);
		
		RestfulResult result=new RestfulResult();
		result.setSuccess(true);
		
		result.setObj(list);
		ModelAndView mav=new ModelAndView();
		mav.addObject(result);
		return result;
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
	@RequestMapping(value = "/searchSchoolName/{schoolname}", method ={RequestMethod.GET})
	public @ResponseBody RestfulResult getPicturesBySchool(HttpSession session,@PathVariable String schoolname,String offset,String length){
		
		Criteria example = new Criteria();
		example.put("school",schoolname);
		
		example.setMysqlOffset(Integer.parseInt(offset));
		example.setMysqlLength(Integer.parseInt(length));
		example.setOrderByClause("score");
		example.setSord("asc");
		List<Customer> customers=customerService.selectByParams(example);
		
		
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(customers);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return result;
	}
	
	/**
	 * 按照图片的标签类型搜索此用户发过的此类型的图片
	 * @param session
	 * @param customerId 此用户的id
	 * @param pictype 投票令,悬赏令
	 * @return
	 */
	@RequestMapping(value = "/search/{customerId}/{pictype}", method ={RequestMethod.GET})
	public @ResponseBody RestfulResult getPicturesBypictype(HttpSession session,@PathVariable String customerId,@PathVariable String pictype){
		
		Criteria example = new Criteria();
		example.put("customerId",customerId);
		example.put("token", pictype);
		
		
		List<Picture1> picture=pictureService.selectByParams(example);
		
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(picture);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return result;
	}
	
	/**
	 * 我的任务清单
	 * @param session
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value = "/task/{customerId}", method ={RequestMethod.GET})
	public @ResponseBody RestfulResult getMyTask(HttpSession session,@PathVariable String customerId){
		
		Criteria example = new Criteria();
		example.put("customerId",customerId);
		example.put("complete",0);
		List<Mytask> mytasks=mytaskService.selectByParams(example);
		
		
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(mytasks);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return result;
	}
	
	
	/**
	 * 获取任务列表
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/tasks", method ={RequestMethod.GET})
	public @ResponseBody RestfulResult getTask(HttpSession session){
		
		Criteria example = new Criteria();
		List<Task> tasks=taskService.selectByParams(example);
	
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(tasks);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return result;
	}
	
	/**
	 * 获取道具列表信息
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/props", method ={RequestMethod.GET})
	public @ResponseBody RestfulResult getProp(HttpSession session){
		
		Criteria example = new Criteria();
		List<Prop> props=propService.selectByParams(example);
	
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(props);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return result;
	}
	
	/**
	 * 赠送道具的方法
	 * @param session
	 * @param customerId 用户id
	 * @param pid 图片的id
	 * @param proptype 道具的类型
	 * @return
	 */
	@RequestMapping(value = "/props/{customerId}/{pid}/{proptype}", method ={RequestMethod.POST})
	public @ResponseBody RestfulResult getPropBybody(HttpSession session,@PathVariable String customerId,@PathVariable String pid,@PathVariable String proptype){
		Present present=new Present();
		present.setCreateTime(new Date());
		present.setCustomerId(Integer.valueOf(customerId));
		Picture1 picture=pictureService.selectByPrimaryKey(Integer.valueOf(pid));
		present.setPresentCustomerId(picture.getCustomerId());
		present.setPresentType(Integer.valueOf(proptype));
		presentService.insertSelective(present);
		
		RestfulResult result = new RestfulResult();
		
		Criteria example = new Criteria();
		example.put("customerId", customerId);
		example.put("pid",pid);
		List<Gameprop> list=gamepropService.selectByParams(example);
		Gameprop game=null;
		Customer customer=customerService.selectByPrimaryKey(Integer.valueOf(customerId));
		
		String content = "";
		String messagetype =null;
		//判断赠送的是什么礼物 1,小红花 2,彩虹 3,炸弹
		if("1".equals(present.getPresentType())){
			if(list.size()>0){
				for (Gameprop gameprop : list) {
					 game=gamepropService.selectByPrimaryKey(gameprop.getId());
				}
			//判断时间是否超过一个小时,否则拒绝操作
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(game.getCreateTime());
			long a =System.currentTimeMillis();
			long b=0;
			try {
				b=(a-sdf.parse(time).getTime())/3600000;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (b>=1) {
				//如果是小红花,
				customer.setGeneralAccount(customer.getGeneralAccount()+1);
				content="送花成功";
				messagetype="9";
				game.setCreateTime(new Date());
				game.setCustomerId(Integer.valueOf(customerId));
				game.setPid(Integer.valueOf(pid));
				game.setFlower(game.getFlower()+1);
			}else{
				content="送花失败";
				messagetype="10";
			}
			}else if (list.size()==0) {
				
			}
			
		}else if("2".equals(present.getPresentType())){
			//判断该用户是否有足够的虚拟币
			if(customer.getGeneralAccount()>10){
				customer.setGeneralAccount(customer.getGeneralAccount()-10);
				//如果是彩虹,增加十个赞
				picture.setAgree(picture.getAgree()+10);
				content="觉得有料";
				messagetype="3";
				game.setCreateTime(new Date());
				game.setCustomerId(Integer.valueOf(customerId));
				game.setPid(Integer.valueOf(pid));
				game.setRainbow(game.getRainbow()+1);
			}else{
				result.setSuccess(false);
				result.setMessage("虚拟币不足");
			}
			
		}else if("3".equals(present.getPresentType())){
			if(customer.getGeneralAccount()>10){
				customer.setGeneralAccount(customer.getGeneralAccount()-10);
				picture.setAgree(picture.getAgree()-10);
				content="觉得无料";
				messagetype="4";
				game.setCreateTime(new Date());
				game.setCustomerId(Integer.valueOf(customerId));
				game.setPid(Integer.valueOf(pid));
				game.setBomb(game.getBomb()+1);
			}else{
				result.setSuccess(false);
				result.setMessage("虚拟币不足");
			}
			
		}
		picture.setHits(picture.getHits()+Me2Constants.METOOHOTVALUE);
		gamepropService.insert(game);
		customerService.updateByPrimaryKeySelective(customer);
		pictureService.updateByPrimaryKeySelective(picture);
		//存到用户消息表中
				Message record = new Message();
				record.setContent(content);
				record.setCreateTime(new Date());
				record.setCustomerId(picture.getCustomerId());
				record.setPid(Integer.valueOf(pid));
				record.setType(messagetype);
				record.setProcessed("1");
				record.setProposer(Integer.valueOf(customerId));
				messageService.insertSelective(record);
		example.clear();
		example.put("customerId", customerId);
		List<Picture1> pictures=pictureService.selectByParams(example);
		int sum=0;
		//获取该用户的所有密图的热度综合,作为积分
		for (Picture1 picture2 : pictures) {
			int a =picture2.getHits();
			sum+=a;
		}
		customer.setScore(customer.getScore()+sum);
		customerService.updateByPrimaryKeySelective(customer);
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
			break;
		}
		customerService.updateByPrimaryKeySelective(customer);
		
//		

		
//		}else {
//			
//			String content = "";
//			String messagetype =null;
//			//判断赠送的是什么礼物 1,小红花 2,彩虹 3,炸弹
//		
//			if("1".equals(present.getPresentType())){
//				//如果是小红花,
//				customer.setGeneralAccount(customer.getGeneralAccount()+1);
//				content="送花成功";
//				messagetype="9";
//				
//					
//					game.setCreateTime(new Date());
//					game.setCustomerId(Integer.valueOf(customerId));
//					game.setPid(Integer.valueOf(pid));
//					game.setFlower(game.getFlower()+1);
//				
//			}else if("2".equals(present.getPresentType())){
//				//判断该用户是否有足够的虚拟币
//				if(customer.getGeneralAccount()>10){
//					customer.setGeneralAccount(customer.getGeneralAccount()-10);
//					//如果是彩虹,增加十个赞
//					picture.setAgree(picture.getAgree()+10);
//					content="觉得有料";
//					messagetype="3";
//					game.setCreateTime(new Date());
//					game.setCustomerId(Integer.valueOf(customerId));
//					game.setPid(Integer.valueOf(pid));
//					game.setRainbow(game.getRainbow()+1);
//				}else{
//					result.setSuccess(false);
//					result.setMessage("虚拟币不足");
//				}
//				
//			}else if("3".equals(present.getPresentType())){
//				if(customer.getGeneralAccount()>10){
//					customer.setGeneralAccount(customer.getGeneralAccount()-10);
//					picture.setAgree(picture.getAgree()-10);
//					content="觉得无料";
//					messagetype="4";
//					game.setCreateTime(new Date());
//					game.setCustomerId(Integer.valueOf(customerId));
//					game.setPid(Integer.valueOf(pid));
//					game.setBomb(game.getBomb()+1);
//				}else{
//					result.setSuccess(false);
//					result.setMessage("虚拟币不足");
//				}
//				
//			}
//			picture.setHits(picture.getHits()+Me2Constants.METOOHOTVALUE);
//			gamepropService.insert(game);
//			customerService.updateByPrimaryKeySelective(customer);
//			pictureService.updateByPrimaryKeySelective(picture);
//			//存到用户消息表中
//			Message record = new Message();
//			record.setContent(content);
//			record.setCreateTime(new Date());
//			record.setCustomerId(picture.getCustomerId());
//			record.setPid(Integer.valueOf(pid));
//			record.setType(messagetype);
//			record.setProcessed("1");
//			record.setProposer(Integer.valueOf(customerId));
//			messageService.insertSelective(record);
//			example.clear();
//			example.put("customerId", customerId);
//			List<Picture> pictures=pictureService.selectByParams(example);
//			int sum=0;
//			//获取该用户的所有密图的热度综合,作为积分
//			for (Picture picture2 : pictures) {
//				int a =picture2.getHits();
//				sum+=a;
//			}
//			customer.setScore(customer.getScore()+sum);
//			customerService.updateByPrimaryKeySelective(customer);
//			switch (customer.getScore()) {
//			case 100:
//				customer.setGrade(1);
//				customer.setGeneralAccount(customer.getGeneralAccount()+10);
//				content="恭喜你升到1级";
//				messagetype="8";
//				break;
//			case 200:
//				customer.setGrade(2);
//				customer.setGeneralAccount(customer.getGeneralAccount()+10);
//				content="恭喜你升到2级";
//				messagetype="8";
//				break;
//			case 500:
//				customer.setGrade(3);
//				customer.setGeneralAccount(customer.getGeneralAccount()+10);
//				content="恭喜你升到3级";
//				messagetype="8";
//				break;
//			case 1000:
//				customer.setGrade(4);
//				customer.setGeneralAccount(customer.getGeneralAccount()+10);
//				content="恭喜你升到4级";
//				messagetype="8";
//				break;
//			case 2000:
//				customer.setGrade(5);
//				customer.setGeneralAccount(customer.getGeneralAccount()+10);
//				content="恭喜你升到5级";
//				messagetype="8";
//				break;
//			case 3000:
//				customer.setGrade(6);
//				customer.setGeneralAccount(customer.getGeneralAccount()+20);
//				content="恭喜你升到6级";
//				messagetype="8";
//				break;
//			case 5000:
//				customer.setGrade(7);
//				customer.setGeneralAccount(customer.getGeneralAccount()+20);
//				content="恭喜你升到7级";
//				messagetype="8";
//				
//				break;
//			case 8000:
//				customer.setGrade(8);
//				customer.setGeneralAccount(customer.getGeneralAccount()+20);
//				content="恭喜你升到8级";
//				messagetype="8";
//				break;
//			case 15000:
//				customer.setGrade(9);
//				customer.setGeneralAccount(customer.getGeneralAccount()+20);
//				content="恭喜你升到9级";
//				messagetype="8";
//				break;
//			case 20000:
//				customer.setGrade(10);
//				customer.setGeneralAccount(customer.getGeneralAccount()+20);
//				content="恭喜你升到10级";
//				messagetype="8";
//				break;
//			case 25000:
//				customer.setGrade(11);
//				customer.setGeneralAccount(customer.getGeneralAccount()+50);
//				content="恭喜你升到11级";
//				messagetype="8";
//				break;
//			case 30000:
//				customer.setGrade(12);
//				customer.setGeneralAccount(customer.getGeneralAccount()+50);
//				content="恭喜你升到12级";
//				messagetype="8";
//				break;
//			case 35000:
//				customer.setGrade(13);
//				customer.setGeneralAccount(customer.getGeneralAccount()+50);
//				content="恭喜你升到13级";
//				messagetype="8";
//				break;
//			case 40000:
//				customer.setGrade(14);
//				customer.setGeneralAccount(customer.getGeneralAccount()+50);
//				content="恭喜你升到14级";
//				messagetype="8";
//				break;
//			case 50000:
//				customer.setGrade(15);
//				customer.setGeneralAccount(customer.getGeneralAccount()+50);
//				content="恭喜你升到15级";
//				messagetype="8";
//				break;
//			case 60000:
//				customer.setGrade(16);
//				customer.setGeneralAccount(customer.getGeneralAccount()+100);
//				content="恭喜你升到16级";
//				messagetype="8";
//				break;
//			case 80000:
//				customer.setGrade(17);
//				customer.setGeneralAccount(customer.getGeneralAccount()+100);
//				content="恭喜你升到17级";
//				messagetype="8";
//				break;
//			case 100000:
//				customer.setGrade(18);
//				customer.setGeneralAccount(customer.getGeneralAccount()+100);
//				content="恭喜你升到18级";
//				messagetype="8";
//				break;
//			case 120000:
//				customer.setGrade(19);
//				customer.setGeneralAccount(customer.getGeneralAccount()+100);
//				content="恭喜你升到19级";
//				messagetype="8";
//				break;
//			case 200000:
//				customer.setGrade(20);
//				customer.setGeneralAccount(customer.getGeneralAccount()+100);
//				content="恭喜你升到20级";
//				messagetype="8";
//				break;
//				
//			default:
//				customer.setGrade(0);
//				break;
//			}
//			customerService.updateByPrimaryKeySelective(customer);
			if (customer.getGrade()==5) {
				content="恭喜你,开启了使用悬赏令的特权";
				messagetype="11";
				
				CustomerPrivilege cp=new CustomerPrivilege();
				cp.setCustomerId(picture.getCustomerId());
				cp.setPrivilegeid(7);
				customerPrivilegeService.insertSelective(cp);
				
				Message record2=new Message();
				record2.setContent(content);
				record2.setCreateTime(new Date());
				record2.setCustomerId(picture.getCustomerId());
				record2.setPid(Integer.valueOf(pid));
				record2.setType(messagetype);
				record2.setProcessed("1");
				record2.setProposer(Integer.valueOf(customerId));
				messageService.insertSelective(record2);
			}
	
			//存到用户消息表中
			Message record1 = new Message();
			record1.setContent(content);
			record1.setCreateTime(new Date());
			record1.setCustomerId(picture.getCustomerId());
			record1.setPid(Integer.valueOf(pid));
			record1.setType(messagetype);
			record1.setProcessed("1");
			record1.setProposer(Integer.valueOf(customerId));
			messageService.insertSelective(record1);
			
			result.setSuccess(true);
			
			result.setObj(customer.getGeneralAccount());
			result.setObj(customer.getScore());
			result.setObj(customer.getGrade());
			ModelAndView mav = new ModelAndView();
			mav.addObject(result);
			return result;
		}

		
		
	

	
	/**
	 * 获取送花的小红豆
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/pictures/{pid}/bean",method=RequestMethod.GET)
	public @ResponseBody RestfulResult bean(HttpSession session,@PathVariable String pid){
		Criteria example=new Criteria();
		example.put("pid",pid);
		example .setOrderByClause("create_time");
		example.setSord("desc");
		List<Gameprop> gameprops=gamepropService.selectByParams(example);
		List list=new ArrayList();
		for (Gameprop gameprop : gameprops) {
			if (gameprop.getFlower()!=0) {
				
				Customer customer=customerService.selectByPrimaryKey(gameprop.getCustomerId());
				list.add(customer);
			}
		}
		RestfulResult result=new RestfulResult();
		result.setSuccess(true);
		result.setObj(list);
		
		
		ModelAndView mav=new ModelAndView();
		mav.addObject(result);
		return result;
	}
	
	/**
	 * 根据用户的位置获取附近的大学信息 根据精度从小到大排序
	 * @param session
	 * @param longitude
	 * @param latitude
	 * @param precision
	 * @return
	 */
	@RequestMapping(value="/searchs/{longitude}/{latitude}/{precision}",method=RequestMethod.GET)
	public @ResponseBody RestfulResult getSchool(HttpSession session,@PathVariable String longitude,@PathVariable String latitude,@PathVariable String precision){
		
		Criteria example=new Criteria();
		example.put("current_longitude",longitude);
		example.put("current_latitude", latitude);
		example.setOrderByClause(precision);
		example.setSord("asc");
		
		List<Customer> list=customerService.selectByParams(example);
		
		
		RestfulResult result=new RestfulResult();
		result.setSuccess(true);
		result.setObj(list);
		ModelAndView mav=new ModelAndView();
		mav.addObject(result);
		return result;
	}
	/**
	 * 判断是否有足够的糖块购买个性任务
	 * @param session
	 * @param customerId
	 * @param taskid
	 * @return
	 */
	@RequestMapping(value="/tasks/{customerId}/{taskid}/different",method=RequestMethod.POST)
	public @ResponseBody RestfulResult checkTaskByDifferent(HttpSession session,@PathVariable Integer customerId,@PathVariable Integer taskid ){
		RestfulResult result=new RestfulResult();
		Task task=taskService.selectByPrimaryKey(taskid);
		Customer customer=customerService.selectByPrimaryKey(customerId);
		if (task.getTasktype()==2 && task.getCustom()>0) {
			if (customer.getGeneralAccount()>10) {
				result.setMessage("可以购买");
			}else{
				result.setMessage("糖块不足");
			}
		}
		
		
		
		return result;
	}
	
	
	/**
	 * 领取个性任务
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/tasks/different",method=RequestMethod.GET)
	public @ResponseBody RestfulResult getTaskByDifferent(HttpSession session, Integer customerId, Integer taskid){
		Task task=taskService.selectByPrimaryKey(taskid);
		Criteria example=new Criteria();
		example.put("taskid",taskid);
		example.put("customerId", customerId);
		
		List<Mytask> lists=mytaskService.selectByParams(example);
		String messagetype="";
		String content="";
		if(lists.size()>0 && task.getTasktype()==2){
			content="已领取过此任务";
			messagetype="17";
		}else{
			
			Mytask mytask=new Mytask();
			mytask.setComplete(0);
			mytask.setCreateTime(new Date());
			mytask.setCustomerId(customerId);
			mytask.setTaskid(taskid);
			mytask.setTaskstyle(2);//2一次性任务
			mytask.setTaskType(2);//2个性任务
			
			mytaskService.insertSelective(mytask);
			content="领取个性任务成功";
			messagetype="17";
		}
		Message record=new Message();
		record.setContent(content);
		record.setCreateTime(new Date());
		record.setCustomerId(customerId);
		record.setProcessed("1");
		record.setType(messagetype);
		messageService.insertSelective(record);
		
		RestfulResult result=new RestfulResult();
		return result;
	}
	
	/**
	 * 判断是否有足够的糖块购投票贴或者悬赏令
	 * @param session
	 * @param customerId
	 * @param taskid
	 * @return
	 */
	@RequestMapping(value="/votes/check",method=RequestMethod.POST)
	public @ResponseBody RestfulResult checkvote(HttpSession session,Integer customerId){
		RestfulResult result=new RestfulResult();
		
		Customer customer=customerService.selectByPrimaryKey(customerId);
		if (customer.getGeneralAccount()>10) {
			result.setMessage("糖块足够");
			result.setObj(customer);
		}else{
			result.setMessage("糖块不足");
			result.setObj(customer);
		}
		
		
		
		return result;
	}
	
	/**
	 * 生成投票令
	 * @param session
	 * @param customerId
	 * @param tokentype
	 * @param va
	 * @param vb
	 * @return
	 */
	@RequestMapping(value="/votes/{customerId}/check",method=RequestMethod.GET)
	public @ResponseBody RestfulResult checkvoteBy(HttpSession session,@PathVariable Integer customerId,String va,String vb){
		RestfulResult result=new RestfulResult();
		Customer customer=customerService.selectByPrimaryKey(customerId);
		
			
			Vote vote=new Vote();
			vote.setCustomerid(customerId);
			vote.setVa(va);
			vote.setVb(vb);
			
			voteService.insertSelective(vote);
			
			result.setObj(customer);
			result.setObj(vote);
			result.setMessage("生成投票令成功");
		
		return result;
	}
}