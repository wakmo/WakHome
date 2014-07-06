package com.convertor.wrapper2;

import org.springframework.context.ConfigurableApplicationContext;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * User: Wakkir.Muzammil
 * Date: 08/10/13
 * Time: 10:46
 * To change this template use File | Settings | File Templates.
 */
public class TestCMDWrapper
{
    private static ExecutorService exeSvr;// = Executors.newSingleThreadExecutor();

    public TestCMDWrapper()
    {

    }

    public static void main(String [] args)
    {
        try
        {
            Future<ConfigurableApplicationContext> context=null;
            InputStream is = new DataInputStream(System.in);
            byte[] cmd = new byte[1024];
            while(is.read(cmd) != -1)
            {
                String mycmd = new String(cmd).trim();
                if(mycmd.equalsIgnoreCase("stop"))
                {
                    System.out.println("Stopping..."+String.valueOf(mycmd));
                    if(context!=null)
                    {
                        context.get().stop();
                    }
                    if(exeSvr!=null)
                    {
                        exeSvr.shutdownNow();
                        exeSvr=null;
                    }
                }
                else if(mycmd.equalsIgnoreCase("start"))
                {
                    if(exeSvr==null)
                    {
                        exeSvr = Executors.newSingleThreadExecutor();
                        context = exeSvr.submit(new WrapperTask());
                        context.get().start();
                    }
                }
                else
                {
                    System.out.println(String.valueOf(mycmd));
                }

                cmd = new byte[1024];
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
