package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.common.constant.Me2Constants;
import com.lettucetech.me2.common.utils.DateUtil;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.pojo.Tagsconnection;
import com.lettucetech.me2.pojo.Tagshot;
import com.lettucetech.me2.service.PictureService;
import com.lettucetech.me2.service.PicturehotService;
import com.lettucetech.me2.service.PicturerecommendService;
import com.lettucetech.me2.service.TXtUserService;
import com.lettucetech.me2.service.TaglistService;
import com.lettucetech.me2.service.TagsconnectionService;
import com.lettucetech.me2.service.TagshotService;
import com.lettucetech.me2.web.form.DataTablePaginationForm;

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
		session.setAttribute("str",str);
		ModelAndView mav = new ModelAndView();
		
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
	    example.setOrderByClause("last_time");
	    example.setSord("desc");
	   example.setDistinct(true);
	    example.setMysqlOffset(iDisplayStart);
	    example.setMysqlLength(iDisplayLength);
	    //是否有查看所有人权限

//		if(!"-1".equals(userId)){
//			example.put("userId", userId);
//		}
//	    
	    int count = tagshotService.countByParams(example);
	    List<Tagshot> metoo = tagshotService.selectByParams(example);
	    
	    //拼接翻页数据
	    List list = new ArrayList();
	    
		for(Tagshot obj : metoo){
			//String aurl = Me2Constants.QINIUPUBLICDOMAIN+"/"+obj.getQiniukey();
			
//			String burl="";
//			String type="";
			//如果有B面
//			if(obj.getPicture().getBpicture()!=null){
//				if(obj.getPicture().getBpicture().getType().equals("1")){
//					burl = QiniuUtil.getDownUrl(obj.getPicture().getBpicture().getQiniukey());
//				}else{
//					burl = obj.getPicture().getBpicture().getQiniukey();
//				}
//				type = obj.getPicture().getBpicture().getType();
//			}
//			String[] d = {obj.getPicture().getPid().toString(),obj.getPicture().getCustomer().getUsername(),aurl,burl,type,
//					obj.getPicture().getTags(),obj.getUser().getName(),
			String[] d={obj.getId().toString(),obj.getTag(),obj.getHits().toString(),obj.getAcount().toString(),obj.getMefriends().toString(),
					DateUtil.dateFormatToString(obj.getLastTime(), "yyyy-MM-dd HH:mm:ss"),""};
			
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
	public ModelAndView add(HttpSession session,String id){
		String str =(String)session.getAttribute("str");
		
		Tagsconnection conn=new Tagsconnection();
		conn.setTagsId(Integer.valueOf(id));
		conn.setTagslistId(str);
	
		tagsconnectionService.insert(conn);
		ModelAndView mav = new ModelAndView();
		mav.addObject("conn",conn);
		mav.setViewName("redirect:/admin/viewselective");
		return mav;
		
	}
	
	
}
