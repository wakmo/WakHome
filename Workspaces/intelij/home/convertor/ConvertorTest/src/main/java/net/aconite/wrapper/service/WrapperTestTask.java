package net.aconite.wrapper.service;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: Wakkir.Muzammil
 * Date: 08/10/13
 * Time: 10:48
 * To change this template use File | Settings | File Templates.
 */
public class WrapperTestTask implements Callable<ConfigurableApplicationContext>
{
        public ConfigurableApplicationContext call() throws Exception
        {
            System.out.println("Execute wrapper task.");
            ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("Spring-Convertor-Quartz.xml");
            System.out.println("Wrapper task is Running.");

            return context;
        }
}
