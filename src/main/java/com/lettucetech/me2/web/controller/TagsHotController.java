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
import com.lettucetech.me2.dao.TagshotMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Recommend;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.pojo.Taglist;
import com.lettucetech.me2.pojo.Tagsconnection;
import com.lettucetech.me2.pojo.Tagshot;
import com.lettucetech.me2.service.RecommendService;
import com.lettucetech.me2.service.TaglistService;
import com.lettucetech.me2.service.TagsconnectionService;
import com.lettucetech.me2.service.TagshotService;
import com.lettucetech.me2.web.form.DataTablePaginationForm;

/**
 * @author 付大海
 *
 */
@Controller
public class TagsHotController
{

  @Autowired
  private TagshotMapper mapper;

  @Autowired
  private TagsconnectionService tagsconnectionService;

  @Autowired
  private TagshotService tagshotService;

  @Autowired
  private RecommendService recommendService;
@Autowired
private TaglistService tagListService;
  /**
   * 跳转到所有标签的页面
 * @param session
 * @param request
 * @return
 */
@RequestMapping({"/admin/viewTags"})
  public ModelAndView selectByTags(HttpSession session, HttpServletRequest request)
  {
	String str1=(String)request.getSession().getAttribute("str1");
	session.removeAttribute("str1");
	String str2=(String)request.getSession().getAttribute("str2");
	session.removeAttribute("str2");
    String str = request.getParameter("taglist");
    String name="";
    if(str!=null){
    Taglist tag=tagListService.selectByPrimaryKey(Integer.valueOf(str));
    name=tag.getTitle();
    }else if(str1!=null){
    	 Taglist tag=tagListService.selectByPrimaryKey(Integer.valueOf(str1));
    	     name=tag.getTitle();
    }else{
    	name="";
    }
    String s = request.getParameter("search");
    String flg = request.getParameter("flag");
    String flag = "";
if(str2==null && str1==null){
    if (flg == null){
    	String f=(String)session.getAttribute("f");
    	session.removeAttribute("f");
    	if(f==null){
      flag = "1";
    	}else if(f!=null){
    		 flag=f;
    		
    	}
    	} else if (flg != null) {
      flag = flg;
      session.setAttribute("f",flag);
    }
    }else if(str1!=null){
    	flag="2";
    }

    if(str==null){
    	String ss=(String)session.getAttribute("ss");
    	session.setAttribute("ss",ss);
    }else{
    	
    	session.setAttribute("ss", str);
    }

//    session.setAttribute("flag", flag);
    ModelAndView mav = new ModelAndView();

    mav.addObject("svalue", s);
    mav.addObject("flag",flag);
    mav.addObject("name",name);
    mav.setViewName("/admin/viewTags");
    return mav;
  }

