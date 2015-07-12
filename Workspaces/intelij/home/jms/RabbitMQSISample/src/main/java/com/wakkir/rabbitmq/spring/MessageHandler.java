/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakkir.rabbitmq.spring;

import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.support.MessageBuilder;

/**
 *
 * @author wakkir
 */
public class MessageHandler
{
    //protected Logger logger = Logger.getLogger("integration");

    public Message handleMessage(Message message)
    {
        //logger.debug("Received: " );
        String data = (String) (message.getPayload());
        System.out.println("MessageHandler::handleMessage......:" + data);

        //return generateMessage(message.getMessageProperties(),("out-"+data).getBytes());
        return generateMessage(message.getHeaders(), ("out-" + data));
    }

    private org.springframework.integration.Message generateMessage(MessageHeaders headers, String sourceData)
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
