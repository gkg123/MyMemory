package com.taotao.service;

import java.util.List;

import com.taotao.utils.TreeResult;

public interface TbContentCategoryService {
	public List<TreeResult> findAllByid(Long parentId);
}
