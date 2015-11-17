package com.vaolan.sspserver.dao;

import java.util.List;


public interface AdCategoryDao {

	public abstract List<Integer> getCategoryByAdId(String adId);

}