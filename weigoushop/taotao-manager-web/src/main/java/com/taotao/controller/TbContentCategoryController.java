package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.service.TbContentCategoryService;
import com.taotao.utils.TreeResult;

@Controller
public class TbContentCategoryController {
	@Autowired
	private TbContentCategoryService tbContentCategoryService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<TreeResult> findByid(@RequestParam(defaultValue="0")Long id){
		List<TreeResult> list = tbContentCategoryService.findAllByid(id);
		return list;
	}
	
}
