package com.hidata.framework.util.xml;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


/**
 * XmlObject 用来做xml相关的操作
 * 它本身是一个xml的对象，并同事具有 {@link Xml} , {@link Map} , {@link Comparable} 的特性
 * 
 * 目前版本没有实现细节
 * @version 0.0
 * @author houzhaowei
 *
 */
public class XmlObject implements Xml ,Map<Object,Object>, Comparable<Object>{
	
	public static XmlObject formObject(){
		return null;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}

	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	public Set<Map.Entry<Object, Object>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Object> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object put(Object key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public void putAll(Map<? extends Object, ? extends Object> m) {
		// TODO Auto-generated method stub
		
	}

	public Object remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Object> values() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

	
}
