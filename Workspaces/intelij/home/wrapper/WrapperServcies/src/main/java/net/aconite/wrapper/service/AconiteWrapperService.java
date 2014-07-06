package net.aconite.wrapper.service;

/**
 * User: Wakkir.Muzammil
 * Date: 08/10/13
 * Time: 12:16
 */


import com.converter.common.WrapperTestTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class AconiteWrapperService  implements  IAconiteWrapperService
{
    private static final ExecutorService exeSvr=Executors.newCachedThreadPool();

    private final int serverSocketPort;
    private final int serverPollRate;

    private static final Logger LOGGER = LoggerFactory.getLogger(AconiteWrapperService.class);


    private volatile  Future<ConfigurableApplicationContext> futureContext=null;


    AconiteWrapperService(final int port, final int pollRate)
    {
        this.serverSocketPort=port;
        this.serverPollRate=pollRate;
        try
        {
            start();
        }
        catch (WrapperServerException ex)
        {
            LOGGER.error("Error!", ex);
        }
    }

    private String start()  throws WrapperServerException
    {
        String response;
        try
        {
            if(futureContext!=null && futureContext.get()!=null && futureContext.get().isRunning())
            {
                LOGGER.info("Converter has already started.");
                response="Converter has already started.\n";
            }
            else
            {
                futureContext = exeSvr.submit(new WrapperTestTask());
                //futureContext = exeSvr.submit(new WrapperConverterTask());
                futureContext.get().start();
                LOGGER.info("Converter started successfully.");
                response="Converter started successfully.\n";
            }
        }
        catch (InterruptedException ex)
        {
            throw new WrapperServerException("Interrupted error while starting converter",ex);
        }
        catch (ExecutionException ex)
        {
            throw new WrapperServerException("Execution error while starting converter",ex);
        }
        return response;
    }


    private boolean stop() throws WrapperServerException
    {
        boolean isStopped=false;
        try
        {
            if(futureContext!=null && futureContext.get()!=null && futureContext.get().isRunning() && exeSvr!=null)
            {
                futureContext.get().stop();
                exeSvr.shutdownNow();
                //exeSvr=null;
                isStopped=true;

                LOGGER.info("Converter stopped successfully.");
            }
            else
            {
                LOGGER.info("Converter is not running.");
            }
        }
        catch (InterruptedException ex)
        {
            throw new WrapperServerException("Interrupted error while stopping converter",ex);
        }
        catch (ExecutionException ex)
        {
            throw new WrapperServerException("Execution error while stopping converter",ex);
        }
        return isStopped;
    }

    @SuppressWarnings("ConstantConditions")
    public void startServer() throws WrapperServerException
    {
        ServerSocket serverSocket=null;
        Socket connectionSocket=null;
        BufferedReader inFromClient=null;
        DataOutputStream outToClient=null;
        boolean isRunning=true;
        try
        {
            serverSocket = new ServerSocket(serverSocketPort);
            InetAddress address = InetAddress.getLocalHost();
            LOGGER.info("Wrapper services started on host: " + address.getHostAddress() + "  port: " + serverSocketPort);
            while(isRunning)
            {
                StringBuilder sb=new StringBuilder();

                connectionSocket = serverSocket.accept();
                inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                String commandReceived = inFromClient.readLine();
                LOGGER.info("command received :" + commandReceived);

                if(commandReceived.equalsIgnoreCase("start"))
                {
                    sb.append(start());
                }
                else
                {
                    if (commandReceived.equalsIgnoreCase("stop"))
                    {
                        LOGGER.info("Stopping converter...");

                        boolean isStopped = stop();
                        if (isStopped)
                        {
                            sb.append("Converter stopped successfully.").append("\n");

                            outToClient.writeBytes(sb.toString());
                            isRunning = !isStopped;
                            return;
                        }
                        else
                        {
                            sb.append("Converter is not running.").append("\n");
                        }
                    }
                    else
                    {
                        LOGGER.info("Invalid command " + commandReceived);
                        sb.append("Invalid command ").append(commandReceived).append("\n");
                    }
                }
                outToClient.writeBytes(sb.toString());
                try
                {
                    Thread.sleep(serverPollRate);
                }
                catch (Exception ignored)
                {
                }
            }
        }
        catch (IOException ex)
        {
            LOGGER.error("IOError! while starting the wrapper service ", ex);
            throw new WrapperServerException("IOError! while starting the wrapper service ", ex);
        }
        catch (Exception ex)
        {
            LOGGER.error("Error! while starting the wrapper service ", ex);
            throw new WrapperServerException("Error occurred in wrapper service ", ex);
        }
        finally
        {
            try
            {
                if(isRunning)
                {
                    try
                    {
                        stop();
                    }
                    catch (WrapperServerException ex)
                    {
                        LOGGER.error("Error while converter stopping! ", ex);
                    }
                }
                if(outToClient!=null)
                {
                    outToClient.close();
                }
                if(inFromClient!=null)
                {
                    inFromClient.close();
                }
                if(connectionSocket!=null)
                {
                    connectionSocket.close();
                }
                if(serverSocket!=null)
                {
                    serverSocket.close();
                }
                System.exit(0);
            }
            catch (IOException ex)
            {
                LOGGER.error("Error while connection closing! ", ex);
            }
        }
    }


}



