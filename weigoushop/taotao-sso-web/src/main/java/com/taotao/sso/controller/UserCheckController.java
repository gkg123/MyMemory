package com.taotao.sso.controller;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbUser;
import com.taotao.service.UserCheckService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;

@Controller
public class UserCheckController {
	@Autowired
	private UserCheckService userCheckService;
	@RequestMapping(value="/user/check/{param}/{type}",method={RequestMethod.GET})
	@ResponseBody
	public TaotaoResult userCheck(@PathVariable String param, @PathVariable int type){
		TaotaoResult result = userCheckService.check(param, type);
		return result;
		
	}
	//注册
	@RequestMapping(value="/user/register",method={RequestMethod.POST})
	@ResponseBody
	public TaotaoResult register(TbUser user){
		TaotaoResult result = userCheckService.register(user);
		
		return result;
		
	}
	
	//登录
	@RequestMapping(value="/user/login",method={RequestMethod.POST})
	@ResponseBody
	public TaotaoResult login(String username,String password,HttpServletRequest request,HttpServletResponse response){
		
		TaotaoResult login = userCheckService.login(username, password);
		if(login.getData()!= null){
			String token = login.getData().toString();
			CookieUtils.setCookie(request, response, "COOKIE_NAME", token);
			return login;
		}
		return login;
	}
	
	//根据token查询用户信息
	@RequestMapping(value="/user/token/{token}",method={RequestMethod.GET})
	@ResponseBody
	public String findByToken(@PathVariable String token,String callback){
		
		TaotaoResult findByToken = userCheckService.findByToken(token);
		
		if(StringUtils.isNotBlank(callback)){
			
//			MappingJacksonValue mjv = new MappingJacksonValue(findByToken);
//			mjv.setJsonpFunction(callback);
//			return mjv;
			
			String call = callback+"("+JsonUtils.objectToJson(findByToken)+");";
			return call;
		}
		return JsonUtils.objectToJson(findByToken);
		
	}
}
