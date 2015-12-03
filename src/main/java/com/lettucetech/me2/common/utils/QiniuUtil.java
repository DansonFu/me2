package com.lettucetech.me2.common.utils;

import com.lettucetech.me2.common.constant.Me2Constants;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

public class QiniuUtil {
	private static Auth auth = Auth.create(Me2Constants.ACCESSKEY, Me2Constants.SECRETKEY);
	public static UploadManager uploadManager = new UploadManager();
	/**
	 * 7牛简单上传token
	 * @param bucket
	 * @return
	 */
	public static String uploadToken(String bucket){
		return uploadToken(bucket,null,60*1000);
	}
	/**
	 * 7牛覆盖上传token
	 * @param bucket
	 * @param key
	 * @return
	 */
	public static String uploadToken(String bucket,String key){
		return uploadToken(bucket,key,60*1000);
	}
	
	/**
	 * 
	 * @param bucket
	 * @param key
	 * @param expires
	 * @return
	 */
	public static String uploadToken(String bucket, String key, long expires){
		return auth.uploadToken(bucket, key, expires, new StringMap()
	    .putNotEmpty("returnBody", "{\"key\": $(key), \"hash\": $(etag), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height)}"));
	}
	/**
	 * 取得7牛的图片下载地址
	 * @param key
	 * @return
	 */
	public static String getDownUrl(String key){
		//默认有效时长：3600秒
		String urlSigned = auth.privateDownloadUrl(Me2Constants.QINIUPRIVATEDOMAIN+"/"+key);
		
		return urlSigned;
	}
	
//	/**
//	 * 取得7牛的图片下载地址
//	 * @param key
//	 * @return
//	 */
//	public static String getDownUrl(String key){
//		//当前时间+1小时的Unix时间戳
//		long e = System.currentTimeMillis()/1000+3600;
//		String url = Me2Constants.QINIUPRIVATEDOMAIN+"/"+key+"?e="+e;
//		String sign = hmacsha1(key,Me2Constants.SECRETKEY);
//		String token = Me2Constants.ACCESSKEY+":"+sign;
//		return url+"&token="+token;
//	}
//	
//	/**  
//     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名  
//     * @param encryptText 被签名的字符串  
//     * @param encryptKey  密钥  
//     * @return  
//     * @throws Exception  
//     */ 
//	public static String hmacsha1(String encryptText,String encryptKey){
//		String result=null;
//		try {
//			byte[] data = encryptKey.getBytes("UTF-8");
//			 //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称  
//	        SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");   
//	        //生成一个指定 Mac 算法 的 Mac 对象  
//	        Mac mac = Mac.getInstance("HmacSHA1");   
//	        //用给定密钥初始化 Mac 对象  
//	        mac.init(secretKey);    
//	       //完成 Mac 操作   
//	        byte[] text = encryptText.getBytes("UTF-8"); 
//	        byte[] finalText = mac.doFinal(text);
//	        result = Base64.encodeToString(finalText, Base64.URL_SAFE | Base64.NO_WRAP);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}  
//       
//        return result; 
//	}
	
	public  class MyRet {
		    private long fsize;
		    private String key;
		    private String hash;
		    private int width;
		    private int height;
			public long getFsize() {
				return fsize;
			}
			public void setFsize(long fsize) {
				this.fsize = fsize;
			}
			public String getKey() {
				return key;
			}
			public void setKey(String key) {
				this.key = key;
			}
			public String getHash() {
				return hash;
			}
			public void setHash(String hash) {
				this.hash = hash;
			}
			public int getWidth() {
				return width;
			}
			public void setWidth(int width) {
				this.width = width;
			}
			public int getHeight() {
				return height;
			}
			public void setHeight(int height) {
				this.height = height;
			}
		    
		}
}
