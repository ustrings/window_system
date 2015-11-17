package com.vaolan.sspserver.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hidata.framework.util.StringUtil;

public class DMPUtil {
	private static Logger logger = Logger.getLogger(DMPUtil.class);
	
	public static List<String> createCurUserKeywordList(String userId) {
	        // 查询用户的所有关键词
	        List<String> keywords = HttpUtil.getResultFromDMPService(userId, DataQueryType.KEYWORD);
	        List<String> newKeywords = new ArrayList<String>();
	        if(keywords == null) {
	        	return null;
	        }
	        for(String keyword : keywords) {
	        	 String[] userKeywordArray = keyword.split("/");
	        	 newKeywords.add(userKeywordArray[0]);
	        }
	        return newKeywords;
	}
	
	 /**
     * 获取当前用户的所有标签
     * 
     * @param adFilterElement
     * @return
     * @author zhoubin
     */
    public static Map<String, String> createCurUserLabelMap(String userId) {
        Map<String, String> labelResultMap = new HashMap<String, String>();
        // 查询用户的所有标签
        List<String> userLabels  = null;
        userLabels = HttpUtil.getResultFromDMPService(userId, DataQueryType.LABEL);
        
//        logger.info("标签查询：userid : " + userId);
        if(userLabels != null && userLabels.size() > 0) {
//        	logger.info("查询结果：result : " + userLabels.toString());
        	for (String iterUserLabels : userLabels) {
                if (StringUtil.isBlank(iterUserLabels)) {
                    continue;
                }
                String[] userLabelArray = iterUserLabels.split("/");
                // 删除最后一个元素
        		userLabelArray = Arrays.copyOfRange(userLabelArray, 0, userLabelArray.length-1);
        		
        		// 生成规则，全面的生成，确保标签的全覆盖
        		// 电商>家电>热水器
        		// md5(电商)
        		// md5(电商家电)
        		// md5(电商家电热水器)
                for (int i = 0; i < userLabelArray.length; i++) {
                    String[] iterCurArray = new String[i + 1];

                    for (int j = 0; j <= i; j++) {
                        iterCurArray[j] = userLabelArray[j];
                    }

                    String curMdLabelKey = JavaMd5Util.md5EncrypFirst16(iterCurArray);

                    if (!labelResultMap.containsKey(curMdLabelKey)) {
                        labelResultMap.put(curMdLabelKey, "1");
                    }
                }
            }
        }

        return labelResultMap;
    }
}
