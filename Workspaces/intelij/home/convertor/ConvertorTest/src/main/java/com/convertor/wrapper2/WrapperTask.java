package com.convertor.wrapper2;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: Wakkir.Muzammil
 * Date: 08/10/13
 * Time: 10:48
 * To change this template use File | Settings | File Templates.
 */
public class WrapperTask implements Callable<ConfigurableApplicationContext>
{
        public ConfigurableApplicationContext call() throws Exception
        {
            System.out.println("Running");
            ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("Spring-Convertor-Quartz.xml");


            return context;
        }
}
