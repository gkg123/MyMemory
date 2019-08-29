package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.redis.JedisClient;
import com.taotao.service.TbContentService;
import com.taotao.utils.EasyUIResult;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;
@Service
public class TbContentServiceImpl implements TbContentService {
	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private JedisClient jedisClient;

	@Override
	public EasyUIResult<TbContent> findByid(Long categoryId, Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		TbContentExample tbContentExample =new TbContentExample();
		Criteria createCriteria = tbContentExample.createCriteria();
		createCriteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tbContentMapper.selectByExample(tbContentExample);
		PageInfo<TbContent> pageinfo = new PageInfo<TbContent>(list);
		EasyUIResult<TbContent> easyui = new EasyUIResult<>();
		easyui.setTotal(pageinfo.getTotal());
		easyui.setRows(list);
		return easyui;
	}
	@Override
	public TaotaoResult save(TbContent tbContent) {
		jedisClient.hdel("Content_ad1", tbContent.getCategoryId().toString());
		
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		tbContentMapper.insert(tbContent);
		return TaotaoResult.ok();
	}
	@Override
	public List<TbContent> findByCategoryid(Long categoryId) {
		
		String json = jedisClient.hget("Content_ad1", categoryId+"");
		if(StringUtils.isNoneBlank(json)){
			List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
			return list;
		}
		TbContentExample tbContentExample =new TbContentExample();
		Criteria createCriteria = tbContentExample.createCriteria();
		createCriteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tbContentMapper.selectByExample(tbContentExample);
		jedisClient.hset("Content_ad1", categoryId+"", JsonUtils.objectToJson(list));
		return list;
	}

}
