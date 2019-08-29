package com.taotao.service;

import com.taotao.pojo.TbUser;
import com.taotao.utils.TaotaoResult;

public interface UserCheckService {
	
	public TaotaoResult check(String param,int type);
	
	//注册
	public TaotaoResult register(TbUser user);
	
	//登录
	public TaotaoResult login(String username,String password);
	//根据token查询用户信息
	public TaotaoResult findByToken(String token);

}
