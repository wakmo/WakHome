package com.mkyong.chunk;

import com.mkyong.User;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.annotation.ServiceActivator;

import java.util.Collection;
import java.util.Iterator;

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
        System.out.println("EndProcessor processing started...");

        Collection col=  (Collection)inPayload;
        System.out.println("Size : "+col.size());
        Iterator it=col.iterator() ;
        while(it.hasNext())
        {
           User user=(User)it.next();
            System.out.println(user.toString());
        }

        System.out.println("EndProcessor processing ended...");

    }
}
