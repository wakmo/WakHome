package com.wakkir.test2;

import com.wakkir.test2.SpringPublisher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 22/11/12
 * Time: 22:07
 * To change this template use File | Settings | File Templates.
 */
public class MyAppContext implements ApplicationContextAware
{
    private ApplicationContext context;

    public void setApplicationContext(ApplicationContext appContext) throws BeansException
    {
        this.context=appContext;
    }

    void printBean()
    {
        SpringPublisher publisher = (SpringPublisher)context.getBean("stockPublisher");
        publisher.start();
    }
}
