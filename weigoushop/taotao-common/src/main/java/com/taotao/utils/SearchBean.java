package com.taotao.utils;

import java.io.Serializable;
import java.util.List;

public class SearchBean implements Serializable{
	private List<SearchResult> list;
	private Long totalCount;
	private Long totalPage;
	public List<SearchResult> getList() {
		return list;
	}
	public void setList(List<SearchResult> list) {
		this.list = list;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public Long getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}
	
}
