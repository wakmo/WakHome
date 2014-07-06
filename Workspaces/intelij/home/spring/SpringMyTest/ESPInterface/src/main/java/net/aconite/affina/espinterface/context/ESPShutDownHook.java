package net.aconite.affina.espinterface.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * User: wakkir.muzammil
 * Date: 27/11/13
 * Time: 12:44
 */
public class ESPShutDownHook extends Thread
{
    private static final Logger logger = LoggerFactory.getLogger(ESPShutDownHook.class);

    private final ConfigurableApplicationContext espContext;

    public ESPShutDownHook(ConfigurableApplicationContext context)
    {
        this.espContext = context;
    }

    public void run()
    {
        logger.debug("shutting down...");
        if (espContext != null)
        {
            espContext.stop();
            logger.debug("wrapper context shutdown");
        }
    }

}
