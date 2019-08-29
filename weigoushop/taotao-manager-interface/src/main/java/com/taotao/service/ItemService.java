package com.taotao.service;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.utils.EasyUIResult;
import com.taotao.utils.TaotaoResult;



public interface ItemService {
	//查询
	public EasyUIResult<TbItem> findAllByPage(Integer page,Integer rows);
	//添加
	public TaotaoResult save(TbItem tbItem,String desc);
	//删除
	public TaotaoResult delete(Long id);
	//根据id查询
	public TbItem findById(Long itemId);
	//根据id查询商品内容
	public TbItemDesc findByItemId(Long itemId);
	
	
}
