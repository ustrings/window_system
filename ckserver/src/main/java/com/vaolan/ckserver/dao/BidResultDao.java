package com.vaolan.ckserver.dao;

import com.vaolan.ckserver.model.BidResult;

public interface BidResultDao {

	/**
	 * 保存竞价请求结果
	 * @param bidResult
	 * */
	public abstract int save(BidResult bidResult);

}