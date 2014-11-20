/*
 *
 *  � 2002 Datacard Platform Seven Limited.  All rights reserved.
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   DataCollectionException.java %
 *  Created:	 Wed Apr 23 15:28:33 2003
 *  Created By:	 %created_by:  brownm %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  DataCollectionException.java~1:java:UKPMA#3 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */

package wak.work.cryptogram.helper;

/**
 * Thrown by a data source when a problem occurrs during data gathering.
 *
 * @author brownm
 */
public class DataCollectionException extends Exception
{
  private int errorCode=0; 
    
  public DataCollectionException(String problem)
  {
    super(problem);
  }

  public DataCollectionException(String problem, Exception e)
  {
    super(problem, e);
  }
  
  public DataCollectionException(String problem,int errorCode)
  {
    super(problem);
    this.errorCode=errorCode;
  }
  
  public DataCollectionException(String problem,int errorCode,Exception e)
  {
    super(problem,e);
    this.errorCode=errorCode;
  }

    public int getErrorCode() 
    {
        return errorCode;
    }

    public void setErrorCode(int errorCode) 
    {
        this.errorCode = errorCode;
    }
  
  
}
