package com.hidata.ad.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 读取配置文件的工具类
 * @author xiaoming
 * @date 2014-1-24
 */
public class ReadConfigFile {
	 public static Logger logger = Logger.getLogger(ReadConfigFile.class);
	 //为了读了properties文件  
	 private Properties properties = null;
	 //流 ，为了读取非propertie文件
	 private InputStream in = null;
	 
	 /** 
	     * 处理propertise文件 
	     * @param fileConfig  文件的明（路径） 
	     * @param flag 为了区分非propertise文件（利用构造方法的重载） 
	     */  
	    public ReadConfigFile(String fileConfig){ //构造方法  
	        //获取输入流  
	        try {  
	            /** 
	             * 首先，调用对象的getClass()方法是获得对象当前的类类型，这部分数据存在方法区中，而后在类类型上调用getClassLoader()方法是得到当前类型的类加载器，在Java中所有的类都是通过加载器加载到虚拟机中的，而且类加载器之间存在父子关系，就是子知道父，父不知道子，这样不同的子加载的类型之间是无法访问的（虽然它们都被放在方法区中），所以在这里通过当前类的加载器来加载资源也就是保证是和类类型同一个加载器加载的。  
	             * 最后调用了类加载器的getResourceAsStream()方法来加载文件资源 
	             */  
	              
	            //http://blog.csdn.net/funi16/article/details/8137708  
	            in = this.getClass().getClassLoader().getResourceAsStream(fileConfig);//这里要说明一下，如果说这个方法是在静态代码块中写的话，那么this是不能用的，this是代表该类的对象，把this改成Object即可，而相应的属性变量也需要改为静态的  
	            properties = new Properties();  
	            properties.load(new InputStreamReader(in,"utf-8"));  
	            in.close();  
	        } catch (Exception e) {
	        	logger.error("读取配置文件错误。。。" + e);
	        	e.printStackTrace();
	        }  
	    }  
	    /** 
	     * 读取properties文件，根据key获取key值所对应的value值 
	     * @param key  
	     * @return value 值 
	     */  
	    public String getValue(String key){  
	        String value = null;  
	        try {  
	            value = properties.getProperty(key);  
	        } catch (Exception e) {  
	           logger.error("根据properties的key值获取value值时出错。。。");
	        }  
	        return value;  
	    }  
	    
	    /**
	     * 读取txt文件，指定某行
	     * @param filePath
	     * @param fileEncoding
	     * @param begin_line_number
	     * @param end_line_number
	     * @return
	     */
	    public static String readFile(String filePath, int number ){
	    	System.getProperty("UTF-8");
			File file = new File(filePath);
			BufferedReader br = null;
			String line = null;
			StringBuilder sb = new StringBuilder();
			
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				if(number == 1){
					return br.readLine().toString();
				}else{
						int i = 2;
						// 空读完前边的行
						while ((line = br.readLine()) != null && i < number) {
							i++;
						}

						if ((line = br.readLine()) != null) {
							sb.append(line + "\n");
						}
						return sb.toString();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
			return null;
		}
	    /**
		 * 以追加的方式向文件中写入
		 * 
		 * @param path
		 * @param value
		 */
		public static void writeFile(String path, String value, boolean isAppend) {
			File f = new File(path);
			FileOutputStream fos = null;
			try {
				if (isAppend) {
					fos = new FileOutputStream(f, isAppend);
				} else {
					fos = new FileOutputStream(f);
				}
				fos.write(value.getBytes("UTF-8"));
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	   public static void main(String args []){
		   String line = ReadConfigFile.readFile("G://hot.txt", 2);
		   System.out.println(line);
//		   String a = re.getValue("othor_block_rule");
//		   System.out.println(a);
	   }
}
