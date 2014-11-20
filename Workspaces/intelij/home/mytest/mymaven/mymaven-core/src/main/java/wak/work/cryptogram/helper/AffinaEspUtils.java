/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.work.cryptogram.helper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author wakkir.muzammil
 */
public class AffinaEspUtils
{
    private static Logger logger = Logger.getLogger(AffinaEspUtils.class);

    public final static String STATUS_INITIAL = "INITIAL";
    public final static String STATUS_PENDING = "PENDING";
    public final static String STATUS_REQUESTED = "REQUESTED";
    public final static String STATUS_RESPONDED = "RESPONDED";
    public final static String STATUS_REQUEST_FAILED = "REQUEST_FAILED";
    public final static String STATUS_RESPONSE_FAILED = "RESPONSE_FAILED";
    public final static String STATUS_UPDATED = "UPDATED";
    public final static String STATUS_UPDATE_FAILED = "UPDATE_FAILED";

    public final static String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public final static String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public final static String DATE_FORMAT_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_FORMAT_YYYY_MM_DD_HHMMSSmmm = "yyyy-MM-dd HH:mm:ss.mmm";
    public final static String DATE_FORMAT_YYYY_MM_DD_HHMMSSmmmZ = "yyyy-MM-dd HH:mm:ss.mmmZ";
    public final static String DATE_FORMAT_YYYYMMDD_HHMMSS = "yyyy.MM.dd HH:mm:ss";
    //Atom (ISO 8601)
    public final static String DATE_FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.mmmZ";

    public final static String TIME_ZONE="GMT";

    public static String getYearInYYYY(Date date) throws AffinaEspUtilException
    {
        Calendar cal = Calendar.getInstance();
        //cal.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));

        if(date==null)
            throw new AffinaEspUtilException("Date cannot be null");

        cal.setTime(date);
        return String.valueOf(cal.get(Calendar.YEAR));
    }

    public static String getMonthInMM(Date date) throws AffinaEspUtilException
    {
        Calendar cal = Calendar.getInstance();
        //cal.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));

        if(date==null)
            throw new AffinaEspUtilException("Date cannot be null");

        cal.setTime(date);
        String monthString="0"+String.valueOf(cal.get(Calendar.MONTH)+1);
        String formattedMonth=monthString.substring(monthString.length()-2,monthString.length());

        return formattedMonth;
    }


    public static long getMonthendDateZeroTime(String year,String month) throws AffinaEspUtilException
    {
        Calendar cal = Calendar.getInstance();
        //cal.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        try {
            if(year==null || year.trim().length()!=4 || Integer.parseInt(year)<1)
                throw new AffinaEspUtilException("Invalid year found");
        }catch(NumberFormatException nfe) {
                AffinaEspUtilException a = new AffinaEspUtilException("Invalid year: "+ nfe.getMessage());
                throw a;
        }

        try {
            if(month==null || month.trim().length()!=2 || Integer.parseInt(month)<1 || Integer.parseInt(month)>12)
                throw new AffinaEspUtilException("Invalid month found");
        }catch(NumberFormatException nfe) {
                AffinaEspUtilException a = new AffinaEspUtilException("Invalid month: "+ nfe.getMessage());
                throw a;
        }

