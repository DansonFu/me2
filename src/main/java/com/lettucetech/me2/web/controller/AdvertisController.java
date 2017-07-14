package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lettucetech.me2.common.constant.Me2Constants;
import com.lettucetech.me2.common.utils.JsonUtil;
import com.lettucetech.me2.common.utils.QiniuUtil;
import com.lettucetech.me2.common.utils.StringUtil;
import com.lettucetech.me2.common.utils.QiniuUtil.MyRet;
import com.lettucetech.me2.pojo.Advertis;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Picture1;
import com.lettucetech.me2.pojo.Picturerecommend;
import com.lettucetech.me2.pojo.Recommend;
import com.lettucetech.me2.pojo.TXtMetoo;
import com.lettucetech.me2.pojo.TXtUser;
import com.lettucetech.me2.service.AdvertisService;
import com.lettucetech.me2.web.form.DataTablePaginationForm;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;


@Controller
public class AdvertisController {

	@Autowired 
	private AdvertisService advertis;
	@Autowired
	private AdvertisService advertisService;
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/admin/advertis")
	public ModelAndView showadvertis(HttpSession session){
//		Criteria example = new Criteria();
//		example.setOrderByClause("creat_time");
//		example.setSord("desc");
//		List<Picture> ps = pictureService.selectByParams(example);
		
		
		ModelAndView mav = new ModelAndView();
		//mav.addObject(ps);
		mav.setViewName("/admin/advertis");
		return mav;
		
	}
	/**
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping( "/admin/getAdvertis")
	public void  getAdvertis(HttpSession session,HttpServletResponse response,String aoData){
		
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
		example.setOrderByClause("sort");
		example.setSord("desc");
		//example.put("picture","pid" );
	    example.setMysqlOffset(iDisplayStart);
	    example.setMysqlLength(iDisplayLength);
		 int count = advertis.countByParams(example);
		List<Advertis> games = advertis.selectByParams(example);
		
		
		List list = new ArrayList();
		String aa ="";
		for(Advertis g : games){
			String burl = "";
			burl =  Me2Constants.QINIUPUBLICDOMAIN+"/"+g.getAdpicture();
			String[] d = {g.getId().toString(),burl,aa};
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
	 * 保存广告图片
	 * @param session
	 * @param pictures
	 * @return
	 */
	@RequestMapping(value = "/admin/saveadvertisaaa", method ={RequestMethod.POST})
	public ModelAndView saveadvertis(HttpSession session,@RequestParam("afile") CommonsMultipartFile afile){
		String akey=null;
		try {
			String token = QiniuUtil.uploadToken(Me2Constants.METOOPULIC);
	        Response res = QiniuUtil.uploadManager.put(afile.getBytes(), null, token);
			MyRet ret = res.jsonToObject(MyRet.class); 
			akey = ret.getKey();
		} catch (QiniuException e) {
			e.printStackTrace();
		}
		
		Criteria example = new Criteria();
		List<Advertis> adver = advertis.selectByParams(example);
		List<Integer> list1 = new ArrayList<Integer>(); 
		for(Advertis pcs : adver){
			Integer c = pcs.getSort();
			list1.add(c);
		}
		Integer sort = 0;
		for(int i=0;i<list1.size();i++){
			if(list1.get(i)>	sort){
				sort = list1.get(i);
			}
		}
		
		//a面
		Advertis ap = new Advertis();
		ap.setAdpicture(akey);
		//去除空格、制表符、换页符等空白字符,#号换成逗号
		
		ap.setSort(sort+1);
		advertis.insertSelective(ap);
		
		//保存管理员操作记录
		
		
		//如果有B面
		
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/admin/advertis");
		return mav;

	}
	
	/**
	 * 广告图片当中删除的方法
	 * 
	 */
	@RequestMapping("/admin/del/advertis")
	public  ModelAndView delcommendmetoo(HttpSession session,String id){
		
		
	    Advertis pc = advertis.selectByPrimaryKey(Integer.valueOf(id));
	    Integer  s  = pc.getSort();
	    Criteria example = new Criteria();
	    List<Advertis> pclist = advertis.selectByParams(example);
	    for(Advertis p:pclist){
	    	if(p.getSort()>s){
	    		Integer b = p.getSort();
	    		p.setSort(b-1);
	    		
	    		p.setAdpicture(p.getAdpicture());
	    		advertis.updateByPrimaryKey(p);
	    	}
	    }
	    
	    advertis.deleteByPrimaryKey(Integer.valueOf(id));
	    
//		Picturerecommend pc = new Picturerecommend();
//		
//		pc.setPid(pid);
//		
//		pc.setSort(sort+1);
//		picurerecommendService.insert(pc);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/admin/advertis");
		return mav;
	}
	
	/**
	 * 精选集合当中顺序上调的方法
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping("admin/upadvertis")
	public  ModelAndView upcommend(HttpSession session,String id){
		
		ModelAndView mav = new ModelAndView();
		Advertis list=advertisService.selectByPrimaryKey(Integer.valueOf(id));
		Integer a = list.getSort();
		Integer b = null;
		if(a==1){
			mav.setViewName("/admin/advertis");
		}else{
		Criteria example = new Criteria();
		example.put("sort", a-1);
		List<Advertis> taglist = advertisService.selectByParams(example);
		for(Advertis tagslist:taglist){
			if(tagslist.getSort().equals(a-1)){
				b = tagslist.getId();
				break;
			}
		}
		Advertis list1 = advertisService.selectByPrimaryKey(b);
		list1.setSort(a);
		advertisService.updateByPrimaryKeySelective(list1);
		list.setSort(a-1);
		advertisService.updateByPrimaryKeySelective(list);
		

		mav.setViewName("/admin/advertis");
		}
		return mav;
	}

	/**
	 * 精选集合当中顺序下调的方法
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping("admin/downadvertis")
	public  ModelAndView downcommend(HttpSession session,String id){
		ModelAndView mav = new ModelAndView();
		Advertis list=advertisService.selectByPrimaryKey(Integer.valueOf(id));
		Integer a = list.getSort();
		Integer b = null;
		
		Criteria example = new Criteria();
		example.put("sort", a+1);
		List<Advertis> taglist = advertisService.selectByParams(example);
		for(Advertis tagslist:taglist){
			if(tagslist.getSort().equals(a+1)){
				b = tagslist.getId();
				break;
			}
		}
		if(b==null){
			mav.setViewName("/admin/advertis");
		}else{
			Advertis list1 = advertisService.selectByPrimaryKey(b);
		list1.setSort(a);
		advertisService.updateByPrimaryKeySelective(list1);
		list.setSort(a+1);
		advertisService.updateByPrimaryKeySelective(list);
		}
		
		mav.setViewName("/admin/advertis");
		
		return mav;
	}
	
}
