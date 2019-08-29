package com.yunhe.demo;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-mq.xml")
public class Demo {
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Queue queue;
	@Test
	public void demo(){
		jmsTemplate.send(queue,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage createTextMessage = session.createTextMessage("hello,activemq");
				return createTextMessage;
			}
		});
	}
}
