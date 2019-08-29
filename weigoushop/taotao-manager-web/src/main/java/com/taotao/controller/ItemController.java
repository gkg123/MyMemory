package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotao.utils.EasyUIResult;
import com.taotao.utils.TaotaoResult;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIResult<TbItem> findAllByPage(Integer page,Integer rows){
		EasyUIResult<TbItem> easyUiResult = itemService.findAllByPage(page, rows);
		return easyUiResult;
		
	}
	
	@RequestMapping("/item/save")
	@ResponseBody
	public TaotaoResult save(TbItem tbItem,String desc){
		TaotaoResult addItem = itemService.save(tbItem, desc);
		addItem.setStatus(200);
		return addItem;
		
	}
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public TaotaoResult delete(Long ids){
		TaotaoResult delete = itemService.delete(ids);
		delete.setStatus(200);
		return delete;
	}
	
}
