package com.vaolan.sspserver.dao.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vaolan.sspserver.dao.MediaCategoryDao;



@Repository
public class MediaCategoryDaoImpl implements MediaCategoryDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	/* (non-Javadoc)
	 * @see com.vaolan.adtarget.dao.impl.MediaCategoryDao#getMediaCategoryByAdId(java.lang.String)
	 */
	@Override
	public List<String> getMediaCategoryCodeByAdId(String adId){
		String sql = "select media_category_code from ad_media_category_link m where m.ad_id=?";
		return jdbcTemplate.queryForList(sql,String.class,adId);
	}

}
