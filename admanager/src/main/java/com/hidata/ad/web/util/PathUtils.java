package com.hidata.ad.web.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PathUtils {
	/**
	 * 获取工程根目录路径
	 */
	public static String getRootPath() {
		return PathUtils.class.getResource("/").getPath();
	}
	
	/**
	 * 获取工程根目录路径
	 */
	public static String getProjectPath() {
		File dir = new File("");
		String courseFile = null;
		try {
			courseFile = dir.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return courseFile;
	}
	
	/**
	 * 通过 user.dir 获取路径
	 * @return
	 */
	public static String getProjectPathByUser() {
		return System.getProperty("user.dir");
	}
	
	/**
	 * 获取当前项目的所有路径，包括 jar 包路径
	 * @return
	 */
	public static String getAllPaths() {
		return System.getProperty("java.class.path");
	}
	
	/**
	 * 获取当前类路径
	 * @return
	 */
	public static <T> String getCurPath(Class<T> t) {
		File f = new File(t.getResource("").getPath());
		return f.toString();
	}
	
	/**
	 * 获取当前资源所在路径：资源必存在于 src 目录下
	 * @param resourceName
	 * @return
	 */
	public static String getCurPath(String resourceName) {
		URL path = PathUtils.class.getClassLoader().getResource(resourceName);
		return path.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(getRootPath());
		System.out.println(getCurPath(PathUtils.class));
		System.out.println(getCurPath("constant.properties"));
		System.out.println(getProjectPath());
		System.out.println(getProjectPathByUser());
		System.out.println(getAllPaths());
	}
}
