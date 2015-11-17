package com.vaolan.sspserver.dao.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vaolan.sspserver.dao.AdCategoryDao;


@Repository
public class AdCategoryDaoImpl implements AdCategoryDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	/* (non-Javadoc)
	 * @see com.vaolan.adtarget.dao.impl.AdCategoryDao#getCategoryByAdId(java.lang.String)
	 */
	@Override
	public List<Integer> getCategoryByAdId(String adId)
	{
		String sql = "select ad_category_id from ad_category_link c where c.ad_id=?";

		return jdbcTemplate.queryForList(sql,Integer.class,adId);
	}
}
