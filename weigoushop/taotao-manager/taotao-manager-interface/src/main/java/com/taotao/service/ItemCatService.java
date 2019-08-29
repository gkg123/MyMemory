package com.taotao.service;

import java.util.List;

import com.taotao.utils.TreeResult;

public interface ItemCatService {
	public List<TreeResult> findAllByid(Long id);
}
