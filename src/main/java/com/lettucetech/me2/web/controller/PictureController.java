package com.lettucetech.me2.web.controller;




import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.lettucetech.me2.common.constant.Me2Constants;
import com.lettucetech.me2.common.pojo.RestfulResult;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Customer;
import com.lettucetech.me2.pojo.Gamecustomer;
import com.lettucetech.me2.pojo.Message;
import com.lettucetech.me2.pojo.Picture;
import com.lettucetech.me2.pojo.Pictureagree;
import com.lettucetech.me2.pojo.Picturehot;
import com.lettucetech.me2.pojo.Picturerecommend;
import com.lettucetech.me2.pojo.Recommend;
import com.lettucetech.me2.pojo.Taglist;
import com.lettucetech.me2.pojo.Tagshot;
import com.lettucetech.me2.service.MessageService;
import com.lettucetech.me2.service.PictureService;
import com.lettucetech.me2.service.PictureagreeService;
import com.lettucetech.me2.service.PicturehotService;
import com.lettucetech.me2.service.PicturerecommendService;
import com.lettucetech.me2.service.RecommendService;
import com.lettucetech.me2.service.TaglistService;
import com.lettucetech.me2.service.TagshotService;
import com.mysql.jdbc.log.Log;

@Controller
public class PictureController {
	@Autowired
	private PictureService pictureService;
	@Autowired
	private PicturehotService picturehotService;
	@Autowired
	private PicturerecommendService picturerecommendService;
	@Autowired
	private PictureagreeService pictureagreeService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private TagshotService tagshotService;
	@Autowired
	private TaglistService taglistService;
	@Autowired
	private RecommendService recommendService;
	
