package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.common.constant.Me2Constants;
import com.lettucetech.me2.common.utils.DateUtil;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.pojo.Taglist;
import com.lettucetech.me2.pojo.Tagshot;
import com.lettucetech.me2.service.TaglistService;
import com.lettucetech.me2.service.TagshotService;
import com.lettucetech.me2.web.form.DataTablePaginationForm;

@Controller
public class TagsListController {
	@Autowired
	private TaglistService tagListService;
	@Autowired
	private TagshotService tagshotService;
	/**
	 * 跳转到标签集合页面
	 *
	 * @param session
	 * @param 
	 * @return
	 */
	 
	@RequestMapping("/admin/viewList")
	public ModelAndView selectByTags(HttpSession session){	
		session.setAttribute("taglist","taglist");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/viewList");;
		return mav;
	}
	/**
	 * 跳转到修改的页面
	 * @param session
	 * @return
	 */
	@RequestMapping("/admin/viewupdateList")
	public ModelAndView updateByTags(HttpSession session,String id){		
		
		Taglist taglist = tagListService.selectByPrimaryKey(Integer.valueOf(id));
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("taglist",taglist);
		mav.setViewName("/admin/updateList");;
		return mav;
	}

	/**
	 * 跳转到添加页面
	 * @param session
	 * @return
	 */
	@RequestMapping("/admin/viewaddList")
	public ModelAndView addByTags(HttpSession session){		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/addList");;
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
	public void getMetooByTags(HttpSession session,HttpServletResponse response,String aoData,Taglist taglist) {
	TXtUser au = (TXtUser) session.getAttribute(Me2Constants.LOGIN_SESSION_DATANAME);
	 
	ArrayList jsonarray = (ArrayList)JsonUtil.Decode(aoData);
    String sEcho = null;
    int iDisplayStart = 0; // 起始索引
    int iDisplayLength = 0; // 每页显示的行数
 
    for (int i = 0; i < jsonarray.size(); i++) {
    	HashMap obj = (HashMap) jsonarray.get(i);
    	 if (obj.get("name").equals("sEcho"))
             sEcho = obj.get("value").toString();
  
//         if (obj.get("name").equals("iDisplayStart"))
//             iDisplayStart = Integer.parseInt(obj.get("value").toString());
//  
//         if (obj.get("name").equals("iDisplayLength"))
//             iDisplayLength = Integer.parseInt(obj.get("value").toString());
    }
    Criteria example = new Criteria();
    example.put("taglist", taglist);
    example.setOrderByClause("num");
    example.setSord("desc");
//    example.setMysqlOffset(iDisplayStart);
//    example.setMysqlLength(iDisplayLength);

    int count = tagListService.countByParams(example);
    List<Taglist> metoo = tagListService.selectByParams(example);
    
	    //拼接翻页数据
	    
	    //拼接翻页数据
	    List list = new ArrayList();
		for(Taglist obj : metoo){
			
			//String[] d={obj.getId().toString(),obj.getTitle(),obj.getNum()};
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

	@RequestMapping(value="/admin/saveList",method={RequestMethod.POST})
	public ModelAndView updateList(HttpSession session,String id,String title,String qiniukey,String num){
		
	
		Taglist list=new Taglist();
		
//		Date date=new Date();
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String time=sdf.format(date);
		list.setId(Integer.valueOf(id));
		list.setTitle(title);
		list.setQiniukey(qiniukey);
		list.setNum(num);
		
		tagListService.updateByPrimaryKeySelective(list);
		
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
//		 Criteria example = new Criteria();
//		 example.put("id", id);
//		 tagListService.deleteByParams(example);
		 
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/viewList");
		return mav;
	}
	/**
	 * 将集合添加到数据库
	 * @param session
	 * @param title
	 * @param key
	 * @param num
	 * @return
	 */
	@RequestMapping(value="/admin/insertList",method={RequestMethod.POST})
	public ModelAndView addList(HttpSession session,String id,String title,String qiniukey,String num){
		Taglist list =new Taglist();
//		list.setId(Integer.valueOf(id));
		list.setTitle(title);
		list.setQiniukey(qiniukey);
		list.setNum(num);
		tagListService.insertSelective(list);
		ModelAndView mav = new ModelAndView();
	
		mav.setViewName("redirect:/admin/viewList");
		return mav;
		
//	}
//	/**
//	 * 显示添加页面
//	 * @param session
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping("/admin/viewaddList")
//	public ModelAndView viewaddList(HttpSession session,String id){
//		
//		Taglist list =tagListService.selectByPrimaryKey(Integer.valueOf(id));
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("list",list);
//		//mav.addObject("domain", Me2Constants.QINIUPUBLICDOMAIN);
//		mav.setViewName("/admin/addList");
//		return mav;
//	}

}
}