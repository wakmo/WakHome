package net.aconite.wrapper.service;

/**
 * Created with IntelliJ IDEA.
 * User: Wakkir.Muzammil
 * Date: 08/10/13
 * Time: 12:16
 * To change this template use File | Settings | File Templates.
 */



import org.springframework.context.ConfigurableApplicationContext;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;


public class AconiteWrapperService
{
    private static ExecutorService exeSvr;
    private ServerSocket serverSocket;

    private int serverSocketPort;
    private int serverPollRate;

    public AconiteWrapperService()
    {

    }


    public void startServer()
    {
        try
        {
            Future<ConfigurableApplicationContext> futureContext=null;

            serverSocket = new ServerSocket(getServerSocketPort());

            InetAddress address = InetAddress.getLocalHost();

            System.out.println("Wrapper services started url ="+address.getHostAddress()+":"+getServerSocketPort());

            while(true)
            {
                StringBuffer sb=new StringBuffer();
                final Socket connectionSocket = serverSocket.accept();
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                String mycmd = inFromClient.readLine();
                String capitalizedSentence = mycmd.toUpperCase();
                System.out.println("command received :"+capitalizedSentence);
                //sb.append("command received :"+capitalizedSentence+"\n");


                if(mycmd.equalsIgnoreCase("start"))
                {
                    if(exeSvr==null)
                    {
                        exeSvr = Executors.newSingleThreadExecutor();
                        futureContext = exeSvr.submit(new WrapperTestTask());
                        futureContext.get().start();
                        System.out.println("Converter started successfully.");
                        sb.append("Converter started successfully.\n");
                    }
                    else
                    {
                        System.out.println("Converter has already started.");
                        sb.append("Converter has already started.\n");
                    }
                }
                else if(mycmd.equalsIgnoreCase("stop"))
                {
                    System.out.println("Stopping converter...");
                    sb.append("Stopping converter...\n");
                    if(futureContext!=null && exeSvr!=null )
                    {
                        futureContext.get().stop();
                        exeSvr.shutdownNow();
                        exeSvr=null;
                        System.out.println("Converter stopped successfully.");
                        sb.append("Converter stopped successfully.\n");
                    }
                    else
                    {
                        System.out.println("Converter is not running.");
                        sb.append("Converter is not running.\n");
                    }
                }
                else
                {
                    System.out.println("Invalid command "+mycmd);
                    sb.append("Invalid command "+mycmd+"\n");
                }

                outToClient.writeBytes(sb.toString());


                try
                {
                    Thread.sleep(getServerPollRate());
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


    public int getServerPollRate()
    {
        return serverPollRate;
    }

    public void setServerPollRate(int serverPollRate)
    {
        this.serverPollRate = serverPollRate;
    }

    public int getServerSocketPort()
    {
        return serverSocketPort;
    }

    public void setServerSocketPort(int serverSocketPort)
    {
        this.serverSocketPort = serverSocketPort;
    }
}



