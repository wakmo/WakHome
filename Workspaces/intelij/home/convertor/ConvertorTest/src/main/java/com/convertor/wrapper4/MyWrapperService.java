package com.convertor.wrapper4;

/**
 * Created with IntelliJ IDEA.
 * User: Wakkir.Muzammil
 * Date: 08/10/13
 * Time: 12:16
 * To change this template use File | Settings | File Templates.
 */


import org.springframework.context.ConfigurableApplicationContext;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class MyWrapperService
{
    static ExecutorService exeSvr;
    ServerSocket serverSocket;
    int SOCKET_PORT = 6600;

    public MyWrapperService()
    {
        listen();
    }

    private void listen()
    {
        try
        {
            Future<ConfigurableApplicationContext> futureContext=null;

            serverSocket = new ServerSocket(SOCKET_PORT);


            while(true)
            {
                final Socket connectionSocket = serverSocket.accept();
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                String mycmd = inFromClient.readLine();
                String capitalizedSentence = mycmd.toUpperCase() + '\n';
                System.out.println("command received "+capitalizedSentence);


                if(mycmd.equalsIgnoreCase("start"))
                {
                    if(exeSvr==null)
                    {
                        exeSvr = Executors.newSingleThreadExecutor();
                        futureContext = exeSvr.submit(new WrapperTestTask());
                        futureContext.get().start();
                        System.out.println("Converter started successfully.");
                    }
                    else
                    {
                        System.out.println("Converter has already started.");
                    }
                }
                else if(mycmd.equalsIgnoreCase("stop"))
                {
                    System.out.println("Stopping converter...");
                    if(futureContext!=null && exeSvr!=null )
                    {
                        futureContext.get().stop();
                        exeSvr.shutdownNow();
                        exeSvr=null;
                        System.out.println("Converter stopped successfully.");
                    }
                    else
                    {
                        System.out.println("Converter is not running.");
                    }
                }
                else
                {
                    System.out.println(mycmd+" Command not found!");
                }

                outToClient.writeBytes(capitalizedSentence+" OK");


                try
                {
                    Thread.sleep(1000);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void main(String [] args)
    {
        try
        {
            new MyWrapperService();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


}



