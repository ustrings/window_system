package com.hidata.ad.web.util;


import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;
import com.huaban.analysis.jieba.WordDictionary;

public class JieBaUtils {
	private static JiebaSegmenter segmenter;
	
	static{
		 String projectPath = PathUtils.getRootPath();
		 WordDictionary.getInstance().init(new File(projectPath));
		 segmenter = new JiebaSegmenter();
	}
	
	/**
	 * 为指定的字符串进行分词
	 * @param target
	 * @return
	 */
	public static Set<String> getFenci(String target) {
		Set<String> result = new HashSet<String>();
		 //E:/eclipse-ee-projects/sspserver/src/main/webapp/WEB-INF/classes/
		List<SegToken> sts = segmenter.process(target, SegMode.SEARCH);
		for(SegToken st : sts) {
			// 如果是停用词就放过，不是停用词就加入到分词列表里面
			if(StopWordsUtils.containsKey(st.word)) {
				continue;
			}
			result.add(st.word);
		} 
		return result;
	}
}
