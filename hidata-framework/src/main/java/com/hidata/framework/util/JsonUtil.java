package com.hidata.framework.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;


/**
 * JSON与实体bean之间转换
 * 
 * @author liming
 * @version 2013-07-22
 * 
 */
public class JsonUtil {
	private final static ObjectMapper mapper = Common4JsonUtil.getMapperInstance(false);

	/**
	 * bean 2 JSON
	 * 
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static String beanToJson(Object obj) throws IOException {
		StringWriter writer = new StringWriter();
		JsonGenerator gen = new JsonFactory().createJsonGenerator(writer);
		mapper.writeValue(gen, obj);
		gen.close();
		String json = writer.toString();
		writer.close();
		return json;
	}

	/**
	 * JSON 2 BEAN
	 * 
	 * @param json
	 * @param cls
	 * @return
	 * @throws Exception
	 */
    public static <T> T jsonToBean(String json, Class<T> cls) throws Exception {
		T vo = mapper.readValue(json, cls);
		return vo;
	}
    
	public static void main(String[] args) throws Exception {
//		// 准备数据
//		Lecard lecard = new Lecard() ;
//		lecard.setBatch("batch") ;
//		lecard.setCardNumber("123456") ;
//		// 实体转JSON字符串
//		String json = beanToJson(lecard);
//		System.out.println("bean 2 json:" + json);
//
//		Object limit2 = jsonToBean(json, Lecard.class);
//		System.out.println(limit2.toString());
	}
}
