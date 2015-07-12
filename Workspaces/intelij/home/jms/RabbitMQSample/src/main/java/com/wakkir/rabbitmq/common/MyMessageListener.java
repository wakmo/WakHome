package com.wakkir.rabbitmq.common;

/**
 * Created by wakkir on 08/07/15.
 */
public class MyMessageListener //implements MessageListener
{

    public void listen(Object msg)
    {
        try
        {
            System.out.println("Receive1: " + (String) msg);
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
