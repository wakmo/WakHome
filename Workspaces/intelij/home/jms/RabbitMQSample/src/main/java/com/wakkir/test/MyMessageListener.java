package com.wakkir.test;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.UnsupportedEncodingException;

/**
 * Created by wakkir on 08/07/15.
 */
public class MyMessageListener //implements MessageListener
{


    public void listen(Message message)
    {
        try
        {
            System.out.println("Receive: "+message.getBody().toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
