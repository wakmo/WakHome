/*
 *

 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   MultosVerifyAlgorithm.java %
 *  Created:	 08 February 2002 14:26:27
 *  Created By:	 %created_by:  aingerr %
 *  Last modified:	 %date_modified:  15 February 2002 11:15:52 %
 *  CI Idenitifier:	 %full_filespec:  MultosVerifyAlgorithm.java~5:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */
package wak.work.cryptogram.graem.securecrypto;

/**
  * Represents the multos verification algorithms that may be used within SCI.
  *
  * @author         %derived_by: aingerr %
  *
  *
  * @version 		%version:  5 %
  * @since
  * @see
  *
  */
import java.io.Serializable;

public class MultosVerifyAlgorithm implements Serializable {
  private String[] algDetails = new String[] {"MULTOS3.4",
                                              "MULTOS4"};

  private int index;

  /**
   * MULTOS3.4 verification algorithm.
   */
  public static final MultosVerifyAlgorithm MULTOS3_4 = new MultosVerifyAlgorithm(0);
  /**
   * MULTOS4 verification algorithm.
   */
  public static final MultosVerifyAlgorithm MULTOS4   = new MultosVerifyAlgorithm(1);

  private MultosVerifyAlgorithm(int index){
    this.index = index;
  }

  public String toString() {
    return algDetails[index];
  }

  public static MultosVerifyAlgorithm getMultosVerifyAlgorithm(String str) {
    MultosVerifyAlgorithm mva = null;

    if (str.equals("MULTOS3.4")) {
      mva = MULTOS3_4;
    }
    else if (str.equals("MULTOS4")) {
      mva = MULTOS4;
    }

    return mva;
  }
}