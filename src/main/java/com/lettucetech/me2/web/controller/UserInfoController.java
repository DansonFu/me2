package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lettucetech.me2.common.pojo.JqGridFilterSearch;
import com.lettucetech.me2.common.pojo.JqGridHandler;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.common.utils.MD5;
import com.lettucetech.me2.common.utils.WebUtil;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtDepartment;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.service.TXtDepartmentService;
import com.lettucetech.me2.service.TXtUserService;

/**
 * 用户信息管理控制层
 * @author pwc
 *
 */
@Controller
public class UserInfoController {
	
	@Autowired
	private TXtUserService userService;
	
	@Autowired
	private TXtDepartmentService departmentService;
	
	@RequestMapping("/admin/toUserInfo")
	public String toUserInfo(){
		return "system/userInfo";
	}
	
	/**
	 * 存放Java字段和数据库字段不一致时两者的对应关系
	 */
	private static Map<String,String> map=new HashMap<String,String>();
	static{
		map.put("userId", "user_id");
		map.put("depId", "dep_id");
		map.put("postId", "post_id");
		map.put("createTime", "create_time");
		map.put("updateTime", "update_time");
		map.put("deleteState", "delete_state");
	}
	
	@RequestMapping("/admin/userInfo/getList")
	public void getList(HttpServletResponse response,JqGridHandler jgh){
		//获取设置好的查询条件类，并将Java字段替换为数据库字段
	    try {
	    	JqGridFilterSearch jgfs=jgh.getFilterSearch(new TXtUser(),map);
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
			List<TXtUser> userInfoList= this.userService.advancedSearching(cri);;
			int total=0,records=0;
			records=total=this.userService.advancedSearchingCount(cri);
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
	
	@RequestMapping(value="/admin/userInfo/oper", params="oper=add")
	public void operAdd(HttpServletResponse response,JqGridHandler jgh,TXtUser user){
		String message="error";
		if(jgh.getOper()!=null){
			if(jgh.getOper().equals("add")){
				MD5 md5=new MD5();
				user.setPassword(md5.getMD5("123456"));
				user.setCreateTime(new Date());
				if(this.userService.insertSelective(user)==1){
					message="success";
				}
			}
		}
		response.setContentType("application/json; charset=utf-8");
	    try {
			response.getWriter().print("{\"message\":\"");
			response.getWriter().print(message);
			response.getWriter().print("\"}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="/admin/userInfo/oper", params="oper=edit")
	public void operEdit(HttpServletResponse response,JqGridHandler jgh,TXtUser user){
		String message="error";
		if(jgh.getOper()!=null){
			if(jgh.getOper().equals("edit")){
				user.setUserId(Integer.parseInt(jgh.getId()));
				user.setUpdateTime(new Date());
				if(this.userService.updateByPrimaryKeySelective(user)==1){
					message="success";
				}
			}
		}
		response.setContentType("application/json; charset=utf-8");
	    try {
			response.getWriter().print("{\"message\":\"");
			response.getWriter().print(message);
			response.getWriter().print("\"}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="/admin/userInfo/oper", params="oper=del")
	public void operDel(HttpServletResponse response,JqGridHandler jgh,TXtUser user){
		String message="error";
		if(jgh.getOper()!=null){
			if(jgh.getOper().equals("del")){
				if(this.userService.deleteByPrimaryKey(Integer.parseInt(jgh.getId()))==1){
					message="success";
				}
			}
		}
		response.setContentType("application/json; charset=utf-8");
	    try {
			response.getWriter().print("{\"message\":\"");
			response.getWriter().print(message);
			response.getWriter().print("\"}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("/admin/userInfo/getDepId")
	public void getDepId(HttpServletResponse response){
		List<TXtDepartment> deparmentList=this.departmentService.selectByParams(new Criteria());
		StringBuffer sb = new StringBuffer();
		sb.append("<select size=\"7\">");
		sb.append("<option value=\"\" selected=\"selected\">请选择</option>");
		for(int i=0;i<deparmentList.size();i++){
			sb.append("<option value=\""+deparmentList.get(i).getDepId()+"\">"+deparmentList.get(i).getDepName()+"</option>");
		}
		sb.append("</select>");
		try {
			response.getWriter().print(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/admin/userInfo/getLeader")
	public void getLeader(HttpServletResponse response){
		List<TXtUser> userList=this.userService.selectByParams(new Criteria());
		StringBuffer sb = new StringBuffer();
		sb.append("<select size=\"7\">");
		sb.append("<option value=\"\" selected=\"selected\">请选择</option>");
		for(int i=0;i<userList.size();i++){
			sb.append("<option value=\""+userList.get(i).getUserId()+"\">"+userList.get(i).getName()+"</option>");
		}
		sb.append("</select>");
		try {
			response.getWriter().print(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/admin/userInfo/setPassword")
	public void setPassword(HttpServletResponse response,HttpSession session,String oldPassword,String newPassword){
		String message="error";
		TXtUser user=(TXtUser)session.getAttribute("user");
		MD5 md5=new MD5();
		if(md5.getMD5(oldPassword).equals(user.getPassword())){
			TXtUser newPasswordUser=new TXtUser();
			newPasswordUser.setUserId(user.getUserId());
			newPasswordUser.setPassword(md5.getMD5(newPassword));
			newPasswordUser.setUpdateTime(new Date());
			if(this.userService.updateByPrimaryKeySelective(newPasswordUser)==1){
				message="success";
				session.setAttribute("user", this.userService.selectByPrimaryKey(user.getUserId()));
			}
		}
		StringBuffer json = new StringBuffer();
		json.append("{\"message\":\""+message+"\"}");
		WebUtil.renderJson(response, json.toString());
	}
	
	@RequestMapping("/admin/userInfo/setUserInfo")
	public void setUserInfo(HttpServletResponse response,HttpSession session,TXtUser user){
		String message="error";
		TXtUser sessionUser=(TXtUser)session.getAttribute("user");
		sessionUser.setName(user.getName());
		sessionUser.setPhone(user.getPhone());
		sessionUser.setEmail(user.getEmail());
		sessionUser.setIdcard(user.getIdcard());
		sessionUser.setSex(user.getSex());
		sessionUser.setQq(user.getQq());
		sessionUser.setAddress(user.getAddress());
		sessionUser.setUpdateTime(new Date());
		if(this.userService.updateByPrimaryKeySelective(sessionUser)==1){
			message="success";
			session.setAttribute("user", sessionUser);
		}
		StringBuffer json = new StringBuffer();
		json.append("{\"message\":\""+message+"\"}");
		WebUtil.renderJson(response, json.toString());
	}
	
	@RequestMapping("/admin/userInfo/getUserInfo")
	public void getUserInfo(HttpServletResponse response,HttpSession session){
		TXtUser sessionUser=(TXtUser)session.getAttribute("user");
		TXtUser user=this.userService.selectByPrimaryKey(sessionUser.getUserId());
		StringBuffer json = new StringBuffer();
		json.append("{\"user\":"+JsonUtil.Encode(user)+"}");
		WebUtil.renderJson(response, json.toString());
	}
	
	@RequestMapping("/admin/userInfo/getUserByAccount")
	public void getUserByAccount(HttpServletResponse response,String account){
		StringBuffer json = new StringBuffer();
		Criteria cri=new Criteria();
		cri.put("account", account);
		List<TXtUser> userList=this.userService.selectByParams(cri);
		if(userList!=null&&userList.size()>0){
			json.append("{\"user\":"+JsonUtil.Encode(userList.get(0))+"}");
		}else{
			json.append("{\"user\":"+null+"}");
		}
		WebUtil.renderJson(response, json.toString());
	}

}
