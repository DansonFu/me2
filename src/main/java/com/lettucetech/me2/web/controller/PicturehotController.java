package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.common.constant.Me2Constants;
import com.lettucetech.me2.common.utils.DateUtil;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Picturehot;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.pojo.Taglist;
import com.lettucetech.me2.pojo.Tagshot;
import com.lettucetech.me2.service.PicturehotService;
import com.lettucetech.me2.service.TaglistService;
import com.lettucetech.me2.service.TagshotService;
import com.lettucetech.me2.web.form.DataTablePaginationForm;


@Controller
public class PicturehotController {
	private static final Logger logger = Logger.getLogger(PicturehotController.class);
	@Autowired
	private TaglistService tagListService;
	@Autowired
	private PicturehotService pictureHotService;
	@Autowired
	private TagshotService tagshotService;
	/**
	 * 跳转查看热门标签帖页面
	 * @param session
	 * @return
	 */
	 @RequestMapping({"/admin/picturehot"})
	  public ModelAndView pcase(HttpSession session, HttpServletRequest request) { 
		 String s = request.getParameter("search");
		 String hot = request.getParameter("hot");
	    Criteria example = new Criteria();
	    example.put("hot", hot);
	    List<Taglist> taglist = tagListService.selectByParams(example);
	    Taglist list = new Taglist();
	    list.setNum(Integer.valueOf("0"));
	    
	    list.setTitle("全部");
	    taglist.add(list);
	    ModelAndView mav = new ModelAndView();
	    mav.addObject("taglists", taglist);
	    mav.addObject("svalue", s);
	    mav.setViewName("/admin/picturehot");
	    return mav;
	  }
	/**
	 * 查询热门标签帖列表
	 * @param session
	 * @param response
	 * @param aoData
	 * @param userId
	 */
	 @RequestMapping({"/admin/getmetoo/picturehot"})
	  public void getMetooByPictureHot(HttpSession session, HttpServletResponse response, String aoData, String hotid,String searchid)
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
	      if (obj.get("name").equals("iDisplayLength"))
	        iDisplayLength = Integer.parseInt(obj.get("value").toString());
	    }
	    Criteria example = new Criteria();
	    example.setOrderByClause("hits");
	    example.setSord("desc");
	    example.setMysqlOffset(Integer.valueOf(iDisplayStart));
	    example.setMysqlLength(Integer.valueOf(iDisplayLength));
	    example.put("state", "0");

	    if(!"".equals(hotid)){
	    	example.put("tagslist_id",hotid);
	    	
	    }
	    if(!"".equals(searchid)){
	    	example.put("tag",searchid);
	    }
	    int count = pictureHotService.countByParams(example);
	    List<Tagshot> metoo = tagshotService.selectByParams(example);
	   // List<Picturehot> hot=pictureHotService.selectByParams(example);
	   
	    List list = new ArrayList();

	    for (Tagshot obj : metoo)
	    {
	    	if(obj.getTag().equals(searchid)){
	      String aurl = Me2Constants.QINIUPUBLICDOMAIN+"/"+ obj.getQiniukey();
	      String[] d = { obj.getId().toString(), obj.getTag(), aurl, obj.getAcount().toString(), 
	        obj.getHits().toString(), obj.getMefriends().toString(), 
	        DateUtil.dateFormatToString(obj.getLastTime(), "yyyy-MM-dd HH:mm:ss"), "" };

	      list.add(d);
	    }else{
	    	 String aurl = Me2Constants.QINIUPUBLICDOMAIN+"/"+ obj.getQiniukey();
		      String[] d = { obj.getId().toString(), obj.getTag(), aurl, obj.getAcount().toString(), 
		        obj.getHits().toString(), obj.getMefriends().toString(), 
		        DateUtil.dateFormatToString(obj.getLastTime(), "yyyy-MM-dd HH:mm:ss"), "" };

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
}
