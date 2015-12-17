package com.lettucetech.me2.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.common.constant.Me2Constants;
import com.lettucetech.me2.common.pojo.RestfulResult;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Customer;
import com.lettucetech.me2.pojo.Picture;
import com.lettucetech.me2.service.PictureService;

@Controller
public class PictureController {
	@Autowired
	private PictureService pictureService;
	
	/**
	 * 保存蜜图AB面
	 * @param session
	 * @param pictures
	 * @return
	 */
	@RequestMapping(value = "/pictures", method ={RequestMethod.POST})
	public ModelAndView addPicture(HttpSession session,@RequestBody List<Picture> pictures){
//		Customer customer = (Customer) session.getAttribute(Me2Constants.METOOUSER);
		Picture A = pictures.get(0);
		
//		A.setCustomerId(customer.getCustomerId());
		A.setCreatTime(new Date());
		pictureService.insertSelective(A);
		if(pictures.size()>1){
			Picture B = pictures.get(1);
//			B.setCustomerId(customer.getCustomerId());
			B.setCreatTime(new Date());
			B.setParentId(A.getPid());
			pictureService.insertSelective(B);
		}
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(pictures);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		
		return mav;

	}
	/**
	 * 查询蜜图
	 * @param session
	 * @param tags  标签
	 * @param type 查询类型  0：并集 1：交集
	 * @param sort 排序字段 hits:热度  creat_time:时间
	 * @param offset 分页查询位置
	 * @param length 分页条数
	 * @return
	 */
	@RequestMapping(value = "/pictures", method ={RequestMethod.GET})
	public ModelAndView getHotPictures(HttpSession session,String tags,String type,String sort,String offset,String length){
		String[] taglist = tags==null?null:tags.split(",");
		Criteria example = new Criteria();
		example.setMysqlOffset(Integer.parseInt(offset));
		example.setMysqlLength(Integer.parseInt(length));
		example.setOrderByClause(sort);
		example.put("taglist", taglist);
		example.put("type", type);
		
		List<Picture> pictures = pictureService.selectByParamsTagSearch(example);
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(pictures);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
}
