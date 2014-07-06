package net.aconite.wrapper.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;

/**
 * User: Wakkir.Muzammil
 * Date: 08/10/13
 * Time: 14:09
 */
public class WrapperServerMain
{
    private final static String pidFileName="awsc";
    private final static String pidFileExtension=".pid";

    private static final Logger LOGGER = LoggerFactory.getLogger(WrapperServerMain.class);


    private String getJavaPID()
    {
        String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
        int p = nameOfRunningVM.indexOf('@');
        String pid = nameOfRunningVM.substring(0, p);
        LOGGER.debug("PID of Running java process : " + pid);
        return pid;
    }

    private void writeToFile(File file,String payload) throws WrapperServerException
    {
        FileWriter fileWritter = null;
        BufferedWriter bufferWritter=null;
        try
        {
            fileWritter = new FileWriter(file.getAbsoluteFile(), true);
            bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(payload);
        }
        catch (IOException ex)
        {
            throw new WrapperServerException("Error while writing to a file",ex);
        }
        finally
        {
            try
            {
                if(bufferWritter!=null)
                {
                    bufferWritter.close();
                }
                if(fileWritter!=null)
                {
                    fileWritter.close();
                }
            }
            catch (IOException ex)
            {
                LOGGER.error("Error while closing PID file",ex);
            }
        }
    }

    private void createTempPIDFile() throws WrapperServerException, IOException
    {
        String tempFileDestination=System.getProperty("mq-converter-log-dir");

        if(tempFileDestination==null || tempFileDestination.length()==0)
        {
           throw new WrapperServerException("Error! VM variable 'mq-converter-log-dir' value is null or empty...");
        }

        File tempFileDir = new File(tempFileDestination);
        // if the directories do not exist, create them
        if (!tempFileDir.exists())
        {
            if (tempFileDir.mkdirs())
            {
                LOGGER.debug("TempFile DIR created : " + tempFileDestination);
            }
        }

        File temp = File.createTempFile(pidFileName, pidFileExtension,tempFileDir);
        //noinspection ResultOfMethodCallIgnored
        temp.createNewFile();
        temp.deleteOnExit();
        LOGGER.debug("PID file: " + temp.getAbsolutePath());

        writeToFile(temp,getJavaPID());
    }

    public void initServer() throws WrapperServerException, IOException
    {
        ConfigurableApplicationContext wrapperContext = new ClassPathXmlApplicationContext("spring-wrapper-services.xml");

        wrapperContext.registerShutdownHook();

        ShutDownHook hook=new ShutDownHook(wrapperContext);

        Runtime.getRuntime().addShutdownHook(hook);

        createTempPIDFile();

        IAconiteWrapperService  wrapperService = (AconiteWrapperService) wrapperContext.getBean("wrapperService");

        wrapperService.startServer();
    }


    public static void main(String [] args)
    {
        //noinspection finally
        try
        {
            new WrapperServerMain().initServer();
        }
        catch(WrapperServerException ex)
        {
            LOGGER.error("An Error Occurred in wrapper server: ",ex);
        }
        catch(Exception ex)
        {
            LOGGER.error("Error!", ex);
        }
        finally
        {
            System.exit(0);
        }
    }


    private static class ShutDownHook extends Thread
    {
        private final ConfigurableApplicationContext wrapperContext;

        public  ShutDownHook(ConfigurableApplicationContext wcontext)
        {
            this.wrapperContext=wcontext;
        }

        public void run()
        {
            LOGGER.debug( "shutting down...");
            if(wrapperContext!=null)
            {
                wrapperContext.stop();
                LOGGER.debug(  "wrapper context shutdown");
            }
        }

    }

}
