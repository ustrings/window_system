package com.vaolan.sspserver.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取url中的顶级域名不包括子域名
 */
public class DomainUtils {

	public static void main(String[] args) throws MalformedURLException {
//		String url = "http://www.jfox.info";
//		String url = "http://www.jfox.info";
//		String url = "http://www.jfox.info";
		String url = "http://1682235.blog.163.com/blog/static/540753232014115101047977/";
		String topDoamin = getTopDomainWithoutSubdomain(url);
		System.out.println(topDoamin);
	}

	/**
	 * 获取 url 的顶级域名
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 */
	public static String getTopDomainWithoutSubdomain(String url) {

		String host = null;
		try {
			host = new URL(url).getHost().toLowerCase();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}// 此处获取值转换为小写
		Pattern pattern = Pattern.compile("[^\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.cc|\\.me|\\.tel|\\.mobi|\\.asia|\\.biz|\\.info|\\.name|\\.tv|\\.hk|\\.公司|\\.中国|\\.网络)");
		Matcher matcher = pattern.matcher(host);
		while (matcher.find()) {
			return matcher.group();
		}
		return null;
	}
}
