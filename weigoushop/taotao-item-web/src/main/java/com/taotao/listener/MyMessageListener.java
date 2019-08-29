package com.taotao.listener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class MyMessageListener implements MessageListener{
	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		
		try {
			//获取id
			String text = textMessage.getText();
			long itemId = Long.parseLong(text);
			
			//查询
			TbItem item = itemService.findById(itemId);
			TbItemDesc tbItemDesc = itemService.findByItemId(itemId);
			
			Map map = new HashMap();
			map.put("item", item);
			map.put("itemDesc", tbItemDesc);
			//获得模板
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate("item.html");
				
			Writer out = new OutputStreamWriter(new FileOutputStream(new File("D:/temp/"+itemId+".html")),"utf-8");
			template.process(map, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
