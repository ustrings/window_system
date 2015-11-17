package com.hidata.framework.util;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtil {

	/**
	 * 获取相应的xml获取相应元素的值，比如
	 * <code><?xml version="1.0" encoding="utf-8"?><alipay><is_success>F</is_success><error>ILLEGAL_PARTNER</error></alipay></code>
	 * <br>
	 * 获取is_success的值
	 * 
	 * @param xmlStr
	 * @param level
	 *            需要传类似//alipay/is_success
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<String> getContentByKey(String xmlStr, String level) {
		Document document = null;
		SAXReader reader = new SAXReader();
		List<String> valueList = new ArrayList<String>();
		try {
			document = reader.read(new ByteArrayInputStream(xmlStr.trim().getBytes()));
			List list = document.selectNodes(level);
			Iterator iter = list.iterator();

			while (iter.hasNext()) {
				Element element = (Element) iter.next();
				String value = element.getText();
				valueList.add(value);
			}

		} catch (DocumentException e) {
			
		}
		return valueList;
	}
	
	/**
	 * 获取相应的xml获取相应元素的值，比如
	 * <code><?xml version="1.0" encoding="utf-8"?><alipay><is_success>F</is_success><error>ILLEGAL_PARTNER</error></alipay></code>
	 * <br>
	 * 获取is_success的值
	 * 
	 * @param xmlStr
	 * @param level
	 *            需要传类似//alipay/is_success
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getContentByKeyOnly(String xmlStr, String level) {
		Document document = null;
		SAXReader reader = new SAXReader();
		List<String> valueList = new ArrayList<String>();
		try {
			document = reader.read(new ByteArrayInputStream(xmlStr.trim().getBytes()));
			List list = document.selectNodes(level);
			Iterator iter = list.iterator();

			while (iter.hasNext()) {
				Element element = (Element) iter.next();
				String value = element.getText();
				valueList.add(value);
			}

		} catch (DocumentException e) {
			
		}
		if(valueList == null || valueList.size() <1){
			return null;
		}else{
			String confirmValue = valueList.get(0);
			return confirmValue;
		}
	}
	
//	@SuppressWarnings("rawtypes")
//	public static List<Check> getCheckResult(String xmlStr, String level) {
//		List<Check> retCheck = new ArrayList<Check>();
//		Document document = null;
//		SAXReader reader = new SAXReader();
//		try {
//			document = reader.read(new ByteArrayInputStream(xmlStr.trim().getBytes()));
//			List list = document.selectNodes(level);
//			Iterator iter = list.iterator();
//
//			while (iter.hasNext()) {
//				Element element = (Element) iter.next();
//				String price = element.elementText("outcome");
//				String ordernumber = element.elementText("merchant_out_order_no");
//				String transdate = element.elementText("transdate");
//				Check check = new Check();
//				check.setOrdernumber(ordernumber);
//				check.setPrice(price);
//				check.setTransdate(transdate);
//				retCheck.add(check);
//			}
//
//		} catch (DocumentException e) {
//			
//		}
//		return retCheck;
//	}

}
