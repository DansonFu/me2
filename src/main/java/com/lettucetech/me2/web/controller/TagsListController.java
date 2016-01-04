package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.common.constant.Me2Constants;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.common.utils.QiniuUtil;
import com.lettucetech.me2.pojo.Comment;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Game;
import com.lettucetech.me2.pojo.Picture;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.pojo.Taglist;
import com.lettucetech.me2.service.PictureService;
import com.lettucetech.me2.service.PicturehotService;
import com.lettucetech.me2.service.PicturerecommendService;
import com.lettucetech.me2.service.TaglistService;
import com.lettucetech.me2.web.form.DataTablePaginationForm;

@Controller
public class TagsListController {
	@Autowired
	private PictureService pictureService;
	@Autowired
	private PicturehotService picturehotService;
	@Autowired
	private PicturerecommendService picturerecommendService;
	@Autowired
	private TaglistService tagListService;
	
	/**
	 * 跳转到标签集合页面
	 *
	 * @param session
	 * @param 
	 * @return
	 */
	 
	@RequestMapping("/admin/viewList")
	public ModelAndView selectByTags(HttpSession session){		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/viewList");;
		return mav;
	}
	
	/**
	 * 查询集合列表
	 * @param session
	 * @param response
	 * @param aoData
	 * @param userId
	 */
	@RequestMapping("/admin/getmetoo/connect")
	public void getMetooByTags(HttpSession session,HttpServletResponse response,String aoData,String userId) {
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
	    example.setOrderByClause("num");
	    example.setSord("desc");
	    example.setMysqlOffset(iDisplayStart);
	    example.setMysqlLength(iDisplayLength);
	    //是否有查看所有人权限

//		if(!"-1".equals(userId)){
//			example.put("userId", userId);
//		}
//	    
	    int count = tagListService.countByParams(example);
	    List<Taglist> metoo = tagListService.selectByParams(example);
	    
	    //拼接翻页数据
	    List list = new ArrayList();
		for(Taglist obj : metoo){
			
			String[] d={obj.getId().toString(),obj.getTitle(),obj.getNum()};
			
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
	 *  修改集合
	 * @param session
	 * @param title
	 * @param key
	 * @return
	 */

	@RequestMapping("/admin/updateList")
	public ModelAndView updateList(HttpSession sessio,String id,String title,String key,String num){
		
	
		Taglist list=new Taglist();
		list.setId(Integer.valueOf(id));
		list.setTitle(title);
		list.setQiniukey(key);
		list.setNum(num);
		
		tagListService.updateByPrimaryKey(list);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/viewList");
		return mav;
	}
	/**
	 * 删除集合
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/delList")
	public ModelAndView delList(HttpSession session,String id){
		
		tagListService.deleteByPrimaryKey(Integer.valueOf(id));
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/viewList");
		return mav;
	}
	/**
	 * 添加集合
	 * @param session
	 * @param title
	 * @param key
	 * @param num
	 * @return
	 */
	@RequestMapping("/admin/addList")
	public ModelAndView addList(HttpSession session,String id,String title,String key,String num){
		
		
		Taglist list = new Taglist();
		list.setId(Integer.valueOf(id));
		list.setTitle(title);
		list.setQiniukey(key);
		list.setNum(num);
		tagListService.insert(list);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/viewList");
		return mav;
		
	}
}
