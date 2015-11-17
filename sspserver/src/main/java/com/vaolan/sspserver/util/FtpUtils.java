package com.vaolan.sspserver.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.mysql.jdbc.log.Log;
import com.vaolan.sspserver.timer.DBInfoFresh;

public class FtpUtils {
	private static Logger logger = Logger.getLogger(FtpUtils.class);
	public static void main(String[] args) throws Exception {
		
//		#ftp server
//		host=118.26.145.26
//		port=21
//		username=admftp
//		password=12ab!@
//		image.directory=/data/admftp/img/
//		img.domain=http://creative.adtina.com/img/
		
		// =======上传测试============
//		String localFile = "E:\\eclipse-ee-projects\\utils\\src\\中国count.txt";
//		
//		String url = "118.26.145.26";
//		int port = 21;
//		String username ="admftp";
//		String password ="12ab!@";
//		
//		String path ="/data/admftp/kw";
//		String filename ="中国1.txt";
		
		
//		String url = "192.168.120.133";
//		int port = 21;
//		String username ="ftpuser";
//		String password ="ftpuser";
//		String path ="/home/ftpuser/";
//		String filename ="中国.txt";
//		InputStream input = new FileInputStream(new File(localFile));
		
		
		
//		uploadFile(url, port, username, password, path, filename, input);
		// =======上传测试============
		
		// =======下载测试============
		String localPath = "D:\\";
		String url = "118.26.145.26";
		int port = 21;
		String username ="admftp";
		String password ="12ab!@";
		String remotePath ="/data/admftp/kw";
//		String fileName = "test.txt";
		String fileName = "生产环境_1405.txt";
		downFile(url, port, username, password, remotePath, fileName, localPath);
		// =======下载测试============
		
	}
	
	/**
	* Description: 向FTP服务器上传文件
	* @param url FTP服务器 hostname
	* @param port FTP服务器端口 默认端 21
	* @param username FTP登录账号
	* @param password FTP登录密码
	* @param path FTP服务器保存目录
	* @param filename 上传到FTP服务器上的文件名
	* @param input 输入流
	* @return 成功返回true，否则返回false
	*/ 
	public static boolean uploadFile(String url,int port,String username, String password, String path, String filename, InputStream input) { 
	    boolean success = false; 
	    FTPClient ftp = new FTPClient(); 
	    try { 
	        int reply; 
	        ftp.connect(url, port);//连接FTP服务器 
//	        ftp.connect(url);//连接FTP服务器 
	        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器 
	        ftp.login(username, password);//登录 
//	        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
//	        ftp.setControlEncoding("GBK");
	        reply = ftp.getReplyCode();
	        if (!FTPReply.isPositiveCompletion(reply)) {
	            ftp.disconnect(); 
	            return success; 
	        }
	        ftp.changeWorkingDirectory(path); 
	        // 设置文件名上传的编码格式为 utf-8
	        ftp.storeFile(new String(filename.getBytes("utf-8"),"iso-8859-1"), input);          
	         
	        input.close(); 
	        ftp.logout(); 
	        success = true; 
	    } catch (IOException e) { 
	        e.printStackTrace(); 
	    } finally { 
	        if (ftp.isConnected()) { 
	            try { 
	                ftp.disconnect(); 
	            } catch (IOException ioe) { 
	            } 
	        } 
	    } 
	    return success;  
}
	
