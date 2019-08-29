package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbContent;
import com.taotao.service.TbContentService;
import com.taotao.utils.EasyUIResult;
import com.taotao.utils.TaotaoResult;

@Controller
public class TbContentController {
	@Autowired
	private TbContentService tbContentService;
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIResult<TbContent> findByCategoryId(Long categoryId,Integer page,Integer rows){
		return tbContentService.findByid(categoryId, page, rows);
	}
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult save(TbContent tbContent){
		return tbContentService.save(tbContent);
		
	}

}
