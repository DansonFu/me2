package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.common.constant.Me2Constants;
import com.lettucetech.me2.common.utils.DateUtil;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.common.utils.MD5;
import com.lettucetech.me2.common.utils.QiniuUtil;
import com.lettucetech.me2.common.utils.QiniuUtil.MyRet;
import com.lettucetech.me2.common.utils.StringUtil;
import com.lettucetech.me2.common.utils.VerifyCodeUtil;
import com.lettucetech.me2.pojo.Comment;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Customer;
import com.lettucetech.me2.pojo.Game;
import com.lettucetech.me2.pojo.Picture;
import com.lettucetech.me2.pojo.TXtMenu;
import com.lettucetech.me2.pojo.TXtMetoo;
import com.lettucetech.me2.pojo.TXtRoleMenu;
import com.lettucetech.me2.pojo.TXtRoleUser;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.service.CommentService;
import com.lettucetech.me2.service.CustomerService;
import com.lettucetech.me2.service.GameService;
import com.lettucetech.me2.service.PictureService;
import com.lettucetech.me2.service.TXtMenuService;
import com.lettucetech.me2.service.TXtMetooService;
import com.lettucetech.me2.service.TXtRoleMenuService;
import com.lettucetech.me2.service.TXtRoleUserService;
import com.lettucetech.me2.service.TXtUserService;
import com.lettucetech.me2.web.form.DataTablePaginationForm;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;


@Controller
public class AdminController {
	private static final Logger logger = Logger.getLogger(AdminController.class);
	@Autowired
	private PictureService pictureService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private GameService gameService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private TXtUserService usi;
	@Autowired
	private TXtRoleUserService roleUserService;
	@Autowired
	private TXtMenuService menuService;
	@Autowired
	private TXtRoleMenuService roleMenuService;
	@Autowired
	private TXtMetooService metooService;
	//随机蜜图使用
	private List<Picture> pictures;

