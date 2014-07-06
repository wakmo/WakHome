package com.convertor.wrapper;

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
            //System.out.println("arg0:"+args[0]);
            ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-converter-wrapper.xml");

            System.out.println("isRunning1:"+ context.isRunning());
            // Let us raise a start event.
            context.start();
            System.out.println("isRunning2:"+ context.isRunning());

            HelloWorld obj = (HelloWorld) context.getBean("helloWorld");

            //obj.getMessage();

            System.out.println("obj.getMessage():"+obj.getMessage());

            try
            {
                System.out.println("running");
                Thread.sleep(10000);
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }

            // Let us raise a stop event.
            //if("STOP".equalsIgnoreCase(args[0]))


            context.stop();
            System.out.println("isRunning3:"+ context.isRunning());
        }
        catch (Exception e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