         //Beware, month start at 0, not 1
        cal.set(Integer.parseInt(year), Integer.parseInt(month)-1, 1);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);//Need to add this, otherwise it will work as 12 hours time than 24 hours time
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, -1);

        printDateTime(cal.getTimeInMillis());

        return cal.getTimeInMillis();
    }

    public static long getMonthEndDateTime(Date date) throws AffinaEspUtilException
    {
        Calendar cal = Calendar.getInstance();
        //cal.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));

       if(date==null)
       {
          throw new AffinaEspUtilException("Date cannot be null");
       }

        cal.setTime(date);
         //Beware, month start at 0, not 1
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);//Need to add this, otherwise it will work as 12 hours time than 24 hours time
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.MILLISECOND, -1);

        printDateTime(cal.getTimeInMillis());

        return cal.getTimeInMillis();
    }

    public static long getEndDateTime(Date date) throws AffinaEspUtilException
    {
        Calendar cal = Calendar.getInstance();
        //cal.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));

       if(date==null)
       {
          throw new AffinaEspUtilException("Date cannot be null");
       }

        cal.setTime(date);
         //Beware, month start at 0, not 1
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        cal.add(Calendar.DATE, 1);
        cal.add(Calendar.MILLISECOND, -1);

        printDateTime(cal.getTimeInMillis());

        return cal.getTimeInMillis();
    }

    public static long getEndDateTime(String stringDate,String format) throws AffinaEspUtilException, ParseException
    {
        return getEndDateTime(getDateFromString(stringDate,format));
    }

    public static long getStartDateTime(String stringDate,String format) throws AffinaEspUtilException, ParseException
    {
        return getDateFromString(stringDate,format).getTime();
    }

    public static Date getDateFromString(String stringDate,String format) throws AffinaEspUtilException, ParseException
    {
        if(stringDate==null || stringDate.length()==0)
       {
          throw new AffinaEspUtilException("Date cannot be null or empty");
       }
       if(format==null || format.length()==0)
       {
          throw new AffinaEspUtilException("Format cannot be null or empty");
       }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        return dateFormat.parse(stringDate);
    }

    //Affina represent expirydate as month end date with zero hours/minuts/second/millisecond
    //But Cardsetup send expiry date to SEM as beginning of next month's zero hours to millis
    //e.g AE : expDate in softcard 2016-09-30 00.00.00.000
    //e.g cardsetup to SEM : expdate in request 2016-09-31 23.59.59.999 (This is correct expiry date mostly)
    public static  long getCardSetupMonthendEndTime(long monthEndTimeInMilliSecond)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(monthEndTimeInMilliSecond);
        cal.add(Calendar.DATE, 1);
        cal.add(Calendar.MILLISECOND, -1);
        printDateTime(cal.getTimeInMillis());
        return cal.getTimeInMillis();
    }
    /**
     * This method Throws AffinaEspUtilException which is a runtime Exception.
     * @param monthEndTimeInMilliSecond
     * @return
     */
    public static long getMonthEndDateZeroTime(long monthEndTimeInMilliSecond)
        throws AffinaEspUtilException
    {
        Date date = new Date(monthEndTimeInMilliSecond);
        String YYYY = getYearInYYYY(date);
        String MM = getMonthInMM(date);
        long longDate = getMonthendDateZeroTime(YYYY, MM);

        return longDate;
    }

    public static  String generateSemTrackingId(String aeTrackingId,String appId,String psn,String appType,String appVersion)
    {
	//String semTrackId=aeTrackingId+"_"+psn+appType+appVersion+"_"+new Timestamp(Calendar.getInstance().getTimeInMillis()).getTime();
	String semTrackId=aeTrackingId+"-"+appId+"-"+psn+"-"+appType+"-"+appVersion;
	return semTrackId.replace(" ","").trim();
    }

    public static  String generateStageScriptTrackingId(String filterTrackingId,String appId,String psn,String appType,String appVersion,long scriptOrder)
    {
	String semTrackId=filterTrackingId+"-"+appId+"-"+psn+"-"+appType+"-"+appVersion+"-"+scriptOrder;
	return semTrackId.replace(" ","").trim();
    }

    public static String generateFilterTrackId(int applicationId,int businessFunctionId,long filterOrder)
    {
        String filterTrackId=applicationId+"_"+businessFunctionId+"_"+filterOrder;
        return filterTrackId.replace(" ","").trim();
    }

    //--------------------------------------------

    //=============================================================================

    public static String  getEmptyIfNegative(int value)
    {
        return value<0?"":value+"";
    }
    //=============================================================================
    public static String  getEmptyIfNegative(long value)
    {
        return value<0?"":value+"";
    }
    //=============================================================================

    public static String  getEmptyIfNull(BigDecimal value)
    {
        return value!=null?value.toPlainString():"";
    }
    //=============================================================================

    public static String getEmptyIfNullAndEscape(String value)
    {
         value = getEmptyIfNull(value);
         return null;//StringEscapeUtils.escapeXml(value);
    }

    public static String  getEmptyIfNull(String value)
    {
         return value!=null?value:"";
    }
    //=============================================================================

    public static boolean  isNullorEmpty(String value)
    {
        return value!=null?(value.length()>0?false:true):true;
    }
    //=============================================================================

    public static String  getDefaultIfNull(String value,String defValue)
    {
        return value!=null?value:defValue;
    }

    //=============================================================================

    public static String getApplicationError(Exception e,boolean isStackTrace)
    {
        if(isStackTrace)
        {
            StringWriter sWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(sWriter));
            return sWriter.getBuffer().toString();
        }
        else
            return e.getMessage();
    }

    //--------------------------------------------

    private static void printDateTime(long ltime)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_ISO8601);
        //dateFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        logger.debug("Month end date and end time :"+ dateFormat.format(new Date(ltime)).toUpperCase());
    }

}
