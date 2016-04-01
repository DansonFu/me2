package com.lettucetech.me2.web.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

import com.lettucetech.me2.common.constant.Me2Constants;
import com.lettucetech.me2.common.pojo.RestfulResult;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Game;
import com.lettucetech.me2.pojo.Gamecustomer;
import com.lettucetech.me2.pojo.Gameface;
import com.lettucetech.me2.pojo.Message;
import com.lettucetech.me2.pojo.Picture;
import com.lettucetech.me2.service.GamecustomerService;
import com.lettucetech.me2.service.GamefaceService;
import com.lettucetech.me2.service.MessageService;
import com.lettucetech.me2.service.PictureService;
import com.lettucetech.me2.service.impl.GameServiceImpl;


@Controller
public class GameController {
	@Autowired
	private GameServiceImpl gameServiceImpl;
	@Autowired
	private GamecustomerService gamecustomerService;
	@Autowired
	private GamefaceService gamefaceService;
	@Autowired
	private PictureService pictureService;
	@Autowired
	private MessageService messageService;
	/**
	 * 查询解密游戏
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/games",method=RequestMethod.GET)
	public ModelAndView games(HttpSession session,HttpServletResponse response) {
		Criteria example = new Criteria();
		example.put("del", 0);
		List<Game> games = gameServiceImpl.selectByParams(example);
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(games);

		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	/**
	 * 查询customeid是否具有查看pid蜜图B面权限
	 * @param session
	 * @param response
	 * @param customeid
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/games/{customeid}/{pid}",method=RequestMethod.GET)
	public ModelAndView checkGames(HttpSession session,HttpServletResponse response,@PathVariable String customeid,@PathVariable String pid) {
		RestfulResult result = new RestfulResult();
		result.setSuccess(false);
		
		Criteria example = new Criteria();
		example.put("pid", pid);
		example.put("customerId", -1);
		//查询是否对所有人开放
		List<Gamecustomer> all = gamecustomerService.selectByParams(example);
		
		//是否对customeid解蜜
		example.put("customerId", customeid);
		List<Gamecustomer> thisCustomer = gamecustomerService.selectByParams(example);
		
		if(all.size()>0 || thisCustomer.size()>0){
			result.setSuccess(true);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	/**
	 * 颜值解蜜申请
	 * @param session
	 * @param gameface
	 * @return
	 */
	@RequestMapping(value="/gameface",method=RequestMethod.POST)
	public ModelAndView saveGamesFace(HttpSession session,Gameface gameface) {
		RestfulResult result = new RestfulResult();
		result.setSuccess(false);
		gameface.setCreateTime(new Date());
		int i = gamefaceService.insertSelective(gameface);
		if(i==1){
			result.setSuccess(true);
			//增加热度
			Picture picture = pictureService.selectByPrimaryKey(gameface.getPid());
			picture.setHits(picture.getHits() + Me2Constants.METOOHOTVALUE);
			pictureService.updateByPrimaryKeySelective(picture);
			
			
			//存到用户消息表中
			Message record = new Message();
			record.setContent("请求你为他解蜜图片");
			record.setCreateTime(new Date());
			record.setCustomerId(picture.getCustomerId());
			record.setPid(gameface.getPid());
			record.setType("1");
			record.setProposer(gameface.getProposer());
			messageService.insertSelective(record);
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	/**
	 * 全部解密游戏
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/pictures/{pid}/decoding2all",method=RequestMethod.POST)
	public ModelAndView decoding2all(HttpSession session,@PathVariable String pid) {
		Gamecustomer gamecustomer = new Gamecustomer();
		gamecustomer.setPid(Integer.parseInt(pid));
		gamecustomer.setCustomerId(-1);
		
		gamecustomerService.insertSelective(gamecustomer);
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	/**
	 * 全部拒绝解密游戏
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/pictures/{pid}/refuse2all",method=RequestMethod.POST)
	public ModelAndView refuseall(HttpSession session,@PathVariable String pid) {
		Gamecustomer gamecustomer = new Gamecustomer();
		gamecustomer.setPid(Integer.parseInt(pid));
		gamecustomer.setCustomerId(0);
		
		gamecustomerService.insertSelective(gamecustomer);
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
}
