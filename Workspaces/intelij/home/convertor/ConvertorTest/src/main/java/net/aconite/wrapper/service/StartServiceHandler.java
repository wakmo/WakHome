package net.aconite.wrapper.service;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 22/11/12
 * Time: 22:35
 * To change this template use File | Settings | File Templates.
 */
public class StartServiceHandler implements ApplicationListener<ContextStartedEvent>
{

    public void onApplicationEvent(ContextStartedEvent event)
    {
        System.out.println("Application Service Handler Started.");

    }
}
