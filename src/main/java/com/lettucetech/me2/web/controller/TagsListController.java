package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.common.utils.QiniuUtil;
import com.lettucetech.me2.common.utils.QiniuUtil.MyRet;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.pojo.Taglist;
import com.lettucetech.me2.service.TaglistService;
import com.lettucetech.me2.service.TagsconnectionService;
import com.lettucetech.me2.service.TagshotService;
import com.lettucetech.me2.web.form.DataTablePaginationForm;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

@Controller
public class TagsListController {
	@Autowired
	private TaglistService tagListService;
	@Autowired
	private TagshotService tagshotService;
	@Autowired
	private TagsconnectionService tagsconnectionService;
	/**
	 * 跳转到标签集合页面
	 *
	 * @param session
	 * @param 
	 * @return
	 */
	 
	@RequestMapping("/admin/viewList")
	public ModelAndView selectByTags(HttpSession session,HttpServletRequest request){	
//		String k="2";
//		request.getSession().setAttribute("k",k);
		
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
		String aurl = Me2Constants.QINIUPUBLICDOMAIN+"/"+taglist.getQiniukey();
		ModelAndView mav = new ModelAndView();
		mav.addObject("aurl",aurl);
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
		mav.setViewName("/admin/addList");
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
    example.setSord("asc");
    example.setMysqlOffset(iDisplayStart);
    example.setMysqlLength(iDisplayLength);

    int count = tagListService.countByParams(example);
    List<Taglist> metoo = tagListService.selectByParams(example);
   
   
	    
	    //拼接翻页数据
	    List list = new ArrayList();
		for(Taglist obj : metoo){
			
			String aurl = Me2Constants.QINIUPUBLICDOMAIN+"/"+obj.getQiniukey();
			
			String s="";
			
			
			String[] d={obj.getId().toString(),obj.getTitle(),aurl,s,s,s};
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
	public ModelAndView updateList(HttpSession session,String id,String title,
			@RequestParam("upload")  CommonsMultipartFile afile){
		
		String akey=null;
		if(afile.getFileItem().getName()!=""){
			try {
				String token = QiniuUtil.uploadToken(Me2Constants.METOOPULIC);
		        Response res = QiniuUtil.uploadManager.put(afile.getBytes(), null, token);
				MyRet ret = res.jsonToObject(MyRet.class); 
				akey = ret.getKey();
			} catch (QiniuException e) {
				e.printStackTrace();
			}
		}
		
		Taglist list=new Taglist();
		
//		Date date=new Date();
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String time=sdf.format(date);
		list.setId(Integer.valueOf(id));
		list.setTitle(title);
		list.setQiniukey(akey);
//		list.setNum(Integer.valueOf(num));
		
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
		
		Taglist pc=tagListService.selectByPrimaryKey(Integer.valueOf(id));
		 Integer  s  = pc.getSort();
		    Criteria example = new Criteria();
		    List<Taglist> pclist = tagListService.selectByParams(example);
		    for(Taglist p:pclist){
		    	if(p.getSort()>s){
		    		
		    		Integer b = p.getSort();
		    		
		    		p.setSort(b-1);
		    		p.setId(p.getId());
		    		p.setNum(p.getNum());
		    		p.setQiniukey(p.getQiniukey());
		    		p.setTitle(p.getTitle());
		    		tagListService.updateByPrimaryKey(p);
		    	}
		    }
		 tagListService.deleteByPrimaryKey(Integer.valueOf(id));
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
	public ModelAndView addList(HttpSession session,String id,String title
			,@RequestParam("upload")  CommonsMultipartFile afile){
		//Taglist list2=tagListService.selectByPrimaryKey(Integer.valueOf(id));
		String akey=null;
		if(afile.getFileItem().getName()!=""){
			try {
				String token = QiniuUtil.uploadToken(Me2Constants.METOOPULIC);
		        Response res = QiniuUtil.uploadManager.put(afile.getBytes(), null, token);
				MyRet ret = res.jsonToObject(MyRet.class); 
				akey = ret.getKey();
			} catch (QiniuException e) {
				e.printStackTrace();
			}
		}
	
		 Taglist list = new Taglist();
		    Criteria example = new Criteria();
		    example.put("id", id);
		    List<Taglist> tag = this.tagListService.selectByParams(example);
		    List list1 = new ArrayList();
		    for(Taglist pcs : tag){
				Integer b = pcs.getSort();
				if(b==1){
					pcs.setSort(b);;
				}else{
				pcs.setSort(b+1);
				}
	    		pcs.setQiniukey(pcs.getQiniukey());
				pcs.setId(pcs.getId());
				pcs.setTitle(pcs.getTitle());
				tagListService.updateByPrimaryKey(pcs);
				list1.add(b);
			}
		    Integer sort = Integer.valueOf(0);
		    int sum = 0;
		    int lost = 0;
		    int max = 0;
		    int total = 0;
		    for (int j = 0; j < tag.size(); j++) {
		      if (((Integer)list1.get(j)).intValue() > max) {
		        max = ((Integer)list1.get(j)).intValue();
		      }
		      sum += ((Integer)list1.get(j)).intValue();//300
		    }
		    total = max * (max + 1) / 2;
		    lost = total - sum;
		     if ((lost > 0) && (lost < tag.size())) {
		      list.setSort(lost);
		    }else if(lost==0){
		    	lost=2;
		    	list.setSort(lost);
		    }
//		     else if(lost==0){
//		    	list.setSort(max+1);
//		    }

		    list.setTitle(title);
		    list.setQiniukey(akey);

		    this.tagListService.insertSelective(list);
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
	/**
	 * 精选集合当中顺序上调的方法
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping("admin/upcommend")
	public  ModelAndView upcommend(HttpSession session,String id){
		ModelAndView mav = new ModelAndView();
		Taglist list=tagListService.selectByPrimaryKey(Integer.valueOf(id));
		Integer a = list.getSort();
		Integer b = null;
		if(a==1){
			mav.setViewName("/admin/viewList");
		}else{
		Criteria example = new Criteria();
		example.put("sort", a-1);
		List<Taglist> taglist = tagListService.selectByParams(example);
		for(Taglist tagslist:taglist){
			if(tagslist.getSort().equals(a-1)){
				b = tagslist.getId();
				break;
			}
		}
		Taglist list1 = tagListService.selectByPrimaryKey(b);
		list1.setSort(a);
		tagListService.updateByPrimaryKeySelective(list1);
		list.setSort(a-1);
		tagListService.updateByPrimaryKeySelective(list);
		mav.setViewName("/admin/viewList");
		}

		
		return mav;
	}
	
	/**
	 * 精选集合当中顺序下调的方法
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping("admin/downcommend")
	public  ModelAndView downcommend(HttpSession session,String id){
		ModelAndView mav = new ModelAndView();
		Taglist list=tagListService.selectByPrimaryKey(Integer.valueOf(id));
		Integer a = list.getSort();
		Integer b = null;
		if(a==1){
			mav.setViewName("/admin/viewList");
		}else{
			
		
		Criteria example = new Criteria();
		example.put("sort", a+1);
		List<Taglist> taglist = tagListService.selectByParams(example);
		for(Taglist tagslist:taglist){
			if(tagslist.getSort().equals(a+1)){
				b = tagslist.getId();
				break;
			}
		}
		if(b==null){
			mav.setViewName("/admin/viewList");
		}else{
		Taglist list1 = tagListService.selectByPrimaryKey(b);
		list1.setSort(a);
		tagListService.updateByPrimaryKeySelective(list1);
		list.setSort(a+1);
		tagListService.updateByPrimaryKeySelective(list);
		}
		
		mav.setViewName("/admin/viewList");
		}
		return mav;
	}
}