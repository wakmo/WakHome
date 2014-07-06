package com.convertor.wrapper;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 22/11/12
 * Time: 22:35
 * To change this template use File | Settings | File Templates.
 */
public class StopEventHandler implements ApplicationListener<ContextStoppedEvent>
{

    public void onApplicationEvent(ContextStoppedEvent event)
    {
        System.out.println("ContextStoppedEvent Received");
    }
}
