package com.taotao.service;


import com.taotao.utils.SearchBean;
import com.taotao.utils.TaotaoResult;

public interface SearchService {
	//添加索引
	public TaotaoResult find()throws Exception;
	//查询
	public SearchBean findBySolr(String q,int page,int rows) ;
	//
	public TaotaoResult saveByitemId(Long itemId)throws Exception;
	
}
