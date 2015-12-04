package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lettucetech.me2.common.constant.Me2Constants;
import com.lettucetech.me2.common.pojo.JqGridHandler;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.common.utils.WebUtil;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.TXtMenu;
import com.lettucetech.me2.service.TXtMenuService;

/**
 * 菜单或按钮
 * @author hwj
 *
 */
@Controller
public class PermissionController {
	
	@Autowired
	private TXtMenuService menuService;
	
	@RequestMapping("/admin/permission")
	public String toPermission(){
		return "system/permission";
	}
	/**
	 * 验证按钮权限
	 * @param response
	 * @param session
	 * @param menuCode
	 */
	@RequestMapping("/admin/checkedButton")
	public void checkedButton(HttpServletResponse response,HttpSession session,String menuCode){

		List<TXtMenu> menuList = (List<TXtMenu>) session.getAttribute(Me2Constants.LOGIN_USER_MENUS);
		String flag = "false";
		if(menuList!=null && menuList.size()>0){
			for (TXtMenu tXtMenu : menuList) {
				if(menuCode.equals(tXtMenu.getCode())){
					flag = "true";
					break;
				}
			}
		}
	    
//		String jsonArray = JsonUtil.Encode(dtpf);
		
		try {
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("aplication/json;charset=UTF-8");
			response.getWriter().print(flag);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 根据条件获取菜单按钮信息列表
	 * @param response
	 * @param jgh 查询条件
	 */
	@RequestMapping("/admin/getPermission")
	public void getDeparmentPost(HttpServletResponse response,JqGridHandler jgh){
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
	
	/**
	 * 对记录进行增删改操作
	 * @param response
	 * @param jgh 删改的id存放在这里
	 * @param material 增删改的其他参数存放在这里
	 */
	@RequestMapping(value="/admin/editPermission",params="oper=add")
	public void editDeparmentPostAdd(HttpServletResponse response,JqGridHandler jgh,TXtMenu menu){
		String message="success";
		if(jgh.getOper()!=null){
			if(jgh.getOper().equals("add")){
				
				menu.setAddTime(new Date());
				menu.setIsLeaf(true);
				if(menu.getpId()==null){
					menu.setLevel(0);
					if(this.menuService.insert(menu)==1){
						message="success";
					}
				}else{
					TXtMenu pdp = this.menuService.selectByPrimaryKey(menu.getpId());
					menu.setLevel(pdp.getLevel()+1);
					pdp.setIsLeaf(false);
					if(this.menuService.updateByPrimaryKeySelective(pdp)==1){
						if(this.menuService.insert(menu)==1){
							message="success";
						}
					}
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
	
	@RequestMapping(value="/admin/editPermission",params="oper=edit")
	public void editDeparmentPostEdit(HttpServletResponse response,JqGridHandler jgh,TXtMenu menu){
		String message="success";
		if(jgh.getOper()!=null){
			if(jgh.getOper().equals("edit")){
				menu.setmId(Integer.parseInt(jgh.getId()));
				menu.setUpdateTime(new Date());
				if(this.menuService.updateByPrimaryKeySelective(menu)==1){
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
	
	@RequestMapping(value="/admin/editPermission",params="oper=del")
	public void editDeparmentPostDel(HttpServletResponse response,JqGridHandler jgh,TXtMenu menu){
		String message="success";
		if(jgh.getOper()!=null){
			if(jgh.getOper().equals("del")){
				menu=this.menuService.selectByPrimaryKey(Integer.parseInt(jgh.getId()));
				if(menu.getIsLeaf()==false){
					message="error";
				}else{
					if(menu.getpId()==null){
						if(this.menuService.deleteByPrimaryKey(menu.getmId())==1){
							message="success";
						}
					}else{
						TXtMenu menuTemp=this.menuService.selectByPrimaryKey(menu.getpId());
						Criteria cri=new Criteria();
						cri.put("pId", menuTemp.getmId());
						if(this.menuService.countByParams(cri)==1){
							menuTemp.setIsLeaf(true);
							if(this.menuService.updateByPrimaryKeySelective(menuTemp)==1){
								if(this.menuService.deleteByPrimaryKey(menu.getmId())==1){
									message="success";
								}
							}
						}else{
							if(this.menuService.deleteByPrimaryKey(menu.getmId())==1){
								message="success";
							}
						}
					}
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