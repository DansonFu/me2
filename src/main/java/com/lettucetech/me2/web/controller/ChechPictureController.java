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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.common.constant.Me2Constants;
import com.lettucetech.me2.common.pojo.RestfulResult;
import com.lettucetech.me2.common.utils.DateUtil;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.common.utils.QiniuUtil;
import com.lettucetech.me2.common.utils.StringUtil;
import com.lettucetech.me2.common.utils.QiniuUtil.MyRet;
import com.lettucetech.me2.pojo.Comment;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Customer;
import com.lettucetech.me2.pojo.Game;
import com.lettucetech.me2.pojo.Picture;
import com.lettucetech.me2.pojo.Picturerecommend;
import com.lettucetech.me2.pojo.TXtMetoo;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.service.PictureService;
import com.lettucetech.me2.service.PicturerecommendService;
import com.lettucetech.me2.service.TXtMetooService;
import com.lettucetech.me2.web.form.DataTablePaginationForm;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
@Controller
public class ChechPictureController {
	@Autowired
	private PictureService pictureService;
	@Autowired
	private TXtMetooService metooService;
	@Autowired
	private PicturerecommendService picurerecommendService;
	/**
	 * 跳转审核蜜图页面,为了给推荐表确定那个蜜图需要添加到推荐表当中。
	 * @return
	 */
	@RequestMapping("/admin/commendcheckmetoo")
	public ModelAndView commondPicture(){
//		Criteria example = new Criteria();
//		example.setOrderByClause("creat_time");
//		example.setSord("desc");
//		List<Picture> ps = pictureService.selectByParams(example);
		
		
		ModelAndView mav = new ModelAndView();
		//mav.addObject(ps);
		mav.setViewName("/admin/checkmetoo");
		return mav;
		
	}
	