	/**
	 * 进入登录界面
	 * @param session
	 * @return
	 */
	@RequestMapping("/toLogin")
	public String toLogin(HttpSession session){
		//获取用户session中的TXtUser对象
		TXtUser user=(TXtUser)session.getAttribute(Me2Constants.LOGIN_SESSION_DATANAME);

		if(user!=null){
			return "redirect:/admin/index";
		}
					
		//否则跳转到登录界面
		return "login";
	}
	/**
	 * 退出
	 * @param session
	 * @return
	 */
	@RequestMapping("/admin/logout")
	public String logout(HttpSession session){
		session.removeAttribute(Me2Constants.LOGIN_SESSION_DATANAME);

		return "login";
	}
	/**
	 * 登录
	 * @param userName
	 * @param password
	 * @param verifyCodeClient
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping("/toLogin/login")
	public void login(String userName,String password,String verifyCodeClient,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String message;
		String sessionVerifyCode = (String)session.getAttribute("verifyCode");
		
		if(StringUtils.isEmpty(userName)){
			message="用户名不得为空";
		}
		if(password==null||password==""){
			message="密码不得为空";
		}
		if(verifyCodeClient==null||verifyCodeClient==""){
			message="验证码不得为空";
		}if(StringUtil.isNullOrEmpty(sessionVerifyCode)){
			message="验证码过期，请重新获取!";
		}else{
			
			if(verifyCodeClient.toUpperCase().equals((sessionVerifyCode).toUpperCase())){
				Criteria cri=new Criteria();
				cri.put("account", userName);
				List<TXtUser> userList=this.usi.selectByParams(cri);
				if(userList==null||userList.size()==0){
					message="用户名或密码错误";
				}else{
					MD5 md5=new MD5();
					TXtUser user=userList.get(0);
					if("1".equals(user.getStatus())){
						message="用户已被禁用";
					}else{
						if(user.getPassword().equals(md5.getMD5(password))){
							Criteria roleUserCriteria=new Criteria();
							roleUserCriteria.put("userId", user.getUserId());
							List<TXtRoleUser> roleUserList=this.roleUserService.selectByParams(roleUserCriteria);
							if(roleUserList!=null && roleUserList.size()>0){
								message="success";
								session.setAttribute(Me2Constants.LOGIN_SESSION_DATANAME, user);
							}else{
								message="没有权限，请联系管理员";
							}
							
						}else{
							message="用户名或密码错误";
						}
					}
				}
			}else{
				message="验证码错误";
			}
		}
		
		try {
			response.getWriter().print(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 用于获取验证码图片
	 * @param request
	 * @param response
	 */
	@RequestMapping("/toLogin/getVerifyCodeImage")
	public void getVerifyCodeImage(HttpServletRequest request,HttpServletResponse response){
		VerifyCodeUtil.outputVerifyCode(request, response);
	}
	/**
	 * 进入首页
	 * 
	 * @return
	 */
	@RequestMapping("/admin/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/index");
		Criteria roleUserCriteria=new Criteria();
		roleUserCriteria.put("userId", ((TXtUser)session.getAttribute(Me2Constants.LOGIN_SESSION_DATANAME)).getUserId());
		//查询用户所有角色
		List<TXtRoleUser> roleUserList=this.roleUserService.selectByParams(roleUserCriteria);
		//查询用户所有角色拥有菜单的交集
		List<TXtRoleMenu> roleMenuList=this.roleMenuService.selectMId(roleUserList);
		List<TXtMenu> menuList = null;
		if(roleMenuList!=null&&roleMenuList.size()>0){
			menuList=this.menuService.selectMenus(roleMenuList);
			session.setAttribute(Me2Constants.LOGIN_USER_MENUS, menuList);
		}
		mav.addObject("menu", menuList);
		mav.addObject("menu2", menuList);
		return mav;
	}
	/**
	 * 发布蜜图
	 * @param session
	 * @param pictures
	 * @return
	 */
	@RequestMapping(value = "/admin/metoo", method ={RequestMethod.GET})
	public ModelAndView metoo(HttpSession session){
		Criteria example = new Criteria();
		//example.put("inneruser", "1");
		example.put("del", "0");
		Customer customer = customerService.selectByParams4Rand(example);
		
		example.clear();
		example.put("del", "0");
		List<Game> games = gameService.selectByParams(example);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/metoo");
		mav.addObject("domain", Me2Constants.QINIUPUBLICDOMAIN);
		mav.addObject("customer", customer);
		mav.addObject("games", games);
		return mav;

	}
	/**
	 * 保存蜜图AB面
	 * @param session
	 * @param pictures
	 * @return
	 */
	@RequestMapping(value = "/admin/savemetoo", method ={RequestMethod.POST})
	public ModelAndView savemetoo(HttpSession session,String tags,String feel,String bfeel,String gameid
			,@RequestParam("afile") CommonsMultipartFile afile,@RequestParam("bfile") CommonsMultipartFile bfile
			,String content,String type,String locationTitle,String locationContent,String customerId){
		String akey=null;
		try {
			String token = QiniuUtil.uploadToken(Me2Constants.METOOPULIC);
	        Response res = QiniuUtil.uploadManager.put(afile.getBytes(), null, token);
			MyRet ret = res.jsonToObject(MyRet.class); 
			akey = ret.getKey();
		} catch (QiniuException e) {
			e.printStackTrace();
		}
		//a面
		Picture ap = new Picture();
		ap.setQiniukey(akey);
		//去除空格、制表符、换页符等空白字符,#号换成逗号
		ap.setTags(tags.replaceAll("\\s*", "").replaceAll("#", ","));
		ap.setMood(feel);
		ap.setCustomerId(Integer.valueOf(customerId));
		ap.setFront("a");
		ap.setCreatTime(new Date());
		ap.setLocationTitle(locationTitle);
		ap.setLocationContent(locationContent);
		pictureService.insertSelective(ap);
		
		//保存管理员操作记录
		TXtUser au = (TXtUser) session.getAttribute(Me2Constants.LOGIN_SESSION_DATANAME);
		TXtMetoo record = new TXtMetoo();
		record.setCreatTime(new Date());
		record.setMetoo(ap.getPid());
		record.setState("0");
		record.setUserId(au.getUserId());
		metooService.insertSelective(record);
		
		//如果有B面
		if(!StringUtil.isNullOrEmpty(bfile.getFileItem().getName()) || !StringUtil.isNullOrEmpty(content)){
			String bkey=null;
			//判断B面是什么内容
			if("1".equals(type)){
				try {
					String token = QiniuUtil.uploadToken(Me2Constants.METOOPRIVATE);
			        Response res = QiniuUtil.uploadManager.put(bfile.getBytes(), null, token);
					MyRet ret = res.jsonToObject(MyRet.class); 
					bkey = ret.getKey();
				} catch (QiniuException e) {
					e.printStackTrace();
				}
			}else if("2".equals(type)){
				bkey = content;
			}
			
			//B面
			Picture bp = new Picture();
			bp.setCustomerId(Integer.valueOf(customerId));
			bp.setQiniukey(bkey);
			bp.setFront("b");
			bp.setType(type);
			bp.setGameId(Integer.valueOf(gameid));
			bp.setCreatTime(new Date());
			bp.setParentId(ap.getPid());
			bp.setMood(bfeel);
			pictureService.insertSelective(bp);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/metoo");
		return mav;

	}
	/**
	 * 评论
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/admin/comment", method ={RequestMethod.GET})
	public ModelAndView comment(HttpSession session){
		Criteria example = new Criteria();
		
		//随机10个评论人
		List<Customer> customers = new ArrayList<Customer>();
		example.clear();
		example.put("inneruser", "1");
		example.put("del", "0");
		for(int i=0;i<10;i++){
			customers.add(customerService.selectByParams4Rand(example));
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/comment");
		mav.addObject("domain", Me2Constants.QINIUPUBLICDOMAIN);
		mav.addObject("customers", customers);
		return mav;
	}
	/**
	 * 随机取一个
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "/admin/randmetoo", method ={RequestMethod.GET})
	public void randmetoo(HttpSession session,HttpServletResponse response){
		Criteria example = new Criteria();
		example.put("front", "a");
		example.put("includeb", "yes");
		while(true){
			pictures = pictureService.selectByParams4Rand(example);
			if( pictures.size()>0) break;
		}
		
		//查询已有评论
		example.clear();
		example.put("pid", pictures.get(0).getPid());
		List<Comment> comments = commentService.selectByParams(example);
		
		List merge = new ArrayList();
		merge.add(pictures.get(0));
		merge.add(comments);
		String jsonArray = JsonUtil.Encode(merge);
		try {
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 取下一个
	 * @param session
	 * @param response
	 * @param id
	 */
	@RequestMapping(value = "/admin/nextmetoo/{id}", method ={RequestMethod.GET})
	public void nextmetoo(HttpSession session,HttpServletResponse response,@PathVariable String id){
		Picture picture=null;
		Criteria example = new Criteria();
		//如果是最后一条则重新取一个集合
		for(int i=0;i<pictures.size();i++){
			if(pictures.get(i).getPid().equals(id)&&(i+1)!=pictures.size()){
				picture = pictures.get(i+1);
			}
		}
		if(picture==null){
			
			example.put("front", "a");
			example.put("includeb", "yes");
			while(true){
				pictures = pictureService.selectByParams4Rand(example);
				if( pictures.size()>0) break;
			}
			picture = pictures.get(0);
		}
		//查询已有评论
		example.clear();
		example.put("pid", picture.getPid());
		List<Comment> comments = commentService.selectByParams(example);
		
		List merge = new ArrayList();
		merge.add(picture);
		merge.add(comments);
		String jsonArray = JsonUtil.Encode(merge);
		try {
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/**
 * 保存评论
 * @param session
 * @param pid
 * @param cid
 * @param content
 * @param file
 * @return
 */
	@RequestMapping(value = "/admin/savecomment", method ={RequestMethod.POST})
	public ModelAndView savecomment(HttpSession session,String pid,String[] cid,String[] content,@RequestParam("file") CommonsMultipartFile[] file){
		
		TXtUser au = (TXtUser) session.getAttribute(Me2Constants.LOGIN_SESSION_DATANAME);
		
		for(int i=0;i<content.length;i++){
			
			if(StringUtil.isNullOrEmpty(content[i]) && StringUtil.isNullOrEmpty(file[i].getFileItem().getName())){
				continue;
			}

			String key=null;
			if(!StringUtil.isNullOrEmpty(file[i].getFileItem().getName())){
				try {
					String token = QiniuUtil.uploadToken(Me2Constants.METOOPULIC);
			        Response res = QiniuUtil.uploadManager.put(file[i].getBytes(), null, token);
					MyRet ret = res.jsonToObject(MyRet.class); 
					key = ret.getKey();
				} catch (QiniuException e) {
					e.printStackTrace();
				}
			}
			Comment record = new Comment();
			record.setContent(content[i]);
			record.setCreatTime(new Date());
			record.setCustomerId(Integer.parseInt(cid[i]));
			record.setPid(Integer.parseInt(pid));
			record.setQiniukey(key);
			
			commentService.insertSelective(record);
			
			//保存管理员操作记录
			TXtMetoo metoo = new TXtMetoo();
			metoo.setCreatTime(new Date());
			metoo.setMetoo(record.getCommentId());
			metoo.setState("1");
			metoo.setUserId(au.getUserId());
			metooService.insertSelective(metoo);
		}

		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/comment");
		return mav;
	}
	/**
	 * 跳转查看蜜图页面
	 * @param session
	 * @return
	 */
	@RequestMapping("/admin/viewmetoo")
	public ModelAndView pcase(HttpSession session) {
		List<TXtUser> users = new ArrayList<TXtUser>();
		if(checkPermission(session,"viewall")){
			Criteria example = new Criteria();
			users = usi.selectByParams(example);
			//增加全部的选项
			TXtUser u=new TXtUser();
			u.setUserId(-1);
			u.setName("全部");
			users.add(0, u);
		}else{
			users.add((TXtUser)session.getAttribute(Me2Constants.LOGIN_SESSION_DATANAME));
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("users", users);
		mav.setViewName("/admin/viewmetoo");
		return mav;
	}
	/**
	 * 查询蜜图列表
	 * @param session
	 * @param response
	 * @param aoData
	 * @param userId
	 */
	@RequestMapping("/admin/getmetoo")
	public void getMetoo(HttpSession session,HttpServletResponse response,String aoData,String userId) {
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
	    example.setMysqlOffset(iDisplayStart);
	    example.setMysqlLength(iDisplayLength);
	    example.put("state", "0");
	    example.setOrderByClause("creat_time");
	    example.setSord("desc");
	    //是否有查看所有人权限

		if(!"-1".equals(userId)){
			example.put("userId", userId);
		}
	    
	    int count = metooService.countByParams(example);
	    List<TXtMetoo> metoo = metooService.selectByParams4MetooPicture(example);
	    
	    //拼接翻页数据
	    List list = new ArrayList();
		for(TXtMetoo obj : metoo){
			String aurl = Me2Constants.QINIUPUBLICDOMAIN+"/"+obj.getPicture().getQiniukey();
			String burl="";
			String type="";
			String bmood="";
			//如果有B面
			if(obj.getPicture().getBpicture()!=null){
				if(obj.getPicture().getBpicture().getType().equals("1")){
					burl = QiniuUtil.getDownUrl(obj.getPicture().getBpicture().getQiniukey());
				}else{
					burl = obj.getPicture().getBpicture().getQiniukey();
				}
				type = obj.getPicture().getBpicture().getType();
				
			}
			if(obj.getPicture().getFront().equals("b")){
				bmood=obj.getPicture().getMood();
			}
			
			String[] d = {obj.getPicture().getPid().toString(),obj.getPicture().getCustomer().getUsername(),aurl,obj.getPicture().getMood(),burl,type,
					obj.getPicture().getTags(),bmood,obj.getUser().getName(),
					DateUtil.dateFormatToString(obj.getPicture().getCreatTime(), "yyyy-MM-dd HH:mm:ss"),""};
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
	 * 查询蜜图信息
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping("/admin/viewpicture")
	public ModelAndView viewpicture(HttpSession session,String pid) {
		Picture picture = pictureService.selectByPrimaryKey(Integer.parseInt(pid));
		picture.setTags(picture.getTags().replace(",", "#"));
		if(picture.getBpicture()!=null && ("1".equals(picture.getBpicture().getType())||
				"3".equals(picture.getBpicture().getType())||"4".equals(picture.getBpicture().getType())))
		{
			String key = QiniuUtil.getDownUrl(picture.getBpicture().getQiniukey());
			picture.getBpicture().setQiniukey(key);
		}
		
		Criteria example = new Criteria();
		example.put("pid", pid);
		List<Comment> comments = commentService.selectByParams(example);
		
		example.clear();
		example.put("del", "0");
		List<Game> games = gameService.selectByParams(example);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("picture", picture);
		mav.addObject("comments", comments);
		mav.addObject("games", games);
		mav.addObject("domain", Me2Constants.QINIUPUBLICDOMAIN);
		mav.setViewName("/admin/viewpicture");
		return mav;
	}
	/**
	 * 修改密图
	 * @param session
	 * @param tags
	 * @param mood
	 * @param bmood
	 * @param gameId
	 * @param afile
	 * @param bfile
	 * @param content
	 * @param type
	 * @param locationTitle
	 * @param locationContent
	 * @param pid
	 * @param bpid
	 * @param cid
	 * @param commentContent
	 * @param file
	 * @return
	 */
	@RequestMapping("/admin/updatepicture")
	public ModelAndView updatepicture(HttpSession session,String tags,String mood,String bmood,String gameId
			,@RequestParam("afile") CommonsMultipartFile afile,@RequestParam("bfile") CommonsMultipartFile bfile
			,String content,String type,String locationTitle,String locationContent,String pid,String bpid,
			String[] cid,String[] commentContent,@RequestParam(value ="file",required = false) CommonsMultipartFile[] file){
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
		//a面
		Picture ap = new Picture();
		
		ap.setPid(Integer.valueOf(pid));
		ap.setQiniukey(akey);
		//去除空格、制表符、换页符等空白字符,#号换成逗号
		ap.setTags(tags.replaceAll("\\s*", "").replaceAll("#", ","));
		ap.setMood(mood);
		ap.setFront("a");
		ap.setLocationTitle(locationTitle);
		ap.setLocationContent(locationContent);
		pictureService.updateByPrimaryKeySelective(ap);
		
		//如果有B面
		if(!"".equals(bfile.getFileItem().getName()) || (content!=null && content!="")){
			String bkey=null;
			//判断B面是什么内容
			if("1".equals(type)&&bfile.getFileItem().getName()!=""){
				try {
					String token = QiniuUtil.uploadToken(Me2Constants.METOOPRIVATE);
			        Response res = QiniuUtil.uploadManager.put(bfile.getBytes(), null, token);
					MyRet ret = res.jsonToObject(MyRet.class); 
					bkey = ret.getKey();
				} catch (QiniuException e) {
					e.printStackTrace();
				}
			}else if("2".equals(type)){
				bkey = content;
			}
			
			//B面
			Picture bp = new Picture();
			bp.setQiniukey(bkey);
			bp.setFront("b");
			bp.setType(type);
			bp.setGameId(Integer.valueOf(gameId));
			bp.setParentId(ap.getPid());
			bp.setMood(bmood);
			if(bpid==null || bpid==""){
				bp.setCreatTime(new Date());
				pictureService.insertSelective(bp);
			}else{
				bp.setPid(Integer.valueOf(bpid));
				pictureService.updateByPrimaryKeySelective(bp);
			}
			
		}
		
		if(cid!=null){
			//修改评论
			for(int i=0;i<cid.length;i++){
				String key=null;
				if(!StringUtil.isNullOrEmpty(file[i].getFileItem().getName())){
					try {
						String token = QiniuUtil.uploadToken(Me2Constants.METOOPULIC);
				        Response res = QiniuUtil.uploadManager.put(file[i].getBytes(), null, token);
						MyRet ret = res.jsonToObject(MyRet.class); 
						key = ret.getKey();
					} catch (QiniuException e) {
						e.printStackTrace();
					}
				}
				Comment record = new Comment();
				record.setCommentId(Integer.parseInt(cid[i]));
				record.setContent(commentContent[i]);
				record.setQiniukey(key);
				
				commentService.updateByPrimaryKeySelective(record);
	
			}
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/viewmetoo");
		return mav;
	}
	
	@RequestMapping("/admin/delpicture")
	public ModelAndView delpicture(HttpSession session,String pid){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/viewmetoo");
		
		if(StringUtil.isNullOrEmpty(pid)){
			return mav;
		}
		
		Criteria example = new Criteria();
		//删除评论表
		example.put("pid", pid);
		commentService.deleteByParams(example);
		//删除B面
		example.clear();
		example.put("parentId", pid);
		pictureService.deleteByParams(example);
		//删除A面
		pictureService.deleteByPrimaryKey(Integer.parseInt(pid));
		//删除`t_xt_metoo`表
		example.clear();
		example.put("state", 0);
		example.put("metoo", pid);
		metooService.deleteByParams(example);
		
		return mav;
	}
	
	private boolean checkPermission(HttpSession session,String code){
		List<TXtMenu> menuList = (List<TXtMenu>) session.getAttribute(Me2Constants.LOGIN_USER_MENUS);
		boolean flag = false;
		if(menuList!=null && menuList.size()>0){
			for (TXtMenu tXtMenu : menuList) {
				if(code.equals(tXtMenu.getCode())){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
}
