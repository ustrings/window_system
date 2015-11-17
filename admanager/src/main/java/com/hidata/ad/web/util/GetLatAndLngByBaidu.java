package com.hidata.ad.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.hidata.ad.web.dto.LatLngDto;
import com.hidata.framework.util.JsonUtil;

/**
 * 获取经纬度通过
 * 
 * @author jueyue 返回格式：Map<String,Object> map map.put("status",
 *         reader.nextString());//状态 map.put("result", list);//查询结果
 *         list<map<String,String>> 密钥:f247cdb592eb43ebac6ccd27f796e2d2
 */
public class GetLatAndLngByBaidu {
	
	private static final String KEY = "lUPU65SRkH2eVGw4m9dOd2uN";
	private static final String URL_STR = "http://api.map.baidu.com/geocoder?address=%s&output=json&key=%s";
	

	/**
	 * @param addr
	 *            查询的地址
	 * @return
	 * @throws IOException
	 */
	public static LatLngDto getCoordinate(String addr) throws IOException {
		String address = null;
		try {
			address = java.net.URLEncoder.encode(addr, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String url = String
				.format(URL_STR, address, KEY);
		URL myURL = null;
		URLConnection httpsConn = null;
		StringBuffer result = new StringBuffer();
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		InputStreamReader insr = null;
		BufferedReader br = null;
		try {
			httpsConn = (URLConnection) myURL.openConnection();// 不使用代理
			if (httpsConn != null) {
				insr = new InputStreamReader(httpsConn.getInputStream(),"UTF-8");
				br = new BufferedReader(insr);
				String data = null;
				while ((data = br.readLine()) != null) {
					result.append(data);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (insr != null) {
				insr.close();
			}
			if (br != null) {
				br.close();
			}
		}
		JSONObject jsObj = JSONObject.fromObject(result.toString());
		jsObj = (JSONObject) jsObj.get("result");
		jsObj = (JSONObject) jsObj.get("location");
		LatLngDto dto = new LatLngDto();
		
		Object objLat = jsObj.get("lat");
		if(objLat != null ){
			dto.setLat(objLat.toString());
		} else {
			dto.setLat(null);
		}
		Object objLng = jsObj.get("lng");
		if(objLng != null ){
			dto.setLng(objLng.toString());
		} else {
			dto.setLng(null);
		}
		
		return dto;
	}

	public static void main(String[] args) throws IOException {
//		getCoordinate("上海市黄浦区建国中路29号");
		getCoordinate("test");
	}

}