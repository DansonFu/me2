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
import com.lettucetech.me2.pojo.Comment;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.service.CommentService;

@Controller
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	/**
	 * 评论蜜图
	 * @param session
	 * @param comment
	 * @return
	 */
	@RequestMapping(value = "/comments", method ={RequestMethod.POST})
	public ModelAndView addComment(HttpSession session,Comment comment){
		RestfulResult result = new RestfulResult();
		result.setSuccess(false);
		
		comment.setCreatTime(new Date());
		int num = commentService.insertSelective(comment);
		if(num ==1){
			result.setSuccess(true);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		
		return mav;
	}
	
	/**
	 * 查询蜜图评论
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/comments/{pid}", method ={RequestMethod.GET})
	public ModelAndView getComment(HttpSession session,@PathVariable String pid){
		Criteria example = new Criteria();
		example.put("pid", pid);
		example.setOrderByClause("creat_time");

		List<Comment> comments = commentService.selectByParams(example);
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(comments);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		
		return mav;
	}
}
