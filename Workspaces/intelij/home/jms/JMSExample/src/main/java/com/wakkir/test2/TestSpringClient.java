package com.wakkir.test2;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 11/11/12
 * Time: 20:09
 * To change this template use File | Settings | File Templates.
 */
public class TestSpringClient
{
    public static void main(String[] args)
    {
        try
        {
            //BrokerService broker = new BrokerService();
            //broker.addConnector("tcp://localhost:61616");
            //broker.setPersistent(false);
            //broker.start();

            //FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/spring-context.xml");
            //XmlBeanFactory context = new XmlBeanFactory(new ClassPathResource("spring-context.xml"));
            //ApplicationContext context= new ClassPathXmlApplicationContext("spring-context.xml");

            ConfigurableApplicationContext context =  new ClassPathXmlApplicationContext("spring-context.xml");
            SpringPublisher publisher = (SpringPublisher)context.getBean("stockPublisher");
            publisher.start();
            //MyAppContext app=new MyAppContext();
            //app.printBean();


        }
        catch (Exception e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
