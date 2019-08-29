package com.taotao.order.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.pojo.TbUser;
import com.taotao.service.UserCheckService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.TaotaoResult;

public class LoginInterceptor implements HandlerInterceptor{
	@Autowired
	private UserCheckService userCheckService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		
		//用户没有登录则跳转到登录界面
		String token = CookieUtils.getCookieValue(request, "COOKIE_NAME");
		if(StringUtils.isBlank(token)){
			
			String url = request.getRequestURL().toString();
			response.sendRedirect("http://localhost:8099/page/login?redicturl="+url);
			return false;
			
		}
		
		//用户身份过期跳转到登录界面
		TaotaoResult findByToken3 = userCheckService.findByToken(token);
		TaotaoResult findByToken2 = findByToken3;
		TaotaoResult findByToken = findByToken2;
		if(findByToken.getStatus() !=200){
			String url = request.getRequestURL().toString();
			response.sendRedirect("http://localhost:8099/page/login?redicturl="+url);
			return false;
			
		}
		
		TbUser user = (TbUser) findByToken3.getData();
		request.setAttribute("user", user);
		
		//登录过 放行
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
