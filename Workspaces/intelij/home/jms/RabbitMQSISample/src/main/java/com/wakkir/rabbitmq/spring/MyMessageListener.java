package com.wakkir.rabbitmq.spring;

import org.springframework.amqp.core.Message;

;

/**
 * Created by wakkir on 08/07/15.
 */
public class MyMessageListener //implements MessageListener
{

    public void listen(byte[] message)
    {
        try
        {
            String data = new String(message);
            System.out.println("listen......:" + data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    /*
     @Override
     public void onMessage(Message msg)     
     {
     try
     {
     System.out.println("Receive2: "+(msg.getBody()).toString());
     }
     catch (Exception e)
     {
     e.printStackTrace();
     }

     }*/

}
