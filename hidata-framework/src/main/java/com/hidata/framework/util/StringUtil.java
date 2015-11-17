package com.hidata.framework.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 继承自  {@link StringUtils}
 * 并且添加了常用的 String 、 String数组的操作
 * @author houzhaowei
 *
 */
public class StringUtil extends StringUtils{

	/**
	 * 数组中是否包含target 
	 * @param array
	 * @param target
	 * @return 包含返回true，不包含返回false
	 */
	public static boolean contains(String[] array,String target){
		boolean contains = false;
		for(String one : array){
			if(one.equals(target)){
				contains = true;
				break;
			}
		}
		return contains;
	}
	
	/**
	 * 数组中是否有某个元素包含 target 
	 * @param array
	 * @param target
	 * @return 数组中某元素包含target则返回true，否则返回false
	 */
	public static boolean containsItemContains(String[] array,String target){
		boolean contains = false;
		for(String one : array){
			if(target.indexOf(one) != -1){
				contains = true;
			}
		}
		return contains;
	}
	/**
	 * 把List<?>里的对象转换成string，用指定分隔符分隔
	 * @param list
	 * @param separator
	 * @return string
	 * @author pujie
	 */
	public static String list2String(List<?> list,String separator){
		String returnValue = "";
		if(null == list){
			return null;
		}
		for(int i=0;i<list.size();i++){
			returnValue +=list.get(i);
			if(i!=list.size()-1){
				returnValue +=separator;
			}
		}
		return returnValue;
	}
	/**
	 * 把byte数组里的四个字节转换成32位的int型数据
	 * @param b byte[]数组
	 * @param index 从索引index处开始转换
	 * @return int
	 * @author pujie
	 * */
	public static int byteArrayToInt(byte[] b, int index) {  
	       return (int) ((((b[index + 3] & 0xff) << 24)  
			               | ((b[index + 2] & 0xff) << 16)  
			               | ((b[index + 1] & 0xff) << 8) 
			               | ((b[index + 0] & 0xff) << 0)));  
	} 
	/**
	 * 从常规url中提取host
	 * @param url  ex:http://baidu.com/bid
	 * @return host ex：baidu.com
	 * */
   public static String getHostUrl(String url){
	   char[] array = url.toCharArray();
	   int start=0,end=0;
	   for(int i=1;i<array.length;i++){
		   if(array[i]=='/' && array[i]==array[i-1]){
			   start = i+1;continue;
		   }else if(array[i]=='/' && array[i-1]!=':'){
			   end=i;break;
		   }
		   if(i==array.length-1 && end<start){
			   end = array.length;
		   }
	   }
	   return new String(array,start,end-start);
   }
	public static void main(String[] args) {
        
	}
}
