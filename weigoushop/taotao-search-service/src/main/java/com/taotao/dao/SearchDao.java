package com.taotao.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.taotao.utils.SearchBean;

public interface SearchDao {
	
	//查询
	public SearchBean findBySolr(SolrQuery query);
}
