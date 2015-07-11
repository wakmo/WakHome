/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wakkir.test;


import org.springframework.amqp.core.AmqpTemplate;
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
       ApplicationContext context= new ClassPathXmlApplicationContext("spring-context-sender.xml");
       AmqpTemplate amqpTemplate=(AmqpTemplate)context.getBean("amqpTemplate");

       String message = "Hello World!";


        amqpTemplate.convertAndSend(" [x] Sent '" + message + "'");

        System.out.println("message sent....");
  } 
    
}
