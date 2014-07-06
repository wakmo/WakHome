package com.wakkir.spring.demo;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: wakkir
 * Date: 07/03/14
 * Time: 23:33
 */
public class RunWorkflow
{

    public static void main(String args[])
    {
        ConfigurableApplicationContext espContext = new ClassPathXmlApplicationContext("workflow-config.xml");

        //StandardWorkflow ss=(StandardWorkflow)espContext.getBean("standardWorkflow");

        //HashMap<String, Object> myMap = new HashMap<String, Object>();
        //myMap.put("myInteger",new Integer(100));
        //myMap.put("myString",new String("Helloooooo"));

        //ss.processWorkflow("action1_action",myMap);

    }
}
