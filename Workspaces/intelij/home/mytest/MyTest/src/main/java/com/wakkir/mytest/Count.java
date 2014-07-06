package com.wakkir.mytest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 30/09/13
 * Time: 19:30
 * To change this template use File | Settings | File Templates.
 */
public class Count
{

    private final static SimpleDateFormat dateFormat_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");


    public static void main(String[] args)
    {
        Calendar cal=Calendar.getInstance();
        cal.set(2016,8,30,0,0,0);
        System.out.println("time1 : " + cal.getTimeInMillis());
        System.out.println("formatted Time1 : " + dateFormat_yyyyMMddHHmmss.format(cal.getTime()).toUpperCase());

        cal.setTimeInMillis(1475190000000l);
        System.out.println("time2 : " + cal.getTimeInMillis());
        System.out.println("formatted Time2 : " + dateFormat_yyyyMMddHHmmss.format(cal.getTime()).toUpperCase());

         /*
        System.out.println("xxxxxx");
        double basic = 0.0;
        double count = 0.0;
        double a = 7.64;
        double r = 8.0 / 1200.0;
        for (int i = 0; i < 35; i++)
        {
            basic = basic + a;
            count = count + a * Math.pow((1 + r), i);
            System.out.println("count" + i + ">" + count);
        }
        System.out.println("count>" + count);
        System.out.println("basic>" + basic);
        */
    }
}
