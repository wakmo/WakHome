/*
 *
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCIKeyException.java %
 *  Created:	 Tue Oct 21 16:18:30 2003
 *  Created By:	 %created_by:  perkinsd %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  SCIKeyException.java~3:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *  1.1			9 Apr 2008	Dick Perkins	Updated to remove recursion in printStackTrace()
 *
 *
 *
 *
 */

package wak.work.cryptogram.graem.securecrypto.generickeyvalue;

import org.apache.log4j.Logger;


public class SCIKeyException extends Exception
{
    private static final Logger log = Logger.getLogger(SCIKeyException.class);


    private final Throwable causingException;

    public SCIKeyException(String message, Throwable causingException)
    {
        super(message);
        this.causingException = causingException;
    }

    public SCIKeyException(String message)
    {
        this(message, null);
    }

    public String getMessage()
    {
        return super.getMessage() + (hasCausingException() ? (" $ " + causingException.getMessage()) : "");
    }

    public void printStackTrace()
    {
        if (hasCausingException())
        {
            log.error("----> caused by:", causingException);
            causingException.printStackTrace();
        }
    }

    public final Throwable getCausingException()
    {
        return causingException;
    }

    public boolean hasCausingException()
    {
        return causingException != null;
    }
}
