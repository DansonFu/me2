package com.lettucetech.me2.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.lettucetech.me2.common.pojo.RestfulResult;
import com.lettucetech.me2.common.utils.IosPushUtil;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Customer;
import com.lettucetech.me2.pojo.Gamecustomer;
import com.lettucetech.me2.pojo.Gameface;
import com.lettucetech.me2.pojo.Message;
import com.lettucetech.me2.service.CustomerService;
import com.lettucetech.me2.service.GamecustomerService;
import com.lettucetech.me2.service.GamefaceService;
import com.lettucetech.me2.service.MessageService;
import com.lettucetech.me2.web.form.MessageForm;

@Controller
public class MessageController {
	@Autowired
	private MessageService messageService;
	@Autowired
	private GamefaceService gamefaceService;
	@Autowired
	private GamecustomerService gamecustomerService;
	@Autowired
	private CustomerService customerService;
	/**
	 * 取得用户的解蜜消息集合
	 * @param session
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value = "/customers/{customerId}/message/notification", method ={RequestMethod.GET})
	public ModelAndView getNotification(HttpSession session,@PathVariable String customerId){
		Criteria example = new Criteria();
		example.put("customerId", customerId);
		example.put("type", 1);
		example.put("processed", 0);
		List<Message> messages = messageService.selectByParams(example);
		
		//组装通知的数据
		List<MessageForm> mfs = new ArrayList<MessageForm>();
		//去除重复的蜜图
		for(Message message : messages){
			//是否已存在蜜图
			boolean flag = false;
			for(MessageForm mf:mfs){
				if(message.getPicture()==mf.getPicture()){
					flag = true;
					break;
				}
			}
			if(!flag){
				MessageForm messageForm = new MessageForm();
				messageForm.setPicture(message.getPicture());
				messageForm.setProposers(new ArrayList<Customer>());
				
				mfs.add(messageForm);
			}
		}
		//把申请解密用户放入相应的蜜图
		for(MessageForm mf:mfs){
			for(Message message : messages){
				if(message.getPicture()==mf.getPicture()){
					mf.getProposers().add(message.getCustomer());
				}
			}
		}
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(mfs);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	/**
	 * 解蜜达成或拒绝消息
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/customers/{customerId}/message/gamedispose", method ={RequestMethod.GET})
	public ModelAndView getGamDisposeMessage(HttpSession session,@PathVariable String customerId){
		Criteria example = new Criteria();
		example.put("customerId", customerId);
		example.put("type", 2);
		example.setOrderByClause("create_time");
		example.setSord("desc");
//		example.put("processed", 0);
		List<Message> messages = messageService.selectByParams(example);
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(messages);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	/**
	 * 查询其它消息--嗡嗡
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/customers/{customerId}/message", method ={RequestMethod.GET})
	public ModelAndView getMessage(HttpSession session,@PathVariable String customerId){
		List<Integer> typelist = new ArrayList<Integer>();
		typelist.add(1);
		typelist.add(2);
		Criteria example = new Criteria();
		example.put("customerId", customerId);
		example.put("typelist", typelist);
		example.setOrderByClause("create_time");
		example.setSord("desc");
//		example.put("processed", 0);
		List<Message> messages = messageService.selectByParams4Classify(example);
//		//去除解蜜申请的消息
//		for(Iterator<Message> it=messages.iterator();it.hasNext();){
//			Message message = it.next();
//			if("1".equals(message.getType())){
//				it.remove();
//			}
//		}
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(messages);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	
	/**
	 * 查询蜜图的颜值解蜜请求(消息--通知)
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/pictures/{pid}/gameface", method ={RequestMethod.GET})
	public ModelAndView getGameface(HttpSession session,@PathVariable String pid){
		Criteria example = new Criteria();
		example.put("pid", pid);
		example.put("type", 1);
		example.put("processed", 0);
		example.setOrderByClause("create_time");
		example.setSord("desc");
		List<Gameface> gamefaces = gamefaceService.selectByParams(example);
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(gamefaces);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	
	/**
	 * 处理颜值解蜜申请
	 * @param session
	 * @param id
	 * @param dispose
	 * @return
	 */
	@RequestMapping(value = "/gameface/{id}/{dispose}", method ={RequestMethod.GET})
	public ModelAndView updateGameface(HttpSession session,@PathVariable String id, @PathVariable String dispose){
		Gson gson = new Gson();
		Gameface gameface= gamefaceService.selectByPrimaryKey(Integer.parseInt(id));
		//设置解蜜申请为已处理
		gameface.setProcessed("1");
		gamefaceService.updateByPrimaryKey(gameface);
		
		//如果同意则加入gamecustomer表
		if("agree".equals(dispose)){
			Gamecustomer gamecustomer = new Gamecustomer();
			gamecustomer.setPid(gameface.getPid());
			gamecustomer.setCustomerId(gameface.getProposer());
			
			gamecustomerService.insert(gamecustomer);
		}
		
		//设置消息为已处理
		Criteria example = new Criteria();
		example.put("pid", gameface.getPid());
		example.put("type", 1);
		example.put("proposer", gameface.getProposer());
		List<Message> messages = messageService.selectByParams(example);
		for(Message message : messages){
			message.setProcessed("1");
			messageService.updateByPrimaryKey(message);
		}

		
		
		Customer customer = customerService.selectByPrimaryKey(gameface.getIssuer());
		String mes = "";
		
		//存到对方用户的消息表中(注意用户身份的转换)
		Message record = new Message();
		if("agree".equals(dispose)){
			record.setContent("为你解蜜了图片");
			mes =customer.getUsername() + "为你解蜜了图片";
		}else {
			record.setContent("拒绝了你的解蜜申请");
			mes =customer.getUsername() + "拒绝了你的解蜜申请";
		}

		record.setCreateTime(new Date());
		record.setCustomerId(gameface.getProposer());
		record.setPid(gameface.getPid());
		record.setType("2");
		record.setProposer(customer.getCustomerId());
		messageService.insert(record);
		
		//发送系统推送消息
		try {
			if("agree".equals(dispose)){
				IosPushUtil.pushMsgNotification(mes,gameface.getCustomer().getApppushtoken());
			}
		} catch (CommunicationException e) {
			e.printStackTrace();
		} catch (KeystoreException e) {
			e.printStackTrace();
		}
		

		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
}
