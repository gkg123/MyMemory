package cn.yunhe.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class ActiveDemo {
	@Test
	public void quene_producer(){
		
		ConnectionFactory connectionFactory = new  ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
		try {
			Connection createConnection = connectionFactory.createConnection();
			createConnection.start();
			Session session = createConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("queue_02");
			MessageProducer createProducer = session.createProducer(queue);
			TextMessage createTextMessage = session.createTextMessage("hello,world~");
			
			createProducer.send(createTextMessage);
			
			createProducer.close();
			session.close();
			createConnection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void quene_consumer(){
		
		ConnectionFactory connectionFactory = new  ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
		try {
			Connection createConnection = connectionFactory.createConnection();
			createConnection.start();
			Session session = createConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("queue_02");
			/*MessageProducer createProducer = session.createProducer(queue);
			TextMessage createTextMessage = session.createTextMessage("hello,world");
			
			createProducer.send(createTextMessage);*/
			MessageConsumer createConsumer = session.createConsumer(queue);
			
			createConsumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					
					TextMessage textMessage = (TextMessage) message;
					
					
					try {
						String text = textMessage.getText();
						System.out.println(text);
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			});
			
			createConsumer.close();
			session.close();
			createConnection.close();
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