	/**
	 * 保存蜜图AB面
	 * @param session
	 * @param pictures
	 * @return
	 */
	@RequestMapping(value = "/pictures", method ={RequestMethod.POST})
	public ModelAndView addPicture(HttpSession session,String pa,String pb){
		
//		JSONObject jsonObject = JSONObject.fromObject(pa);
//		Picture A =(Picture)JSONObject.toBean(jsonObject);
//		A.setCreatTime(new Date());
//		pictureService.insertSelective(A);
//		
//		JSONObject json = JSONObject.fromObject(pb);
//		Picture B=(Picture)JSONObject.toBean(json);
//		B.setCreatTime(new Date());
//		B.setParentId(A.getPid());
//		pictureService.insertSelective(B);
		
		
				Gson gson=new Gson();
				Picture A=gson.fromJson(pa,Picture.class);
					
					A.setCreatTime(new Date(System.currentTimeMillis()+28800000));
					pictureService.insertSelective(A);
					
					Picture B=gson.fromJson(pb,Picture.class);
					B.setCreatTime(new Date(System.currentTimeMillis()+28800000));
					B.setParentId(A.getPid());
					pictureService.insertSelective(B);
				
			
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(gson.toJson(A));
		result.setObj(gson.toJson(B));
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		
		return mav;
	
	}
	/**
	 * 增加蜜图热度
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/pictures/{pid}/increasehot", method ={RequestMethod.PUT})
	public ModelAndView addPicture(HttpSession session,@PathVariable Integer pid){
		Picture picture = pictureService.selectByPrimaryKey(pid);
		picture.setHits(picture.getHits() + Me2Constants.METOOHOTVALUE);
		pictureService.updateByPrimaryKeySelective(picture);
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		
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
//		example.put("front", "a");
		example.put("del", 0);
		
		List<Picture> pictures = pictureService.selectByParamsTagSearch(example);
		
		//查询@过自己的蜜图
		Customer customer = (Customer) session.getAttribute(Me2Constants.METOOUSER);
		example.clear();
		example.setMysqlOffset(0);
		example.setMysqlLength(1);
		example.put("del", 0);
		example.put("front", "a");
		example.put("atSeen", 0);
		example.put("atCustomerId", customer!=null?customer.getCustomerId():null);
		List<Picture> atPicture = pictureService.selectByParams(example);
		for(Picture picture : atPicture){
			pictures.add(0, picture);
			//设置为已读
			picture.setAtSeen("1");
			pictureService.updateByPrimaryKeySelective(picture);
		}
		
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
		//客户端已查询的蜜图列表，用于热门蜜图的显示防止重复
		List<Integer> plist = (List<Integer>)session.getAttribute("plist");
		if(plist==null){
			plist = new ArrayList<Integer>();
		}
		//已取推荐蜜图数量，每次热门蜜图翻页使用
		Integer count = (Integer) session.getAttribute("count");
		if(count==null){
			count = 0;
		}
		
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
		//热门标签集合的ID为1
		example.put("tagslistId", 1);
		 
		List<Picturehot> phs = picturehotService.selectByParams4Rand(example);
		
		for(Picturehot ph : phs){
			plist.add(ph.getPid());
			pictures.add(ph.getPicture());
		}
		
		//查询@过自己的蜜图
		Customer customer = (Customer) session.getAttribute(Me2Constants.METOOUSER);
		example.clear();
		example.setMysqlOffset(0);
		example.setMysqlLength(1);
		example.put("del", 0);
		example.put("front", "a");
		example.put("atSeen", 0);
		example.put("atCustomerId", customer!=null?customer.getCustomerId():null);
		List<Picture> atPicture = pictureService.selectByParams(example);
		for(Picture picture : atPicture){
			plist.add(picture.getPid());
			pictures.add(0, picture);
			//设置为已读
			picture.setAtSeen("1");
			pictureService.updateByPrimaryKeySelective(picture);
		}
		
		session.setAttribute("plist", plist);
		session.setAttribute("count", count);
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(pictures);
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	/**
	 * 取标签集的蜜图
	 * @param session
	 * @param id
	 * @param offset
	 * @param length
	 * @return
	 */
	@RequestMapping(value = "/pictures/tagslist", method ={RequestMethod.GET})
	public ModelAndView getTagslistPictures(HttpSession session,String id,String sort,String offset,String length){
		//客户端已查询的蜜图列表防止重复
		String attributename = "plist"+id;
		List<Integer> plist = (List<Integer>)session.getAttribute(attributename);
		if(plist==null||"0".equals(offset)){
			plist = new ArrayList<Integer>();
		}
		//已取推荐蜜图数量
		Integer count = (Integer) session.getAttribute("count");
		if(count==null){
			count = 0;
		}
		
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
		
		//随机取length-1 个标签集的蜜图
		example.clear();
		example.put("size", size);
		example.put("plist", plist);
		//热门标签集合的ID为1
		example.put("tagslistId", 1);
		example.put("sort", sort);
		List<Picturehot> phs = picturehotService.selectByParams4Rand(example);
		
		for(Picturehot ph : phs){
			plist.add(ph.getPid());
			pictures.add(ph.getPicture());
		}
		
		//查询@过自己的蜜图
		Customer customer = (Customer) session.getAttribute(Me2Constants.METOOUSER);
		example.clear();
		example.setMysqlOffset(0);
		example.setMysqlLength(1);
		example.put("del", 0);
		example.put("front", "a");
		example.put("atSeen", 0);
		example.put("atCustomerId", customer!=null?customer.getCustomerId():null);
		List<Picture> atPicture = pictureService.selectByParams(example);
		for(Picture picture : atPicture){
			plist.add(picture.getPid());
			pictures.add(0, picture);
			//设置为已读
			picture.setAtSeen("1");
			pictureService.updateByPrimaryKeySelective(picture);
		}
		
		session.setAttribute(attributename, plist);
		session.setAttribute("count", count);
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(pictures);
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	
	/**
	 * 我的足迹
	 * @param session
	 * @param customerId
	 * @param offset
	 * @param length
	 * @return
	 */
	@RequestMapping(value = "/customers/{customerId}/pictures", method ={RequestMethod.GET})
	public ModelAndView getPicturesByCustomer(HttpSession session,@PathVariable String customerId,String offset,String length){
		Criteria example = new Criteria();
//		example.setMysqlOffset(Integer.parseInt(offset));
//		example.setMysqlLength(Integer.parseInt(length));
		example.setOrderByClause("creat_time");
		example.setSord("desc");
		example.put("customerId", customerId);
		example.put("front", "a");
		
		List<Picture> pictures = pictureService.selectByParams(example);
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(pictures);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	
	/**
	 * 点赞或踩贴
	 * @param pictureagree
	 * @return
	 */
	@RequestMapping(value = "/pictureagrees", method ={RequestMethod.POST})
	public ModelAndView addPictureagree(Integer pid,Integer customerId,String type){
		Pictureagree pictureagree = new Pictureagree();
		pictureagree.setPid(pid);
		pictureagree.setCustomerId(customerId);
		pictureagree.setType(type);
		
		pictureagreeService.insertSelective(pictureagree);
		
		String content = "";
		String messagetype =null;
		
		//增加点赞踩和热度值
		Picture picture =pictureService.selectByPrimaryKey(pictureagree.getPid());
		//0:踩  1 :赞
		if("1".equals(pictureagree.getType())){
			//赞 数量+1
			picture.setAgree(picture.getAgree()+1);
			content="觉得有料";
			messagetype ="3";
		}else if("0".equals(pictureagree.getType())){
			picture.setDisagree(picture.getDisagree()+1);
			content="觉得无料";
			messagetype ="4";
		}
		picture.setHits(picture.getHits()+Me2Constants.METOOHOTVALUE);
		
		pictureService.updateByPrimaryKeySelective(picture);
		
		//存到用户消息表中
		Message record = new Message();
		record.setContent(content);
		record.setCreateTime(new Date());
		record.setCustomerId(picture.getCustomerId());
		record.setPid(pid);
		record.setType(messagetype);
		record.setProposer(customerId);
		messageService.insertSelective(record);
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	/**
	 * 取消点赞或踩贴
	 * @param pid
	 * @param customerId
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/pictureagrees", method ={RequestMethod.DELETE})
	public ModelAndView cancelPictureagree(Integer pid,Integer customerId,String type){
		Criteria example = new Criteria();
		example.put("pid", pid);
		example.put("customerId", customerId);
		example.put("type", type);
		
		pictureagreeService.deleteByParams(example);
		
		//减少点赞踩和热度值
		Picture picture =pictureService.selectByPrimaryKey(pid);
		//0:踩  1 :赞
		if("1".equals(type)){
			//赞 数量+1
			picture.setAgree(picture.getAgree()-1);
		}else if("0".equals(type)){
			picture.setDisagree(picture.getDisagree()-1);
		}
		picture.setHits(picture.getHits()-Me2Constants.METOOHOTVALUE);
		
		pictureService.updateByPrimaryKeySelective(picture);
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	/**
	 * 判断是否已赞
	 * @param pid
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value = "/pictureagrees/{customerId}/{pid}/checkagree", method ={RequestMethod.GET})
	public ModelAndView checkagree(@PathVariable Integer pid,@PathVariable Integer customerId){
		RestfulResult result = new RestfulResult();
		result.setSuccess(false);
		
		Criteria example = new Criteria();
		example.put("pid", pid);
		example.put("customerId", customerId);
		example.put("type", 1);
		
		if(pictureagreeService.selectByParams(example).size()>0){
			result.setSuccess(true);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	/**
	 * 判断是否已踩
	 * @param pid
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value = "/pictureagrees/{customerId}/{pid}/checkdisagree", method ={RequestMethod.GET})
	public ModelAndView checkdisagree(@PathVariable Integer pid,@PathVariable Integer customerId){
		RestfulResult result = new RestfulResult();
		result.setSuccess(false);
		
		Criteria example = new Criteria();
		example.put("pid", pid);
		example.put("customerId", customerId);
		example.put("type", 0);
		
		if(pictureagreeService.selectByParams(example).size()>0){
			result.setSuccess(true);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	/**
	 * 标签匹配
	 * @param tag
	 * @return
	 */
	@RequestMapping(value = "/tagshot/{tag}", method ={RequestMethod.GET})
	public ModelAndView tagsMatching(@PathVariable String tag){
		Criteria example = new Criteria();
		example.put("tag", tag);
		example.setMysqlOffset(0);
		example.setMysqlLength(20);
		List<Tagshot> tags = tagshotService.selectByParams4Matching(example);
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(JsonUtil.Encode(tags));
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	/**
	 * 标签匹配
	 * @param tag
	 * @return
	 */
	@RequestMapping(value = "/recommend/{tagName}", method ={RequestMethod.GET})
	public ModelAndView recommendMatching(@PathVariable String tagName){
		Criteria example = new Criteria();
		example.put("tagName", tagName);
		example.setMysqlOffset(0);
		example.setMysqlLength(20);
		List<Recommend> tags = recommendService.selectByParams4Matching(example);
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(JsonUtil.Encode(tags));
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	/**
	 * 查询标签集
	 * @return
	 */
	@RequestMapping(value = "/tagslist", method ={RequestMethod.GET})
	public ModelAndView getTagslist(){
		Criteria example = new Criteria();
		example.setOrderByClause("num");

		List<Taglist> tags = taglistService.selectByParams(example);
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(JsonUtil.Encode(tags));
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
	/**
	 * 根据pid获取picture对象
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/pictures/{pid}/gain",method=RequestMethod.POST)
	public ModelAndView gainpictures(HttpSession session,@PathVariable String pid) {
		Picture picture =pictureService.selectByPrimaryKey(Integer.valueOf(pid));
		
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(picture);
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		return mav;
	}
}
