package com.lettucetech.me2.common.utils;

import java.sql.Timestamp;
import java.util.Date;

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
}
