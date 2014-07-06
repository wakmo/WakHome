package com.converter.common;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.Callable;

/**
 * User: Wakkir.Muzammil
 * Date: 08/10/13
 * Time: 10:48
 */
@SuppressWarnings("DefaultFileTemplate")
public class WrapperTestTask implements Callable<ConfigurableApplicationContext>
{
        public ConfigurableApplicationContext call() throws Exception
        {
            System.out.println("Executing wrapper task.");

            AbstractApplicationContext context = new ClassPathXmlApplicationContext("Spring-Convertor-Quartz.xml");

            context.registerShutdownHook();

            System.out.println("Wrapper task is Running.");

            return context;
        }
}
