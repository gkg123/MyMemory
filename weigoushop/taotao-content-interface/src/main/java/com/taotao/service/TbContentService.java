package com.taotao.service;

import java.util.List;

import com.taotao.pojo.TbContent;
import com.taotao.utils.EasyUIResult;
import com.taotao.utils.TaotaoResult;

public interface TbContentService {
	public EasyUIResult<TbContent> findByid(Long categoryId,Integer page,Integer rows);


	public TaotaoResult save(TbContent tbContent);
	
	public List<TbContent> findByCategoryid(Long categoryId);
}
