package com.hidata.web.util;

import java.util.List;

public class Pager {
	private long curPage = 1;
	private long allPage;
	private long allRecord;
	private int pageRecord = 10;
	
	// 分页内容
	private List<?> result;
	
	public Pager() {
	}
	
	public Pager(long curPage,long allRecord) {
		super();
		this.curPage = curPage;
		this.allRecord = allRecord;
	}

	public long getCurPage() {
		return curPage;
	}

	public void setCurPage(long curPage) {
		this.curPage = curPage;
	}

	public long getAllPage() {
		return allPage;
	}

	public void setAllPage(long allPage) {
		this.allPage = allPage;
	}

	public long getAllRecord() {
		return allRecord;
	}

	public void setAllRecord(long allRecord) {
		this.allRecord = allRecord;
	}

	public int getPageRecord() {
		return pageRecord;
	}

	public void setPageRecord(int pageRecord) {
		this.pageRecord = pageRecord;
	}

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}
}