	/**
	 * 读取 ftp 上关键词文件的信息
	 * @param remotePath
	 * @param fileName
	 * @return
	 */
	public static List<String> getFtpFileContent(String remotePath, String fileName) {
		logger.info("开始下载文件。。。");
		List<String> listContent = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		FTPClient ftp = new FTPClient();
		try {
			int reply; 
			ftp.connect(Constant.FTP_HOST, Integer.parseInt(Constant.FTP_PORT)); 
			//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器 
			ftp.login(Constant.FTP_USER, Constant.FTP_PWD);//登录 
			reply = ftp.getReplyCode(); 
			// 设置成为被动链接模式，主动模式有问题
			ftp.enterLocalPassiveMode();
			
			ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录 
			FTPFile[] fs = ftp.listFiles();
			for(FTPFile ff:fs){
				String remotFileName = new String(ff.getName().getBytes("iso-8859-1"),"utf-8");
				if(remotFileName.equals(fileName)){ 
					logger.info("file read：" + remotFileName);
					File dir = new File(Constant.FTP_DOWNLOAD_FILE_PATH);
					if(!dir.exists()){
						dir.mkdirs();
					}
					File localFile = new File(Constant.FTP_DOWNLOAD_FILE_PATH + remotFileName);
					OutputStream is = new FileOutputStream(localFile);  
					ftp.retrieveFile(ff.getName(), is); 
					is.close(); 
				} 
			} 

			ftp.logout(); 
		} catch (IOException e) { 
			logger.error("下载ftp文件 : " + remotePath + fileName +" 出错！！！");
			logger.error(e);
			return null;
		} finally { 
			if (ftp.isConnected()) { 
				try { 
					ftp.disconnect(); 
				} catch (IOException ioe) { 
					logger.error("关闭 ftp 连接 出错！！！");
					logger.info(ioe);
				} 
			} 
		} 

		try {
			File newLocalFile = new File(Constant.FTP_DOWNLOAD_FILE_PATH + fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(newLocalFile),"utf-8"));
			String line = null;
			int lineNum = 0;
			while((line=reader.readLine())!=null) {
				if(line.trim().length()<=0) {
					continue;
				}
				lineNum++;
				sb.append(line).append(" ");
				// 根据指定大小的数量去分割一个字符串
				if(lineNum%Constant.NUM_FOR_KEYWORD_TO_FENCI == 0) {
					listContent.add(sb.toString());
					//        		String str = new String(sb.toString().getBytes("utf-8"),"GBK");
					//        		System.out.println(str);
					sb = new StringBuffer();
				}
			}

			logger.info("读取文本文件行数 ： " + lineNum);

			if(sb!=null && sb.length() > 0) {
				listContent.add(sb.toString());
			}
			sb = null;
			reader.close();
			File delete =  new File(Constant.FTP_DOWNLOAD_FILE_PATH + fileName);

			delete.deleteOnExit();
		} catch(Exception e) {
			logger.info("Error when download ftp file.");
			e.printStackTrace();
		}

		return listContent; 
	}
	
	/**
	* Description: 从FTP服务器下载文件
	* @param url FTP服务器hostname
	* @param port FTP服务器端口
	* @param username FTP登录账号
	* @param password FTP登录密码
	* @param remotePath FTP服务器上的相对路径
	* @param fileName 要下载的文件名
	* @param localPath 下载后保存到本地的路径
	* @return
	*/ 
	public static boolean downFile(String url, int port,String username, String password, String remotePath,String fileName,String localPath) { 
	    boolean success = false; 
	    FTPClient ftp = new FTPClient();
	    try { 
	        int reply; 
	        ftp.connect(url, port); 
	        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器 
	        ftp.login(username, password);//登录 
	        reply = ftp.getReplyCode(); 
	        if (!FTPReply.isPositiveCompletion(reply)) { 
	            ftp.disconnect(); 
	            return success; 
	        } 
	        ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录 
	        FTPFile[] fs = ftp.listFiles(); 
	        for(FTPFile ff:fs){
	        	String remotFileName = new String(ff.getName().getBytes("iso-8859-1"),"utf-8");
	            if(remotFileName.equals(fileName)){ 
	                File localFile = new File(localPath+"/"+remotFileName); 
	                OutputStream is = new FileOutputStream(localFile);  
	                ftp.retrieveFile(ff.getName(), is); 
	                is.close(); 
	            } 
	        } 
	         
	        ftp.logout(); 
	        success = true; 
	    } catch (IOException e) { 
	        e.printStackTrace(); 
	    } finally { 
	        if (ftp.isConnected()) { 
	            try { 
	                ftp.disconnect(); 
	            } catch (IOException ioe) { 
	            } 
	        } 
	    } 
	    return success; 
	}
}
