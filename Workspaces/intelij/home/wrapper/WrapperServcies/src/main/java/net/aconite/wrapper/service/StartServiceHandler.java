package net.aconite.wrapper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

/**
 * User: wakkir
 * Date: 22/11/12
 * Time: 22:35
 */
public class StartServiceHandler implements ApplicationListener<ContextStartedEvent>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(WrapperServerMain.class);

    public void onApplicationEvent(ContextStartedEvent event)
    {
        LOGGER.info("Application Service Handler Started.");

    }
}
