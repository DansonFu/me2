package com.lettucetech.me2.web.controller;

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
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Game;
import com.lettucetech.me2.pojo.Gamecustomer;
import com.lettucetech.me2.pojo.Gameface;
import com.lettucetech.me2.service.GamecustomerService;
import com.lettucetech.me2.service.GamefaceService;
import com.lettucetech.me2.service.impl.GameServiceImpl;


@Controller
public class GameController {
	@Autowired
	private GameServiceImpl gameServiceImpl;
	@Autowired
	private GamecustomerService gamecustomerService;
	@Autowired
	private GamefaceService gamefaceService;
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
	@RequestMapping(value="/games/face",method=RequestMethod.POST)
	public ModelAndView saveGamesFace(HttpSession session,Gameface gameface) {
		RestfulResult result = new RestfulResult();
		result.setSuccess(false);
		
		int i = gamefaceService.insertSelective(gameface);
		if(i==1){
			result.setSuccess(true);
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
}
