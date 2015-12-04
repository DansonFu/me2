package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lettucetech.me2.common.pojo.JqGridFilterSearch;
import com.lettucetech.me2.common.pojo.JqGridHandler;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.common.utils.WebUtil;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtRole;
import com.lettucetech.me2.service.TXtRoleService;

/**
 * 角色管理控制层
 * @author pwc
 *
 */
@Controller
public class RoleController {
	
	@Autowired
	private TXtRoleService roleService;
	
	@RequestMapping("/admin/toRole")
	public String toRole(){
		return "system/role";
	}
	
	/**
	 * 存放Java字段和数据库字段不一致时两者的对应关系
	 */
	private static Map<String,String> map=new HashMap<String,String>();
	static{
		map.put("roleId", "role_id");
		map.put("createTime", "create_time");
		map.put("updateTime", "update_time");
	}
	
	@RequestMapping("/admin/role/getList")
	public void getList(HttpServletResponse response,JqGridHandler jgh){
		//获取设置好的查询条件类，并将Java字段替换为数据库字段
		JqGridFilterSearch jgfs=jgh.getFilterSearch(new TXtRole(),map);
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
		List<TXtRole> roleList=this.roleService.advancedSearching(cri);
		int total=0,records=0;
		records=total=this.roleService.advancedSearchingCount(cri);
		total=total/jgh.getRows()+(((total%jgh.getRows())==0)?0:1);//总页数
		StringBuffer json=new StringBuffer();
		json.append("{");
		json.append("\"total\":\""+total+"\",");
		json.append("\"page\":\""+jgh.getPage()+"\",");
		json.append("\"records\":\""+records+"\",");
		json.append("\"rows\":");
		json.append(JsonUtil.Encode(roleList));
		json.append("}");
		WebUtil.renderJson(response, json.toString());
	}
	
	@RequestMapping(value="/admin/role/oper", params="oper=add")
	public void operAdd(HttpServletResponse response,JqGridHandler jgh,TXtRole role){
		String message="error";
		if(jgh.getOper()!=null){
			if(jgh.getOper().equals("add")){
				role.setCreateTime(new Date());
				if(this.roleService.insertSelective(role)==1){
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
	
	@RequestMapping(value="/admin/role/oper", params="oper=edit")
	public void operEdit(HttpServletResponse response,JqGridHandler jgh,TXtRole role){
		String message="error";
		if(jgh.getOper()!=null){
			if(jgh.getOper().equals("edit")){
				role.setRoleId(Integer.parseInt(jgh.getId()));
				role.setUpdateTime(new Date());
				if(this.roleService.updateByPrimaryKeySelective(role)==1){
					message="success";
				}
			}
			if(jgh.getOper().equals("del")){
				if(this.roleService.deleteByPrimaryKey(Integer.parseInt(jgh.getId()))==1){
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
	
	
	@RequestMapping(value="/admin/role/oper", params="oper=del")
	public void operDel(HttpServletResponse response,JqGridHandler jgh,TXtRole role){
		String message="error";
		if(jgh.getOper()!=null){
			if(jgh.getOper().equals("del")){
				if(this.roleService.deleteByPrimaryKey(Integer.parseInt(jgh.getId()))==1){
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
}
