package net.aconite.wrapper.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: Wakkir.Muzammil
 * Date: 08/10/13
 * Time: 14:09
 */
public class WrapperClientMain
{
    private static final Logger LOGGER = LoggerFactory.getLogger(WrapperClientMain.class);


    public void initClient(String[] commands) throws WrapperClientException
    {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-wrapper-client.xml");

        context.registerShutdownHook();

        IAconiteWrapperClient wrapperClient = (AconiteWrapperClient) context.getBean("wrapperClient");

        if(commands!=null && commands.length==1)
        {
            wrapperClient.sendCommand(commands[0]);
        }
        else
        {
            LOGGER.warn("stop or start argument must be presented.");
        }
    }

    public static void main(String [] args)
    {
        try
        {
           new WrapperClientMain().initClient(args);
        }
        catch(WrapperClientException ex)
        {
            LOGGER.error("An Error Occurred in wrapper client: ",ex);
        }
        catch(Exception ex)
        {
            LOGGER.error("Error!",ex);
        }
    }

}
