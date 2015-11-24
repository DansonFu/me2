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
