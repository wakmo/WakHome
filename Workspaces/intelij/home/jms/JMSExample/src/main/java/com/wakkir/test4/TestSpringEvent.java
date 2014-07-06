package com.wakkir.test4;

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
            ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-converter-event.xml");


            System.out.println("isRunning1:"+ context.isRunning());
            // Let us raise a start event.
            context.start();
            System.out.println("isRunning2:"+ context.isRunning());

            HelloWorld obj = (HelloWorld) context.getBean("helloWorld");

            //obj.getMessage();

            System.out.println("obj.getMessage():"+obj.getMessage());

            // Let us raise a stop event.
            context.stop();
            System.out.println("isRunning3:"+ context.isRunning());
        }
        catch (Exception e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
