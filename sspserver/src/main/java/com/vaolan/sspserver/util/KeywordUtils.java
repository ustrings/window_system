package com.vaolan.sspserver.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vaolan.sspserver.model.AdSort;

/**
 * 过滤关键词广告帮助类
 * @author ZTD
 *
 */
public class KeywordUtils {
 
	/**
	 * 过滤关键词
	 * @param keyWordAdidsMap
	 * @param keywords
	 * @return
	 */
	public static Map<String, AdSort> getKeywordAdplanId(Map<String, Set<String>> keyWordAdidsMap, List<String> keywords) {
    	Map<String, AdSort> adIdFreq = new HashMap<String, AdSort>();
    	for(String keyword : keywords) {
    		if(keyWordAdidsMap.containsKey(keyword)) {
    			Set<String> adIds = keyWordAdidsMap.get(keyword);
    			for(String adId :  adIds) {
    				if(adIdFreq.containsKey(adId)) {
    					adIdFreq.get(adId).increase();
    				} else {
    					adIdFreq.put(adId, new AdSort(adId, 1));
    				}
    			}
    		}
    	}
    	
    	return adIdFreq;
    }
}
