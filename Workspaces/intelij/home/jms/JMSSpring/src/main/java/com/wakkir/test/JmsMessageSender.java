/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakkir.test;

import java.util.Date;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class JmsMessageSender
{

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination defaultReplyDestination;

    /**
     * send text to default destination
     *
     * @param text
     */
    public void send(final String text, final boolean doReply)
    {

        this.jmsTemplate.send(new MessageCreator()
        {
            @Override
            public Message createMessage(Session session) throws JMSException
            {
                Message message = session.createTextMessage(text);
                message.setJMSCorrelationID(String.valueOf((new Date()).getTime()));
                if (doReply)
                {
                    message.setJMSReplyTo(defaultReplyDestination);
                }
                System.out.println("Sending : " + text);
                return message;
            }
        });
    }

    /**
     * Simplify the send by using convertAndSend
     *
     * @param text
     */
    public void sendText(final String text)
    {
        this.jmsTemplate.convertAndSend(text);
    }

    /**
     * Send text message to a specified destination
     *
     * @param text
     */
    public void send(final Destination dest, final String text)
    {

        this.jmsTemplate.send(dest, new MessageCreator()
        {
            @Override
            public Message createMessage(Session session) throws JMSException
            {
                Message message = session.createTextMessage(text);
                message.setJMSCorrelationID(String.valueOf((new Date()).getTime()));
                message.setJMSReplyTo(defaultReplyDestination);
                return message;
            }
        });
    }

    /**
     * Send text message to a specified destination
     *
     * @param text
     */
    public void send(final Destination dest, final String text, final Destination reply)
    {

        this.jmsTemplate.send(dest, new MessageCreator()
        {
            @Override
            public Message createMessage(Session session) throws JMSException
            {
                Message message = session.createTextMessage(text);
                message.setJMSCorrelationID(String.valueOf((new Date()).getTime()));
                message.setJMSReplyTo(reply);
                return message;
            }
        });
    }
}
