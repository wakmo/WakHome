/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author wakkir.muzammil
 */
public class EspWebContextListener implements ServletContextListener
{
    private static final Logger logger = LoggerFactory.getLogger(EspWebContextListener.class);

    public void contextInitialized(ServletContextEvent sce)
    {
        logger.info("ESP Interface web context started....");
    }

    public void contextDestroyed(ServletContextEvent sce)
    {
        logger.info("ESP Interface web context stopped....");
    }

}
