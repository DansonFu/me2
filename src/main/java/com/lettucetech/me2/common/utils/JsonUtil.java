package com.lettucetech.me2.common.utils;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;






import org.apache.commons.collections.map.ListOrderedMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * Json数据的转换方法
 * 
 * @author zharong
 *
 */
public class JsonUtil {

	/** 将Object转为Json数据 */
	public static String Encode(Object obj) {
		if (obj == null || obj.toString().equals("null"))
			return null;
		if (obj != null && obj.getClass() == String.class) {
			return obj.toString();
		}
		JSONSerializer serializer = new JSONSerializer();
		serializer.transform(new DateTransformer("yyyy-MM-dd' 'HH:mm:ss"), Date.class);
		serializer.transform(new DateTransformer("yyyy-MM-dd' 'HH:mm:ss"), Timestamp.class);
		return serializer.exclude("*.class").deepSerialize(obj);
	}
	/** 将Json数据转为Object */
	public static Object Decode(String json) {
		if (StringUtil.isNullOrEmpty(json))
			return "";
		JSONDeserializer<Object> deserializer = new JSONDeserializer<Object>();
		// deserializer.use(String.class, new
		// DateTransformer("yyyy-MM-dd' 'HH:mm:ss"));
		Object obj = deserializer.deserialize(json);
		if (obj != null && obj.getClass() == String.class) {
			return Decode(obj.toString());
		}
		return obj;
	}
	/** 将Json数据转为Object */
	public static Object Decode(String json,Class c) {
		if (StringUtil.isNullOrEmpty(json))
			return "";
		JSONDeserializer<Object> deserializer = new JSONDeserializer<Object>();
		// deserializer.use(String.class, new
		// DateTransformer("yyyy-MM-dd' 'HH:mm:ss"));
		Object obj = deserializer.deserialize(json, c);
		if (obj != null && obj.getClass() == String.class) {
			return Decode(obj.toString());
		}
		return obj;
	}
	  /**
	    * 
	   * json转换map.
	   * <br>详细说明
	   * @param jsonStr json字符串
	   * @return
	   * @return Map<String,Object> 集合
	   * @throws
	   * @author slj
	    */
	    public static Map<String, Object> parseJSON2Map(String jsonStr){
	        ListOrderedMap map = new ListOrderedMap();
	        //最外层解析
	        JSONObject json = JSONObject.fromObject(jsonStr);
	        for(Object k : json.keySet()){
	            Object v = json.get(k); 
	            //如果内层还是数组的话，继续解析
	            if(v instanceof JSONArray){
	                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	                Iterator<JSONObject> it = ((JSONArray)v).iterator();
	                while(it.hasNext()){
	                    JSONObject json2 = it.next();
	                    list.add(parseJSON2Map(json2.toString()));
	                }
	                map.put(k.toString(), list);
	            } else {
	                map.put(k.toString(), v);
	            }
	        }
	        return map;
	    }
	  
}
