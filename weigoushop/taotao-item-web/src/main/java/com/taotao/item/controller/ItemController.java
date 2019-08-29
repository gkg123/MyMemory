package com.taotao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{id}")
	public String find(@PathVariable long id,Model model){
		TbItem item = itemService.findById(id);
		TbItemDesc tbItemDesc = itemService.findByItemId(id);
		
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", tbItemDesc);
		return "item";
	}
	
}
