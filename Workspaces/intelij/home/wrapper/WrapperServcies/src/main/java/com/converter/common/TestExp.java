package com.converter.common;

import java.lang.management.ManagementFactory;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir.muzammil
 * Date: 18/10/13
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 */
public class TestExp
{
    public void exp() throws TestException
    {

        try
        {
            System.out.println("inside try1");
            //int a=1/0;

            System.out.println("inside try2");
        }
        catch(Exception ex)
        {
            System.out.println("inside catch");
            throw new TestException("sdfsdfsdf",ex);
        }
        finally
        {
            System.out.println("inside finally");
        }
    }

    public void getPid()
    {
        String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
        int p = nameOfRunningVM.indexOf('@');
        String pid = nameOfRunningVM.substring(0, p);
        System.out.println("Java app PID: " + pid);
    }

    public static void main(String[] args)
    {
        TestExp t=new TestExp();
        System.out.println("starting");

        t.getPid();

        try
        {
            t.exp();

        }
        catch (TestException e)
        {
            System.out.println("TestException "+e.getMessage());
        }
        System.out.println("ended");

    }
}
