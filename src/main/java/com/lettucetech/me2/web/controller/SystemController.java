package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.common.pojo.JqGridFilterSearch;
import com.lettucetech.me2.common.pojo.JqGridHandler;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.common.utils.WebUtil;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtDepartment;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.service.TXtDepartmentService;
import com.lettucetech.me2.service.TXtUserService;

@Controller
public class SystemController {
	private static final Logger logger = Logger.getLogger(SystemController.class);
	@Autowired
	private TXtDepartmentService  departmentService;
	@Autowired
	private TXtUserService  userService;
	
	/**
	 * 部门管理
	 * @param session
	 * @return
	 */
	@RequestMapping("/admin/department")
	public ModelAndView department(HttpSession session) {
		logger.debug("SystemController - index");
//		Criteria example = new Criteria();
//		List<TXtDepartment> list = departmentServiceImpl.selectByParams(example);
		ModelAndView mav = new ModelAndView();
//		mav.addObject("deplist", list);
		mav.setViewName("/system/department");
		return mav;
	}
	
	/**
	 * 存放Java字段和数据库字段不一致时两者的对应关系
	 */
	private static Map<String,String> map=new HashMap<String,String>();
	static{
		map.put("depId", "dep_id");
		map.put("depName", "dep_name");
		map.put("depManager", "dep_manager");
	}


	/**
	 * 查询部门
	 * @param request
	 * @param response
	 * @param pager
	 * @param department
	 * @throws IOException 
	 */
	@RequestMapping("/admin/getDepartments")
	public void getDepartments(HttpServletResponse response,JqGridHandler jgh) {
		//获取设置好的查询条件类，并将Java字段替换为数据库字段
	    try {
	    	JqGridFilterSearch jgfs=jgh.getFilterSearch(new TXtDepartment(),map);
			Criteria cri=new Criteria();
			cri.put("jgfs", jgfs);
			cri.setMysqlOffset(jgh.getRows()*(jgh.getPage()-1));
			cri.setMysqlLength(jgh.getRows());
			if (StringUtils.isNotEmpty(jgh.getSidx())) {
				if(map.get(jgh.getSidx())!=null){
					cri.setOrderByClause(map.get(jgh.getSidx()));
				}else{
					cri.setOrderByClause(jgh.getSidx());
				}
				cri.setSord(jgh.getSord());
			}
			List<TXtDepartment> userInfoList= this.departmentService.advancedSearching(cri);;
			int total=0,records=0;
			records=total=this.departmentService.advancedSearchingCount(cri);
			total=total/jgh.getRows()+(((total%jgh.getRows())==0)?0:1);//总页数
			StringBuffer json=new StringBuffer();
			json.append("{");
			json.append("\"total\":\""+total+"\",");
			json.append("\"page\":\""+jgh.getPage()+"\",");
			json.append("\"records\":\""+records+"\",");
			json.append("\"rows\":");
			json.append(JsonUtil.Encode(userInfoList));
			json.append("}");
			WebUtil.renderJson(response, json.toString());
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	/**
	 * 对部门增删改查
	 * @param pager
	 * @param department
	 * @param response
	 */
	@RequestMapping(value="/admin/editDepartments",params="oper=add")
	public void editDepartmentsAdd(JqGridHandler pager,TXtDepartment department,HttpServletResponse response){
	    try {
	    	if(pager.getOper()!=null){
				if(pager.getOper().equals("add")){

					departmentService.insert(department);
				}
			}
		    response.setContentType("application/json; charset=utf-8");
		    response.getWriter().print("{\"message\":\"");
		    response.getWriter().print("success");
			response.getWriter().print("\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="/admin/editDepartments",params="oper=edit")
	public void editDepartmentsEdit(JqGridHandler pager,TXtDepartment department,HttpServletResponse response){
	    try {
	    	if(pager.getOper()!=null){
				if(pager.getOper().equals("edit")){
					//如果id为空则取pager带过来的id值
					if(department.getDepId()==null || "".equals(department.getDepId())){
						department.setDepId(Integer.parseInt(pager.getId()));
					}
					departmentService.updateByPrimaryKeySelective(department);
				}
			}
		    response.setContentType("application/json; charset=utf-8");
		    response.getWriter().print("{\"message\":\"");
		    response.getWriter().print("success");
			response.getWriter().print("\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="/admin/editDepartments",params="oper=del")
	public void editDepartmentsDel(JqGridHandler pager,TXtDepartment department,HttpServletResponse response){
	    try {
	    	if(pager.getOper()!=null){
				if(pager.getOper().equals("del")){
					String[] ids = pager.getId().split(",");
					for(String id : ids){
						departmentService.deleteByPrimaryKey(Integer.parseInt(id));
					}
				}
			}
		    response.setContentType("application/json; charset=utf-8");
		    response.getWriter().print("{\"message\":\"");
		    response.getWriter().print("success");
			response.getWriter().print("\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 查询系统 用户
	 */
	@RequestMapping("/admin/getUsers")
	public void getUsers(HttpServletResponse response){
		List<TXtUser> users = userService.selectByParams(new Criteria());
		StringBuffer sb = new StringBuffer();
		sb.append("<select>");
		sb.append("<option value=\"\">请选择</option>");
		for(int i=0;i<users.size();i++){
			sb.append("<option value=\""+users.get(i).getUserId()+"\">"+users.get(i).getName()+"</option>");
		}
		sb.append("</select>");
		
		try {
			response.getWriter().print(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
