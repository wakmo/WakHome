/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.utils;

import net.aconite.affina.espinterface.constants.EspConstant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author wakkir.muzammil
 */
public class ESPUtils
{
    public static String getFormattedCurrentDate()
    {
        return getFormattedDate(new Date(), EspConstant.DATE_FORMAT_YYYY_MM_DD_HHMMSSmmm);
    }

    public static String getFormattedDate(Date date, String format)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date).toUpperCase();
    }

    public static String getDefaultPayloadHeader()
    {
        return "\n" + getFormattedCurrentDate() + " | ";
    }

    public static long getMonthendEndTime(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1); //Beware, month start at 0, not 1
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
        cal.add(Calendar.SECOND, -1);

        return cal.getTimeInMillis();
    }

}
