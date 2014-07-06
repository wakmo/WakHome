/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.helper;

/**
 *
 * @author thushara.pethiyagoda
 */
public class DataUtil
{
    /**
     * 
     * @param value
     * @return 
     */
    public static boolean isNull(Object value)
    {
        return value == null;
    }
    /**
     * Checks whether string is empty if it is not null.
     * <p/>
     * @param value
     * <p/>
     * @return
     */
    public static boolean isEmpty(String value)
    {
        return (isNull(value)) || value.trim().length() == 0;
    }

    /**
     *
     * @param value String representation of a long value.
     * <p/>
     * @return returns true if the value is a date.
     */
    public static boolean isDate(String value)
    {
        long date;
        try
        {
            date = Long.parseLong(value);
        }
        catch (Exception ex)
        {
            return false;
        }
        return DateHelper.isDate(date);
    }  
}
