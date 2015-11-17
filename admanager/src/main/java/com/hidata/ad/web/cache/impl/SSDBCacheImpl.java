package com.hidata.ad.web.cache.impl;

import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.ssdb4j.spi.Response;
import org.nutz.ssdb4j.spi.SSDB;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.cache.SSDBCache;
import com.hidata.framework.ssdb.SSDBRegister;

@Component
public class SSDBCacheImpl implements SSDBCache
{
	private static Logger logger = Logger.getLogger(SSDBCacheImpl.class);
	
	@Resource(name = "SSDBRegister")
	private SSDBRegister ssdbRegister;
	
	private SSDB ssdb;
	
	@PostConstruct
	public void afterConstruct() {
		if (null == ssdb) {
			ssdb = ssdbRegister.init();
		}
	}
	@Override
	public Set<String> getAdUaByTid(String tid){
		Set<String> set = null;
		int times=3;
	    int index = 0;
	    Response response = null;
 		while(index<times){
 			 try {
 				response =  ssdb.hscan(tid, "", "", 100);
 	 			if(response==null){
 	 				index++;
 	 			}else{
 	 				Map<String,String> map = response.mapString();
 	 				set = map.keySet();
 	 				break;
 	 			}
 			} catch (Exception e) {
 				logger.error("SSDBCacheImpl getAdUaByTid error",e);
 				e.printStackTrace();
 				index++;
 			}
 			
 		}
	   
	   
		return set;
	}
}
