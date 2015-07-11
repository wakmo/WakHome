/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wakkir.test;

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

        ApplicationContext context= new ClassPathXmlApplicationContext("spring-context-reciever.xml");
    }

}
