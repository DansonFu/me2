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
import com.lettucetech.me2.pojo.TXtRoleUser;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.service.TXtRoleService;
import com.lettucetech.me2.service.TXtRoleUserService;
import com.lettucetech.me2.service.TXtUserService;

/**
 * 角色权限设置控制层
 * @author pwc
 *
 */
@Controller
public class RoleUserController {
	
	@Autowired
	private TXtRoleService roleService;
	
	@Autowired
	private TXtUserService userService;
	
	@Autowired
	private TXtRoleUserService roleUserService;
	
	@RequestMapping("/admin/toRoleUser")
	public String toRolePower(){
		return "system/roleUser";
	}
	
	@RequestMapping("/admin/roleUser/getUserList")
	public void getUserList(HttpServletResponse response){
		Criteria cri=new Criteria();
		List<TXtUser> userInfoList=this.userService.advancedSearching(cri);
		StringBuffer json=new StringBuffer();
		json.append("{");
		json.append("\"total\":\""+1+"\",");
		json.append("\"page\":\""+1+"\",");
		json.append("\"records\":\""+userInfoList.size()+"\",");
		json.append("\"rows\":");
		json.append(JsonUtil.Encode(userInfoList));
		json.append("}");
		WebUtil.renderJson(response, json.toString());
	}
	
	@RequestMapping("/admin/roleUser/getRoleList")
	public void getRoleList(HttpServletResponse response){
		Criteria cri=new Criteria();
		List<TXtRole> roleList=this.roleService.advancedSearching(cri);
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
	
	@RequestMapping("/admin/roleUser/getUserSelected")
	public void getPerSelected(HttpServletResponse response,JqGridHandler jgh,Integer selectedId){
		Criteria criteria = new Criteria();
		//criteria.setOrderByClause("name desc");
		criteria.put("roleId", selectedId);
		List<TXtRoleUser> roleUser = this.roleUserService.selectByParams(criteria);
		StringBuffer json=new StringBuffer();
		json.append("{");
		json.append("\"rows\":");
		json.append(JsonUtil.Encode(roleUser));
		json.append("}");
		WebUtil.renderJson(response, json.toString());
	}
//	
//	@RequestMapping("/admin/roleUser/getPermission")
//	public void getDeparmentPost(HttpServletResponse response,JqGridHandler jgh,Integer selectedId){
//		Criteria criteria = new Criteria();
//		//criteria.setOrderByClause("name desc");
//		List<TXtMenu> menus = this.menuService.selectByParams(criteria);
//		StringBuffer json=new StringBuffer();
//		json.append("{");
//		json.append("\"rows\":");
//		json.append(JsonUtil.Encode(treeToList(listToTree(menus))));
//		json.append("}");
//		WebUtil.renderJson(response, json.toString());
//	}
//	
	@RequestMapping("/admin/roleUser/saveRoleSelected")
	public void savePerSelected(HttpServletResponse response,String selectedIds,String selectedId){
		if(this.roleUserService.saveRoleSelected(selectedIds, selectedId)){
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