  /**
   * 查看所有标签
 * @param session
 * @param response
 * @param aoData
 * @param font
 * @param searchid
 */
@RequestMapping({"/admin/getmetooByTags"})
  public void getMetooByTags(HttpSession session, HttpServletResponse response, String aoData, String font, String searchid)
  {
    TXtUser au = (TXtUser)session.getAttribute("adminuser");

    ArrayList jsonarray = (ArrayList)JsonUtil.Decode(aoData);

    int iDisplayStart = 0;
    int iDisplayLength = 0;
    String sEcho = null;

    for (int i = 0; i < jsonarray.size(); i++) {
      HashMap obj = (HashMap)jsonarray.get(i);
      if (obj.get("name").equals("sEcho")) {
        sEcho = obj.get("value").toString();
      }
      if (obj.get("name").equals("iDisplayStart")) {
        iDisplayStart = Integer.parseInt(obj.get("value").toString());
      }
      if (obj.get("name").equals("iDisplayLength")) {
        iDisplayLength = Integer.parseInt(obj.get("value").toString());
      }

    }

    Criteria example = new Criteria();

    example.setDistinct(true);
    example.setMysqlOffset(Integer.valueOf(iDisplayStart));
    example.setMysqlLength(Integer.valueOf(iDisplayLength));

    List list = new ArrayList();
    if ("5".equals(font)) {
      example.setOrderByClause("id");
      example.setSord("desc");
    }
    else if ("2".equals(font)) {
      example.setOrderByClause("hits");
      example.setSord("desc");
    }
    else if ("3".equals(font)) {
      example.setOrderByClause("acount");
      example.setSord("desc");
    }
    else if ("4".equals(font)) {
      example.setOrderByClause("mefriends");
      example.setSord("desc");
    }
    else if ("1".equals(font)) {
      example.setOrderByClause("last_time");
      example.setSord("desc");
    }
//
//    if(!"-1".equals(font)){
//    	
//    	example.setOrderByClause("hits");
//    	example.setSord("desc");
//    }
    
    if (!"".equals(searchid)) {
      example.put("tag", searchid);
    }

    int count = this.tagshotService.countByParams(example);
    List<Tagshot> metoo = this.tagshotService.selectByParams(example);

    String s = "";
    for (Tagshot obj : metoo)
    {
    	String aurl= Me2Constants.QINIUPUBLICDOMAIN+"/"+obj.getQiniukey();
        String[] d = { obj.getId().toString(), obj.getTag(), obj.getHits().toString(), obj.getAcount().toString(), obj.getMefriends().toString(),aurl, 
          DateUtil.dateFormatToString(obj.getLastTime(), "yyyy-MM-dd HH:mm:ss"), "", s, s };
        list.add(d);

    }

    DataTablePaginationForm dtpf = new DataTablePaginationForm();
    dtpf.setsEcho(sEcho);
    dtpf.setiTotalDisplayRecords(count);
    dtpf.setiTotalRecords(count);
    dtpf.setAaData(list);

    String jsonArray = JsonUtil.Encode(dtpf);
    try
    {
      response.setHeader("Cache-Control", "no-cache");
      response.setContentType("aplication/json;charset=UTF-8");
      response.getWriter().print(jsonArray);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 删除所有标签的方法(暂时没用)
 * @param session
 * @param id
 * @return
 */
@RequestMapping({"/admin/delTags"})
  public ModelAndView delTags(HttpSession session, String id)
  {
    this.tagshotService.deleteByPrimaryKey(Integer.valueOf(id));

    ModelAndView mav = new ModelAndView();
    mav.setViewName("redirect:/admin/viewTags");
    return mav;
  }

  /**
   * 将所有标签中的元素添加到精选集合中的方法
 * @param session
 * @param request
 * @return
 */
@RequestMapping({"/admin/add"})
  public ModelAndView add(HttpSession session, HttpServletRequest request){
    String ids = request.getParameter("arr");
  if(ids==""){
	//  String a="钱包里空空如也呢!!";
	  ModelAndView mav = new ModelAndView();
	//  mav.addObject("avalue",a);
	    mav.setViewName("redirect:/admin/viewTags");
	    return mav;
  }else{
    String str = (String)session.getAttribute("ss");
    session.removeAttribute("ss");
    
    	String g="1";
      Tagsconnection conn = new Tagsconnection();
      String[] d = ids.split(",");
      for (int i = 0; i < d.length; i++) {
        String c = d[i];
        Criteria example =new Criteria();
        example.put("tagslistId", str);
//        List<Tagsconnection> tagconn= tagsconnectionService.selectByParams(example);
//        for (Tagsconnection taglist : tagconn) {
        	Tagshot tag = this.tagshotService.selectByPrimaryKey(Integer.valueOf(c));
//        	
//			if(tag.getId().equals(taglist.getTagsId())){
//				ModelAndView mav = new ModelAndView();
//				request.getSession().setAttribute("str1",str);
//				Taglist tagslist=tagListService.selectByPrimaryKey(Integer.valueOf(str));
//				String title=tagslist.getTitle();
//				String tags=tag.getTag();
//				mav.addObject("title",title);
//				mav.addObject("tsg",tags);
//				 mav.setViewName("/admin/myerror");
//				 return mav;
//			}else{
				
				conn.setTagsId(tag.getId());
				conn.setTagslistId(str);
				
				this.tagsconnectionService.insert(conn);
			}
//		}

//      }
      String cid = conn.getTagslistId();
      request.getSession().setAttribute("cid", cid);
      // request.getSession().setAttribute("g",g);
      ModelAndView mav = new ModelAndView();
      mav.addObject("g",g);
      mav.setViewName("redirect:/admin/viewselective");
      return mav;
    }
  }
  
  /**
   * 将所有标签中的元素添加到热门标签的页面中的方法
 * @param session
 * @param request
 * @return
 */
@RequestMapping({"/admin/addtag"})
  public ModelAndView addtag(HttpSession session, HttpServletRequest request){
    String ids = request.getParameter("arr");
  if(ids==""){
	//  String a="钱包里空空如也呢!!";
	 
	//  mav.addObject("avalue",a); 
	  ModelAndView mav = new ModelAndView();
	    mav.setViewName("redirect:/admin/viewTags");
	    return mav;
  }else{
    String str = (String)session.getAttribute("ss");
    session.removeAttribute("ss");
    ModelAndView mav = new ModelAndView();
    if (str == null) {
      String[] d = ids.split(",");
      Recommend recommend = new Recommend();
      for (int i = 0; i < d.length; i++) {
        String c = d[i];
        Criteria example = new Criteria();
      
        
		
       // Criteria example = new Criteria();
        example.put("id", c);
        List<Recommend> commend =recommendService.selectByParams(example);
        Tagshot tag = this.tagshotService.selectByPrimaryKey(Integer.valueOf(c));
        List list1 = new ArrayList();
        for (Recommend pcs : commend) {
        	
//        	if(tag.getId().equals(pcs.getTagId())){
//        		 request.getSession().setAttribute("str2",str);
//        		String tagslist="热门";
//        		 mav.addObject("tagslist",tagslist);
//        		 String tags=tag.getTag();
//        		 mav.addObject("tag", tags);
//        		 mav.setViewName("/admin/myerror");
//        		 return mav;
//        	}else {
        		
          Integer b = pcs.getSort();
          if(b==null){
				b=1;
				pcs.setSort(b);
			}
          else{
			
			pcs.setSort(b+1);
			}
  			pcs.setQiniukey(pcs.getQiniukey());
			pcs.setId(pcs.getId());
			pcs.setAcount(pcs.getAcount());
			pcs.setHits(pcs.getHits());
			pcs.setLasttime(pcs.getLasttime());
			pcs.setMefriends(pcs.getMefriends());
			pcs.setTagId(pcs.getTagId());
			pcs.setTagname(pcs.getTagname());
  			recommendService.updateByPrimaryKey(pcs);
  			
          
          
          list1.add(b);
        	}
       
        Integer sort = Integer.valueOf(0);
	    int sum = 0;
	    int lost = 0;
	    int max = 0;
	    int total = 0;
	    for (int j = 0; j < commend.size(); j++) {
	      if (((Integer)list1.get(j)).intValue() > max) {
	        max = ((Integer)list1.get(j)).intValue();
	      }
	      sum += ((Integer)list1.get(j)).intValue();//300
	    }
	    total = max * (max + 1) / 2;
	    lost = total - sum;
	     if ((lost > 0) && (lost < commend.size())) {
	      recommend.setSort(lost);
	    }else if(lost==0){
	    	lost=1;
	    	recommend.setSort(lost);
	    }
        
			recommend.setTagId(tag.getId());
			recommend.setAcount(tag.getAcount());
			recommend.setHits(tag.getHits());
			recommend.setMefriends(tag.getMefriends());
			recommend.setTagname(tag.getTag());
			recommend.setQiniukey(tag.getQiniukey());
			recommend.setLasttime(tag.getLastTime());
        this.recommendService.insert(recommend);
        }
      
     // return mav;
    }
//    else if (str != null)
//    {
//    	String g="1";
//      Tagsconnection conn = new Tagsconnection();
//      String[] d = ids.split(",");
//      for (int i = 0; i < d.length; i++) {
//        String c = d[i];
//        Tagshot tag = this.tagshotService.selectByPrimaryKey(Integer.valueOf(c));
//
//        conn.setTagsId(tag.getId());
//        conn.setTagslistId(str);
//
//        this.tagsconnectionService.insert(conn);
//      }
//      String cid = conn.getTagslistId();
//      request.getSession().setAttribute("cid", cid);
//     // request.getSession().setAttribute("g",g);
//     mav.addObject("g",g);
     // mav.setViewName("redirect:/admin/viewselective");
    }
  ModelAndView mav = new ModelAndView();
  mav.setViewName("redirect:/admin/viewrecommend");
  return mav;
  }
  

  /**
   * 刷新所有标签中的元素的方法
 * @param session
 * @return
 */
@RequestMapping({"/admin/viewflash"})
  public ModelAndView submit(HttpSession session)
  {
    Map map = new HashMap();
    map.put("str3", ",");
    List tagshot = mapper.strsplit1(map);

    ModelAndView mav = new ModelAndView();
    mav.addObject(map);
    mav.setViewName("redirect:/admin/viewTags");
    return mav;
  }
}