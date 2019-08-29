package com.taotao.service;

import com.taotao.pojo.OrderInfo;
import com.taotao.utils.TaotaoResult;

public interface OrderService {
	
	public TaotaoResult create(OrderInfo orderInfo);

}
