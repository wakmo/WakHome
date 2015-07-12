/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakkir.rabbitmq.spring;

import java.util.ArrayList;

/**
 *
 * @author wakkir
 */
public class AMQPMessageHandler
{
    //protected Logger logger = Logger.getLogger("integration");

    public ArrayList<String> handleMessage(byte[] data)
    {
        System.out.println("AMQPMessageHandler::handleMessage: " + new String(data));

        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++)
        {
            arrayList.add(i + ":" + new String(data));
        }

        return arrayList;
    }

}
