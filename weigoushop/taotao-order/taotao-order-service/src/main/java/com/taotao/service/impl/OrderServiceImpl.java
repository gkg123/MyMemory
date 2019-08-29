package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.pojo.OrderInfo;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import com.taotao.redis.JedisClient;
import com.taotao.service.OrderService;
import com.taotao.utils.TaotaoResult;
@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TbOrderMapper tbOrderMapper;
	@Autowired
	private TbOrderItemMapper tbOrderItemMapper;
	@Autowired
	private TbOrderShippingMapper tbOrderShippingMapper;
	@Override
	public TaotaoResult create(OrderInfo orderinfo) {
		
		if(!jedisClient.exists("Order_id")) {
			jedisClient.set("Order_id", "2019500");
		}
		String order_id = jedisClient.incr("Order_id").toString();
		orderinfo.setOrderId(order_id);
		orderinfo.setPostFee("0");
		orderinfo.setStatus(1);
		Date date = new Date();
		orderinfo.setCreateTime(date);
		orderinfo.setUpdateTime(date);
		//添加
		tbOrderMapper.insert(orderinfo);
		
		List<TbOrderItem> orderItems = orderinfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			String orderItemid = jedisClient.incr("Orderitem_id").toString();
			tbOrderItem.setId(orderItemid);
			tbOrderItem.setOrderId(order_id);
			//添加
			tbOrderItemMapper.insert(tbOrderItem);
		}
	
		TbOrderShipping orderShipping = orderinfo.getOrderShipping();
		orderShipping.setOrderId(order_id);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		//添加
		tbOrderShippingMapper.insert(orderShipping);
		return TaotaoResult.ok(order_id);
	}

}
