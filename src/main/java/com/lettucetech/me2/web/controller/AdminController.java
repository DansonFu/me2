package com.lettucetech.me2.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.common.constant.Me2Constants;
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
	
	@RequestMapping(value = "/admin/comment", method ={RequestMethod.GET})
	public ModelAndView comment(HttpSession session){
		Criteria example = new Criteria();
		example.put("includeb", "yes");
		if(pictures==null){
			pictures = pictureService.selectByParams4Business(example);
		}
		//随机取一个
		Random rand = new Random();
		Picture picture = pictures.get(rand.nextInt(pictures.size()));
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/comment");
//		mav.addObject("customers", customers);
		mav.addObject("picture", picture);
		return mav;

	}
}
