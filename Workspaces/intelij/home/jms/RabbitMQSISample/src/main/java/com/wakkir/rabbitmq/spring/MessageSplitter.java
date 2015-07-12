/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakkir.rabbitmq.spring;

import java.util.ArrayList;
import java.util.List;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;

import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.integration.support.MessageBuilder;

/**
 *
 * @author wakkir
 */
public class MessageSplitter extends AbstractMessageSplitter
{

    @Override
    protected List<?> splitMessage(Message<?> message)
    {
        ArrayList<?> messages = (ArrayList<?>) message.getPayload();

        System.out.println("MessageSplitter::Total messages: " + messages.size());
        for (Object mess : messages)
        {
            System.out.println("MessageSplitter::" + mess.toString());
        }
        return messages;
    }
    /*
     //protected Logger logger = Logger.getLogger("integration");
     @Splitter
     public List<Message> split(Message message)
     {
     String data=new String((byte[])message.getPayload());
                
     //logger.debug("Received: " );
     System.out.println("MessageSplitter......:"+data);
		
                
     List<Message> outMessages = new ArrayList<>();
     for (int i = 0; i < 5; i++) 
     {
     //outMessages.add(generateMessage(message.getMessageProperties(),(i+":"+data).getBytes()));
     outMessages.add(generateMessage(message.getHeaders(),(i+":"+data)));
     }
     return outMessages;

		
		
     }*/

    private Message generateMessage(MessageHeaders headers, String sourceData)
    {

        return MessageBuilder.withPayload(sourceData)
                .copyHeaders(headers)
                .setHeader("HEADER_TYPE", "MYTEST")
                //.setHeader(EspConstant.MQ_MESSAGE_TYPE, EspConstant.CARD_SETUP_REQUEST)
                .build();
    }
    /*    
     private Message generateMessage(MessageProperties props, byte[] sourceData)
     {
        
     return MessageBuilder
     .withBody(sourceData)
     .copyHeaders(props.getHeaders())
     .copyProperties(props)                
     .setHeader("HEADER_TYPE", "MYTEST")
     //.setHeader(EspConstant.MQ_MESSAGE_TYPE, EspConstant.CARD_SETUP_REQUEST)
     .build();
     }
     */
}
