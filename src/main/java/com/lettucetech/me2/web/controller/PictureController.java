package com.lettucetech.me2.web.controller;

import java.util.ArrayList;
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
import com.lettucetech.me2.pojo.Picturehot;
import com.lettucetech.me2.pojo.Picturerecommend;
import com.lettucetech.me2.service.PictureService;
import com.lettucetech.me2.service.PicturehotService;
import com.lettucetech.me2.service.PicturerecommendService;

@Controller
public class PictureController {
	@Autowired
	private PictureService pictureService;
	@Autowired
	private PicturehotService picturehotService;
	@Autowired
	private PicturerecommendService picturerecommendService;
	
	//客户端已查询的蜜图列表，用于热门蜜图的显示防止重复
	List<Integer> plist = new ArrayList<Integer>();
	//已取推荐蜜图数量，每次热门蜜图翻页使用
	int count = 0;
	
	
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
	 * 按标签查询蜜图分页数据
	 * @param session
	 * @param tags  标签
	 * @param type 查询类型  0：并集 1：交集
	 * @param sort 排序字段 hits:热度  creat_time:时间
	 * @param offset 分页查询开始位置
	 * @param length 分页条数
	 * @return
	 */
	@RequestMapping(value = "/pictures", method ={RequestMethod.GET})
	public ModelAndView getPicturesByTags(HttpSession session,String tags,String type,String sort,String offset,String length){
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
	/**
	 * 热门蜜图
	 * （需求为控制台设置20个热门标签，每个标签取20条热度最高的共200蜜图打乱顺序，每次用户使用热门随机查询，每次翻页第一条为推荐蜜图）
	 * @param session
	 * @param offset 分页查询开始位置
	 * @param length 分页条数
	 * @return
	 */
	@RequestMapping(value = "/pictures/hot", method ={RequestMethod.GET})
	public ModelAndView getHotPictures(HttpSession session,String offset,String length){
		List<Picture> pictures = new ArrayList<Picture>();
		Criteria example = new Criteria();
		//热门数量
		int size = Integer.parseInt(length);
		
		//取一个推荐蜜图
		example.setMysqlLength(1);
		example.setMysqlOffset(count++);
		example.setOrderByClause("sort");
		List<Picturerecommend> pr = picturerecommendService.selectByParams4Hot(example);
		if(pr.size()>0){
			plist.add(pr.get(0).getPid());
			pictures.add(pr.get(0).getPicture());
			size-=1;
		}
		
		//随机取length-1 个热门蜜图
		example.clear();
		example.put("size", size);
		example.put("plist", plist);
		List<Picturehot> phs = picturehotService.selectByParams4Rand(example);
		
		for(Picturehot ph : phs){
			plist.add(ph.getPid());
			pictures.add(ph.getPicture());
		}
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(pictures);
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
}
