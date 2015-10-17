/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakkir.photo;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author wakkir
 */
public class RunDemo
{
   //@Autowired
    //private static AmqpTemplate amqpTemplate;

    public static void main(String[] argv) throws Exception
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context-photo.xml");
        RenameMediaFileNames renameMediaFileNames = (RenameMediaFileNames) context.getBean("photo");

        renameMediaFileNames.doRename();

        System.out.println("file renamed....");

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

        System.out.println("Spring stopped...");
    }

}
