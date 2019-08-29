package com.taotao.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.redis.JedisClient;
import com.taotao.service.UserCheckService;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;
@Service
public class UserCheckServiceImpl implements UserCheckService{
	@Autowired
	private TbUserMapper tbUserMapper;
	@Autowired
	private JedisClient jedisCilent;
	
	

	@Override
	public TaotaoResult check(String param, int type) {
		
		TbUserExample example = new TbUserExample();
		Criteria createCriteria = example.createCriteria();
		
		if(type == 1){
			
			createCriteria.andUsernameEqualTo(param);
		}
		else if(type == 2){
			createCriteria.andPhoneEqualTo(param);
			
		}
		else if(type == 3){
			
			createCriteria.andEmailEqualTo(param);
			
		}
		else{
			return TaotaoResult.build(400, "输入类型错误");
			
		}
		
		List<TbUser> list = tbUserMapper.selectByExample(example);
		
		if(list !=null && list.size()>0){
			
			return TaotaoResult.ok(false);
		}else{
			return TaotaoResult.ok(true);
			
		}
		
	}


	//注册
	@Override
	public TaotaoResult register(TbUser user) {
		
		//用户名
		if(StringUtils.isNotBlank(user.getUsername())){
			TaotaoResult check = check(user.getUsername(),1);
			
			if(!(boolean) check.getData()){
				return TaotaoResult.build(400, "用户名被占用");
			}
			
		}
		//密码
		if(StringUtils.isBlank(user.getPassword())){

				return TaotaoResult.build(400, "密码不能为空");

		}
		//电话
		if(StringUtils.isNotBlank(user.getPhone())){
			TaotaoResult check = check(user.getPhone(),2);
			
			if(!(boolean) check.getData()){
				return TaotaoResult.build(400, "该手机号已经注册过了~");
			}
			
		}
		//邮箱
		if(StringUtils.isNotBlank(user.getEmail())){
			TaotaoResult check = check(user.getEmail(),3);
			
			if(!(boolean) check.getData()){
				return TaotaoResult.build(400, "该邮箱已经注册过了~");
			}
			
		}
		
		Date data = new Date();
		user.setUpdated(data);
		user.setCreated(data);
		//加密处理
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5DigestAsHex);
		
		tbUserMapper.insert(user);
		
		return TaotaoResult.ok(user);
	}

	
	//登录
	@Override
	public TaotaoResult login(String username, String password) {
		
		TbUserExample example = new TbUserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUsernameEqualTo(username);
		List<TbUser> list = tbUserMapper.selectByExample(example);
		
		if(list == null || list.size() == 0){
			
			return TaotaoResult.build(400, "用户名或密码错误");
			
		}
		
		TbUser tbUser = list.get(0);
		
		if(!(DigestUtils.md5DigestAsHex(password.getBytes())).equals(tbUser.getPassword())){
			
			return TaotaoResult.build(400, "用户名或密码错误");
		}
		
		String token = UUID.randomUUID().toString();
		jedisCilent.set("USER_TOKEN:"+token, JsonUtils.objectToJson(tbUser));
		//设置失效时间
		jedisCilent.expire("USER_TOKEN"+token, 1800);
		
		return TaotaoResult.ok(token);
		

	}


	@Override
	public TaotaoResult findByToken(String token) {
		
		String json = jedisCilent.get("USER_TOKEN:"+token);
		
		if(StringUtils.isBlank(json)){
			
			return TaotaoResult.build(400, "信息过期，请重新登录");
		}
		
		TbUser jsonToPojo = JsonUtils.jsonToPojo(json, TbUser.class);
		//重新设置有效时间
		jedisCilent.expire("USER_TOKEN"+token, 1800);
		
		return TaotaoResult.ok(jsonToPojo);
	}
	

}
