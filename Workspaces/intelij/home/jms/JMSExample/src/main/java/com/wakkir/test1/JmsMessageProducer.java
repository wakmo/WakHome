package com.wakkir.test1;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Component
public class JmsMessageProducer
{

	private static final Logger logger= LoggerFactory.getLogger(JmsMessageProducer.class);

	public static final String	MESSAGE_COUNT= "messageCount";

	@Autowired
	private JmsTemplate	template			= null;
	private int			messageCount	= 100;

	/** Generates JMS messages
	 * @throws NestedRuntimeException */
	@PostConstruct
	public void generateMessages() throws NestedRuntimeException, JMSException
	{
		for (int i = 0; i < messageCount; i++)
		{
			final int index = i;
			final String text = "Message number is " + i + ".";

			template.send(new MessageCreator()
			{
				public Message createMessage(Session session) throws JMSException
				{
					Message message = session.createTextMessage(text);
					message.setIntProperty(MESSAGE_COUNT, index);

					System.out.println("Sending message: " + text);

					return message;
				}
			});
		}
	}

}