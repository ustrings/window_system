package com.vaolan.ckserver.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vaolan.ckserver.dao.BidResultDao;
import com.vaolan.ckserver.model.BidResult;

@Repository
public class BidResultDaoImpl implements BidResultDao {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	/* (non-Javadoc)
	 * @see com.vaolan.ckserver.dao.impl.BidResultDao#save(com.vaolan.ckserver.model.BidResult)
	 */
	@Override
	public int save(BidResult bidResult){
		String sql="INSERT INTO ad_bid_result (bid,encryption,result_price,crc_verify,create_time) "+
				   "VALUES (?,?,?,?,CURRENT_TIMESTAMP())";
		return jdbcTemplate.update(sql, bidResult.getBid(),
				bidResult.getEncryption(),
				bidResult.getResultPrice(),
				bidResult.getCrcVerify());
	}
}
