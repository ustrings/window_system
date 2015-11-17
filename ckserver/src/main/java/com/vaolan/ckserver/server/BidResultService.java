package com.vaolan.ckserver.server;


import com.vaolan.ckserver.model.BidResult;

public interface BidResultService {

	/**
	 * 保存竞价结果信息
	 * @param bidResult
	 * */
	public abstract int saveBidResult(BidResult bidResult);
	/**
	 * 竞价结果保存到日志文件
	 * @param bidResult
	 * */
	public void saveBidResult2Log(BidResult bidResult);

}