package com.vaolan.sspserver.filter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.nutz.ssdb4j.spi.SSDB;
import org.springframework.stereotype.Service;

import com.hidata.framework.ssdb.SSDBRegister;
import com.hidata.framework.util.StringUtil;
import com.vaolan.sspserver.model.AdFilterElement;
import com.vaolan.sspserver.model.AdPlanKeyWord;
import com.vaolan.sspserver.model.AdvPlan;
import com.vaolan.sspserver.util.Constant;
import com.vaolan.sspserver.util.SSDBUtils;
import com.vaolan.sspserver.util.StringArrUtils;

/**
 * 广告计划对应关键字按需生成,如果满足投放需求才加载 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年12月15日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhangtengda
 */
@Service
public class AdplanKeywordFilter {
	private static Logger logger = Logger.getLogger(AdplanKeywordFilter.class);
	
//	@Resource(name = "SSDBRegister14_8890")
//	private SSDBRegister SSDBRegister14_8890;
	
//	@Resource(name = "SSDBRegister14_8891")
//	private SSDBRegister SSDBRegister14_8891;
//	
//	@Resource(name = "SSDBRegister14_8892")
//	private SSDBRegister SSDBRegister14_8892;
//	
//	@Resource(name = "SSDBRegister14_8893")
//	private SSDBRegister SSDBRegister14_8893;
//	
//	@Resource(name = "SSDBRegister14_8894")
//	private SSDBRegister SSDBRegister14_8894;
//	
//	@Resource(name = "SSDBRegister14_8895")
//	private SSDBRegister SSDBRegister14_8895;
//	
//	@Resource(name = "SSDBRegister14_8896")
//	private SSDBRegister SSDBRegister14_8896;
//	
//	@Resource(name = "SSDBRegister14_8897")
//	private SSDBRegister SSDBRegister14_8897;
//	
//	private SSDBRegister sSDBRegister;
	

//    // 当前ad+ua用户对应的关键词
//    private String[] userKeyword = null; 

    /**
     * 过滤用户标签广告
     * @param advPlan
     * @param adFilterElement
     * @return
     */
    public boolean doFilter(AdvPlan advPlan, AdFilterElement adFilterElement, List<String> keywords) {
        return this.adKeywrodFilter(advPlan, adFilterElement, keywords);
    }

    /**
     * 判断当前用户的关键词中有没有命中的关键词广告
     * @param advPlan
     * @param adFilterElement
     * @return
     */
    public boolean adKeywrodFilter(AdvPlan advPlan, AdFilterElement adFilterElement, List<String> keywords) {
        // 用户关键词数组
        if(keywords==null || keywords.size()<= 0) {
        	return false;
        }
        boolean flag = false;
        // 对于是关键词的广告投放的广告才去判断
        if (advPlan.isKeywordFilter()) {
        	 // 广告对应的关键词对象
            AdPlanKeyWord adKeyword = advPlan.getAdPlanKeyWord();
            if (StringUtil.isBlank(adFilterElement.getAdAcct())
                    && StringUtil.isBlank(adFilterElement.getIp())) {
                return false;
            }
        
//            // 判断匹配类 1：精准 2：中心
//            if(adKeyword.getMatchType().equals(Constant.KEYWORD_EQUAL_MACH)) {
//            	return StringArrUtils.equalMatch(userKeywordArr, adKeywordArr);
//            } else if(adKeyword.getMatchType().equals(Constant.KEYWORD_CONTAINS_MACH)) {
//            	return StringArrUtils.equalMatch(userKeywordArr, adKeywordArr);
//            }

            return false;
        }
        return flag;
    }

//    /**
//     * 获取当前用户的所有关键词
//     * 
//     * @param adFilterElement
//     * @return
//     * @author zhoubin
//     */
//    public String[] getCurUserKeyword(AdFilterElement adFilterElement, AdPlanKeyWord adKeyword) {
//
//    	String[] keywordResultList= null;
//
//        String adAcct = adFilterElement.getAdAcct();
//        String userAgent = adFilterElement.getUserAgent();
//        String adAcct16 = JavaMd5Util.md5EncrypFirst16(adAcct);
//        String userAgent16 = JavaMd5Util.md5EncrypFirst16(userAgent);
//        // 生成用户ID
//        String userId = adAcct16 + userAgent16;
//        userId = userId.toLowerCase();
//        // 查询用户的所有标签
//        try{
//        	// 如果查询ES效率低的话就使用查询 redis 的方式
//        	keywordResultList = this.getUserKeywordFromSSDBByAdUa(userId);
//        } catch (Exception e) {
//        	logger.info(e);
//        }
//
//        return keywordResultList;
//    }
    
  
    
    /**
     * 向es发送请求
     * @param searchSql
     * @param url
     * @return
     */
    public static String httpClient(String searchSql,String url){ 
		 String crowd_baseInfo="";
		try{		
		   DefaultHttpClient  client = new DefaultHttpClient();
		   HttpPost httpPost=new HttpPost(url);
		   StringEntity entity = new StringEntity(searchSql,"utf-8");
		   entity.setContentType("application/json");
		   entity.setContentEncoding("utf-8");
		   httpPost.setEntity(entity);
          HttpResponse resp=client.execute(httpPost);
       
		   InputStream stream = resp.getEntity().getContent(); 
		   BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8")); 
		   StringBuffer buf = new StringBuffer();  
	       String line;  
	       while (null != (line = br.readLine())) {
	          buf.append(line).append("\n");  
	       }  
	       crowd_baseInfo = buf.toString();
	       System.out.println(crowd_baseInfo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return crowd_baseInfo;
	}
    
    /**
     * ssdb
     * @param userId
     * @return
     */
//    public String[] getUserKeywordFromSSDBByAdUa(String userId) {
////    	userId = "6723c569c57236d7e008fe6d3b29c30f";
//        String[] userLabels = null;
////        SSDB ssdb = SSDBRegister14_8890.init();
////    	long start = System.currentTimeMillis();
////    	 String value = null;
////    	try {
////    		value = SSDBUtils.get(ssdb, userId);
////    		ssdb._depose();
////    	} catch(Exception e) {
////    		logger.error("query ssdb for userid " + userId + " occur error.");
////    		try {
////    			value = SSDBUtils.get(ssdb, userId);
////    		} catch(Exception e1) {
////    			logger.error("query ssdb for userid " + userId + " occur error again.");
////    			ssdb = SSDBRegister14_8890.init();
////    			value = SSDBUtils.get(ssdb, userId);
////    			try {
////					ssdb._depose();
////				} catch (Exception e2) {
////					logger.error("close ssdb link occur error : " + e2);
////				}
////    		}
////    	}
////        long end = System.currentTimeMillis();
////        logger.info("ssdb query time : " + (end-start));
////        if(!StringUtil.isEmpty(value)) {
////        	userLabels =value.split(";");
////        }
////        logger.info("query result" + Arrays.toString(userLabels));
//        
//        return userLabels;
//    }

    

    public static void main(String[] args) {
//    	getUserKeywordFromESByAdUa("1234");
    }
    


}
