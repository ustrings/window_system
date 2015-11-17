package com.hidata.web.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
* jsp 转换成 html
* 把动态网页转换成静态网页
*/
public class JspToHtml {

        /**
         * 根据url生成静态页面
         *
         * @param urlStr  动态文件路经 如：[url]http://www.163.com/x.jsp[/url]
         * @param path 文件存放路经如：x:\\abc\bbb.html
         * @return
         */
        public static boolean JspToHtmlByURL(String urlStr, String path) {
                //从utl中读取html存为str
                String str = "";
                try {
                        URL url = new URL(urlStr);
                        URLConnection uc = url.openConnection();
                        uc.setConnectTimeout(20000);
                        uc.setReadTimeout(20000);
                        InputStream is = uc.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
//                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String tmp = null;
                        while ((tmp = br.readLine()) != null) {
                                str += tmp + "\n";
                        }
                        is.close();
                        //写入文件
                        File f = new File(path);
                        File parent  = f.getParentFile();
                        if (!parent.exists()) {
                        	parent.mkdirs();
                        } else {
                        	if(f.exists()) {
                        		f.delete();
                        	}
                        }
//                        BufferedWriter o = new BufferedWriter(new FileWriter(f));
                        
                        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
                        BufferedWriter o=new BufferedWriter(write);  
                        
                        System.out.println("======================");
                        System.out.println(str);
                        System.out.println("======================");
                        str="<%@ page contentType=\"text/html;charset=UTF-8\"%>" + str;
                        o.write(str);
                        o.close();
                        str = "";
                        return true;
                } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                }
        }
        
        public static boolean copyFile(String sourceFilePath, String destFilePath) {
        	  //从utl中读取html存为str
            String str = "";
            try {
                    InputStream is = new FileInputStream(new File(sourceFilePath));
                    BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                    String tmp = null;
                    while ((tmp = br.readLine()) != null) {
                            str += tmp + "\n";
                    }
                    is.close();
                    //写入文件
                    File f = new File(destFilePath);
                    File parent  = f.getParentFile();
                    if (!parent.exists()) {
                    	parent.mkdirs();
                    } else {
                    	if(f.exists()) {
                    		f.delete();
                    	}
                    }
                    OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
                    BufferedWriter o=new BufferedWriter(write);  
                    
                    o.write(str);
                    o.close();
                    return true;
            } catch (Exception e) {
                    e.printStackTrace();
                    return false;
            }
        }
        
        /**
         * 检测文件是否存在
         * @param filePath
         * @return
         */
        public static boolean fileExists(String filePath) {
        	File file = new File(filePath);
        	if(file.exists()) {
        		return true;
        	}
        	return false;
        }

        /**
         * 测试main 函数
         *
         * @param arg
         */
        public static void main(String[] arg) {
                long begin = System.currentTimeMillis();
                //循环生成20个html文件
//                String url = "http://www.hao123.com/";//模板文件地址
//                String url = "http://localhost:8080/create/test";//模板文件地址
//                String url = "http://www.vao345.com/";//模板文件地址
                String url = "http://www.baidu.com/";//模板文件地址
                String savepath = "e:/temp/test/baidu.html";//生成文件地址
                JspToHtmlByURL(url, savepath);
                System.out.println("用时:" + (System.currentTimeMillis() - begin) + "ms");
        }
} 
