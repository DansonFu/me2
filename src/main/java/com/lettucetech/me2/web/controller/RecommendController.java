package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.common.constant.Me2Constants;
import com.lettucetech.me2.common.utils.DateUtil;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Recommend;
//import com.lettucetech.me2.pojo.Recommend;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.pojo.Taglist;
import com.lettucetech.me2.pojo.Tagsconnection;
import com.lettucetech.me2.pojo.Tagshot;
import com.lettucetech.me2.service.Picture1Service;
import com.lettucetech.me2.service.PicturehotService;
import com.lettucetech.me2.service.PicturerecommendService;
import com.lettucetech.me2.service.RecommendService;
//import com.lettucetech.me2.service.RecommendService;
import com.lettucetech.me2.service.TXtUserService;
import com.lettucetech.me2.service.TaglistService;
import com.lettucetech.me2.service.TagsconnectionService;
import com.lettucetech.me2.service.TagshotService;
import com.lettucetech.me2.web.form.DataTablePaginationForm;

@Controller
public class RecommendController {

	@Autowired
	private RecommendService recommendService;
	@Autowired
	private TagsconnectionService tagsconnectionService;
	@Autowired
	private TagshotService tagshotService;
	@Autowired
	private TXtUserService usi;
	@Autowired
	private TaglistService tagsListService;
	@Autowired
//	private RecommendService recommendService;
//	/**
//	 * 根据热度查询标签
//	 * @param session
//	 * @param hits
//	 * @return
//	 */
//	@RequestMapping(value = "/hits", method ={RequestMethod.GET})
//	public ModelAndView selectByHits(HttpSession session, String hits){
//		Criteria example = new Criteria();
//		example.put("hits", hits);
//		example.setOrderByClause("hits");
//		
//		List<Tagshot> tags=tagshotService.selectByParams(example);
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("tags", tags);
//		return mav;
//	}
//	
	/**
	 * 跳转到热门标签页面
	 *
	 * @param session
	 * @param tag
	 * @return
	 */
	 
	@RequestMapping("/admin/viewrecommend")
	public ModelAndView selectByTags(HttpSession session,HttpServletRequest request){	
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/admin/viewrecommend");;
		return mav;
	}
	
	/**
	 * 查询标签列表
	 * @param session
	 * @param response
	 * @param aoData
	 * @param userId
	 */
	@RequestMapping("/admin/getmetooByrecommend")
	public void getMetooByTags(HttpSession session,HttpServletResponse response,String aoData) {
		TXtUser au = (TXtUser) session.getAttribute(Me2Constants.LOGIN_SESSION_DATANAME);
		
		ArrayList jsonarray = (ArrayList)JsonUtil.Decode(aoData);
	    String sEcho = null;
	    int iDisplayStart = 0; // 起始索引
	    int iDisplayLength = 0; // 每页显示的行数
	   
	   
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
	    
	    example.setOrderByClause("sort");
	    example.setSord("desc");
	   example.setDistinct(true);
	    example.setMysqlOffset(iDisplayStart);
	    example.setMysqlLength(iDisplayLength);
	    //是否有查看所有人权限

//		if(!"-1".equals(userId)){
//			example.put("userId", userId);
//		}
//	   
	    String s="";
	    int count = recommendService.countByParams(example);
	    List<Recommend> metoo = recommendService.selectByParams(example);
	    
	    //拼接翻页数据
	    List list = new ArrayList();
	    
		for(Recommend obj : metoo){
		//判断如果图片的pid相同就去重
			
				
					
					String[] d={obj.getId().toString(),obj.getTagId().toString(),obj.getTagname(),obj.getHits().toString(),obj.getAcount().toString()
							,obj.getMefriends().toString(),
							DateUtil.dateFormatToString(obj.getLasttime(), "yyyy-MM-dd HH:mm:ss"),""
							,s,s};
					list.add(d);
				
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
	 * 删除标签
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/delrecommend")
	public ModelAndView delTags(HttpSession session,String id){
	
		Recommend pc=recommendService.selectByPrimaryKey(Integer.valueOf(id));
		 Integer  s  = pc.getSort();
		    Criteria example = new Criteria();
		    List<Recommend> pclist = recommendService.selectByParams(example);
		    for(Recommend p:pclist){
		    	if(p.getSort()>s){
		    		
		    		Integer b = p.getSort();
		    		p.setSort(b-1);
		    		p.setId(p.getId());
		    		p.setAcount(p.getAcount());
		    		p.setHits(p.getHits());
		    		p.setLasttime(p.getLasttime());
		    		p.setMefriends(p.getMefriends());
		    		p.setTagname(p.getTagname());
		    		p.setTagId(p.getTagId());
		    		p.setQiniukey(p.getQiniukey());
		    	
		    		recommendService.updateByPrimaryKey(p);
		    	}
		    }
		
		recommendService.deleteByPrimaryKey(Integer.valueOf(id));

		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/viewrecommend");
		return mav;
	}


/**
 * 精选集合当中顺序上调的方法
 * @param session
 * @param pid
 * @return
 */
@RequestMapping("admin/uprecommend")
public  ModelAndView upcommend(HttpSession session,String id){
	
	ModelAndView mav = new ModelAndView();
	Recommend list=recommendService.selectByPrimaryKey(Integer.valueOf(id));
	Integer a = list.getSort();
	Integer b = null;
	if(a==1){
		mav.setViewName("/admin/viewrecommend");
	}else{
	Criteria example = new Criteria();
	example.put("sort", a-1);
	List<Recommend> taglist = recommendService.selectByParams(example);
	for(Recommend tagslist:taglist){
		if(tagslist.getSort().equals(a-1)){
			b = tagslist.getId();
			break;
		}
	}
	Recommend list1 = recommendService.selectByPrimaryKey(b);
	list1.setSort(a);
	recommendService.updateByPrimaryKeySelective(list1);
	list.setSort(a-1);
	recommendService.updateByPrimaryKeySelective(list);
	

	mav.setViewName("/admin/viewrecommend");
	}
	return mav;
}

/**
 * 精选集合当中顺序下调的方法
 * @param session
 * @param pid
 * @return
 */
@RequestMapping("admin/downrecommend")
public  ModelAndView downcommend(HttpSession session,String id){
	ModelAndView mav = new ModelAndView();
	Recommend list=recommendService.selectByPrimaryKey(Integer.valueOf(id));
	Integer a = list.getSort();
	Integer b = null;
	if(a==1){
		mav.setViewName("/admin/viewrecommend");
	}else{
	Criteria example = new Criteria();
	example.put("sort", a+1);
	List<Recommend> taglist = recommendService.selectByParams(example);
	for(Recommend tagslist:taglist){
		if(tagslist.getSort().equals(a+1)){
			b = tagslist.getId();
			break;
		}
	}
	if(b==null){
		mav.setViewName("/admin/viewrecommend");
	}else{
	Recommend list1 = recommendService.selectByPrimaryKey(b);
	list1.setSort(a);
	recommendService.updateByPrimaryKeySelective(list1);
	list.setSort(a+1);
	recommendService.updateByPrimaryKeySelective(list);
	}
	
	mav.setViewName("/admin/viewrecommend");
	}
	return mav;
}
}

