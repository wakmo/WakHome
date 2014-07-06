package net.aconite.wrapper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.Callable;

/**
 * User: Wakkir.Muzammil
 * Date: 08/10/13
 * Time: 10:48
 */

class WrapperConverterTask implements Callable<ConfigurableApplicationContext>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(WrapperServerMain.class);

    public ConfigurableApplicationContext call() throws Exception
    {
        LOGGER.info("Executing wrapper task.");

        AbstractApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/application-context.xml");

        context.registerShutdownHook();

        LOGGER.info("Wrapper task is Running.");

        return context;
    }
}
