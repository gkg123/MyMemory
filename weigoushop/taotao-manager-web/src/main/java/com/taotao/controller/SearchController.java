package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.service.SearchService;
import com.taotao.utils.TaotaoResult;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	@RequestMapping("/search/add")
	@ResponseBody
	public TaotaoResult save() throws Exception{
		return searchService.find();
	}
}
