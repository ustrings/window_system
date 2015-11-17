package com.hidata.ad.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.core.io.support.PropertiesLoaderUtils;



public class FTPUtil {
	
	private static Log LOG = LogFactory.getLog(FTPUtil.class);
	
	private FTPClient ftpClient = null;
	
	private String host;
	private int port;
	
	private FTPUtil(String host, int port){
		this.host = host;
		this.port = port;
		if (ftpClient == null){
			ftpClient = new FTPClient();
		}
	}
	
	public static FTPUtil getInstance(String host, int port){
		return new FTPUtil(host, port);
	}
	
	public void connectAndLogin(String username, String password){
		try {
			// 连接ftp服务器
			ftpClient.connect(host, port);
		}  catch (IOException e) {
			e.printStackTrace();
			LOG.info("FTP SERVER CONNECT ERROR " + e.getMessage());
		}
		
		try {
			// 登录ftp服务器
			ftpClient.login(username, password);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (IOException e) {
			e.printStackTrace();
			LOG.info("LOGIN FTP SERVER ERROR " + e.getMessage());
		}
	}
	
	public void changeWorkDirectory(String path){
		try {
			ftpClient.changeWorkingDirectory(path);
		} catch (IOException e) {
			e.printStackTrace();
			LOG.info("FTP CHANGE WORKING DIRECTORY ERROR " + e.getMessage());
		}
	}
	
	// 上传文件到ftp服务器
	public boolean storeFileToServer(File file, String name){
		FileInputStream input = null;
		boolean saveSuccess = false;
		try {
			input = new FileInputStream(file);
			saveSuccess = ftpClient.storeFile(name, input);
		} catch (IOException e) {
			e.printStackTrace();
			LOG.info("FTP UPLOAD FILE ERROR " + e.getMessage());
		} finally{
			try {
				if (input != null){
					input.close();
				}
				if (ftpClient != null && ftpClient.isConnected()){
					ftpClient.disconnect();
				}
			} catch (IOException e) {
			}
		}
		return saveSuccess;
	}
	
	// 退出
	public void logout(){
		if (ftpClient != null){
			try {
				ftpClient.logout();
			} catch (IOException e) {
			}
		}
	}
	// 上传文件到ftp服务器
		public boolean storeFileToServer(InputStream input, String name){
			boolean saveSuccess = false;
			try {
				saveSuccess = ftpClient.storeFile(name, input);
			} catch (IOException e) {
				e.printStackTrace();
				LOG.info("FTP UPLOAD FILE ERROR " + e.getMessage());
			} finally{
				try {
					if (input != null){
						input.close();
					}
					if (ftpClient != null && ftpClient.isConnected()){
						ftpClient.disconnect();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return saveSuccess;
		}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
//		FTPUtil ftp = FTPUtil.getInstance("192.168.1.109", 21);
//		ftp.connectAndLogin("root", "123456");
//		File file = new File("/work/workspace/hidata-ad-web/src/main/java/com/hidata/ad/web/util/AdConstant.java");
//		ftp.changeWorkDirectory("/var/www/ftp");
//		boolean result = ftp.storeFileToServer(file, "aaaa");
//		System.out.println(result);
		
		Properties props = PropertiesLoaderUtils.loadAllProperties("application.properties");
		
		System.out.println(props.get("jdbc.driver.write"));
	}

}
