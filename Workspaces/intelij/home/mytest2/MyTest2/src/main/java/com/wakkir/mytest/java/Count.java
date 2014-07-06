package com.wakkir.mytest.java;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 30/09/13
 * Time: 19:30
 * To change this template use File | Settings | File Templates.
 */
public class Count
{

    public static void main(String[] args)
    {
        System.out.println("xxxxxx");
        double basic=0.0;
        double count=0.0;
        double a=7.64;
        double r=8.0/1200.0;
        for(int i=0;i<35;i++)
        {
            basic=basic+a;
            count=count+a*Math.pow((1+r),i);
            System.out.println("count"+i+">"+count);
        }
        System.out.println("count>"+count);
        System.out.println("basic>"+basic);
    }
}
