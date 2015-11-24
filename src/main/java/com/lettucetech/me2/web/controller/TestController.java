package com.lettucetech.me2.web.controller;
import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.lettucetech.me2.common.utils.QiniuUtil.MyRet;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
@Scope("prototype")
@Controller
@RequestMapping("/products")
public class TestController{
	Auth auth = Auth.create("G8vPvJGMMZ_2mCLrTpHUDuuHWHZYg0JoUVpFEPHR", "eK6IFx3sqEg7yTRmL7VsNyx3JGSybesJlso1gy96");
	// 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
	UploadManager uploadManager = new UploadManager();
 /**
  * 测试方法,HelloWord
  * @param request
  * @param response
  * @return
  * @throws Exception
  */
 @RequestMapping(value="/list",method=RequestMethod.GET)
    public String getProducts(HttpServletRequest request,HttpServletResponse response) throws Exception {
   
        request.setAttribute("name", "helloWord");
         
        return "test";
         
    }
 
 @RequestMapping(value="/info/{proId}",method=RequestMethod.GET)
 public String getProductInfo(@PathVariable String proId, HttpServletRequest request,HttpServletResponse response) throws Exception {

       request.setAttribute("name", proId);
        
       return "test";
      
 }
 
 @RequestMapping(value="/info",method=RequestMethod.POST)
 public String insertProduct(Product pro,@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request,HttpServletResponse response) throws Exception {
	    try {
	    	String token = getUpToken();
	        Response res = uploadManager.put(file.getBytes(), null, token);
	        MyRet ret = res.jsonToObject(MyRet.class);
	        System.out.println(res.toString());
	        System.out.println(res.bodyString());
	    } catch (QiniuException e) {
	        Response r = e.response;
	        // 请求失败时简单状态信息
	        System.out.println(r.toString());
	        try {
	            // 响应的文本信息
	        	System.out.println(r.bodyString());
	        } catch (QiniuException e1) {
	            //ignore
	        }
	    }
	    
     request.setAttribute("name", pro.getPid()+"___"+pro.getPname());
      
     return "test";
      
 }
 
 /**
 * 生成上传token
 *
 * @param bucket  空间名
 * @param key     key，可为 null
 * @param expires 有效时长，单位秒。默认3600s
 * @param policy  上传策略的其它参数，如 new StringMap().put("endUser", "uid").putNotEmpty("returnBody", "")。
 *        scope通过 bucket、key间接设置，deadline 通过 expires 间接设置
 * @param strict  是否去除非限定的策略字段，默认true
 * @return 生成的上传token
 */
 public String uploadToken(String bucket, String key, long expires, StringMap policy, boolean strict){
	return null;
	 
 }
 

 


 /**
 * 生成上传token
 *
 * @param bucket  空间名
 * @param key     key，可为 null
 * @param expires 有效时长，单位秒。默认3600s
 * @param policy  上传策略的其它参数，如 new StringMap().put("endUser", "uid").putNotEmpty("returnBody", "")。
 *        scope通过 bucket、key间接设置，deadline 通过 expires 间接设置
 * @param strict  是否去除非限定的策略字段，默认true
 * @return 生成的上传token
 */
	private String getUpToken(){
		//简单上传
//	    return auth.uploadToken("huidian");
		return auth.uploadToken("huidian", null, 3600, new StringMap()
        .putNotEmpty("returnBody", "{\"key\": $(key), \"hash\": $(etag), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height)}"));
	    }
 
}

 class Product{
	 private String pid;
	 private String pname;

	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
}
