package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lettucetech.me2.common.pojo.JqGridHandler;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.common.utils.WebUtil;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtMenu;
import com.lettucetech.me2.pojo.TXtRole;
import com.lettucetech.me2.pojo.TXtRoleMenu;
import com.lettucetech.me2.service.TXtMenuService;
import com.lettucetech.me2.service.TXtRoleMenuService;
import com.lettucetech.me2.service.TXtRoleService;

/**
 * 角色权限设置控制层
 * @author pwc
 *
 */
@Controller
public class RolePowerController {
	
	@Autowired
	private TXtRoleService roleService;
	
	@Autowired
	private TXtMenuService menuService;
	
	@Autowired
	private TXtRoleMenuService roleMenuService;
	
	@RequestMapping("/admin/toRolePower")
	public String toRolePower(){
		return "system/rolePower";
	}
	
	@RequestMapping("/admin/rolePower/getList")
	public void getList(HttpServletResponse response){
		List<TXtRole> roleList=this.roleService.selectAll();
		StringBuffer json=new StringBuffer();
		json.append("{");
		json.append("\"total\":\""+1+"\",");
		json.append("\"page\":\""+1+"\",");
		json.append("\"records\":\""+roleList.size()+"\",");
		json.append("\"rows\":");
		json.append(JsonUtil.Encode(roleList));
		json.append("}");
		WebUtil.renderJson(response, json.toString());
	}
	
	@RequestMapping("/admin/rolePower/getPerSelected")
	public void getPerSelected(HttpServletResponse response,JqGridHandler jgh,Integer selectedId){
		Criteria criteria = new Criteria();
		//criteria.setOrderByClause("name desc");
		criteria.put("roleId", selectedId);
		List<TXtRoleMenu> menus = this.roleMenuService.selectByParams(criteria);
		StringBuffer json=new StringBuffer();
		json.append("{");
		json.append("\"rows\":");
		json.append(JsonUtil.Encode(menus));
		json.append("}");
		WebUtil.renderJson(response, json.toString());
	}
	
	@RequestMapping("/admin/rolePower/getPermission")
	public void getDeparmentPost(HttpServletResponse response,JqGridHandler jgh,Integer selectedId){
		Criteria criteria = new Criteria();
		//criteria.setOrderByClause("name desc");
		List<TXtMenu> menus = this.menuService.selectByParams(criteria);
		StringBuffer json=new StringBuffer();
		json.append("{");
		json.append("\"rows\":");
		json.append(JsonUtil.Encode(treeToList(listToTree(menus))));
		json.append("}");
		WebUtil.renderJson(response, json.toString());
	}
	
	@RequestMapping("/admin/rolePower/savePerSelected")
	public void savePerSelected(HttpServletResponse response,String selectedIds,String selectedId){
		if(this.roleMenuService.savePerSelected(selectedIds, selectedId)){
			StringBuffer json=new StringBuffer();
			json.append("{");
			json.append("\"message\":\"success\"");
			json.append("}");
			WebUtil.renderJson(response, json.toString());
		}else{
			StringBuffer json=new StringBuffer();
			json.append("{");
			json.append("\"message\":\"error\"");
			json.append("}");
			WebUtil.renderJson(response, json.toString());
		}
		
	}
	
	///用于转换listtree//////////////////////////////////////////////////////////////////////////////////////
		class MenuTree{
			private TXtMenu parent;
			private List<MenuTree> child=new ArrayList<MenuTree>();
			public TXtMenu getParent() {
				return parent;
			}
			public void setParent(TXtMenu parent) {
				this.parent = parent;
			}
			public List<MenuTree> getChild() {
				return child;
			}
			public void setChild(List<MenuTree> child) {
				this.child = child;
			}
		}
		
		public MenuTree listToTree(List<TXtMenu> menuList){
			MenuTree root=new MenuTree();
			root.setParent(new TXtMenu());
			for(int i=0;i<menuList.size();i++){
				if(menuList.get(i).getpId()==null){
					MenuTree st=new MenuTree();
					st.setParent(menuList.get(i));
					root.getChild().add(st);
					menuList.remove(i--);
				}
			}
			listToTreeWhile(root, menuList);
			return root;
		}
		
		private void listToTreeWhile(MenuTree root,List<TXtMenu> menuList){
			for(int i=0;i<root.getChild().size();i++){
				for(int j=0;j<menuList.size();j++){
					if(menuList.get(j).getpId()==root.getChild().get(i).getParent().getmId()){
						MenuTree st=new MenuTree();
						st.setParent(menuList.get(j));
						root.getChild().get(i).getChild().add(st);
						menuList.remove(j--);
					}
				}
				listToTreeWhile(root.getChild().get(i),menuList);
			}
		}
		
		public List<TXtMenu> treeToList(MenuTree root){
			List<TXtMenu> deparmentPostList=new ArrayList<TXtMenu>();
			treeToListWhile(root,deparmentPostList);
			deparmentPostList.remove(0);
			return deparmentPostList;
		}
		
		private void treeToListWhile(MenuTree root,List<TXtMenu> menuList){
			menuList.add(root.getParent());
			for(int i=0;i<root.getChild().size();i++){
				treeToListWhile(root.getChild().get(i),menuList);
			}
		}
		/////////////////////////////////////////////////////////////////////////////////////////

}
