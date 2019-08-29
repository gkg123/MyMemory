package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.TbContentCategoryService;
import com.taotao.utils.TreeResult;
@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	@Override
	public List<TreeResult> findAllByid(Long parentId) {
		//查询条件
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example );
		List<TreeResult> list1 = new ArrayList();
		//封装
		for (TbContentCategory tbContentCategory : list) {
			TreeResult treeResult = new TreeResult();
			treeResult.setId(tbContentCategory.getId());
			treeResult.setText(tbContentCategory.getName());
			treeResult.setState(tbContentCategory.getIsParent()? "closed":"open");
			list1.add(treeResult);
		}
		return list1;
	}

}
