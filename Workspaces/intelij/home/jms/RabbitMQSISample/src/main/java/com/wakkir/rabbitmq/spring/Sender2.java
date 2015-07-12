/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakkir.rabbitmq.spring;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author wakkir
 */
public class Sender2
{
   //@Autowired
    //private static AmqpTemplate amqpTemplate;

    public static void main(String[] argv) throws Exception
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context-sender.xml");
        AmqpTemplate amqpTemplate = (AmqpTemplate) context.getBean("amqpTemplate");

       //String message = "Hello World!";
        //------------------------------------
        MessageProperties properties = new MessageProperties();
        properties.setHeader("keyword", "SALES");
        //properties.setContentType("text/plain");
        String text = "1234567;Branch A;SALES;3000.50;Pending approval";
        Message message = new Message(text.getBytes(), properties);
        amqpTemplate.send(message);

         // Create a new MessageProperties
        // Assign custom header and content type
        properties = new MessageProperties();
        properties.setHeader("keyword", "INVENTORY");
        //properties.setContentType("text/plain");
        text = "1234568;Branch A;INVENTORY;Printer;30;10";
        message = new Message(text.getBytes(), properties);
         //amqpTemplate.send(message);

         // Create a new MessageProperties
        // Assign custom header and content type
        properties = new MessageProperties();
        properties.setHeader("keyword", "ORDER");
        //properties.setContentType("text/plain");
        text = "1234569;Branch B;ORDER;Keyboard;50";

        message = new Message(text.getBytes(), properties);
         //amqpTemplate.send(message);
        //-------------------------------------

        //amqpTemplate.convertAndSend(" [x] Sent '" + message + "'");
        System.out.println("message sent....");

        // sleep for 1 second
        try
        {
            Thread.sleep(10 * 1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        // close application context
        ((ClassPathXmlApplicationContext) context).close();

        System.out.println("Sender stopped...");
    }

}
