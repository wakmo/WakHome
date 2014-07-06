package net.aconite.affina.espinterface.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * User: wakkir
 * Date: 22/11/12
 * Time: 22:35
 */
public class StopServiceHandler implements ApplicationListener<ContextStoppedEvent>
{
    private static final Logger logger = LoggerFactory.getLogger(StopServiceHandler.class);

    public void onApplicationEvent(ContextStoppedEvent event)
    {
        logger.info("Application Service Handler Stopped.");
    }
}
