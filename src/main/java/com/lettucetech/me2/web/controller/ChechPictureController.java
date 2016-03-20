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
import com.lettucetech.me2.service.CommentService;
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
	@Autowired
	private CommentService commentService;
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
//	    example.put("del", "0");
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
		for(Picture pp : ps){
			String aurl = Me2Constants.QINIUPUBLICDOMAIN+"/"+pp.getQiniukey();
			String burl="";
			String type="";
			//如果有B面
			if(pp.getBpicture()!=null){
				if("1".equals(pp.getBpicture().getType())){
					burl = QiniuUtil.getDownUrl(pp.getBpicture().getQiniukey());
				}else{
					burl = pp.getBpicture().getQiniukey();
				}
				type = pp.getBpicture().getType();
			}
			String mood = "";
			if(pp.getMood()!=null){
				 mood = pp.getMood();
			}else if(pp.getMood()==null){
				mood = "";
			}
			String bmood = "";
			if(pp.getBpicture()!=null){
				if(pp.getBpicture().getMood() != null){
					bmood = pp.getBpicture().getMood();
				}else if(pp.getBpicture().getMood() == null){
					bmood = "";
				}
			}else{
				bmood = "";
			}
			
			String[] d = {pp.getPid().toString(),pp.getCustomer().getInneruser(),aurl,mood,burl,bmood,type,pp.getTags(),
					DateUtil.dateFormatToString(pp.getCreatTime(), "yyyy-MM-dd HH:mm:ss"),pp.getRecommend(),""};
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
		mav.setViewName("/admin/manageCommendMetoo");
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
				aa = DateUtil.dateFormatToString(pcs.getPeriod(),"yyyy-MM-dd HH:mm:ss");
			}else{
				aa="";
			}
			String[] d = {pcs.getId().toString(),pcs.getPid().toString(),aurl,pcs.getSort().toString(),
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
	public ModelAndView viewpicture(HttpSession session,String id) {
		
//		Criteria example = new Criteria();
//		example.put("id", id);
		Picturerecommend pct = picurerecommendService.selectByPrimaryKey(Integer.valueOf(id));
		
		
		ModelAndView mav = new ModelAndView();
//		mav.addObject("list", list);
		mav.addObject("pct",pct);
		mav.addObject("domain", Me2Constants.QINIUPUBLICDOMAIN);
		mav.setViewName("/admin/manageperiodmetoo");
		return mav;
	}
	
	/**
	 * 将推荐蜜图列表的有效期修改保存到picturecommend表中
	 * 
	 */
	@RequestMapping(value = "/admin/saveperiodmetoo/commend", method ={RequestMethod.POST})
	public ModelAndView saveperiodcommendmetoo(HttpSession session,String period,String id,String pid,String sort){
	    
		
		Picturerecommend pc = new Picturerecommend();
		Date date = DateUtil.formatDateFromString(period,"yyyy-MM-dd HH:mm:ss");
	    pc.setPeriod(date);
	    pc.setPid(Integer.valueOf(pid));
	    pc.setSort(Integer.valueOf(sort));
	    pc.setId(Integer.valueOf(id));
		picurerecommendService.updateByPrimaryKey(pc);
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/admin/showcommendcheck");
			return mav;

	}
	
	/**
	 * 将蜜图pid保存到picturecommend表中,如果图片已经添加过则不用添加
	 * 
	 */
	@RequestMapping("/admin/save/commend")
	public ModelAndView savecommendmetoo(HttpSession session,String pid){
	    
		Picture p = pictureService.selectByPrimaryKey(Integer.valueOf(pid));
		String a = p.getRecommend();
		
		if("0".equals(a)){
			Criteria  example  = new Criteria();
			List<Picturerecommend> list = picurerecommendService.selectByParams(example);
			List<Integer> list1 = new ArrayList<Integer>(); 
			for(Picturerecommend pcs : list){
				Integer b = pcs.getSort();
				list1.add(b);
			}
			Integer sort = 0;
			for(int i=0;i<list1.size();i++){
				if(list1.get(i)>sort){
					sort = list1.get(i);
				}
			}
			Picturerecommend pc = new Picturerecommend();
			pc.setPid(Integer.valueOf(pid));
			
			pc.setSort(sort+1);
			picurerecommendService.insertSelective(pc);
			p.setRecommend("1");
			pictureService.updateByPrimaryKeySelective(p);
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/admin/commendcheckmetoo");
			return mav;
		}else{
			ModelAndView mav = new ModelAndView();
			mav.setViewName("/admin/error");
			return mav;
		}
		//保存管理员操作记录
//		TXtUser au = (TXtUser) session.getAttribute(Me2Constants.LOGIN_SESSION_DATANAME);
//		TXtMetoo record = new TXtMetoo();
//		record.setCreatTime(new Date());
//		record.setMetoo(pc.getPid());
//		record.setState("0");
//		record.setUserId(au.getUserId());
//		metooService.insertSelective(record);
		
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("redirect:/admin/commendcheckmetoo");
//		return mav;

	}
	
	
	/**
	 * 审核蜜图图片当中逻辑删除的方法
	 * 
	 */
	@RequestMapping("/admin/delcheckmetoo/commend")
	public  ModelAndView delcheckdmetoo(HttpSession session,String pid){
		Picture p = pictureService.selectByPrimaryKey(Integer.valueOf(pid));
		if("0".equals(p.getDel())){
			p.setDel("1");
			  pictureService.updateByPrimaryKeySelective(p);
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/admin/commendcheckmetoo");
			return mav;
		}else{
//			p.setDel("1");
//			  pictureService.updateByPrimaryKeySelective(p);
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/admin/commendcheckmetoo");
			return mav;
		}
	}
	
	/**
	 * 滚播图片当中图片删除的方法
	 * 
	 */
	@RequestMapping("/admin/del/commend")
	public  ModelAndView delcommendmetoo(HttpSession session,String id){
		
		
	    Picturerecommend pc = picurerecommendService.selectByPrimaryKey(Integer.valueOf(id));
	    Integer  s  = pc.getSort();
	    Criteria example = new Criteria();
	    List<Picturerecommend> pclist = picurerecommendService.selectByParams(example);
	    for(Picturerecommend p:pclist){
	    	if(p.getSort()>s){
	    		if(p.getPeriod()==null){
	    			p.setPeriod(null);
	    		}else{
	    			p.setPeriod(p.getPeriod());
	    		}
	    		Integer b = p.getSort();
	    		p.setSort(b-1);
	    		
	    		p.setPid(p.getPid());
	    		p.setId(p.getId());
	    		picurerecommendService.updateByPrimaryKey(p);
	    	}
	    }
	    
	    picurerecommendService.deleteByPrimaryKey(Integer.valueOf(id));
	    
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
	/**
	 * 推荐图片当中顺序上调的方法
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping("admin/upcommendmetoo")
	public  ModelAndView upcommendmetoo(HttpSession session,String id){
		Picturerecommend prec = picurerecommendService.selectByPrimaryKey(Integer.valueOf(id));
		Integer a = prec.getSort();
		Integer b = null;
		
		Criteria example = new Criteria();
		example.put("sort", a-1);
		List<Picturerecommend> lpc = picurerecommendService.selectByParams(example);
		for(Picturerecommend pc:lpc){
			if(pc.getSort().equals(a-1)){
				b = pc.getId();
				break;
			}
		}
		Picturerecommend prec1 = picurerecommendService.selectByPrimaryKey(b);
		prec1.setSort(a);
		picurerecommendService.updateByPrimaryKeySelective(prec1);
		prec.setSort(a-1);
		picurerecommendService.updateByPrimaryKeySelective(prec);
		
		//Integer a = prec.getSort();
		
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
	
	/**
	 * 推荐图片当中顺序下调的方法
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping("admin/downcommendmetoo")
	public  ModelAndView downcommendmetoo(HttpSession session,String id){
		Picturerecommend prec = picurerecommendService.selectByPrimaryKey(Integer.valueOf(id));
		Integer a = prec.getSort();
		Integer b = null;
		
		Criteria example = new Criteria();
		example.put("sort", a+1);
		List<Picturerecommend> lpc = picurerecommendService.selectByParams(example);
		for(Picturerecommend pc:lpc){
			if(pc.getSort().equals(a+1)){
				b = pc.getId();
				break;
			}
		}
		Picturerecommend prec1 = picurerecommendService.selectByPrimaryKey(b);
		prec1.setSort(a);
		picurerecommendService.updateByPrimaryKeySelective(prec1);
		prec.setSort(a+1);
		picurerecommendService.updateByPrimaryKeySelective(prec);
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
	
	/**
	 * 转入评论内容页面
	 * @param pid
	 * @return
	 */
	@RequestMapping("/admin/checkview/comment")
	public ModelAndView commontcontentPicture(HttpSession session,String pid){
		Criteria example = new Criteria();
		example.setOrderByClause("creat_time");
	    example.setSord("desc");
	    example.put("del", "0");
//		example.setOrderByClause("creat_time");
	    example.put("pid", Integer.valueOf(pid));
		List<Comment> cs = commentService.selectByParams(example);
		
		
		ModelAndView mav = new ModelAndView();
//		mav.addObject(cs);
		mav.setViewName("/admin/commentmetoodel");
		return mav;
		
	}
	
	/**
	 * 评论内容页面的显示
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping("/admin/check/comment")
public void  getCommentContent(HttpSession session,HttpServletResponse response,String aoData,@PathVariable String pid){
		
		
		
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
		example.put("pid",Integer.valueOf(pid) );
	    example.setOrderByClause("creat_time");
	    example.setSord("desc");
	    example.put("del", "0");
		int count = commentService.countByParams(example);
		List<Comment> clist = commentService.selectByParams(example);
		
		
		List list = new ArrayList();
		String aa ;
		for(Comment cs : clist){
			if(cs.getCreatTime()!=null){
				aa = DateUtil.dateFormatToString(cs.getCreatTime(),"yyyy-MM-dd HH:mm:ss");
			}else{
				aa="";
			}
			String[] d = {cs.getCommentId().toString(),cs.getPid().toString(),cs.getCustomer().getInneruser(),cs.getContent(),
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
	 * 蜜图评论内容当中逻辑删除的方法
	 * 
	 */
	@RequestMapping("/admin/delcontent/comment")
	public  ModelAndView delcontentcheckdmetoo(HttpSession session,String commentId){
		Comment c = commentService.selectByPrimaryKey(Integer.valueOf(commentId));
		if("0".equals(c.getDel())){
			c.setDel("1");
			  commentService.updateByPrimaryKeySelective(c);
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/admin/checkview/comment");
			return mav;
		}else{
//			p.setDel("1");
//			  pictureService.updateByPrimaryKeySelective(p);
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/admin/checkview/comment");
			return mav;
		}
	}

}
