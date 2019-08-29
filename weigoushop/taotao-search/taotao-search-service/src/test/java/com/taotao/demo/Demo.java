package com.taotao.demo;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class Demo {
	@Test
	public void demo(){
		SolrServer s= new HttpSolrServer("http://192.168.25.129:8080/solr");
		//创建文档对象
		SolrInputDocument sd = new SolrInputDocument();
//		sd.setField("id", "test_01");
//		sd.setField("item_title", "华为");
		
		try {
//			s.add(sd);
			s.deleteById("test_01");
			s.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@Test
	public void find(){
		SolrServer s=  new HttpSolrServer("http://192.168.25.129:8080/solr");
		SolrQuery sq = new SolrQuery();
		sq.setQuery("华为");
		sq.set("df", "item_title");
		try {
			QueryResponse query = s.query(sq);
			//获得结果集
			SolrDocumentList list = query.getResults();
			System.out.println("count:"+list.size());
			
			for (SolrDocument solrDocument : list) {
				System.out.println(solrDocument.get("id"));
				System.out.println(solrDocument.get("item_title"));
			}
			
			
			
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
