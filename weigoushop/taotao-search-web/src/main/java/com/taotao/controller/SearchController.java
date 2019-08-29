package com.taotao.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.service.SearchService;
import com.taotao.utils.SearchBean;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	@RequestMapping("/search")
	public  String find(String q,@RequestParam(defaultValue="1")int page,Model model){
		
		try {
			q = new String(q.getBytes("iso-8859-1"),"utf-8" );
			SearchBean findBySolr = searchService.findBySolr(q, page, 60);
			
			model.addAttribute("query", q);
			model.addAttribute("totalPages",findBySolr.getTotalPage());
			model.addAttribute("itemList", findBySolr.getList());
			model.addAttribute("page", page);
			
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "search";	
	}
}
