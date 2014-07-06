package net.aconite.affina.espinterface.context;

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
    private static final Logger logger = LoggerFactory.getLogger(StartServiceHandler.class);

    public void onApplicationEvent(ContextStartedEvent event)
    {
        logger.info("Application Service Handler Started.");

    }
}
