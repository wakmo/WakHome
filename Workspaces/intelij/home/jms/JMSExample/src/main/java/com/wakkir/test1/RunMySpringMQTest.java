package com.wakkir.test1;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jms.core.JmsTemplate;

public class RunMySpringMQTest
{

	public static void main(String[] args)
	{
		 //ApplicationContext ctx = new FileSystemXmlApplicationContext("JmsMessageListenerTest-context.xml");
		ClassPathResource res = new ClassPathResource("JmsMessageListenerTest-context.xml");
		XmlBeanFactory ctx = new XmlBeanFactory(res);

		JmsTemplate template = (JmsTemplate) ctx.getBean("jmsProducerTemplate");
		/*
		template.send(new MessageCreator()
		{
			public Message createMessage(Session session) throws JMSException
			{
				MapMessage message = session.createMapMessage();
				message.setStringProperty("name", "Craig");
				return message;
			}
		});*/
	}

}
