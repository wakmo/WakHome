package com.wakkir.test;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Service;

@Service
/**
 * Listener Implement Spring SessionAwareMessageListener Interface
 *
 */
public class JmsMessageListener implements SessionAwareMessageListener<TextMessage>
{

    @Override
    public void onMessage(TextMessage message, Session session) throws JMSException
    {
        // This is the received message
        System.out.println("Receive: " + message.getText());
        System.out.println("CorrelationID: " + message.getJMSCorrelationID());

        if (true)
        {
            System.out.println("Error raised at consumer Queue");
            throw new JMSException("Error at consumer");
        }

        // Let's prepare a reply message - a "ACK" String
        ActiveMQTextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("ACK : " + message.getText());
        textMessage.setCorrelationId(message.getJMSCorrelationID());
        System.out.println("Acknoledging : " + textMessage.getText());

    // Message send back to the replyTo address of the income message.
        // Like replying an email somehow. 
        MessageProducer producer = session.createProducer(message.getJMSReplyTo());
        producer.send(textMessage);
    }

}
