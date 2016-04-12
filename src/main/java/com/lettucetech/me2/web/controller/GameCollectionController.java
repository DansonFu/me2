package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.lettucetech.me2.common.constant.Me2Constants;
import com.lettucetech.me2.common.pojo.RestfulResult;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.common.utils.MD5;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Customer;
import com.lettucetech.me2.pojo.Gamecomment;
import com.lettucetech.me2.service.GamecommentService;
import com.lettucetech.me2.web.form.DataTablePaginationForm;
@Controller
public class GameCollectionController {

	@Autowired
	private GamecommentService gamecommentService;
	
	@RequestMapping(value="/othergame",method=RequestMethod.POST)
	public ModelAndView othergame(HttpSession session, String customerId, String comments) {
		Gson gson=new Gson();
		Gamecomment g = new Gamecomment();
		g.setCustomerId(Integer.valueOf(customerId));
		g.setComments(comments);
		gamecommentService.insert(g);
		gson.toJson(g);
		RestfulResult result = new RestfulResult();
		result.setSuccess(true);
		result.setObj(g);
		ModelAndView mav = new ModelAndView();
		mav.addObject(result);
		
		return mav;
		
	}
	
	
	
	/**
	 * 跳转游戏评论页面
	 * @return
	 */
	@RequestMapping("/admin/showgameview")
	public ModelAndView showcommondPicture(){
//		Criteria example = new Criteria();
//		example.setOrderByClause("creat_time");
//		example.setSord("desc");
//		List<Picture> ps = pictureService.selectByParams(example);
		
		
		ModelAndView mav = new ModelAndView();
		//mav.addObject(ps);
		mav.setViewName("/admin/othergameview");
		return mav;
		
	}
	/**
	 * 显示gamecomment表的信息
	 * @param session
	 * @return
	 */
	@RequestMapping( "/admin/gamecommentmetoo")
	public void  getCommendMetoo(HttpSession session,HttpServletResponse response,String aoData){
		
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
//		example.setOrderByClause("sort");
//		example.setSord("desc");
		//example.put("picture","pid" );
	    example.setMysqlOffset(iDisplayStart);
	    example.setMysqlLength(iDisplayLength);
		 int count = gamecommentService.countByParams(example);
		List<Gamecomment> games = gamecommentService.selectByParams(example);
		
		
		List list = new ArrayList();
		String aa ;
		for(Gamecomment g : games){
//			if(pcs.getPeriod()!=null){
//				aa = DateUtil.dateFormatToString(pcs.getPeriod(),"yyyy-MM-dd HH:mm:ss");
//			}else{
//				aa="";
//			}
			String[] d = {g.getId().toString(),g.getCustomerId().toString(),g.getComments()};
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
	
}