	/**
	 * 以创建时间倒序排列查询蜜图列表，确定推荐蜜图到推荐表。
	 * @param session
	 * @param response
	 * @param aoData
	 */
	@RequestMapping("/amdin/check/metoo")
	public void getMetoo(HttpSession session,HttpServletResponse response,String aoData) {
		//TXtUser au = (TXtUser) session.getAttribute(Me2Constants.LOGIN_SESSION_DATANAME);
		
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
	    example.setOrderByClause("creat_time");
	    example.setSord("desc");
	    example.put("front", "a");
	    example.setMysqlOffset(iDisplayStart);
	    example.setMysqlLength(iDisplayLength);

	    //是否有查看所有人权限

//		if(!"-1".equals(userId)){
//			example.put("userId", userId);
//		}
//	    
    int count = pictureService.countByParams(example);
    List<Picture> ps = pictureService.selectByParams(example);
	    
	    //拼接翻页数据
	    List list = new ArrayList();
		for(Picture obj : ps){
			String aurl = Me2Constants.QINIUPUBLICDOMAIN+"/"+obj.getQiniukey();
			String burl="";
			String type="";
			//如果有B面
			if(obj.getBpicture()!=null){
				if(obj.getBpicture().getType().equals("1")){
					burl = QiniuUtil.getDownUrl(obj.getBpicture().getQiniukey());
				}else{
					burl = obj.getBpicture().getQiniukey();
				}
				type = obj.getBpicture().getType();
			}
//			String[] d = {obj.getPid().toString(),obj.getBpicture().getCustomer().getUsername(),aurl,burl,type,
//					obj.getTags(),obj.getMood(),
//					DateUtil.dateFormatToString(obj.getBpicture().getCreatTime(), "yyyy-MM-dd HH:mm:ss"),""};
			String[] d = {obj.getPid().toString(),obj.getCustomerId().toString(),aurl,burl,type,
					obj.getTags(),obj.getMood(),
					DateUtil.dateFormatToString(obj.getCreatTime(), "yyyy-MM-dd HH:mm:ss"),""};
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
	 * 跳转蜜图推荐页面，将推荐蜜图表中的数据呈现，为了方便推荐蜜图的管理。
	 * @return
	 */
	@RequestMapping("/admin/showcommendcheck")
	public ModelAndView showcommondPicture(){
//		Criteria example = new Criteria();
//		example.setOrderByClause("creat_time");
//		example.setSord("desc");
//		List<Picture> ps = pictureService.selectByParams(example);
		
		
		ModelAndView mav = new ModelAndView();
		//mav.addObject(ps);
		mav.setViewName("/admin/addCommendMetoo");
		return mav;
		
	}
	/**
	 * 显示推荐蜜图表的信息，并进行有效的管理
	 * @param session
	 * @return
	 */
	@RequestMapping( "/admin/showcommendmetoo")
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
		example.setOrderByClause("sort");
		//example.put("picture","pid" );
	    example.setMysqlOffset(iDisplayStart);
	    example.setMysqlLength(iDisplayLength);
		 int count = picurerecommendService.countByParams(example);
		List<Picturerecommend> pc = picurerecommendService.selectByParams(example);
		
		
		List list = new ArrayList();
		String aa ;
		for(Picturerecommend pcs : pc){
			String aurl = Me2Constants.QINIUPUBLICDOMAIN+"/"+pcs.getPicture().getQiniukey();
			if(pcs.getPeriod()!=null){
				aa = pcs.getPeriod().toString();
			}else{
				aa="";
			}
			String[] d = {pcs.getPid().toString(),aurl,pcs.getSort().toString(),
					aa,""};
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
	 * 修改推荐蜜图时的载入页面，即查询修改的推荐蜜图信息
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping("/admin/viewcommendmetoo")
	public ModelAndView viewpicture(HttpSession session,Integer id) {
		
		Criteria example = new Criteria();
		example.put("id", id);
		Picturerecommend pct = picurerecommendService.selectByPrimaryKey(id);
//		List<Picturerecommend> pic = picurerecommendService.selectByParams(example);
//
//        List list = new ArrayList();
//        String aa;
//		for(Picturerecommend pc : pic){
//			String aurl = Me2Constants.QINIUPUBLICDOMAIN+"/"+pc.getPicture().getQiniukey();
//			if(pc.getPeriod()!=null){
//				aa = pc.getPeriod().toString();
//			}else{
//				aa="";
//			}
//			String[] d = {pc.getPid().toString(),aurl,pc.getSort().toString(),
//					aa,""};
//			list.add(d);
//		}
		ModelAndView mav = new ModelAndView();
//		mav.addObject("list", list);
		mav.addObject("pct",pct);
		mav.addObject("domain", Me2Constants.QINIUPUBLICDOMAIN);
		mav.setViewName("/admin/menumetoo");
		return mav;
	}
	
	/**
	 * 修改推荐蜜图
	 * @param session
	 * @param sort
	 * @param period
	 * @return
	 */
		@RequestMapping("/admin/updatecommendmetoo")
		public ModelAndView updateCommendMetoo(HttpSession session,String sort,
				String period){
			
			Picturerecommend pc = new Picturerecommend();
			Date date = DateUtil.formatDateFromString(period);
		    pc.setPeriod(date);
		    
			pc.setSort(Integer.valueOf(sort));
			picurerecommendService.updateByPrimaryKey(pc);
			
			
//			if(cid!=null){
//				//修改评论
//				for(int i=0;i<cid.length;i++){
//					String key=null;
//					if(!StringUtil.isNullOrEmpty(file[i].getFileItem().getName())){
//						try {
//							String token = QiniuUtil.uploadToken(Me2Constants.METOOPULIC);
//					        Response res = QiniuUtil.uploadManager.put(file[i].getBytes(), null, token);
//							MyRet ret = res.jsonToObject(MyRet.class); 
//							key = ret.getKey();
//						} catch (QiniuException e) {
//							e.printStackTrace();
//						}
//					}
//					Comment record = new Comment();
//					record.setCommentId(Integer.parseInt(cid[i]));
//					record.setContent(commentContent[i]);
//					record.setQiniukey(key);
//					
//					commentService.updateByPrimaryKeySelective(record);
		
//				}
//			}
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/admin/showcommendcheck");
			return mav;
		}
	
	
	
	/**
	 * 将蜜图pid保存到picturecommend表中
	 * 
	 */
	@RequestMapping("/admin/save/commend")
	public ModelAndView savecommendmetoo(HttpSession session,Integer pid){
	    Criteria  example  = new Criteria();
		 Integer sort = picurerecommendService.countByParams(example);
		Picturerecommend pc = new Picturerecommend();
		pc.setPid(pid);
		
		pc.setSort(sort+1);
		picurerecommendService.insert(pc);
		
		//保存管理员操作记录
//		TXtUser au = (TXtUser) session.getAttribute(Me2Constants.LOGIN_SESSION_DATANAME);
//		TXtMetoo record = new TXtMetoo();
//		record.setCreatTime(new Date());
//		record.setMetoo(pc.getPid());
//		record.setState("0");
//		record.setUserId(au.getUserId());
//		metooService.insertSelective(record);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/showcommendcheck");
		return mav;

	}
	
	/**
	 * 推荐图片当中图片删除的方法
	 * 
	 */
	@RequestMapping("/admin/del/commend")
	public  ModelAndView delcommendmetoo(HttpSession session,Integer pid){
	    Criteria  example  = new Criteria();
	    example.put("pid", pid);
		picurerecommendService.deleteByParams(example);
//		Picturerecommend pc = new Picturerecommend();
//		
//		pc.setPid(pid);
//		
//		pc.setSort(sort+1);
//		picurerecommendService.insert(pc);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/showcommendcheck");
		return mav;
	}

}
