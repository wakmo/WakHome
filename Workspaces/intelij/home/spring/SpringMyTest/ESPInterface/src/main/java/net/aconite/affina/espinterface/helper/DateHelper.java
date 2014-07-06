/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.helper;

import java.text.*;
import java.util.*;

/**
 *
 * @author thushara.pethiyagoda
 */
public class DateHelper
{

    /***/
    private static final String DATE_FORMAT_ddMMyy_HHmm = "ddMMyy_HHmm";
    /***/
    private static final String UPDATE_DATE_FORMAT_ddMMyy_HHmmss = "ddMMyy_HHmmss";
    /***/
    private static final String UPDATE_DATE_FORMAT_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    /***/
    private static String DEFAULT_LONG_DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    /***/
    private static String US_LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /***/
    public static int DEFAULT_START_DATE_TIME = 1;
    /***/
    public static int DEFAULT_END_DATE_TIME = 2;
    
    /**
     * 
     * @param format
     * @param date
     * @return 
     */
    public static String formatDate(String format, Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String formattedDate = sdf.format(date);
        try
        {
            sdf.parse(formattedDate);
        }
        catch (Exception ex)
        {
            return null;
        }
        
        return formattedDate;
    }

    /**
     * 
     * @return 
     */
    public static String getDateFormattedAsddMMyy_HHmm()
    {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String formattedDate = formatDate(DATE_FORMAT_ddMMyy_HHmm, date);        
        return formattedDate;
    }

    /**
     * 
     * @return 
     */
    public static String getDateFormattedAsddMMyy_HHmmss()
    {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String formattedDate = formatDate(UPDATE_DATE_FORMAT_ddMMyy_HHmmss, date);  
        return formattedDate;
    }
    /**
     * 
     * @return 
     */
    public static String getDateFormattedAsyyyy_MM_dd_HH_mm_ss()
    {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String formattedDate = formatDate(UPDATE_DATE_FORMAT_yyyy_MM_dd_HH_mm_ss, date);         
        return formattedDate;
    }    
    
    
    /** 
     * 
     * @param defaultDateTimeType
     * @return java.sql.Timestamp
     */
    public static java.sql.Timestamp getTimestampUSFormat(long date)
    {        
        java.sql.Timestamp timeStamp = null;        
        try
        {            
            Date d = convertToDate(date);            
            String startdatetime = formatDate(US_LONG_DATE_FORMAT, d);
            timeStamp = java.sql.Timestamp.valueOf(startdatetime);            
        }
        catch (Exception ex)
        {
            return timeStamp;
        }        
        return timeStamp;
    }
    /**
     * 
     * @param date
     * @return 
     */
    public static java.sql.Timestamp getTimestampUSFormat(Date date)
    {
        return getTimestampUSFormat(date.getTime());
    }
    /**
     * 
     * @param date
     * @return 
     */
    public static java.sql.Timestamp getTimestampDefaultFormat(Date date)
    {
        return getTimestampDefaultFormat(date.getTime());
    }
    /**
     * 
     * @param date
     * @return 
     */
    public static java.sql.Timestamp getTimestampDefaultFormat(long date)
    {        
        java.sql.Timestamp timeStamp = null;        
        try
        {            
            Date d = convertToDate(date);            
            String formattedDate = formatDate(DEFAULT_LONG_DATE_FORMAT, d);            
            timeStamp = java.sql.Timestamp.valueOf(formattedDate);
            
        }
        catch (Exception ex)
        {
            return timeStamp;
        }        
        return timeStamp;
    }
    /**
     * 
     * @return 
     */
    public static java.sql.Timestamp today()
    {
        Date d = new Date();
        return getTimestampUSFormat(d);
    }
    /**
     * Converts a long value to Date.
     * Expects a long value of a proper date as this method does not do any validation.
     * @param longDate long representation of a vlid Date.
     * @return Date object
     */
    public static Date convertToDate(long longDate)
    {
        Date d = new Date(longDate);
        
        return d;
    }
    /**
     * 
     * @param date
     * @return 
     */
    public static boolean isDate(long date)
    {
        Date dat;
        try
        {
            dat = convertToDate(date);
        }
        catch (Exception ex)
        {
            return false;
        }
        String d = formatDate(DEFAULT_LONG_DATE_FORMAT, dat);
        return d != null && d.trim().length() > 0;
    }
    /**
     * The must be a parse-able Long type string representation.  
     * @param date
     * @return 
     */
    public static long fromStringTolong(String date)
    {
        return Long.parseLong(date);
    }
    /**
     * 
     * @param date
     * @return 
     */
    public static java.sql.Timestamp fromLongStringToTimeStamp(String date)
    {
        long longDate = fromStringTolong(date);        
        return getTimestampUSFormat(longDate);
    }
}
