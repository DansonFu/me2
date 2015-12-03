package com.lettucetech.me2.web.controller;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

public class Test {
	
	Auth auth = Auth.create("G8vPvJGMMZ_2mCLrTpHUDuuHWHZYg0JoUVpFEPHR", "eK6IFx3sqEg7yTRmL7VsNyx3JGSybesJlso1gy96");


	// 简单上传，使用默认策略
	private String getUpToken0(){
	    return auth.uploadToken("bucket");
	}

	// 覆盖上传
	private String getUpToken1(){
	    return auth.uploadToken("bucket", "key");
	}

	// 设置指定上传策略
	private String getUpToken2(){
	    return auth.uploadToken("bucket", null, 3600, new StringMap()
	         .put("callbackUrl", "call back url").putNotEmpty("callbackHost", "")
	         .put("callbackBody", "key=$(key)&hash=$(etag)"));
	}

	// 设置预处理、去除非限定的策略字段
	private String getUpToken3(){
	    return auth.uploadToken("bucket", null, 3600, new StringMap()
	            .putNotEmpty("persistentOps", "").putNotEmpty("persistentNotifyUrl", "")
	            .putNotEmpty("persistentPipeline", ""), true);
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
//	public String uploadToken(String bucket, String key, long expires, StringMap policy, boolean strict){
//		return key;
//		
//	}
	
	public static void main(String[] args) {
		long e = System.currentTimeMillis()/1000+3600;
		System.out.println(e);
		System.out.println(""+new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date(e * 1000)));

	}

}
