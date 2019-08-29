package com.taotao.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.utils.SearchBean;
import com.taotao.utils.SearchResult;

@Repository
public class SearchImpl implements SearchDao{
	
		@Autowired
		private SolrServer solrServer;
		//1.查询
		public SearchBean findBySolr(SolrQuery query) {
			//1.
			try {
				QueryResponse response = solrServer.query(query);
				//2.
				SolrDocumentList results = response.getResults();
				
			
				List<SearchResult> list = new ArrayList<>();
				for (SolrDocument solrDocument : results) {
					SearchResult item = new SearchResult();
					item.setId((String) solrDocument.get("id"));
					item.setCategory_name((String) solrDocument.get("item_category_name"));
					item.setImage((String) solrDocument.get("item_image"));
					item.setPrice((long) solrDocument.get("item_price"));
					item.setSell_point((String) solrDocument.get("item_sell_point"));
					
					Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
					List<String> list2 = highlighting.get(solrDocument.get("id")).get("item_title");
					
					String title = "";
					if(list2 != null&&list2.size()>0) {
						title = list2.get(0);
					}else {
						title = (String) solrDocument.get("item_title");
					}
					item.setTitle(title);
					
					list.add(item);
				}
				SearchBean searchBean = new SearchBean();
				searchBean.setList(list);
				searchBean.setTotalCount(results.getNumFound());
				
				return searchBean;
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
}
