package com.taotao.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;

@Controller
public class CartController {
	@Autowired
	private ItemService itemService;
	@RequestMapping("/cart/add/{itemId}")
	public String cart(@PathVariable Long itemId,Integer num,HttpServletRequest request,HttpServletResponse response){
		
		List<TbItem> list = findItemCart(request);
		boolean flag=false;
		for (TbItem tbItem : list) {
			//判断购物车有没有该商品
			if(tbItem.getId().longValue() == itemId.longValue()){
				
				tbItem.setNum(tbItem.getNum()+num);	
				flag=true;
				break;
				
			}
		}
		
		if(!flag){
			TbItem findById = itemService.findById(itemId);
			findById.setNum(num);
			list.add(findById);
			CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), 172800, true);
			
		}
		return "cartSuccess";
	}
	
	
	public List<TbItem> findItemCart(HttpServletRequest request){
		
		String json = CookieUtils.getCookieValue(request, "TT_CART", true);
		if(StringUtils.isNotBlank(json)){
			
			List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
			return list;
		}
		return new ArrayList();
	}
	
	@RequestMapping("/cart/cart")
	public String show(HttpServletRequest request){
		List<TbItem> list = findItemCart(request);
		request.setAttribute("cartList", list);
		return "cart";
		
	}
	
	//修改购物车中的商品
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public TaotaoResult update(@PathVariable Long itemId,@PathVariable Integer num,HttpServletRequest request,HttpServletResponse response){
		
		List<TbItem> findItemCart = findItemCart(request);
		
		for (TbItem tbItem : findItemCart) {
			if(tbItem.getId().longValue() == itemId.longValue()){
				tbItem.setNum(num);
			}
			
		}
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(findItemCart), 172800, true);
		
		return TaotaoResult.ok();
		
	}
	
	//删除购物车中的商品
	@RequestMapping("/cart/delete/{itemId}")
	public String delItem(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response){
		
		List<TbItem> findItemCart = findItemCart(request);
		for (TbItem tbItem : findItemCart) {
			if(tbItem.getId().longValue() == itemId.longValue()){
				
				findItemCart.remove(tbItem);
				break;
				
			}
		}
		
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(findItemCart), 172800, true);
		
		return "redirect:/cart/cart.html";
		
	}
}
