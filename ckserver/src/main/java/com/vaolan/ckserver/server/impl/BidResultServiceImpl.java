package com.vaolan.ckserver.server.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.hidata.framework.cache.redis.RedisUtil;
import com.hidata.framework.util.StringUtil;
import com.vaolan.ckserver.dao.BidResultDao;
import com.vaolan.ckserver.model.BidResult;
import com.vaolan.ckserver.server.BidResultService;
import com.vaolan.ckserver.util.AdStatLogUtil;
import com.vaolan.ckserver.util.Constant;

@Service
public class BidResultServiceImpl implements BidResultService{
	
	@Autowired
	private BidResultDao resultDao;
	private static Logger Log = Logger.getLogger(BidResultServiceImpl.class);
	/* (non-Javadoc)
	 * @see com.vaolan.ckserver.server.impl.BidResultService#saveBidResult(com.vaolan.ckserver.model.BidResult)
	 */
	@Override
	@Transactional
	public int saveBidResult(BidResult bidResult){
		return resultDao.save(bidResult);
	}
	@Override
	public void saveBidResult2Log(BidResult bidResult){
		String channel = bidResult.getChannel();
		String domain = bidResult.getDomain();
		String adSize = bidResult.getAdSize();
		String viewScreem = bidResult.getViewScreen();
		String adId = bidResult.getAdId();
		//竞价反馈结果写入redis
		if(StringUtil.isNotBlank(channel)
				&& StringUtil.isNotBlank(domain)
				&& StringUtil.isNotBlank(adSize)
				&& StringUtil.isNotBlank(viewScreem)
				&& StringUtil.isNotBlank(adId)
				&& Constant.CRC_SUCCESS.equals(bidResult.getCrcVerify())){
			String ts = System.currentTimeMillis()+"";
			String dimension_2 = channel+Constant.SEPARATE+domain;
			String dimension_3 = channel+Constant.SEPARATE+domain+Constant.SEPARATE+adSize+viewScreem;
			String dimension_4 = channel+Constant.SEPARATE+domain+Constant.SEPARATE+adSize+viewScreem+
								 Constant.SEPARATE+adId;
			Jedis client = RedisUtil.getJedisClientFromPool();
			try {
				Pipeline pl = client.pipelined();
				pl.hset(channel, Constant.OK_PRICE, bidResult.getResultPrice());
				pl.hset(channel, Constant.OK_TS, ts);
				pl.hincrBy(channel,Constant.OK_COUNT,1L);
				pl.hset(dimension_2, Constant.OK_PRICE,bidResult.getResultPrice());
				pl.hset(dimension_2, Constant.OK_TS, ts);
				pl.hincrBy(dimension_2,Constant.OK_COUNT,1L);
				pl.hset(dimension_3, Constant.OK_PRICE,bidResult.getResultPrice());
				pl.hset(dimension_3, Constant.OK_TS, ts);
				pl.hincrBy(dimension_3,Constant.OK_COUNT,1L);
				pl.hset(dimension_4, Constant.OK_PRICE,bidResult.getResultPrice());
				pl.hset(dimension_4, Constant.OK_TS, ts);
				pl.hincrBy(dimension_4,Constant.OK_COUNT,1L);
				pl.sync();
			} catch (Exception e) {
				Log.error("insert okInfo to redis failed!",e);
			}finally{
				RedisUtil.releaseJedisClientFromPool(client);
			}
		}
		//写入日志
		//AdStatLogUtil.saveBidRes(bidResult);
	}
}
