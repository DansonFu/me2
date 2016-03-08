package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.util.http.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.common.constant.Me2Constants;
import com.lettucetech.me2.common.utils.DateUtil;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.dao.TagsconnectionMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.pojo.Tagsconnection;
import com.lettucetech.me2.pojo.Tagshot;
import com.lettucetech.me2.service.PictureService;
import com.lettucetech.me2.service.PicturehotService;
import com.lettucetech.me2.service.PicturerecommendService;
import com.lettucetech.me2.service.TaglistService;
import com.lettucetech.me2.service.TagsconnectionService;
import com.lettucetech.me2.service.TagshotService;
import com.lettucetech.me2.service.impl.TagsconnectionServiceImpl;
import com.lettucetech.me2.web.form.DataTablePaginationForm;

@Controller
public class TagsSelectiveController {
	@Autowired
	private PictureService pictureService;
	@Autowired
	private PicturehotService picturehotService;
	@Autowired
	private PicturerecommendService picturerecommendService;
	@Autowired
	private TagsconnectionService tagsconnectionService;
	@Autowired
	private TagshotService tagshotService;
	@Autowired
	private TaglistService tagListService;
	
	/**
	 * 跳转到标签集合页面
	 *
	 * @param session
	 * @param 
	 * @return
	 */
	 
	@RequestMapping("/admin/viewselective")
	public ModelAndView selectByTags(HttpSession session,String id){
		session.setAttribute("taglist", id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("tid",id);
		mav.setViewName("/admin/viewselective");
		return mav;
	}
	@RequestMapping("/admin/viewTag")
	public ModelAndView viewByTags(HttpSession session){	
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/viewTags");;
		return mav;
	}
	/**
	 * 查询集合列表
	 * @param session
	 * @param response
	 * @param aoData
	 * @param userId
	 */
	@RequestMapping(value="/admin/getmetoo/selective",method={RequestMethod.POST})
	public void getMetooByselective(HttpSession session,HttpServletResponse response,String aoData) {
		TXtUser au = (TXtUser) session.getAttribute(Me2Constants.LOGIN_SESSION_DATANAME);
		String taglist=(String)session.getAttribute("taglist");
		ArrayList jsonarray = (ArrayList)JsonUtil.Decode(aoData);
	    String sEcho = null;
	    
	    int iDisplayStart = 0; // 起始索引
	    int iDisplayLength = 25; // 每页显示的行数
	 
	    for (int i = 0; i < jsonarray.size(); i++) {
	    	HashMap obj = (HashMap) jsonarray.get(i);
	    	 if (obj.get("name").equals("sEcho"))
	             sEcho = obj.get("value").toString();
	  
	         if (obj.get("name").equals("iDisplayStart"))
	             iDisplayStart = Integer.parseInt(obj.get("value").toString());
	  
	         if (obj.get("name").equals("iDisplayLength"))
	             iDisplayLength = Integer.parseInt(obj.get("value").toString());
	         
	    }
	    Criteria example = new Criteria();
	  
	    example.setMysqlOffset(iDisplayStart);
	    example.setMysqlLength(iDisplayLength);
	    //是否有查看所有人权限

//		if(!"-1".equals(userId)){
//			example.put("userId", userId);
//		}
//	    
	    int count = tagsconnectionService.countByParams(example);
	    List<Tagshot> conn = tagshotService.selectByParams(example);
	    List<Tagsconnection> metoo=tagsconnectionService.selectByParams(example);
	    //拼接翻页数据
	    List list = new ArrayList();
	    	String g = null;
	    	for(Tagsconnection tagsconn : metoo){
	    		g =tagsconn.getTagslistId();
	    		
	    	}
	    		for(Tagshot obj:conn){
	    			
	    		
			//加一个条件,判断是在哪一个集合中?
	    		if(taglist.equals(g)){
			
	    			String[] d={obj.getId().toString(),obj.getTag(),obj.getAcount().toString()
						,obj.getHits().toString(),obj.getMefriends().toString(),
						DateUtil.dateFormatToString(obj.getLastTime(), "yyyy-MM-dd HH:mm:ss"),""};		
	    			list.add(d);	
				}
	    		}
	    	
	    
		
		DataTablePaginationForm dtpf = new DataTablePaginationForm();
		dtpf.setsEcho(sEcho);
		dtpf.setiTotalDisplayRecords(count);
		dtpf.setiTotalRecords(count);
		dtpf.setAaData(list);
	    
		String jsonArray = JsonUtil.Encode(dtpf);
		
		try {
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("aplication/json;charset=UTF-8");
			response.getWriter().print(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    	}
	
	
	/**
	 * 提交
	 * @param session
	 * @param pid
	 * @param taglist_id
	 * @return
	 */
	@RequestMapping("/admin/submit")
	public ModelAndView submit(HttpSession session){
		
		  Map<String, Object> map = new HashMap<String, Object>();
		  map.put("beginlength",0);
		 
		ModelAndView mav = new ModelAndView();
		mav.addObject(map);
		mav.setViewName("redirect:/admin/picturehot");
		return mav;
	}
//	/**
//	 * 保存已选标签
//	 * @param session
//	 * @param id
//	 * @param tag
//	 * @param acount
//	 * @param hits
//	 * @param mefriends
//	 * @param key
//	 * @param lastTime
//	 * @return
//	 */
//	@RequestMapping(value="/admin/saveselective")
//	public ModelAndView saveselective(HttpSession session,String id,Tagsconnection conn,Taglist taglist){
//		
//		Tagsconnection c=new Tagsconnection();
//		
//		c.setTagslistId(conn.getTagslistId());
//		c.setTagsId(conn.getTagsId());
//		
//		tagsconnectionService.insert(conn);
//		
//		ModelAndView mav = new ModelAndView();
//	
//		mav.setViewName("redirect:/admin/viewselective");
//		return mav;
//	}

	
	/**
	 * 删除标签
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/delselective")
	public ModelAndView delList(HttpSession session,String id){
		
		tagshotService.deleteByPrimaryKey(Integer.valueOf(id));
//		 Criteria example = new Criteria();
//		 example.put("id", id);
//		 tagsconnectionService.deleteByParams(example);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/viewselective");
		return mav;
	}
	/**
	 * 恢复默认设置按钮
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/admin/reagain")
	public ModelAndView saveselective(HttpSession session){

		Tagsconnection c=new Tagsconnection();
		
		
		ModelAndView mav = new ModelAndView();
	
		mav.setViewName("redirect:/admin/viewselective");
		return mav;
	}
	
	
}
