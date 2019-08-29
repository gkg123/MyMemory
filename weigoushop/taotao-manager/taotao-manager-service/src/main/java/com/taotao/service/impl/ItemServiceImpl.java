package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.redis.JedisClient;
import com.taotao.service.ItemService;
import com.taotao.utils.EasyUIResult;
import com.taotao.utils.IDUtils;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper; 
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Topic topic;
	@Autowired
	private JedisClient jedisClient;
	
	
	@Override
	public EasyUIResult<TbItem> findAllByPage(Integer page, Integer rows) {
		//获取第1页，10条内容，默认查询总数count
		PageHelper.startPage(page, rows);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);

		//用PageInfo对结果进行包装
		PageInfo<TbItem> pageinfo = new PageInfo<TbItem>(list);
		EasyUIResult<TbItem> easyUiResult = new EasyUIResult<>();
		easyUiResult.setRows(list);
		easyUiResult.setTotal( pageinfo.getTotal());
		return easyUiResult;
	}
	@Override
	public TaotaoResult save(TbItem tbItem, String desc) {
	
		 final long genItemId = IDUtils.genItemId();
		
		tbItem.setId(genItemId);
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		tbItem.setStatus((byte)1);
		tbItemMapper.insert(tbItem);
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(genItemId);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setUpdated(new Date());
		tbItemDesc.setCreated(new Date());
		tbItemDescMapper.insert(tbItemDesc);
		
		jmsTemplate.send(topic,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage createTextMessage = session.createTextMessage(genItemId+"");
				
				
				return createTextMessage;
			}
		});
		
		return new TaotaoResult();
	}
	@Override
	public TaotaoResult delete(Long id) {
		int deleteByPrimaryKey = tbItemMapper.deleteByPrimaryKey(id);
		tbItemDescMapper.deleteByPrimaryKey(id);
		System.out.println(id);
		return new TaotaoResult();
	}
	@Override
	public TbItem findById(Long itemId) {
		//查询缓存
		String json = jedisClient.get("ITEM:"+itemId+":BASE");
		//如果没有添加到缓存
		if(StringUtils.isNoneBlank(json)){
			TbItem jsonToPojo = JsonUtils.jsonToPojo(json, TbItem.class);
			return jsonToPojo;
		}
		
		TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
		jedisClient.set("ITEM:"+itemId+":BASE", JsonUtils.objectToJson(item));
		jedisClient.expire("ITEM:"+itemId+":BASE", 3600*24);
		return item;
		
	}
	@Override
	public TbItemDesc findByItemId(Long itemId) {
		// TODO Auto-generated method stub
		//查询缓存
				String json = jedisClient.get("ITEM:"+itemId+":DESC");
				//如果没有添加到缓存
				if(StringUtils.isNoneBlank(json)){
					 TbItemDesc jsonToPojo = JsonUtils.jsonToPojo(json, TbItemDesc.class);
					return jsonToPojo;
				}
				
		
		
		TbItemDesc selectByPrimaryKey = tbItemDescMapper.selectByPrimaryKey(itemId);
		jedisClient.set("ITEM:"+itemId+":DESC", JsonUtils.objectToJson(selectByPrimaryKey));
		jedisClient.expire("ITEM:"+itemId+":DESC", 3600*24);
		
		return selectByPrimaryKey;
	}

}
