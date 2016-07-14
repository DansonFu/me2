package com.pizza.wechat.request;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

import com.pizza.wechat.request.bean.DataMap;
import com.pizza.wechat.util.Utils;
/**
 * 集合类型
 * @ClassName : JSONList
 * @author : emmy.cheng
 * @date : 2015年11月14日 下午4:51:13
 */
public class JSONList implements DataMap {
	private List<Object> list = new ArrayList<Object>(10);
	/**
	 * 在指定位置插入元素
	 * @param index
	 * @param value
	 */
	public void add(int index,Object value){
		list.add(index, value);;
	}
	/**
	 * 添加元素
	 * @param value
	 */
	public void add(Object value) {
		list.add(value);
	}
	/**
	 * 获取元素
	 * @param index
	 * @return
	 */
	public Object get(int index) {
		return list.get(index);
	}
	/**
	 * 获取元素字符
	 * @param index
	 * @return
	 */
	public String getString(int index) {
		return Utils.toString(get(index));
	}
	/**
	 * 获取元素整型
	 * @param index
	 * @return
	 */
	public Integer getInt(int index) {
		return Utils.toInt(get(index));
	}
	/**
	 * 获取元素长整形
	 * @param index
	 * @return
	 */
	public Long getLong(int index) {
		return Utils.toLong(get(index));
	}
	/**
	 * 根据索引删除元素
	 * @param index
	 */
	public void remove(int index) {
		list.remove(index);
	}
	/**
	 * 根据对象删除元素
	 * @param value
	 */
	public void remove(Object value) {
		list.remove(value);
	}
	/**
	 * 获取迭代器
	 * @return
	 */
	public Iterator<Object> iterator() {
		return list.iterator();
	}
	/**
	 * 是否为空
	 * @return
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}
	/**
	 * 集合长度
	 * @return
	 */
	public int size() {
		return list.size();
	}
	/**
	 * 查找元素
	 * @param o
	 * @return
	 */
	public boolean contains(Object o) {
		return list.contains(o);
	}
	/**	
	 * 清空元素
	 */
	public void clear(){
		list.clear();
	}
	
	@Override
	public String toJSONString() {
		return JSONArray.fromObject(list).toString();
	}

}
