package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import com.lettucetech.me2.pojo.Picture;
import com.lettucetech.me2.pojo.Recommend;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.pojo.Taglist;
import com.lettucetech.me2.pojo.Tagsconnection;
import com.lettucetech.me2.pojo.Tagshot;
import com.lettucetech.me2.pojo.Task;
import com.lettucetech.me2.service.RecommendService;
import com.lettucetech.me2.service.TaglistService;
import com.lettucetech.me2.service.TagsconnectionService;
import com.lettucetech.me2.service.TagshotService;
import com.lettucetech.me2.service.TaskService;
import com.lettucetech.me2.web.form.DataTablePaginationForm;

/**
 * @author 付大海
 *
 */
@Controller
public class TasksController
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
  @Autowired
  private TaskService taskService;



  /**
   * 跳转到所有标签的页面
 * @param session
 * @param request
 * @return
 */
@RequestMapping({"/admin/viewTasks"})
  public ModelAndView selectByTags(HttpSession session, HttpServletRequest request)
  {
    ModelAndView mav = new ModelAndView();

    
    mav.setViewName("/admin/viewTasks");
    return mav;
  }

/**
 * 跳转到修改的页面
 * @param session
 * @return
 */
@RequestMapping("/admin/viewupdateTask")
public ModelAndView updateByTags(HttpSession session,String id){		
	
	Task task = taskService.selectByPrimaryKey(Integer.valueOf(id));
	
	ModelAndView mav = new ModelAndView();
	
	mav.addObject("task",task);
	mav.setViewName("/admin/updateTask");;
	return mav;
}

/**
 * 跳转到添加页面
 * @param session
 * @return
 */
@RequestMapping("/admin/viewaddTask")
public ModelAndView addByTags(HttpSession session){		
	ModelAndView mav = new ModelAndView();
	mav.setViewName("/admin/addTask");
	return mav;
}


  /**
   * 查看所有任务
 * @param session
 * @param response
 * @param aoData
 * @param font
 * @param searchid
 */
@RequestMapping({"/admin/getTaskByTasks"})
  public void getMetooByTags(HttpSession session, HttpServletResponse response, String aoData)
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
   

    int count = this.taskService.countByParams(example);
    List<Task> metoo = taskService.selectByParams(example);

    String s = "";
    for (Task obj : metoo)
    {
    	
        String[] d = {obj.getId().toString(),obj.getTitle(),obj.getContent(),obj.getTasktype().toString(),obj.getTaskstyle().toString(),
          DateUtil.dateFormatToString(obj.getCreateTime(), "yyyy-MM-dd HH:mm:ss"),  
          DateUtil.dateFormatToString(obj.getLastTime(), "yyyy-MM-dd HH:mm:ss"), obj.getCustom().toString(),obj.getAward().toString(),s};
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
   * 逻辑删除任务
 * @param session
 * @param id
 * @return
 */
@RequestMapping({"/admin/delTask"})
  public ModelAndView delTags(HttpSession session, String id)
  {Task p = taskService.selectByPrimaryKey(Integer.valueOf(id));
	if("1".equals(p.getDel())){
		p.setDel(0);
		taskService.updateByPrimaryKeySelective(p);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/viewTasks");
		return mav;
	}else{
		p.setDel(1);
		taskService.updateByPrimaryKeySelective(p);
    ModelAndView mav = new ModelAndView();
    mav.setViewName("redirect:/admin/viewTasks");
    return mav;
  }
  }

/**
 * 新建任务
 * @param session
 * @param title
 * @param content
 * @param tasktype
 * @param taskstyle
 * @param custom
 * @param award
 * @return
 */
@RequestMapping({"/admin/addTask"})
public ModelAndView addTask(HttpSession session,String title,String content,String tasktype,String taskstyle,
		String createTime,String lastTime,String custom,String award){
		Task task=new Task();
		task.setAward(Integer.valueOf(award));
		task.setContent(content);
		Date date = DateUtil.formatDateFromString(createTime,"yyyy-MM-dd HH:mm:ss");
		Date date1=DateUtil.formatDateFromString(lastTime,"yyyy-MM-dd HH:mm:ss");
		
		task.setLastTime(date1);
		task.setCreateTime(date);
		task.setCustom(Integer.valueOf(custom));
		task.setTaskstyle(Integer.valueOf(taskstyle));
		task.setTasktype(Integer.valueOf(tasktype));
		task.setTitle(title);
		taskService.insertSelective(task);
	
		ModelAndView mav = new ModelAndView();
	    mav.setViewName("redirect:/admin/viewTasks");
	    return mav;
}
 
@RequestMapping({"/admin/updateTask"})
public ModelAndView updateTask(HttpSession session,String id,String title,String content,String tasktype,String taskstyle,
		String createTime,String lastTime,String custom,String award){
		Task task=taskService.selectByPrimaryKey(Integer.valueOf(id));
		task.setAward(Integer.valueOf(award));
		task.setContent(content);
		Date date = DateUtil.formatDateFromString(createTime,"yyyy-MM-dd HH:mm:ss");
		task.setCreateTime(date);
		Date date1 = DateUtil.formatDateFromString(lastTime,"yyyy-MM-dd HH:mm:ss");
		
		task.setCustom(Integer.valueOf(custom));
		task.setTaskstyle(Integer.valueOf(taskstyle));
		task.setTasktype(Integer.valueOf(tasktype));
		task.setTitle(title);
		task.setLastTime(date1);
		taskService.updateByPrimaryKeySelective(task);
	
		ModelAndView mav = new ModelAndView();
	    mav.setViewName("redirect:/admin/viewTasks");
	    return mav;
}


}