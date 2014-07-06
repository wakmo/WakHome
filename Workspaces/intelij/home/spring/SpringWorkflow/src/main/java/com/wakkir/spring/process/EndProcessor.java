package com.wakkir.spring.process;

import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * User: wakkir
 * Date: 08/03/14
 * Time: 03:01
 */
public class EndProcessor
{

    @ServiceActivator
    public void process(Message inMessage)
    {
        // At info level, the data recorded shall be limited to the message type
        //   and its identifier (tracking reference or service instance).
        // At debug level, the complete message shall be recorded.

        MessageHeaders inHeaders = inMessage.getHeaders();
        Object inPayload = inMessage.getPayload();

        System.out.println("==============================================");
        System.out.println("EndProcessor processing..."+(String)inPayload);



    }
}
