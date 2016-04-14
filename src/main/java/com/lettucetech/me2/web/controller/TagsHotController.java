package com.lettucetech.me2.web.controller;

import com.lettucetech.me2.common.utils.DateUtil;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.dao.TagshotMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Recommend;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.pojo.Tagsconnection;
import com.lettucetech.me2.pojo.Tagshot;
import com.lettucetech.me2.service.RecommendService;
import com.lettucetech.me2.service.TagsconnectionService;
import com.lettucetech.me2.service.TagshotService;
import com.lettucetech.me2.web.form.DataTablePaginationForm;
import java.io.IOException;
import java.io.PrintWriter;
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

  @RequestMapping({"/admin/viewTags"})
  public ModelAndView selectByTags(HttpSession session, HttpServletRequest request)
  {
    String str = request.getParameter("taglist");
    String s = request.getParameter("search");
    String flg = request.getParameter("flag");
    String flag = "";
    if (flg == null)
    {
      flag = "1";
    } else if (flg != null) {
      flag = flg;
    }
    session.setAttribute("str", str);
    session.setAttribute("flag", flag);
    ModelAndView mav = new ModelAndView();

    mav.addObject("svalue", s);
    mav.addObject("flag",flag);
    mav.setViewName("/admin/viewTags");
    return mav;
  }

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
    if ("0".equals(font)) {
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
    else if ("5".equals(font)) {
      example.setOrderByClause("last_time");
      example.setSord("desc");
    }

    if (!"".equals(searchid)) {
      example.put("tag", searchid);
    }

    int count = this.tagshotService.countByParams(example);
    List<Tagshot> metoo = this.tagshotService.selectByParams(example);

    String s = "";
    for (Tagshot obj : metoo)
    {
      if (searchid.contains(obj.getTag()))
      {
        String[] d = { obj.getId().toString(), obj.getTag(), obj.getHits().toString(), obj.getAcount().toString(), obj.getMefriends().toString(), 
          DateUtil.dateFormatToString(obj.getLastTime(), "yyyy-MM-dd HH:mm:ss"), "", s, s };
        list.add(d);
      } else {
        String[] d = { obj.getId().toString(), obj.getTag(), obj.getHits().toString(), obj.getAcount().toString(), obj.getMefriends().toString(), 
          DateUtil.dateFormatToString(obj.getLastTime(), "yyyy-MM-dd HH:mm:ss"), "", s, s };
        list.add(d);
      }

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

  @RequestMapping({"/admin/delTags"})
  public ModelAndView delTags(HttpSession session, String id)
  {
    this.tagshotService.deleteByPrimaryKey(Integer.valueOf(id));

    ModelAndView mav = new ModelAndView();
    mav.setViewName("redirect:/admin/viewTags");
    return mav;
  }

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
    String str = (String)session.getAttribute("str");
    ModelAndView mav = new ModelAndView();
    if (str == null) {
      String[] d = ids.split(",");
      Recommend recommend = new Recommend();
      for (int i = 0; i < d.length; i++) {
        String c = d[i];
        Tagshot tag = this.tagshotService.selectByPrimaryKey(Integer.valueOf(c));
        recommend.setTagId(tag.getId());
        recommend.setAcount(tag.getAcount());
        recommend.setHits(tag.getHits());
        recommend.setMefriends(tag.getMefriends());
        recommend.setTagname(tag.getTag());
        recommend.setQiniukey(tag.getQiniukey());
        recommend.setLasttime(tag.getLastTime());
        Criteria example = new Criteria();
        example.put("id", c);
        List<Recommend> commend =recommendService.selectByParams(example);
        List list1 = new ArrayList();
        for (Recommend pcs : commend) {
          Integer b = pcs.getSort();
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
          sum += ((Integer)list1.get(j)).intValue();
        }
        total = max * (max + 1) / 2;
        lost = total - sum;
        if (lost == 0)
        {
          for (int k = 0; k < commend.size(); k++) {
            if (((Integer)list1.get(k)).intValue() > sort.intValue()) {
              sort = (Integer)list1.get(k);
            }
          }

          recommend.setSort(Integer.valueOf(sort.intValue() + 1));
        } else if ((lost > 0) && (lost < commend.size())) {
          recommend.setSort(Integer.valueOf(lost));
        }
        this.recommendService.insert(recommend);

        mav.setViewName("redirect:/admin/viewrecommend");
      }
     // return mav;
    }
    else if (str != null)
    {
      Tagsconnection conn = new Tagsconnection();
      String[] d = ids.split(",");
      for (int i = 0; i < d.length; i++) {
        String c = d[i];
        Tagshot tag = this.tagshotService.selectByPrimaryKey(Integer.valueOf(c));

        conn.setTagsId(tag.getId());
        conn.setTagslistId(str);

        this.tagsconnectionService.insert(conn);
      }
      String cid = conn.getTagslistId();
      request.getSession().setAttribute("cid", cid);

      mav.setViewName("redirect:/admin/viewselective");
    }
    return mav;
  }
  }
  

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