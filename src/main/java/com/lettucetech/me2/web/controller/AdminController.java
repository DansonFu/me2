package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.common.utils.QiniuUtil;
import com.lettucetech.me2.common.utils.QiniuUtil.MyRet;
import com.lettucetech.me2.pojo.Comment;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Customer;
import com.lettucetech.me2.pojo.Game;
import com.lettucetech.me2.pojo.Picture;
import com.lettucetech.me2.service.CommentService;
import com.lettucetech.me2.service.CustomerService;
import com.lettucetech.me2.service.GameService;
import com.lettucetech.me2.service.PictureService;
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

	private List<Picture> pictures;
	/**
	 * 保存蜜图AB面
	 * @param session
	 * @param pictures
	 * @return
	 */
	@RequestMapping(value = "/admin/metoo", method ={RequestMethod.GET})
	public ModelAndView metoo(HttpSession session){
		Criteria example = new Criteria();
//		example.put("inneruser", "1");
//		List<Customer> customers = customerService.selectByParams(example);
//		example.clear();
		example.put("del", "0");
		List<Game> games = gameService.selectByParams(example);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/metoo");
//		mav.addObject("customers", customers);
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
	public ModelAndView savemetoo(HttpSession session,String[] username,String[] tags,String[] feel,String[] gameid
			,@RequestParam("afile") CommonsMultipartFile[] afile,@RequestParam("bfile") CommonsMultipartFile[] bfile){
		//内部用户
		Criteria example = new Criteria();
		example.put("inneruser", "1");
		List<Customer> customers = customerService.selectByParams(example);
		
		//随机
		Random rand = new Random();
		for(int i=0;i<afile.length;i++){
			//如果没上传A图，不做处理
			if("".equals(afile[i].getFileItem().getName())){ break;}
						    
		    int listIndex = rand.nextInt(customers.size()); 
			int customerid = customers.get(listIndex).getCustomerId();
			String akey=null;
			try {
				String token = QiniuUtil.uploadToken(Me2Constants.METOOPULIC);
		        Response res = QiniuUtil.uploadManager.put(afile[i].getBytes(), null, token);
				MyRet ret = res.jsonToObject(MyRet.class); 
				akey = ret.getKey();
			} catch (QiniuException e) {
				e.printStackTrace();
			}
			//a面
			Picture ap = new Picture();
			ap.setQiniukey(akey);
			//去除空格、制表符、换页符等空白字符
			ap.setTags(tags[i].replaceAll("\\s*", ""));
			ap.setMood(feel[i]);
			ap.setCustomerId(customerid);
			ap.setFront("a");
			ap.setCreatTime(new Date());
			pictureService.insertSelective(ap);
			
			if(!"".equals(bfile[i].getFileItem().getName())){
				String bkey=null;
				try {
					String token = QiniuUtil.uploadToken(Me2Constants.METOOPRIVATE);
			        Response res = QiniuUtil.uploadManager.put(bfile[i].getBytes(), null, token);
					MyRet ret = res.jsonToObject(MyRet.class); 
					bkey = ret.getKey();
				} catch (QiniuException e) {
					e.printStackTrace();
				}
				//B面
				Picture bp = new Picture();
				bp.setCustomerId(customerid);
				bp.setQiniukey(bkey);
				bp.setFront("b");
				bp.setType("1");
				bp.setGameId(Integer.valueOf(gameid[i]));
				bp.setCreatTime(new Date());
				bp.setParentId(ap.getPid());
				pictureService.insertSelective(bp);
			}
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
		if(pictures==null || pictures.size()==0){
			Criteria example = new Criteria();
			example.put("front", "a");
			example.put("includeb", "yes");
			while(true){
				pictures = pictureService.selectByParams4Rand(example);
				if( pictures.size()>0) break;
			}
		}

		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/comment");
		mav.addObject("domain", Me2Constants.QINIUPUBLICDOMAIN);
		mav.addObject("picture", pictures.get(0));
		mav.addObject("BURL",QiniuUtil.getDownUrl(pictures.get(0).getBpicture().getQiniukey()));
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
		String jsonArray = JsonUtil.Encode(pictures.get(0));
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
		for(int i=0;i<pictures.size();i++){
			if(pictures.get(i).getPid().equals(id)&&(i+1)!=pictures.size()){
				picture = pictures.get(i+1);
			}
		}
		if(picture==null){
			Criteria example = new Criteria();
			example.put("front", "a");
			example.put("includeb", "yes");
			while(true){
				pictures = pictureService.selectByParams4Rand(example);
				if( pictures.size()>0) break;
			}
			picture = pictures.get(0);
		}

		String jsonArray = JsonUtil.Encode(picture);
		try {
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/admin/savecomment", method ={RequestMethod.POST})
	public ModelAndView savecomment(HttpSession session,String pid,String[] content,@RequestParam("file") CommonsMultipartFile[] file){
		//内部用户
		Criteria example = new Criteria();
		example.put("inneruser", "1");
		List<Customer> customers = customerService.selectByParams(example);
		
		//随机
		Random rand = new Random();
		for(int i=0;i<content.length;i++){
			if(content[i]==null && file[i].getFileItem().getName()==null){continue;}
			
		    int listIndex = rand.nextInt(customers.size()); 
			int customerid = customers.get(listIndex).getCustomerId();
			String key=null;
			if(file[i].getFileItem().getName()!=null){
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
			record.setCustomerId(customerid);
			record.setPid(Integer.parseInt(pid));
			record.setQiniukey(key);
			
			commentService.insertSelective(record);
		}

		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/comment");
		return mav;
	}
}
