/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakkir.rabbitmq.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author wakkir
 */
public class Reciever2
{

    public static void main(String[] argv) throws Exception
    {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context-reciever.xml");

        System.out.println("Reciever: start to listen....");
        // sleep for 1 second
        try
        {
            Thread.sleep(1 * 60 * 1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        // close application context
        ((ClassPathXmlApplicationContext) context).close();
        System.out.println("Reciever: stopped listening....");
    }

}
