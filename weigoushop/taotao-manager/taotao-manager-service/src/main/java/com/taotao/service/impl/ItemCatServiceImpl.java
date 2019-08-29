package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;
import com.taotao.utils.TreeResult;
@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	@Override
	public List<TreeResult> findAllByid(Long parentId) {
		//查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria createCriteria = example.createCriteria();
		Criteria andParentIdEqualTo = createCriteria.andParentIdEqualTo(parentId);
		
		List<TbItemCat> selectByExample = tbItemCatMapper.selectByExample(example );
		
		List<TreeResult> list = new ArrayList<TreeResult>();
		for (TbItemCat tbItemCat : selectByExample) {
			TreeResult treeResult = new TreeResult();
			treeResult.setId(tbItemCat.getId());
			treeResult.setText(tbItemCat.getName());
			treeResult.setState(tbItemCat.getIsParent()?"closed":"open");
			list.add(treeResult);
			
		}
		
		return list;
	}

}
