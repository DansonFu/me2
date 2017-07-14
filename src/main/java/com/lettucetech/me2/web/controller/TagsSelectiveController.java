package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.lettucetech.me2.pojo.Taglist;
import com.lettucetech.me2.pojo.Tagsconnection;
import com.lettucetech.me2.pojo.Tagshot;
import com.lettucetech.me2.service.Picture1Service;
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
	private Picture1Service pictureService;
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
	@Autowired
	private TagsconnectionMapper mapper;
	/**
	 * 跳转到标签集合页面
	 *
	 * @param session
	 * @param 
	 * @return
	 */
	 
	@RequestMapping("/admin/viewselective")
	 public ModelAndView selectByTags(HttpSession session, String id, HttpServletRequest request)
	  {
	    String str = id;
	    String flag = "2";//flag用于判断tagshot页面的复选框隐藏,2是隐藏添加到热门的复选框
	    String conn = (String)request.getSession().getAttribute("cid");//获取tagshot页面传来的tagsconnection的taglistid,用于展示页面的集合名字
	    session.removeAttribute("cid");
	    // String g=(String)request.getSession().getAttribute("g");//获取tagshot页面穿来的g值,用于判断是否隐藏添加标签按钮
	  //  String k=(String) request.getSession().getAttribute("k");//获取taglist页面传过来的k值,用于判断是否隐藏添加标签按钮
	   String g=request.getParameter("g");
	    String tag = id;
	    String i = conn;
	    String hotid = conn;
	    String hotid1 = id;

	    session.setAttribute("taglist", id);
	    String name = "";
	    Taglist taglist1;
	  
	    if (str == null) {
	    	if(i==null){
	    		String tagslistid =(String)session.getAttribute("taglistid");
		    	
		    	conn=tagslistid;
		    	taglist1 = tagListService.selectByPrimaryKey(Integer.valueOf(tagslistid));
	    	}else{
	      taglist1 = tagListService.selectByPrimaryKey(Integer.valueOf(i));
	    	}
	    }
	    	
	    
	    else {
	      taglist1 = tagListService.selectByPrimaryKey(Integer.valueOf(tag));
	      session.setAttribute("taglistid",tag);
	    }

	    name = taglist1.getTitle();//如果是从taglist页面进来的,展示的名字为id获取的,如果是从tagshot页面进来的,展示的名字是conn获取的

	    ModelAndView mav = new ModelAndView();
	    mav.addObject("cid", conn);
	    mav.addObject("flag", flag);
	    mav.addObject("tid", id);
	    mav.addObject("name", name);
	   
	    mav.addObject("g",g);
	    
	    if (hotid == null) {
	      mav.addObject("hotid", hotid1);
	    }
	    else
	    {
	      mav.addObject("hotid", hotid);
	    }

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
	 @RequestMapping(value={"/admin/getmetoo/selective"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  public void getMetooByselective(HttpSession session, HttpServletResponse response, String aoData, String tid, String cid)
	  {
	    TXtUser au = (TXtUser)session.getAttribute("adminuser");

	    ArrayList jsonarray = (ArrayList)JsonUtil.Decode(aoData);
	    String sEcho = null;

	    int iDisplayStart = 0;
	    int iDisplayLength = 0;

	    for (int i = 0; i < jsonarray.size(); i++) {
	      HashMap obj = (HashMap)jsonarray.get(i);
	      if (obj.get("name").equals("sEcho")) {
	        sEcho = obj.get("value").toString();
	      }
	      if (obj.get("name").equals("iDisplayStart")) {
	        iDisplayStart = Integer.parseInt(obj.get("value").toString());
	      }
	      if (obj.get("name").equals("iDisplayLength")) {
	        iDisplayLength = Integer.parseInt(obj.get("value").toString());
	      }
	    }
	    Criteria example = new Criteria();

	    example.setMysqlOffset(Integer.valueOf(iDisplayStart));
	    example.setMysqlLength(Integer.valueOf(iDisplayLength));
	    if (!"".equals(tid)) {
	      example.put("tagslistId", tid);
	    }
	    if (!"".equals(cid)) {
	      example.put("tagslistId", cid);
	    }

	    int count = this.tagsconnectionService.countByParams(example);
	    List<Tagsconnection> metoo = tagsconnectionService.selectByParams(example);

	    List list = new ArrayList();

	    for (Tagsconnection obj : metoo)
	    {
	      Tagshot tagshot = tagshotService.selectByPrimaryKey(obj.getTagsId());

	      if (obj.getTagslistId().equals(tid))
	      {
	        String[] d = { obj.getId().toString(), tagshot.getTag(), tagshot.getAcount().toString(), 
	          tagshot.getHits().toString(), tagshot.getMefriends().toString(), 
	          DateUtil.dateFormatToString(tagshot.getLastTime(), "yyyy-MM-dd HH:mm:ss"), "" };
	        list.add(d);
	      }
	      else if (obj.getTagslistId().equals(cid))
	      {
	        String[] d = { obj.getId().toString(), tagshot.getTag(), tagshot.getAcount().toString(), 
	          tagshot.getHits().toString(), tagshot.getMefriends().toString(), 
	          DateUtil.dateFormatToString(tagshot.getLastTime(), "yyyy-MM-dd HH:mm:ss"), "" };
	        list.add(d);
	      }

	    }

	    DataTablePaginationForm dtpf = new DataTablePaginationForm();
	    dtpf.setsEcho(sEcho);
	    dtpf.setiTotalDisplayRecords(count);
	    dtpf.setiTotalRecords(count);
	    dtpf.setAaData(list);

	    String jsonArray = JsonUtil.Encode(dtpf);
	    try
	    {
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
	 @RequestMapping({"/admin/submit"})
	  public ModelAndView submit(HttpSession session){
	  
	    Map map = new HashMap();
	    map.put("beginlength", Integer.valueOf(0));
	    List<Tagsconnection> conn = mapper.testpro(map);
	    ModelAndView mav = new ModelAndView();
	    mav.addObject(map);
	    mav.setViewName("redirect:/admin/picturehot");
	    return mav;
	  }

	
	/**
	 * 删除标签
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/delselective")
	public ModelAndView delList(HttpSession session,String id){
		
		tagsconnectionService.deleteByPrimaryKey(Integer.valueOf(id));
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

		
		
		ModelAndView mav = new ModelAndView();
	
		mav.setViewName("redirect:/admin/viewselective");
		return mav;
	}
	
	
}
