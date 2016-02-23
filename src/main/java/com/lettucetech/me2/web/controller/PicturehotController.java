package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.lettucetech.me2.pojo.TXtMenu;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.pojo.Tagsconnection;
import com.lettucetech.me2.service.PicturehotService;
import com.lettucetech.me2.service.TXtUserService;
import com.lettucetech.me2.service.TagsconnectionService;
import com.lettucetech.me2.web.form.DataTablePaginationForm;


@Controller
public class PicturehotController {
	private static final Logger logger = Logger.getLogger(PicturehotController.class);

	@Autowired
	private TXtUserService usi;
	@Autowired
		private PicturehotService pictureHotService;
	@Autowired
	private TagsconnectionService tagsconnectionService;
		
	/**
	 * 跳转查看热门标签帖页面
	 * @param session
	 * @return
	 */
	@RequestMapping("/admin/picturehot")
	public ModelAndView pcase(HttpSession session) {
		List<TXtUser> users = new ArrayList<TXtUser>();
		if(checkPermission(session,"viewall")){
			Criteria example = new Criteria();
			users = usi.selectByParams(example);
			//增加全部的选项
			TXtUser u=new TXtUser();
			u.setUserId(-1);
			u.setName("全部");
			users.add(0, u);
		}else{
			users.add((TXtUser)session.getAttribute(Me2Constants.LOGIN_SESSION_DATANAME));
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("users", users);
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
	@RequestMapping("/admin/getmetoo/picturehot")
	public void getMetooByPictureHot(HttpSession session,HttpServletResponse response,String aoData,String userId) {
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
	    example.setMysqlOffset(iDisplayStart);
	    example.setMysqlLength(iDisplayLength);
	    example.put("state", "0");
	    
	    //是否有查看所有人权限

		if(!"-1".equals(userId)){
			example.put("userId", userId);
		}
	    
	    int count = pictureHotService.countByParams(example);
	    List<Tagsconnection> metoo = tagsconnectionService.selectByParams(example);
	    
	    //拼接翻页数据
	    List list = new ArrayList();
		for(Tagsconnection obj : metoo){
			String aurl = Me2Constants.QINIUPUBLICDOMAIN+"/"+obj.getTagshot().getQiniukey();
			String[] d = {obj.getTagsId().toString(),obj.getTagshot().getTag(),aurl,obj.getTagshot().getAcount().toString(),
					obj.getTagshot().getHits().toString(),obj.getTagshot().getMefriends().toString(),obj.getTagshot().getQiniukey(),
					DateUtil.dateFormatToString(obj.getTagshot().getLastTime(), "yyyy-MM-dd HH:mm:ss"),""};
			
			
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
	private boolean checkPermission(HttpSession session,String code){
		List<TXtMenu> menuList = (List<TXtMenu>) session.getAttribute(Me2Constants.LOGIN_USER_MENUS);
		boolean flag = false;
		if(menuList!=null && menuList.size()>0){
			for (TXtMenu tXtMenu : menuList) {
				if(code.equals(tXtMenu.getCode())){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
//	/**
//	 *  将pid,taglist_id添加到Picturehot表中
//	 * @param session
//	 * @param id
//	 * @param pid
//	 * @param taglist_id
//	 * @return
//	 */
//	
//	@RequestMapping("/admin/insertpicturehot")
//	public ModelAndView addList(HttpSession session,String id,String pid,String taglist_id){
//		
////		 Criteria example = new Criteria();
////		example.put("taglist", taglist);
//		Picturehot hot=new Picturehot();
//		hot.setId(Integer.valueOf(id));
//		hot.setPid(Integer.valueOf(pid));
//		hot.setTagslistId(Integer.valueOf(taglist_id));
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("/admin/picturehot");
//		return mav;
//		
//	}
}
