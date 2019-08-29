package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.service.ItemCatService;
import com.taotao.utils.TreeResult;

@Controller
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<TreeResult> findAllByid(@RequestParam(defaultValue="0") Long id ){
		List<TreeResult> list = itemCatService.findAllByid(id);
		return list;
		
	}
	
}
