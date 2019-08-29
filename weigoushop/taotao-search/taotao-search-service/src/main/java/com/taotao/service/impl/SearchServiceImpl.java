package com.taotao.service.impl;


import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.dao.SearchDao;
import com.taotao.mapper.TbsearchMapper;
import com.taotao.service.SearchService;
import com.taotao.utils.SearchBean;
import com.taotao.utils.SearchResult;
import com.taotao.utils.TaotaoResult;
@Service
public class SearchServiceImpl implements SearchService{
	@Autowired
	private TbsearchMapper tbsearchMapper;
	@Autowired
	private SolrServer solrServer;
	@Autowired
	private SearchDao searchDao;

	@Override
	public TaotaoResult find() throws Exception {
		
		List<SearchResult> find = tbsearchMapper.find();
		
		for (SearchResult searchItem : find) {
			
				SolrInputDocument document = new SolrInputDocument();
				
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategory_name());
				document.addField("item_desc", searchItem.getItem_desc());
				solrServer.add(document);
			
			}
			solrServer.commit();
			return TaotaoResult.ok();
	}

	//查询
	@Override
	public SearchBean findBySolr(String q,int page,int rows) {
		SolrQuery query = new SolrQuery();
		//查询域
		query.setQuery(q);
		query.set("df", "item_title");
		//分页
		query.setStart((page-1)*rows);
		query.setRows(rows);
		
		//高亮
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		
		//总页数
		SearchBean findBySolr = searchDao.findBySolr(query);
		Long totalCount = findBySolr.getTotalCount();
		Long long1 = totalCount/rows;
		if(long1%rows>0){
			long1++;
		}
		findBySolr.setTotalPage(long1);
		return findBySolr;
	}

	@Override
	public TaotaoResult saveByitemId(Long itemId) throws Exception {
		SearchResult searchItem = tbsearchMapper.findByItemId(itemId);
		
			
				SolrInputDocument document = new SolrInputDocument();
				
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategory_name());
				document.addField("item_desc", searchItem.getItem_desc());
				solrServer.add(document);
			
			
			solrServer.commit();
			return TaotaoResult.ok();
	}

}
