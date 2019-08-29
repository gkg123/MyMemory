package com.taotao.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.service.SearchService;

public class MyMessageListener implements MessageListener{
	@Autowired
	private SearchService searchServcie;

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		
		try {
			String text = textMessage.getText();
			long itemId = Long.parseLong(text);
			
			searchServcie.saveByitemId(itemId);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
