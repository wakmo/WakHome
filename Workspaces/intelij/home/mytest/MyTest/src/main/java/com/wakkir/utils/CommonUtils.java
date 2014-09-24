package com.wakkir.utils;

/**
 * User: wakkir
 * Date: 26/12/13
 * Time: 21:33
 */
public class CommonUtils
{
    public static void printInfo(int index,String oldFileName, String newFileName)
    {
        System.out.println("{"+getFormattedIndex(index)+"OldFileName :" + oldFileName+", NewFileName :" + newFileName+"}");
    }

    public static String getFormattedIndex(int index)
    {
        try
        {            
            return "Id:"+getFormattedIndexWithLength(index,4)+", ";
        }
        catch(Exception ex)
        {
            return "";
        }
    }
    
    public static String getFormattedIndexWithLength(int index,int length)
    {
        try
        {
            String fixedString="000000"+index;
            return fixedString.substring(fixedString.length()-length,fixedString.length());
        }
        catch(Exception ex)
        {
            return "";
        }
    }
}
