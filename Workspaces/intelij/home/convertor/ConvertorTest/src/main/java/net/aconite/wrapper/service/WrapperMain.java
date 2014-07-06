package net.aconite.wrapper.service;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: Wakkir.Muzammil
 * Date: 08/10/13
 * Time: 14:09
 * To change this template use File | Settings | File Templates.
 */
public class WrapperMain
{

    public static void main(String [] args)
    {
        try
        {
            ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-wrapper-services.xml");

            AconiteWrapperService wrapperService = (AconiteWrapperService) context.getBean("WrapperService");

            wrapperService.startServer();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
