package com.taotao.mapper;

import java.util.List;

import com.taotao.utils.SearchResult;

public interface TbsearchMapper {
	
	//查询
	public List<SearchResult> find();
	
	public SearchResult findByItemId(Long itemId);

}
