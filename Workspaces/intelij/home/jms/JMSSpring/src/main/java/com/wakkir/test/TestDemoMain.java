package com.wakkir.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
public class TestDemoMain 
{
 
  public static void main(String[] args) 
  {
    // create Spring context
    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
    
    // get bean from context
    JmsMessageSender jmsMessageSender = (JmsMessageSender)ctx.getBean("jmsMessageSender");
         
    // send to default destination 
    jmsMessageSender.send("hello JMS",false);
    try 
    {
      Thread.sleep(1000);
    } 
    catch (InterruptedException e) 
    {
      e.printStackTrace();
    }
         
    // send to a code specified destination
    //Queue queue = new ActiveMQQueue("AnotherDest");
    //jmsMessageSender.send(queue, "hello Another Message");
    
    try 
    {
      Thread.sleep(1000);
    } 
    catch (InterruptedException e) 
    {
      e.printStackTrace();
    }
    // send to a code specified destination
    //Queue queue1 = new ActiveMQQueue("Send2RecieveQueue");
    //Queue reply1 = new ActiveMQQueue("Send2ReplyQueue");
    //jmsMessageSender.send(queue1, "hello Another Message",reply1);
         
    // sleep for 1 second
    try 
    {
      Thread.sleep(10000);
    } 
    catch (InterruptedException e) 
    {
      e.printStackTrace();
    }
   
    // close application context
    //((ClassPathXmlApplicationContext)ctx).close();
  }
}
