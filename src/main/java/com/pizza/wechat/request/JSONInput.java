package com.pizza.wechat.request;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import com.pizza.wechat.request.bean.DataMap;
import com.pizza.wechat.util.Utils;

/**
 * API接口请求入参
 * 
 * @ClassName : JSONOutput
 * @author : emmy.cheng
 * @Description : TODO
 * @date : 2015年10月23日 上午12:08:29
 */
public class JSONInput implements DataMap {
	private Map<String, Object> data = new HashMap<String, Object>();

	/**
	 * 添加对象
	 * 
	 * @param key
	 * @param value
	 *            void
	 */
	public void add(String key, Object value) {
		data.put(key, value);
	}

	/**
	 * 获取对象
	 * 
	 * @param key
	 * @return Object
	 */
	public Object get(String key) {
		return data.get(key);
	}

	/**
	 * 获取字符串
	 * 
	 * @param key
	 * @return String
	 */
	public String getString(String key) {
		return Utils.toString(data.get(key));
	}

	/**
	 * 获取整型
	 * 
	 * @param key
	 * @return Integer
	 */
	public Integer getInt(String key) {
		return Utils.toInt(data.get(key));
	}

	/**
	 * 获取长整型
	 * 
	 * @param key
	 * @return Integer
	 */
	public Long getLong(String key) {
		return Utils.toLong(data.get(key));
	}

	/**
	 * 获取对象数组
	 * 
	 * @param key
	 * @return Object[]
	 */
	public Object[] getArray(String key) {
		return (Object[]) data.get(key);
	}

	/**
	 * 删除指定key值
	 * 
	 * @param key
	 *            void
	 */
	public void remove(String key) {
		data.remove(key);
	}

	/**
	 * 清空数据 void
	 */
	public void clear() {
		data.clear();
	}

	/**
	 * 获取参数名
	 * 
	 * @Title:
	 * @Description: TODO
	 * @return
	 */
	public Iterator<String> getKeys() {
		return data.keySet().iterator();
	}
	/**
	 * 查找key
	 * @Title:
	 * @Description: TODO
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key) {
		return data.containsKey(key);
	}
	/**
	 * 数据长度
	 * @Title:
	 * @Description: TODO
	 * @return
	 */
	public int size() {
		return data.size();
	}

	public String toJSONString() {
		return JSONObject.fromObject(data).toString();
	}
}
