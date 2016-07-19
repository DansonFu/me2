package com.lettucetech.me2.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lettucetech.me2.common.pojo.AccessToken;
import com.lettucetech.me2.common.pojo.Button;
import com.lettucetech.me2.common.pojo.CommonButton;
import com.lettucetech.me2.common.pojo.ComplexButton;
import com.lettucetech.me2.common.pojo.Menu;
import com.lettucetech.me2.common.utils.WeixinUtil;

	

	/**
	* 类名: MenuManager </br>
	* 包名： com.souvc.weixin.main
	* 描述:菜单管理器类 </br>
	* 开发人员： liuhf </br>
	* 创建时间：  2015-12-1 </br>
	* 发布版本：V1.0  </br>
	 */
	public class MenuManager {
	    private static Logger log = LoggerFactory.getLogger(MenuManager.class);

//	    public static void main(String[] args) throws  Exception {
//	        // 第三方用户唯一凭证
//	    	String appId="wx7db28beaa8f18221";
//	  	 
//	        // 第三方用户唯一凭证密钥
//	        String appSecret = "44c72f00e918c74d5fc60588d49ed923";
//
//	        // 调用接口获取access_token
//	        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
//
//	        if (null != at) {
//	            // 调用接口创建菜单
//	            int result = WeixinUtil.createMenu(getMenu(), at.getAccessToken());
//
//	            // 判断菜单创建结果
//	            if (0 == result)
//	                log.info("菜单创建成功！");
//	            else
//	                log.info("菜单创建失败，错误码：" + result);
//	        }
//	    }
//
//	    /**
//	     * 组装菜单数据
//	     * 
//	     * @return
//	     */
//	    private static Menu getMenu() {
//	        CommonButton btn11 = new CommonButton();
//	        btn11.setName("开赛");
//	        btn11.setType("view");
//	       btn11.setUrl("http://192.168.0.128:8080/wait1");
//	        
//
//	        CommonButton btn12 = new CommonButton();
//	        btn12.setName("邀赛");
//	        btn12.setType("view");
//	       btn12.setUrl("http://192.168.0.128:8080/invited");
//	        
//
//	        CommonButton btn13 = new CommonButton();
//	        btn13.setName("积分榜");
//	        btn13.setType("click");
//	        btn13.setKey("13");
//	        
////	        CommonButton btn21 = new CommonButton();
////	        btn13.setName("神兜兜1");
////	        btn13.setType("click");
////	        btn13.setKey("21");
////
//	        CommonButton btn31 = new CommonButton();
//	        btn31.setName("积分");
//	        btn31.setType("click");
//	        btn31.setKey("31");
//
//	        CommonButton btn32 = new CommonButton();
//	        btn32.setName("APP");
//	        btn32.setType("click");
//	        btn32.setKey("32");
//
//	        
//
//	        
//	        /**
//	         * 微信：  mainBtn1,mainBtn2,mainBtn3底部的三个一级菜单。
//	         */
//	        
//	        ComplexButton mainBtn1 = new ComplexButton();
//	        mainBtn1.setName("找红人");
//	        //一级下有4个子菜单
//	        mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13});
//
//	        
////	        ComplexButton mainBtn2 = new ComplexButton();
////	        mainBtn2.setName("神兜兜");
////	        mainBtn1.setSub_button(new CommonButton[] { btn21});
////
////	        
//	        ComplexButton mainBtn3 = new ComplexButton();
//	        mainBtn3.setName("我的");
//	        mainBtn3.setSub_button(new CommonButton[] { btn31, btn32 });
//
//	        
//	        /**
//	         * 封装整个菜单
//	         */
//	        Menu menu = new Menu();
//	        menu.setButton(new Button[] { mainBtn1,mainBtn3});
//
//	        return menu;
//	    }
	/**
	  * 获得ACCESS_TOKEN
	 * @Title: getAccess_token
	 * @Description: 获得ACCESS_TOKEN
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	  */
	 private static String getAccess_token(){  

	  String APPID="wx7db28beaa8f18221";
	  String APPSECRET="44c72f00e918c74d5fc60588d49ed923";

	       String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ APPID + "&secret=" +APPSECRET;
	       String accessToken = null;
	      try {
	             URL urlGet = new URL(url);
	             HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();    

	             http.setRequestMethod("GET");      //必须是get方式请求    
	             http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
	             http.setDoOutput(true);        
	             http.setDoInput(true);
	             System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
	             System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
	             http.connect();

	             InputStream is =http.getInputStream();
	             int size =is.available();
	             byte[] jsonBytes =new byte[size];
	             is.read(jsonBytes);
	             String message=new String(jsonBytes,"UTF-8");

	             JSONObject demoJson = new JSONObject(message);
	             accessToken = demoJson.getString("access_token");

	             System.out.println(message);
	             } catch (Exception e) {
	                 e.printStackTrace();
	             }
	        return accessToken;
	     }

	 /**
	  * 创建Menu
	 * @Title: createMenu
	 * @Description: 创建Menu
	 * @param @return
	 * @param @throws IOException    设定文件
	 * @return int    返回类型
	 * @throws
	  */
	    public static String createMenu() {
	      String menu = "{\"button\":[{\"name\":\"找红人\",\"sub_button\":[{\"type\":\"view\",\"name\":\"开赛\",\"url\":\"http://192.168.0.128:8080/wait1\"},{\"type\":\"view\",\"name\":\"邀赛\",\"url\":\"http://192.168.0.128:8080/invited\"},{\"type\":\"click\",\"name\":\"积分榜\",\"key\":\"03_SCORES\"}]},{\"type\":\"click\",\"name\":\"神兜兜\",\"key\":\"1\"},{\"name\":\"我的\",\"sub_button\":[{\"type\":\"click\",\"name\":\"积分\",\"key\":\"01_SCORE\"},{\"type\":\"click\",\"name\":\"APP\",\"key\":\"02_APPLICATION\"}]}]}";

	        //此处改为自己想要的结构体，替换即可
	        String access_token= getAccess_token();
	        String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
	        try {
	           URL url = new URL(action);
	           HttpURLConnection http =   (HttpURLConnection) url.openConnection();    

	           http.setRequestMethod("POST");        
	           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
	           http.setDoOutput(true);        
	           http.setDoInput(true);
	           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
	           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
	           http.connect();
	           OutputStream os= http.getOutputStream();    
	           os.write(menu.getBytes("UTF-8"));//传入参数    
	           os.flush();
	           os.close();

	           InputStream is =http.getInputStream();
	           int size =is.available();
	           byte[] jsonBytes =new byte[size];
	           is.read(jsonBytes);
	           String message=new String(jsonBytes,"UTF-8");
	           return "返回信息"+message;
	           } catch (MalformedURLException e) {
	               e.printStackTrace();
	           } catch (IOException e) {
	               e.printStackTrace();
	           }    
	        return "createMenu 失败";
	   }

	    /**
	     * 删除当前Menu
	    * @Title: deleteMenu
	    * @Description: 删除当前Menu
	    * @param @return    设定文件
	    * @return String    返回类型
	    * @throws
	     */
	   public static String deleteMenu()
	   {
	       String access_token= getAccess_token();
	       String action = "https://api.weixin.qq.com/cgi-bin/menu/delete? access_token="+access_token;
	       try {
	          URL url = new URL(action);
	          HttpURLConnection http =   (HttpURLConnection) url.openConnection();    

	          http.setRequestMethod("GET");        
	          http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
	          http.setDoOutput(true);        
	          http.setDoInput(true);
	          System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
	          System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
	          http.connect();
	          OutputStream os= http.getOutputStream();    
	          os.flush();
	          os.close();

	          InputStream is =http.getInputStream();
	          int size =is.available();
	          byte[] jsonBytes =new byte[size];
	          is.read(jsonBytes);
	          String message=new String(jsonBytes,"UTF-8");
	          return "deleteMenu返回信息:"+message;
	          } catch (MalformedURLException e) {
	              e.printStackTrace();
	          } catch (IOException e) {
	              e.printStackTrace();
	          }
	       return "deleteMenu 失败";   
	   }
	 public static void main(String[] args) {

	  System.out.println(createMenu());
	 }
	
}
