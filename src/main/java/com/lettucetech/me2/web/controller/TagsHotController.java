package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
//import com.lettucetech.me2.pojo.Recommend;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.pojo.Tagsconnection;
import com.lettucetech.me2.pojo.Tagshot;
import com.lettucetech.me2.service.PictureService;
import com.lettucetech.me2.service.PicturehotService;
import com.lettucetech.me2.service.PicturerecommendService;
//import com.lettucetech.me2.service.RecommendService;
import com.lettucetech.me2.service.TXtUserService;
import com.lettucetech.me2.service.TaglistService;
import com.lettucetech.me2.service.TagsconnectionService;
import com.lettucetech.me2.service.TagshotService;
import com.lettucetech.me2.web.form.DataTablePaginationForm;
import com.qiniu.util.Json;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class TagsHotController {

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
	 
	@RequestMapping("/admin/viewTags")
	public ModelAndView selectByTags(HttpSession session,HttpServletRequest request){	
		 
		String str = request.getParameter("taglist");
		String s= request.getParameter("search");
		String flg=request.getParameter("flag");
		String flag="";
		if(flg==null){
			
		 flag="1";
		}else if(flg!=null){
			flag=flg;
		}
		session.setAttribute("str",str);
		session.setAttribute("flag",flag);
		ModelAndView mav = new ModelAndView();
		//mav.addObject("flag",flag);
		mav.addObject("svalue",s);
		
		mav.setViewName("/admin/viewTags");;
		return mav;
	}
	
	/**
	 * 查询标签列表
	 * @param session
	 * @param response
	 * @param aoData
	 * @param userId
	 */
	@RequestMapping("/admin/getmetooByTags")
	public void getMetooByTags(HttpSession session,HttpServletResponse response,String aoData,String font,String searchid) {
		TXtUser au = (TXtUser) session.getAttribute(Me2Constants.LOGIN_SESSION_DATANAME);
		
		 
			ArrayList jsonarray = (ArrayList)JsonUtil.Decode(aoData);
			  
			 int iDisplayStart = 0; // 起始索引
			 int iDisplayLength = 0;
			 String sEcho=null;
			  
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
			 
			 List list = new ArrayList();
			 if(("1").equals(font)){
					example.setOrderByClause("id");
					example.setSord("asc");
					
				}else if(("2").equals(font)){
					example.setOrderByClause("hits");
					example.setSord("asc");
					
				}else if(("3").equals(font)){
					example.setOrderByClause("acount");
					example.setSord("asc");
					
				}else if(("4").equals(font)){
					example.setOrderByClause("mefriends");
					example.setSord("asc");
				
				}else if(("5").equals(font)){
					example.setOrderByClause("last_time");
					example.setSord("asc");
					
				}
			  
			 if(!"".equals(searchid)){
				 example.put("tag", searchid);
			 }
				 
			 int count = tagshotService.countByParams(example);
				 List<Tagshot> metoo = tagshotService.selectByParams(example);
				 
				 //拼接翻页数据
				
				 String s="";
				 for(Tagshot obj : metoo){
					 //判断如果图片的pid相同就去重
					 if(searchid.contains(obj.getTag())){
					 
					 String[] d={obj.getId().toString(),obj.getTag(),obj.getHits().toString(),obj.getAcount().toString(),obj.getMefriends().toString(),
							 DateUtil.dateFormatToString(obj.getLastTime(), "yyyy-MM-dd HH:mm:ss"),"",s,s};
					 list.add(d);
					 }else{
						 String[] d={obj.getId().toString(),obj.getTag(),obj.getHits().toString(),obj.getAcount().toString(),obj.getMefriends().toString(),
								 DateUtil.dateFormatToString(obj.getLastTime(), "yyyy-MM-dd HH:mm:ss"),"",s,s};
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
	 * 删除标签
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/delTags")
	public ModelAndView delTags(HttpSession session,String id){
	
		tagshotService.deleteByPrimaryKey(Integer.valueOf(id));
//		 Criteria example = new Criteria();
//		 example.put("id", id);
//		 tagshotService.deleteByParams(example);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/viewTags");
		return mav;
	}
	/**
	 *  添加标签,当点击viewTags 页面上的添加按钮时,将标签的id保存到tagsconnection表中
	 * @param session
	 *
	 */
	
	
	@RequestMapping(value="/admin/add")
	public ModelAndView add(HttpSession session,HttpServletRequest request){
		String[] ids = request.getParameterValues("checkname");

	    if (null != ids && !"".equals(ids.toString())){
	        for(int i = 0 ; i < ids.length; i++ ){ // 将ids 字符串按逗号分隔为 字符串数组
	           
	            List<String> list = new ArrayList<String>();
	           
	        }
	    }
		
		String str =(String)session.getAttribute("str");

		Tagsconnection conn=new Tagsconnection();
		
			// conn.setTagsId(c);
			 conn.setTagslistId(str);
			 
			 tagsconnectionService.insert(conn);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("conn",conn);
		mav.setViewName("redirect:/admin/viewselective");
		return mav;
		
	}
	@RequestMapping(value="/admin/addtag")
	public ModelAndView addtag(HttpSession session,HttpServletRequest request){
		String[] tagshot = request.getParameterValues("name");
		String cate="";
		for(int i=0;i<tagshot.length;i++)
		{
		    cate+=tagshot[i]+" ";
		}
		String str =(String)session.getAttribute("str");
		
		Tagsconnection conn=new Tagsconnection();
		
		
			 
			// conn.setTagsId(tagid);
			 conn.setTagslistId(str);
			 
			 tagsconnectionService.insert(conn);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("conn",conn);
		mav.setViewName("redirect:/admin/viewselective");
		return mav;
		
	}
	
	/**
	 * 刷新
	 * @param session
	 * @param pid
	 * @param taglist_id
	 * @return
	 */
	@RequestMapping("/admin/viewflash")
	public ModelAndView submit(HttpSession session){
		
		  Map<String, Object> map = new HashMap<String, Object>();
		  map.put("str3",",");
		 
		ModelAndView mav = new ModelAndView();
		mav.addObject(map);
		mav.setViewName("redirect:/admin/viewTags");
		return mav;
	}
}
/**
* 搜索标签
* 
*/
//@RequestMapping(value="/admin/searchtag")
//public ModelAndView search(HttpSession session,HttpServletRequest request){
//	//String str =(String)session.getAttribute("str");
//	
//	String str=request.getParameter("searcher");
//	Criteria example = new Criteria();
//	
//	example.put("str", str);
//	tagshotService.selectByParams(example);
//	ModelAndView mav = new ModelAndView();
//	mav.addObject("str",str);
//	mav.setViewName("redirect:/admin/viewTags");
//	return mav;
//}
//}
//@RequestMapping(value="/admin/viewsearch")
//public ModelAndView searchTag(HttpSession session,HttpServletRequest request){
//	//String str =(String)session.getAttribute("str");
//	
//	String str=request.getParameter("search");
//	Criteria example = new Criteria();
//	
//	example.put("str", str);
//	tagshotService.selectByParams(example);
//	ModelAndView mav = new ModelAndView();
//	mav.addObject("str",str);
//	mav.setViewName("redirect:/admin/searchTag");
//	return mav;
//}
///**
//*   修改标签
//* @param session
//* @param tag
//* @param count
//* @param hits
//* @param mefriends
//* @param key
//* @param lastTime
//* @return
//*/
//
//
//@RequestMapping("/admin/updateTags")
//public ModelAndView updateTags(HttpSession session,String tag,String acount,String hits,String mefriends
//		,String key,Timestamp lastTime  ){
//	
//	
//	Tagshot list=new Tagshot();
//	
//	list.setTag(tag);
//	list.setAcount(Integer.valueOf(acount));
//	list.setHits(Integer.valueOf(hits));
//	list.setMefriends(Integer.valueOf(mefriends));
//	list.setQiniukey(key);
//	list.setLastTime(lastTime);
//	
//	
//	tagshotService.updateByPrimaryKeySelective(list);
//	
//	ModelAndView mav = new ModelAndView();
//	mav.setViewName("redirect:/admin/viewTags");
//	return mav;
//}