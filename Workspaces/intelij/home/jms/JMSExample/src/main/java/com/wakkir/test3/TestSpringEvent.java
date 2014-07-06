package com.wakkir.test3;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 11/11/12
 * Time: 20:09
 * To change this template use File | Settings | File Templates.
 */
public class TestSpringEvent
{
    public static void main(String[] args)
    {
        try
        {
            ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-event.xml");

            // Let us raise a start event.
            context.start();

            HelloWorld obj = (HelloWorld) context.getBean("helloWorld");

            //obj.getMessage();

            System.out.println("obj.getMessage():"+obj.getMessage());

            // Let us raise a stop event.
            context.stop();
        }
        catch (Exception e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
