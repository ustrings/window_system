package com.hidata.framework.util;

import net.sf.json.JSONObject;

/**
 * 返回JSON构造器
 * 
 * @author liming
 * @version 2013-07-15
 */
public class JsonBuilder {

	public final static int SUCESS_CODE = 0;
	public final static int FAIL_CODE = 1;

	JSONObject json = new JSONObject();

	public JSONObject getJson() {
		return json;
	}

	public JsonBuilder addRecord(String key, String value) {
		json.put(key, value);
		return this;
	}

	/**
	 * 返回成功JSON串
	 * 
	 * @param content
	 * @return
	 */
	public static String sucess(String content) {
		JSONObject json = new JSONObject();
		json.put("code", SUCESS_CODE);
		json.put("msg", content);
		return json.toString();
	}

	/**
	 * 返回失败JSON串
	 * 
	 * @param content
	 * @return
	 */
	public static String fail(String content) {
		JSONObject json = new JSONObject();
		json.put("code", FAIL_CODE);
		json.put("msg", content);
		return json.toString();
	}

	public static void main(String[] args) {
		System.out.println(new JsonBuilder().addRecord("age", "20").addRecord("birthday", "1987-05-01").getJson());
	}
}
