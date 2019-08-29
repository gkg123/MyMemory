package com.taotao.order.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.pojo.OrderInfo;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbUser;
import com.taotao.service.OrderService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;

@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order/order-cart")
	public String getOrderCart(HttpServletRequest request){
		
		List<TbItem> list = findItemCart(request);
		request.setAttribute("cartList", list);
		return "order-cart";
		
	}
	//查询购物车
public List<TbItem> findItemCart(HttpServletRequest request){
		
		
		String json = CookieUtils.getCookieValue(request, "TT_CART", true);
		if(StringUtils.isNotBlank(json)){
			
			List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
			return list;
		}
		return new ArrayList();
	}
	@RequestMapping("/order/create")
	public String create(OrderInfo orderInfo ,HttpServletRequest request){
	
		TbUser user = (TbUser) request.getAttribute("user");
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		TaotaoResult create = orderService.create(orderInfo);
		request.setAttribute("orderId", create.getData().toString());
		request.setAttribute("payment", orderInfo.getPayment());
		DateTime date = new DateTime();
		DateTime time = date.plusDays(2);
		request.setAttribute("date", time.toString("yyyy-MM-dd"));
		
		return "success";
	
	
	}
	
}
