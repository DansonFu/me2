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
import com.lettucetech.me2.service.PictureService;
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
	    

	   example.setDistinct(true);
	    example.setMysqlOffset(iDisplayStart);
	    example.setMysqlLength(iDisplayLength);
	    //是否有查看所有人权限

//		if(!"-1".equals(userId)){
//			example.put("userId", userId);
//		}
//	    
	    int count = recommendService.countByParams(example);
	    List<Recommend> metoo = recommendService.selectByParams(example);
	    
	    //拼接翻页数据
	    List list = new ArrayList();
	    
		for(Recommend obj : metoo){
		//判断如果图片的pid相同就去重
			
				
					
					String[] d={obj.getTagId().toString(),obj.getTagname(),obj.getHits().toString(),obj.getAcount().toString()
							,obj.getMefriends().toString(),
							DateUtil.dateFormatToString(obj.getLasttime(), "yyyy-MM-dd HH:mm:ss"),""
							};
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
//	/**
//	 *   修改标签
//	 * @param session
//	 * @param tag
//	 * @param count
//	 * @param hits
//	 * @param mefriends
//	 * @param key
//	 * @param lastTime
//	 * @return
//	 */
//	
//	
//	@RequestMapping("/admin/updateTags")
//	public ModelAndView updateTags(HttpSession session,String tag,String acount,String hits,String mefriends
//			,String key,Timestamp lastTime  ){
//		
//		
//		Tagshot list=new Tagshot();
//		
//		list.setTag(tag);
//		list.setAcount(Integer.valueOf(acount));
//		list.setHits(Integer.valueOf(hits));
//		list.setMefriends(Integer.valueOf(mefriends));
//		list.setQiniukey(key);
//		list.setLastTime(lastTime);
//		
//		
//		tagshotService.updateByPrimaryKeySelective(list);
//		
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("redirect:/admin/viewTags");
//		return mav;
//	}
	/**
	 * 删除标签
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/delrecommend")
	public ModelAndView delTags(HttpSession session,String id){
	
		recommendService.deleteByPrimaryKey(Integer.valueOf(id));
//		 Criteria example = new Criteria();
//		 example.put("id", id);
//		 tagshotService.deleteByParams(example);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/viewrecommend");
		return mav;
	}

//	/**
//	 * 搜索标签
//	 * 
//	 */
////	@RequestMapping(value="/admin/searchtag")
////	public ModelAndView search(HttpSession session){
////		//String str =(String)session.getAttribute("str");
////		
////		String str=request.getParameter("search");
////		Criteria example = new Criteria();
////		
////		example.put("str", str);
////		tagshotService.selectByParams(example);
////		ModelAndView mav = new ModelAndView();
////		mav.addObject("str",str);
////		mav.setViewName("redirect:/admin/searchTag");
////		return mav;
////}
//	@RequestMapping(value="/admin/viewsearch")
//	public ModelAndView searchTag(HttpSession session,HttpServletRequest request){
//		//String str =(String)session.getAttribute("str");
//		
//		String str=request.getParameter("search");
//		Criteria example = new Criteria();
//		
//		example.put("str", str);
//		tagshotService.selectByParams(example);
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("str",str);
//		mav.setViewName("redirect:/admin/searchTag");
//		return mav;
//}
/**
 * 精选集合当中顺序上调的方法
 * @param session
 * @param pid
 * @return
 */
@RequestMapping("admin/uprecommend")
public  ModelAndView upcommend(HttpSession session,String id){
	Recommend list=recommendService.selectByPrimaryKey(Integer.valueOf(id));
	Integer a = list.getSort();
	Integer b = null;
	
	Criteria example = new Criteria();
	example.put("font", a-1);
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
	
	//Integer a = prec.getSort();
	
//	Picturerecommend pc = new Picturerecommend();
//	
//	pc.setPid(pid);
//	
//	pc.setSort(sort+1);
//	picurerecommendService.insert(pc);
	ModelAndView mav = new ModelAndView();
	mav.setViewName("/admin/viewrecommend");
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
	Recommend list=recommendService.selectByPrimaryKey(Integer.valueOf(id));
	Integer a = list.getSort();
	Integer b = null;
	
	Criteria example = new Criteria();
	example.put("font", a+1);
	List<Recommend> taglist = recommendService.selectByParams(example);
	for(Recommend tagslist:taglist){
		if(tagslist.getSort().equals(a+1)){
			b = tagslist.getId();
			break;
		}
	}
	Recommend list1 = recommendService.selectByPrimaryKey(b);
	list1.setSort(a);
	recommendService.updateByPrimaryKeySelective(list1);
	list.setSort(a+1);
	recommendService.updateByPrimaryKeySelective(list);
//	Picturerecommend pc = new Picturerecommend();
//	
//	pc.setPid(pid);
//	
//	pc.setSort(sort+1);
//	picurerecommendService.insert(pc);
	ModelAndView mav = new ModelAndView();
	mav.setViewName("/admin/viewrecommend");
	return mav;
}
}

